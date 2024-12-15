package com.example.tugasakhirmobile.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasakhirmobile.database.FavoriteDao
import com.example.tugasakhirmobile.databinding.ItemFavoriteBinding
import com.example.tugasakhirmobile.databinding.ItemFurnitureBinding
import com.example.tugasakhirmobile.model.Furniture
import com.example.tugasakhirmobile.model.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserFavoriteAdapter(
    private val favoriteDao: FavoriteDao,
    private val username: String
) : RecyclerView.Adapter<UserFavoriteAdapter.FurnitureViewHolder>() {

    private var favoriteList: List<Favorite> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setFavorites(favorites: List<Favorite>) {
        this.favoriteList = favorites
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FurnitureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val favorite = favoriteList[position]
        holder.bind(favorite)
    }

    override fun getItemCount(): Int = favoriteList.size

    inner class FurnitureViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: Favorite) {
            binding.tvName.text = favorite.furnitureName
            binding.tvBrand.text = "Brand: ${favorite.furnitureBrand}"
            binding.tvColor.text = "Color: ${favorite.furnitureColor}"
            binding.tvMaterial.text = "Material: ${favorite.furnitureMaterial}"
            binding.tvStyle.text = "Style: ${favorite.furnitureStyle}"
            binding.tvCondition.text = "Condition: ${favorite.furnitureCondition}"
            binding.tvSize.text = "Size: ${favorite.furnitureSize}"

            Glide.with(binding.ivPicture.context)
                .load(favorite.furniturePictureUrl)
                .into(binding.ivPicture)

            binding.btnRemoveFromFavorite.setOnClickListener {
                removeFavorite(favorite)
                Toast.makeText(binding.root.context, "Removed from favorite", Toast.LENGTH_SHORT).show()
            }
        }

        private fun removeFavorite(favorite: Favorite) {
            CoroutineScope(Dispatchers.IO).launch {
                favoriteDao.delete(favorite)
                CoroutineScope(Dispatchers.Main).launch {
                    val updatedList = favoriteList.toMutableList()
                    updatedList.remove(favorite)
                    setFavorites(updatedList)
                }
            }
        }
    }
}
