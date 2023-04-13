package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.learnandroid.loginsqlite.DBHelper

class MainActivity : AppCompatActivity() {

    private lateinit var DB: DBHelper

    private lateinit var username: EditText

    private lateinit var password: EditText

    private lateinit var repassword: EditText

    private lateinit var signup: Button

    private lateinit var signin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialiseVariables()

        signup.setOnClickListener {
            if (username.text.isNullOrEmpty())
            {
                username.error = "Please enter the email"
                username.requestFocus()
            }
            if( password.text.isNullOrEmpty()) {
                password.error = "Please enter the password"
                password.requestFocus()
            }
            if( repassword.text.isNullOrEmpty()) {
                repassword.error = "Please re-enter the password"
                repassword.requestFocus()
            }
            else {
                if (password.text.toString() == repassword.text.toString() && !username.text.isNullOrEmpty() && !password.text.isNullOrEmpty() && !repassword.text.isNullOrEmpty()) {

                    val checkuser: Boolean = DB.checkusername(username.text.toString())
                    if (!checkuser) {
                        insertUserIntoDatabase()
                    }
                    else
                    {
                        Snackbar.make(signup, "User already exists! please sign in", Snackbar.LENGTH_LONG).show()
                    }
                } else
                {
                    Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        signin.setOnClickListener {
            startActivity(Intent(applicationContext, loginActivity::class.java))
        }

    }
    private fun initialiseVariables(){
        username = findViewById(R.id.emailInput)
        password = findViewById(R.id.passwordInput)
        repassword = findViewById(R.id.re_passwordInput)
        signup = findViewById(R.id.signup_button)
        signin = findViewById(R.id.btnLogin)
        DB = DBHelper(this)
    }
    private fun insertUserIntoDatabase(){
        val insert: Boolean = DB.insertData(username.text.toString(), password.text.toString())
        if (insert) {
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        }
        else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
        }
    }

}