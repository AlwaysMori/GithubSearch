package com.nanda.githubsearch.database

import androidx.room.Database
import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Room


@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract val favoriteDao: FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "FavoritesDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}