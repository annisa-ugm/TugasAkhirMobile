package com.example.tugasakhirmobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tugasakhirmobile.model.Favorite
import com.example.tugasakhirmobile.model.Furniture

@Database(entities = [Furniture::class, Favorite::class], version = 1, exportSchema = false)
abstract class DatabaseInstance:RoomDatabase() {
    abstract fun furnitureDao():FurnitureDao
    abstract fun favoriteDao():FavoriteDao

    companion object{
        @Volatile
        private var INSTANCE:DatabaseInstance? = null

        fun getDatabase(context: Context): DatabaseInstance {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseInstance::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}