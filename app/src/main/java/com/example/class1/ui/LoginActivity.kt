package com.example.class1.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.class1.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Activates the login text box receives whatever the user inputs into the text field for login username
        loginUsernameField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //unused
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //unused
            }
            override fun afterTextChanged(p0: Editable?) {
                //unused
            }

        } )
        //
        loginButton.setOnClickListener {
           // passes the username from login screen to wherever we decide to pull it from
            startActivity(Intent(this, MainActivity::class.java).apply{putExtra("username", loginUsernameField.text) })
        }
    }
}