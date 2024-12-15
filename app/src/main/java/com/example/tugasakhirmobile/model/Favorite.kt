package com.example.tugasakhirmobile.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table-favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "furniture_id")
    val furnitureId: String,
    @ColumnInfo(name = "furniture_name")
    val furnitureName: String,
    @ColumnInfo(name = "furniture_picture_url")
    val furniturePictureUrl: String,
    @ColumnInfo(name = "furniture_brand")
    val furnitureBrand: String,
    @ColumnInfo(name = "furniture_color")
    val furnitureColor: String,
    @ColumnInfo(name = "furniture_material")
    val furnitureMaterial: String,
    @ColumnInfo(name = "furniture_style")
    val furnitureStyle: String,
    @ColumnInfo(name = "furniture_condition")
    val furnitureCondition: String,
    @ColumnInfo(name = "furniture_size")
    val furnitureSize: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)
