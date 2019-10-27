package com.example.class1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.class1.CountRepository

class CountViewModel(application: Application) : AndroidViewModel(application) {
    // declaring CountRepository
    private val repository = CountRepository(application.applicationContext)

    // Uses the method in CountRepository to retrieve the user count
    fun getUserCount(name: String) = repository.getUserCount(name)

    // Uses the method in CountRepository to set the user count
    fun setUserCount(name: String, count: Long) = repository.setUserCount(name, count)
}