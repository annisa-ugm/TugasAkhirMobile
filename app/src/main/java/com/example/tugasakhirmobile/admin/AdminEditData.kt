package com.example.tugasakhirmobile.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasakhirmobile.R
import com.example.tugasakhirmobile.databinding.ActivityAdminEditDataBinding
import com.example.tugasakhirmobile.model.Furniture
import com.example.tugasakhirmobile.network.ApiClient
import com.example.tugasakhirmobile.network.ApiServices
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminEditData : AppCompatActivity() {

    private lateinit var binding: ActivityAdminEditDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data dari Intent
        val furnitureId = intent.getStringExtra("furniture_id")
        val furnitureName = intent.getStringExtra("furniture_name")
        val furniturePictureUrl = intent.getStringExtra("furniture_picture_url")
        val furnitureStock = intent.getStringExtra("furniture_stock")
        val furnitureBrand = intent.getStringExtra("furniture_brand")
        val furnitureColor = intent.getStringExtra("furniture_color")
        val furnitureMaterial = intent.getStringExtra("furniture_material")
        val furnitureStyle = intent.getStringExtra("furniture_style")
        val furnitureCondition = intent.getStringExtra("furniture_condition")
        val furnitureSize = intent.getStringExtra("furniture_size")

        binding.edtName.setText(furnitureName)
        binding.edtPictureUrl.setText(furniturePictureUrl)
        binding.edtStock.setText(furnitureStock.toString())
        binding.edtBrand.setText(furnitureBrand)
        binding.edtColor.setText(furnitureColor)
        binding.edtMaterial.setText(furnitureMaterial)
        binding.edtStyle.setText(furnitureStyle)
        binding.edtCondition.setText(furnitureCondition)
        binding.edtSize.setText(furnitureSize)

        binding.btnSave.setOnClickListener {
            val updatedFurniture = Furniture(
                _id = furnitureId ?: "",
                name = binding.edtName.text.toString(),
                picture_url = binding.edtPictureUrl.text.toString(),
                stock = binding.edtStock.text.toString(),
                brand = binding.edtBrand.text.toString(),
                color = binding.edtColor.text.toString(),
                material = binding.edtMaterial.text.toString(),
                style = binding.edtStyle.text.toString(),
                condition = binding.edtCondition.text.toString(),
                size = binding.edtSize.text.toString()
            )

            val apiService = ApiClient.getInstance()
            val jsonRequest = updatedFurniture.toJson().toRequestBody("application/json".toMediaType())
            apiService.updateFurniture(furnitureId ?: "", jsonRequest).enqueue(object : Callback<Furniture> {
                override fun onResponse(call: Call<Furniture>, response: Response<Furniture>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AdminEditData, "Furniture updated successfully! Please click button refresh", Toast.LENGTH_SHORT).show()
                        finish()  // Kembali ke halaman sebelumnya setelah berhasil update
                    } else {
                        Toast.makeText(this@AdminEditData, "Failed to update furniture!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Furniture>, t: Throwable) {
                    Toast.makeText(this@AdminEditData, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
