package com.example.tugasakhirmobile.user

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasakhirmobile.databinding.ItemFurnitureBinding
import com.example.tugasakhirmobile.model.Furniture
import com.bumptech.glide.Glide
import com.example.tugasakhirmobile.database.FavoriteDao
import com.example.tugasakhirmobile.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserFurnitureAdapter(private val favoriteDao: FavoriteDao, private val prefManager: PrefManager) :
    RecyclerView.Adapter<UserFurnitureAdapter.FurnitureViewHolder>() {

    private var furnitureList: List<Furniture> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setFurniture(furnitureList: List<Furniture>) {
        this.furnitureList = furnitureList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val binding = ItemFurnitureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FurnitureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val furniture = furnitureList[position]
        holder.bind(furniture)
    }

    override fun getItemCount() = furnitureList.size

    inner class FurnitureViewHolder(private val binding: ItemFurnitureBinding) :
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

            // Update the Add to Favorite button to add the item to favorites when clicked
            binding.btnAddToFavorite.setOnClickListener {
                checkFavorite(furniture)
            }
        }

        private fun addFavorite(furniture: Furniture) {
            CoroutineScope(Dispatchers.IO).launch {
                val username = prefManager.getUsername()
                val favorite = Favorite(
                    username = username,
                    furnitureId = furniture._id,
                    furnitureName = furniture.name,
                    furniturePictureUrl = furniture.picture_url,
                    furnitureBrand = furniture.brand,
                    furnitureColor = furniture.color,
                    furnitureMaterial = furniture.material,
                    furnitureStyle = furniture.style,
                    furnitureCondition = furniture.condition,
                    furnitureSize = furniture.size,
                    isFavorite = true
                )
                favoriteDao.insert(favorite)
                Log.d("Favorite", "Favorite added: ${furniture.name}")
            }
        }

        private fun checkFavorite(furniture: Furniture) {
            val username = prefManager.getUsername()

            CoroutineScope(Dispatchers.IO).launch {
                // Cek apakah furniture dengan ID tertentu sudah ada di tabel favorites
                val existingFavorite = favoriteDao.getFavoritesByUsername(username).find { it.furnitureId == furniture._id }

                withContext(Dispatchers.Main) {
                    if (existingFavorite != null) {
                        Toast.makeText(
                            binding.root.context,
                            "Product is already in your favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        addFavorite(furniture)
                        Toast.makeText(
                            binding.root.context,
                            "Product is added to your favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
}
