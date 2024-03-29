package com.nanda.githubsearch.viewmodel


import com.nanda.githubsearch.database.Favorite
import androidx.lifecycle.ViewModel
import retrofit2.Call
import com.nanda.githubsearch.network.response.DataItems
import androidx.lifecycle.LiveData
import com.nanda.githubsearch.repository.FavoriteRepo
import com.nanda.githubsearch.network.retrofit.ApiConfig
import androidx.lifecycle.MutableLiveData
import retrofit2.Response
import android.app.Application
import com.nanda.githubsearch.network.response.DetailUserResponse
import android.util.Log
import retrofit2.Callback


class DetailViewModel(application: Application) : ViewModel() {

    private var isLoadings = false
    private var followersLoad = false
    private var followingLoad = false

    private val favoRepo: FavoriteRepo = FavoriteRepo(application)

    companion object {
        private const val TAG = "errorDetail"
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _details = MutableLiveData<DetailUserResponse>()
    val detailUser : LiveData<DetailUserResponse> = _details

    private val _listFollowing = MutableLiveData<List<DataItems>>()
    val listFollowing: LiveData<List<DataItems>> = _listFollowing

    private val _listFollower = MutableLiveData<List<DataItems>>()
    val listFollowers: LiveData<List<DataItems>> = _listFollower

    fun getDetailUser(username: String) {
        if (!isLoadings) {
            _isLoading.value = true
            val config = ApiConfig.instance.getDetailUser(username)
            config.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _details.postValue(response.body())
                    } else {
                        Log.e(TAG, "onfailure  ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isLoadings = true
        }
    }

    fun getFollowers(username: String) {
        if (!followersLoad) {
            _isLoading.value = true
            val config = ApiConfig.instance.getFollowers(username)
            config.enqueue(object : Callback<List<DataItems>> {
                override fun onResponse(
                    call: Call<List<DataItems>>,
                    response: Response<List<DataItems>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _listFollower.postValue(response.body())
                    } else {
                        Log.e(TAG, "onfailure  ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<DataItems>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            followersLoad = true
        }
    }


    fun getFollowing(username: String) {
        if (!followingLoad) {
            _isLoading.value = true
            val config = ApiConfig.instance.getFollowing(username)
            config.enqueue(object : Callback<List<DataItems>> {
                override fun onResponse(
                    call: Call<List<DataItems>>,
                    response: Response<List<DataItems>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _listFollowing.postValue(response.body())
                        Log.d("SuccesGet", "succes ${response.message()}")
                    } else {
                        Log.e(TAG, "onfailure  ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<DataItems>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            followingLoad = true
        }
    }
    fun insertFavorite(favorite: Favorite){
        favoRepo.insert(favorite)
    }

    fun deleteFavorite(favorite: Favorite){
        favoRepo.deleteFavorite(favorite)
    }
    fun isFavorite(username: String) : LiveData<Boolean> = favoRepo.isFavorite(username)
}

