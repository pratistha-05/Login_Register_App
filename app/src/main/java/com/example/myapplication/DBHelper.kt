package com.learnandroid.loginsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "Login.db", null, 1) {
  override fun onCreate(MyDB: SQLiteDatabase) {
    MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)")
  }

  override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i1: Int) {
    MyDB.execSQL("drop Table if exists users")
  }

  fun insertData(username: String?, password: String?): Boolean {
    val MyDB = this.writableDatabase
    val contentValues = ContentValues()
    contentValues.put("username", username)
    contentValues.put("password", password)
    val result = MyDB.insert("users", null, contentValues)
    if (result == -1L)
      return false
    else
      return true
  }

  fun checkusername(username: String): Boolean {
    val MyDB = this.writableDatabase
    val cursor = MyDB.rawQuery("Select * from users where username = ?", arrayOf(username))
    if (cursor.count > 0)
      return true
    else
      return false
  }

  fun checkusernamepassword(username: String, password: String): Boolean {
    val MyDB = this.writableDatabase
    val cursor = MyDB.rawQuery(
      "Select * from users where username = ? and password = ?",
      arrayOf(username, password)
    )
    return if (cursor.count > 0) true else false
  }

  companion object {
    const val DBNAME = "Login.db"
  }
}