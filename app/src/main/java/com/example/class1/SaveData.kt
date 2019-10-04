package com.example.class1

import android.content.Context
import android.content.SharedPreferences

class SaveData (context: Context) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("file", Context.MODE_PRIVATE)

    // will remember the state of the night mode ON/OFF
    fun setDarkModeState(state:Boolean?){
        val editor = sharedPreferences.edit()
        editor.putBoolean("Dark", state!!)
        editor.apply()
    }

    // will load the night mode state
    fun loadDarkModeState(): Boolean? {
        val state = sharedPreferences.getBoolean("Dark", false)
        return (state)
    }

}