package com.example.testapplication.sharedPreferencesHelper

import android.content.Context
import com.example.testapplication.MainActivity.Companion.MAIN_CONTEXT

object DataStorage {

    private const val firstStart = "first_start_key"

    private val sharedWeather by lazy {
        MAIN_CONTEXT?.getSharedPreferences(firstStart, Context.MODE_PRIVATE)
    }


    // when app first start = false, when second+ start = true
    fun getFirstSate(): Boolean = sharedWeather?.getBoolean(firstStart, false) ?: false
    fun setFirstState(state: Boolean = true) = sharedWeather?.edit()?.putBoolean(firstStart, state)?.apply()

}