package com.nanda.githubsearch.repository

import com.nanda.githubsearch.database.FavoriteDao
import java.util.concurrent.ExecutorService
import com.nanda.githubsearch.database.FavoriteDatabase
import android.app.Application
import com.nanda.githubsearch.database.Favorite
import java.util.concurrent.Executors


class FavoriteRepo(application: Application) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val favoriteDao: FavoriteDao

    init {
        val database = FavoriteDatabase.getInstance(application)
        favoriteDao = database.favoriteDao
    }

    fun insert(favorite: Favorite) {
        executorService.execute { favoriteDao.insert(favorite) }
    }

    fun isFavorite(username: String) = favoriteDao.isFavorite(username)

    fun getAllFavorite() = favoriteDao.getAllFavorites()

    fun deleteFavorite(favorite: Favorite) {
        executorService.execute { favoriteDao.delete(favorite) }
    }
}