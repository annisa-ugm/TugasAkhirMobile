package com.example.tugasakhirmobile.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "table-furniture")
data class Furniture(
    @PrimaryKey
    @NonNull
    val _id: String = "",
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "picture_url")
    val picture_url: String,
    @ColumnInfo(name = "stock")
    val stock: String,
    @ColumnInfo(name = "brand")
    val brand: String,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "material")
    val material: String,
    @ColumnInfo(name = "style")
    val style: String,
    @ColumnInfo(name = "condition")
    val condition: String,
    @ColumnInfo(name = "size")
    val size: String,
)
{
    fun toJson(): String {
        return Gson().toJson(this)
    }
}