package com.example.class1

import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.example.class1.util.rotate90
import com.example.class1.util.toggleVisibility
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var counter: Long = 0
    var username: Any? = ""
    fun getStore() =  getPreferences(Context.MODE_PRIVATE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = intent.extras?.get("username").toString()


        // check to see if username is in shared preferences
        // if not then add to shared preferences
        // save counter to username
        // can check shared preferences file
        // View > Tool Windows > Device File Explorer > data > data > com.example.class1 > shared_prefs

        if (savedInstanceState != null){
            updateCounter(savedInstanceState.getLong(username.toString(), 0))
        }
        else if (getStore().contains(username.toString())){
            updateCounter(getStore().getLong(username.toString(), 0))
        }

        // Old
        /*
        if (savedInstanceState != null) {
            updateCounter(savedInstanceState.getLong(COUNTER_KEY, 0))
        }
        else if (getStore().contains(COUNTER_KEY)) {
            updateCounter(getStore().getLong(COUNTER_KEY, 0))
        }*/
        /*if (savedInstanceState != null){
            counter = savedInstanceState.getLong(COUNTER_KEY, 0)
            textCounter.text = counter.toString()
        }*/

        myButton.setOnClickListener{
            counter++
            updateCounter(counter)

            /*myImage.rotate90()

            myButton.text = when (counter){
                1 -> "stop"
                in 2 .. 9 -> myButton.text.toString().plus( "!")
                else -> myButton.text
            }*/
        }
    }

    // Attempt to update/change username later
    /*
    private fun updateUsername(name: String){
        username = name
    }*/

    private fun updateCounter(count: Long){
        counter = count
        textCounter.text = counter.toString()
    }

    override fun onPause() {
        super.onPause()
        getStore().edit().putLong(username.toString(), counter).apply()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.run {
            putLong(username.toString(), counter)
        }

        super.onSaveInstanceState(outState)
    }

    /* I don;t think I need this anymore
    companion object {
        private const val COUNTER_KEY = "counterKey"
    }*/


}

