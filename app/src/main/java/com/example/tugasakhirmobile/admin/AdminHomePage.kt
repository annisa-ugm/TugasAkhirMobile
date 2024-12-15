package com.example.tugasakhirmobile.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirmobile.databinding.ActivityAdminHomePageBinding
import com.example.tugasakhirmobile.model.Furniture
import com.example.tugasakhirmobile.network.ApiClient
import com.example.tugasakhirmobile.user.LoginActivity
import com.example.tugasakhirmobile.user.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminHomePage : AppCompatActivity() {
    private lateinit var binding: ActivityAdminHomePageBinding
    private val furnitureAdapter = FurnitureAdapter()
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager.getInstance(this)

        binding = ActivityAdminHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchFurnitureData()

        binding.btnAdd.setOnClickListener {
            intent = Intent(this@AdminHomePage, AdminAddData::class.java)
            startActivity(intent)
        }

        binding.btnRefresh.setOnClickListener {
            fetchFurnitureData()  // Memanggil fungsi untuk me-refresh data
            Toast.makeText(this@AdminHomePage, "Furniture data refreshed", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            prefManager.clear()
            val intent = Intent(this@AdminHomePage, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.rvAdminFurniture.apply {
            layoutManager = LinearLayoutManager(this@AdminHomePage)
            adapter = furnitureAdapter
        }
    }

    private fun fetchFurnitureData() {
        val apiService = ApiClient.getInstance()

        val apiCall = apiService.getAllFurniture()
        apiCall.enqueue(object : Callback<List<Furniture>> {
            override fun onResponse(
                call: Call<List<Furniture>>,
                response: Response<List<Furniture>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { furnitureList ->
                        furnitureAdapter.setFurniture(furnitureList)
                    }
                } else {
                    Toast.makeText(
                        this@AdminHomePage,
                        "Failed to load furniture data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Furniture>>, t: Throwable) {
                Log.e("AdminHomePage", "Error fetching furniture data", t)
                Toast.makeText(
                    this@AdminHomePage,
                    "Error fetching furniture data: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
