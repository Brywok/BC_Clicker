package com.example.class1

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import me.ibrahimsn.library.LiveSharedPreferences


class CountRepository(context: Context) {
    // initializing SharedPreferences and LiveSharedPreferences into variables
    private val preferences:SharedPreferences
    private val liveSharedPreferences: LiveSharedPreferences

    init{
        // create a shared preferences file called "PREFS"
        preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        // wrapping the preferences file we just created in Live version
        liveSharedPreferences = LiveSharedPreferences(preferences)
    }

    // Stores the user count into PREFS
    fun setUserCount(name: String, count: Long){
        preferences.edit().putLong(name, count).apply()
    }

    // retrieve the count for the user from PREFS
    fun getUserCount(name: String): LiveData<Long> =
        Transformations.map(liveSharedPreferences.listenMultiple(listOf(name), 0L)) { it[name] }

    companion object {
        // sets the name of the xml file that the PREFS data is saved to
        private const val PREFS = "clickouts"
    }

}