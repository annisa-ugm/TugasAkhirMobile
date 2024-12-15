package com.example.tugasakhirmobile.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasakhirmobile.model.Furniture
import com.bumptech.glide.Glide
import com.example.tugasakhirmobile.databinding.AdminItemFurnitureBinding
import com.example.tugasakhirmobile.network.ApiClient
import com.example.tugasakhirmobile.network.ApiServices
import retrofit2.Call

class FurnitureAdapter() :
    RecyclerView.Adapter<FurnitureAdapter.FurnitureViewHolder>() {

    private var furnitureList: MutableList<Furniture> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setFurniture(furnitureList: List<Furniture>) {
        this.furnitureList = furnitureList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val binding = AdminItemFurnitureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FurnitureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val furniture = furnitureList[position]
        holder.bind(furniture)
    }

    override fun getItemCount() = furnitureList.size

    inner class FurnitureViewHolder(private val binding: AdminItemFurnitureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(furniture: Furniture) {
            binding.tvName.text = "${furniture.name}"
            binding.tvStock.text = "Stock: ${furniture.stock}"
            binding.tvBrand.text = "Brand: ${furniture.brand}"
            binding.tvColor.text = "Color: ${furniture.color}"
            binding.tvMaterial.text = "Material: ${furniture.material}"
            binding.tvStyle.text = "Style: ${furniture.style}"
            binding.tvCondition.text = "Condition: ${furniture.condition}"
            binding.tvSize.text = "Size: ${furniture.size}"

            Glide.with(binding.ivPicture.context)
                .load(furniture.picture_url)
                .into(binding.ivPicture)

            binding.btnEdit.setOnClickListener {
                val intent = Intent(binding.root.context, AdminEditData::class.java).apply {
                    putExtra("furniture_id", furniture._id)
                    putExtra("furniture_name", furniture.name)
                    putExtra("furniture_picture_url", furniture.picture_url)
                    putExtra("furniture_stock", furniture.stock)
                    putExtra("furniture_brand", furniture.brand)
                    putExtra("furniture_color", furniture.color)
                    putExtra("furniture_material", furniture.material)
                    putExtra("furniture_style", furniture.style)
                    putExtra("furniture_condition", furniture.condition)
                    putExtra("furniture_size", furniture.size)
                }
                binding.root.context.startActivity(intent)
            }

            binding.btnDelete.setOnClickListener {
                furniture._id?.let { furnitureId ->
                    val context = binding.root.context
                    val apiService = ApiClient.getInstance()

                    val call = apiService.deleteFurniture(furnitureId)
                    call.enqueue(object : retrofit2.Callback<Furniture> {
                        override fun onResponse(call: Call<Furniture>, response: retrofit2.Response<Furniture>) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Furniture deleted successfully!", Toast.LENGTH_SHORT).show()
                                furnitureList.removeAt(bindingAdapterPosition)
                                notifyItemRemoved(bindingAdapterPosition)
                            } else {
                                Toast.makeText(context, "Failed to delete furniture: ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Furniture>, t: Throwable) {
                            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } ?: run {
                    Toast.makeText(binding.root.context, "Furniture ID is null!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
