package com.nanda.githubsearch.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nanda.githubsearch.database.Favorite
import com.nanda.githubsearch.repository.FavoriteRepo


class FavoriteViewModel(application: Application) : ViewModel() {
    private val repoFavo: FavoriteRepo = FavoriteRepo(application)

    fun getAllFavorite(): LiveData<List<Favorite>> = repoFavo.getAllFavorite()
    init {
        getAllFavorite()
    }
}