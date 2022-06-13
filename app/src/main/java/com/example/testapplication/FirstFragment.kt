package com.example.testapplication

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.database.TableNote
import com.example.testapplication.databinding.FragmentFirstBinding
import com.example.testapplication.internetAPI.InternetManager
import com.example.testapplication.recyclerView.AdapterRecycler
import com.example.testapplication.sharedPreferencesHelper.DataStorage
import com.example.testapplication.viewModel.ViewModelManage
import com.google.android.material.snackbar.Snackbar


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private val viewModelManage by activityViewModels<ViewModelManage>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var callbackNetwork: ConnectivityManager.NetworkCallback
    private val adapterRV by lazy {
        AdapterRecycler(listOf(), ::goToEdit)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = FragmentFirstBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.adapter = adapterRV

        makingRequest {
            viewModelManage.liveDataNotes.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    binding.textState.run {
                        text = requireActivity().resources.getString(R.string.noData)
                        visibility = View.VISIBLE
                    }
                } else {
                    binding.textState.visibility = View.GONE
                    adapterRV.setList(it)
                }

            }
        }



        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {

                    val itemTable = adapterRV.dataList[viewHolder.adapterPosition]
                    viewModelManage.deleteData(itemTable)

                }
            }).attachToRecyclerView(recyclerView)

    }

    private fun goToEdit(item: TableNote) {

        findNavController(view!!).navigate(
            R.id.action_FirstFragment_to_secondFragment,
            bundleOf(
                "note" to item
            )
        )

    }

    private fun makingRequest(displayData: ()->Unit) {
        if (!DataStorage.getFirstSate()) {
            if (!InternetManager.isOnline(requireContext())) {
               binding.textState.run {
                   text = requireActivity().resources.getString(R.string.notConnection)
                   visibility = View.VISIBLE
               }

            }

            val cm = requireActivity().getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager

            val timer = object : CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    binding.progressCircularBar.visibility = View.GONE
                    binding.textState.visibility = View.GONE
                    DataStorage.setFirstState(true)
                    displayData()
                    cm.unregisterNetworkCallback(callbackNetwork)
                }

            }

            callbackNetwork = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    requireActivity().runOnUiThread {
                        binding.progressCircularBar.visibility = View.VISIBLE
                        binding.textState.visibility = View.GONE
                    }

                    timer.start()

                }

                override fun onLost(network: Network) {
                    requireActivity().runOnUiThread {
                        binding.progressCircularBar.visibility = View.GONE
                        binding.textState.run {
                            text = requireActivity().resources.getString(R.string.notConnection)
                            visibility = View.VISIBLE
                        }

                    }

                    timer.cancel()
                }
            }

            cm.registerDefaultNetworkCallback(callbackNetwork)
        } else {
            displayData()
        }

    }

}