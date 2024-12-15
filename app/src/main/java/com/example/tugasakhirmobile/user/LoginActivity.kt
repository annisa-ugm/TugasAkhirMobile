package com.example.tugasakhirmobile.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugasakhirmobile.R
import com.example.tugasakhirmobile.admin.AdminHomePage
import com.example.tugasakhirmobile.databinding.ActivityLoginBinding
import com.example.tugasakhirmobile.model.User
import com.example.tugasakhirmobile.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager.getInstance(this)

        checkLoginStatus()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = ApiClient.getInstance()

        with(binding) {
            btnLogin.setOnClickListener{
                val response = client.getAllUser()
                response.enqueue(object : Callback<List<User>> {
                    override fun onResponse(p0: Call<List<User>>, response: Response<List<User>>) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.forEach { i ->
                                if(i.username == usernameInput.text.toString() && i.password == passwordInput.text.toString()){
                                    prefManager.setLoggedIn(true)
                                    prefManager.saveUsername(usernameInput.text.toString())
                                    prefManager.saveEmail(i.email)
                                    prefManager.savePassword(passwordInput.text.toString())
                                    prefManager.saveRole(i.role)
                                    checkLoginStatus()
                                    finish()
                                }
                            }
                        } else {
                            Log.e("API Error", "Response not successful or body is null")
                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Koneksi error ${t.toString()}", Toast.LENGTH_SHORT).show()
                    }
                })

            }

            tvRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    fun checkLoginStatus() {
        if (prefManager.isLoggedIn()) {
            val intentToHome = if (prefManager.getRole() == "admin") {
                Toast.makeText(this@LoginActivity, "You login as an admin", Toast.LENGTH_SHORT).show()
                Intent(this@LoginActivity, AdminHomePage::class.java)
            } else {
                Toast.makeText(this@LoginActivity, "You login as a user", Toast.LENGTH_SHORT).show()
                Intent(this@LoginActivity, MainActivity::class.java)
            }
            intentToHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentToHome)
        }
    }
}