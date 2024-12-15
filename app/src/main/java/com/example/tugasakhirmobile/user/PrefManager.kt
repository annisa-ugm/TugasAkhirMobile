package com.example.tugasakhirmobile.user

import android.content.Context

class PrefManager private constructor(context: Context){
    private val sharedPreferences = context.getSharedPreferences(
        PREFS_FILENAME, Context.MODE_PRIVATE
    )

    companion object {
        private const val PREFS_FILENAME = "AuthAppPref"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_ROLE = "user"
        private const val KEY_PASSWORD = "password"

        @Volatile
        private var instance:PrefManager? = null
        fun getInstance(context: Context): PrefManager{
            return instance ?: synchronized(this){
                instance ?: PrefManager(context.applicationContext).also { pref ->
                    instance = pref
                }
            }
        }

    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun saveUsername(username:String){
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    fun getUsername():String{
        return sharedPreferences.getString(KEY_USERNAME, "")?:""
    }

    fun saveEmail(email:String){
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }

    fun getEmail():String{
        return sharedPreferences.getString(KEY_EMAIL, "")?:""
    }

    fun savePassword(password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }

    fun getPassword():String{
        return sharedPreferences.getString(KEY_PASSWORD, "")?:""
    }

    fun saveRole(role: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ROLE, role)
        editor.apply()
    }

    fun getRole():String{
        return sharedPreferences.getString(KEY_ROLE, "")?:""
    }

    fun clear(){
        sharedPreferences.edit().also {
            it.clear()
            it.apply()
        }
    }
}