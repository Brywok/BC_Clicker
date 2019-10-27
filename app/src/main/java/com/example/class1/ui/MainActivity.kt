package com.example.class1.ui

//import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.class1.R
import com.example.class1.viewmodel.CountViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/*
class MainActivity : AppCompatActivity() {

    private var counter: Long = 0
    var username: Any? = ""
    private var nightModeSwitch: Switch? = null
    private lateinit var saveData: SaveData
    fun getStore() =  getPreferences(Context.MODE_PRIVATE)


    override fun onCreate(savedInstanceState: Bundle?) {

        saveData = SaveData(this)
        if (saveData.loadDarkModeState() == true) {
            setTheme(R.style.darkTheme)
        } else
            setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = intent.extras?.get("username").toString()


        // check to see if username is in shared preferences
        // if not then add to shared preferences
        // save counter to username
        // can check shared preferences file
        // View > Tool Windows > Device File Explorer > data > data > com.example.class1 > shared_prefs

        if (savedInstanceState != null) {
            updateCounter(savedInstanceState.getLong(username.toString(), 0))
        } else if (getStore().contains(username.toString())) {
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

        myButton.setOnClickListener {
            counter++
            updateCounter(counter)

            /*myImage.rotate90()

            myButton.text = when (counter){
                1 -> "stop"
                in 2 .. 9 -> myButton.text.toString().plus( "!")
                else -> myButton.text
            }*/
        }

        //darkModeSwitch.
        nightModeSwitch = findViewById<View>(R.id.nightModeSwitch) as Switch?
        if (saveData.loadDarkModeState() == true) {
            nightModeSwitch!!.isChecked = true
        }

        //
        nightModeSwitch!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveData.setDarkModeState(true)
                restartApplication()
            } else {
                saveData.setDarkModeState(false)
                restartApplication()
            }
        }

        // Attempt to update/change username later
        /*
        private fun updateUsername(name: String){
            username = name
        }*/
    }



    private fun updateCounter(count: Long){
        counter = count
        textCounter.text = counter.toString()
    }

    private fun restartApplication() {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
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
*/

//new
class MainActivity : AppCompatActivity() {
    // Declare our viewmodel for the count object which will be updated whenever counter changes
    private lateinit var countViewModel: CountViewModel

    private var counter: Long = 0 // Declare out counter var that will store number of user clicks, default value is 0
    private fun getUsername() = intent.extras?.get("username").toString().toLowerCase(Locale.US)
    // retrieves the username that was stored in LoginActivity

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countViewModel = ViewModelProviders.of( this).get(CountViewModel::class.java)
        // Updates UI with the users last stored counter value
        // retrieves the value from PREFS file through CountRepository
        countViewModel.getUserCount(getUsername()).observe(this, androidx.lifecycle.Observer { updateCounter(it) })

        // Main button action that will increase the counter value by 1
        myButton.setOnClickListener{
            countViewModel.setUserCount(getUsername(), counter + 1)
        }
    }

    // used to update the text field showing the users current click count
    private fun updateCounter(count: Long) {
        counter = count
        textCounter.text = counter.toString()
    }
}