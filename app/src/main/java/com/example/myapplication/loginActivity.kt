package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.learnandroid.loginsqlite.DBHelper


class loginActivity : AppCompatActivity() {

  private lateinit var DB: DBHelper

  private lateinit var username: EditText

  private lateinit var password: EditText

  private lateinit var btnSignup: Button

  private lateinit var loginButton: Button

  private lateinit var forgot_password: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    initialiseVariables()

    loginButton.setOnClickListener {
      if (username.text.isNullOrEmpty())
        username.error = "Please enter the email"
      if( password.text.isNullOrEmpty())
        password.error = "Please enter the password"
      else {
          val checkuserpass = DB.checkusernamepassword(username.text.toString(), password.text.toString())
          if (checkuserpass) {
            Toast.makeText(this, "Sign in successfull", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, HomeActivity::class.java))
          }
          else {
            Snackbar.make(loginButton, "Invalid Email or Password", Snackbar.LENGTH_LONG).show()
          }
        }
    }

    btnSignup.setOnClickListener {
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    forgot_password.setOnClickListener {
        startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
    }
  }
  private fun initialiseVariables() {
    username = findViewById(R.id.login_emailInput)
    password = findViewById(R.id.login_passwordInput)
    btnSignup = findViewById(R.id.btn_signup)
    loginButton = findViewById(R.id.loginButton)
    forgot_password = findViewById(R.id.forgot_password)
    DB = DBHelper(this)
  }
}