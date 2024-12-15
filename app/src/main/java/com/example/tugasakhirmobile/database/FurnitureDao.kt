package com.example.tugasakhirmobile.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tugasakhirmobile.model.Furniture

@Dao
interface FurnitureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(furniture: Furniture)

    @Update
    fun update(furniture: Furniture)

    @Delete
    fun delete(furniture: Furniture)

    @get:Query("SELECT * from `table-furniture`")
    val allFurniture: LiveData<List<Furniture>>


}