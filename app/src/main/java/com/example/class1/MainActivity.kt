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
    fun getStore() =  getPreferences(Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null){
            counter = savedInstanceState.getLong(COUNTER_KEY, 0)
            textCounter.text = counter.toString()
        }

        myButton.setOnClickListener{
            counter++
            textCounter.text = counter.toString()

            /*myImage.rotate90()

            myButton.text = when (counter){
                1 -> "stop"
                in 2 .. 9 -> myButton.text.toString().plus( "!")
                else -> myButton.text
            }*/
        }
    }

    override fun onPause() {
        super.onPause()
        getStore().edit().putLong(COUNTER_KEY, counter).apply()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putLong(COUNTER_KEY, counter)
        }

        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val COUNTER_KEY = "counterKey"
    }


}

