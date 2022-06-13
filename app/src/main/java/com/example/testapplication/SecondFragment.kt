package com.example.testapplication

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.testapplication.database.TableNote
import com.example.testapplication.databinding.FragmentSecondBinding
import com.example.testapplication.viewModel.ViewModelManage
import java.util.*


class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private val viewModelManage by activityViewModels<ViewModelManage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSecondBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments?.getParcelable<TableNote>("note")
        binding.editTitle.setText(arg?.title)
        binding.editDescription.setText(arg?.description)

    }

    private fun saveDate(oldObject: TableNote) {



        val editTitle = binding.editTitle.text.toString()
        val editDescription = binding.editDescription.text.toString()

        val writeDate =
            if (oldObject.title == editTitle && oldObject.description == editDescription) {
                oldObject.date
            } else Date()

        viewModelManage.updateData(
            TableNote(
                oldObject.uid,
                editTitle,
                editDescription,
                writeDate
            )
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.save_button_option) {
            saveDate(arguments?.getParcelable("note")!!)
            true
        } else super.onOptionsItemSelected(item)
    }

}