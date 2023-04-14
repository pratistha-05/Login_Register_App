package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.learnandroid.loginsqlite.DBHelper

class ForgotPasswordActivity : AppCompatActivity() {
  private lateinit var DB: DBHelper

  private lateinit var username: EditText

  private lateinit var password: EditText

  private lateinit var repassword: EditText

  private lateinit var signin: Button
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_forgot_password)

    initialiseVariables()

    signin.setOnClickListener {
      if (username.text.isNullOrEmpty()) username.error = "Please enter the email"
      if (password.text.isNullOrEmpty()) password.error = "Please enter the password"
      if (repassword.text.isNullOrEmpty()) repassword.error = "Please re-enter the password"
      else {
        if (password.text.toString() != repassword.text.toString()) {
          Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
          return@setOnClickListener
        } else if (password.text.toString() == repassword.text.toString() && !username.text.isNullOrEmpty()) {
          if (DB.checkusername(username.text.toString().trim())) {
            val update = DB.updatePassword(username.text.toString(), password.text.toString())
            if (update) {
              Toast.makeText(
                this, "Password for has been changed successfully!", Toast.LENGTH_LONG
              ).show()
              startActivity(Intent(applicationContext, HomeActivity::class.java))
            } else {
              Snackbar.make(
                signin, "Please enter a new password you have not used before", Snackbar.LENGTH_LONG
              ).show()
            }
          } else {
            Toast.makeText(this, "Email does not exist!", Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
  }


  private fun initialiseVariables() {
    username = findViewById(R.id.forgot_emailInput)
    password = findViewById(R.id.forgot_passwordInput)
    repassword = findViewById(R.id.re_forgot_passwordInput)
    signin = findViewById(R.id.forgot_loginButton)
    DB = DBHelper(this)
  }
}