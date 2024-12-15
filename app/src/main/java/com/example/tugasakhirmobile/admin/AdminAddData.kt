package com.example.tugasakhirmobile.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasakhirmobile.databinding.ActivityAdminAddDataBinding
import com.example.tugasakhirmobile.model.Furniture
import com.example.tugasakhirmobile.model.User
import com.example.tugasakhirmobile.network.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAddData : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAddDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = ApiClient.getInstance()

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val pictureUrl = binding.etPictureUrl.text.toString()
            val stock = binding.etStock.text.toString()
            val brand = binding.etBrand.text.toString()
            val color = binding.etColor.text.toString()
            val material = binding.etMaterial.text.toString()
            val style = binding.etStyle.text.toString()
            val condition = binding.etCondition.text.toString()
            val size = binding.etSize.text.toString()

            if (name.isNotEmpty() && pictureUrl.isNotEmpty() && stock.isNotEmpty() &&
                brand.isNotEmpty() && color.isNotEmpty() && material.isNotEmpty() &&
                style.isNotEmpty() && condition.isNotEmpty() && size.isNotEmpty()) {

                val jsonObject = JSONObject().apply {
                    put("name", name)
                    put("picture_url", pictureUrl)
                    put("stock", stock)
                    put("brand", brand)
                    put("color", color)
                    put("material", material)
                    put("style", style)
                    put("condition", condition)
                    put("size", size)
                }

                val requestBody = RequestBody.create(
                    "application/json".toMediaTypeOrNull(),
                    jsonObject.toString()
                )

                val response = client.postNewFurniture(requestBody)
                response.enqueue(object : Callback<Furniture>{
                    override fun onResponse(call: Call<Furniture>, response: Response<Furniture>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@AdminAddData, "Data added successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@AdminAddData, AdminHomePage::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@AdminAddData, "Failed to add data: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Furniture>, t: Throwable) {
                        Toast.makeText(this@AdminAddData, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
