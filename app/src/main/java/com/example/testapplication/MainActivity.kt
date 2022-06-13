package com.example.testapplication

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.testapplication.database.TableNote
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.internetAPI.InternetManager
import com.example.testapplication.internetAPI.InternetManager.isOnline
import com.example.testapplication.sharedPreferencesHelper.DataStorage.getFirstSate
import com.example.testapplication.viewModel.ViewModelManage
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.cancel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var callbackNetwork: ConnectivityManager.NetworkCallback
    private val viewModelManage by viewModels<ViewModelManage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        MAIN_CONTEXT = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.progressHorizontalBar.max = 100

        makingRequest()

        val navHostFragment = binding.contentMain.navHostFragment.getFragment() as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        // create listener on change layouts in nav fragmentContainer
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val listFragment = resources.getString(R.string.first_fragment_label)
            val editFragment = resources.getString(R.string.second_fragment_label)

            when (destination.label) {
                listFragment -> {
                    binding.fab.visibility = View.VISIBLE
                }

                editFragment -> {
                    binding.fab.visibility = View.GONE
                }
            }
        }


        binding.fab.setOnClickListener {

            viewModelManage.addData(
                TableNote(
                    title = resources.getString(R.string.newNote),
                    description = "",
                    date = Date()
                )
            )
        }

    }

    private fun makingRequest() {
        if (getFirstSate()) {
            if (!isOnline(this)) {
                Snackbar.make(binding.mainL, R.string.notConnection, Snackbar.LENGTH_SHORT)
                    .show()
            }

            val cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

            val timer = object : CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.progressHorizontalBar.progress += 20
                }

                override fun onFinish() {
                    binding.progressHorizontalBar.visibility = View.GONE
                    cm.unregisterNetworkCallback(callbackNetwork)
                }

            }

            callbackNetwork = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    runOnUiThread {
                        binding.progressHorizontalBar.visibility = View.VISIBLE
                    }

                    timer.start()

                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.progressHorizontalBar.visibility = View.GONE
                        binding.progressHorizontalBar.progress = 0
                        Snackbar.make(binding.mainL, R.string.notConnection, Snackbar.LENGTH_SHORT)
                            .show()

                    }

                    timer.cancel()
                }
            }

            cm.registerDefaultNetworkCallback(callbackNetwork)
        }

    }

    companion object {
        var MAIN_CONTEXT: MainActivity? = null
    }

}