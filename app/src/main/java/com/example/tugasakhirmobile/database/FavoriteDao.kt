package com.example.tugasakhirmobile.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tugasakhirmobile.model.Favorite
import com.example.tugasakhirmobile.model.Furniture

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM `table-favorites` WHERE username = :username")
    suspend fun getFavoritesByUsername(username: String): List<Favorite>
}