package com.nanda.githubsearch.ui

import com.nanda.githubsearch.adapter.AdapterSectionPagerForDetail
import com.nanda.githubsearch.database.Favorite
import com.nanda.githubsearch.viewmodel.DetailViewModel
import com.nanda.githubsearch.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.nanda.githubsearch.ui.MainActivity.Companion.ARG_LOGIN
import androidx.core.content.ContextCompat
import android.os.Bundle
import com.nanda.githubsearch.databinding.ActivityDetailBinding
import android.annotation.SuppressLint
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.nanda.githubsearch.R


class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var userFavorite: Favorite

    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(application )
    }
    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title= getString(R.string.detail_user)

        val getKey = intent.getStringExtra(ARG_LOGIN)

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        if (getKey != null){
            val adapterPager = AdapterSectionPagerForDetail(this, getKey)
            binding.apply {
                viewPager2Course.adapter = adapterPager
                TabLayoutMediator(tabLayoutDetailCourse, viewPager2Course) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }

        if (getKey != null){
            viewModel.getDetailUser(getKey)
        }
        viewModel.detailUser.observe(this) {
            if (it != null) {
                binding.apply {
                    tvNameuser.text = it.name
                    tvUsernameDetail.text = it.login
                    bio.text = it.bio.toString()
                    tvFollowers.text = getString(R.string.followers_count, it.followers)
                    tvFollowing.text = getString(R.string.following_count, it.following)
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .fitCenter()
                        .into(ivProfile)
                }

                userFavorite = Favorite(it.login, it.avatarUrl)
                viewModel.isFavorite(userFavorite.username).observe(this) { it1 ->
                    setIsFavorite(it1)
                }
            }
        }

        binding.fabAddFavo.apply {
            setOnClickListener {
                if (tag == TAG_ADD){
                    viewModel.deleteFavorite(userFavorite)
                } else {
                    viewModel.insertFavorite(userFavorite)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) { binding.progressbarDetail.visibility = if (state) View.VISIBLE else View.GONE }


    private fun setIsFavorite(favo: Boolean){
        binding.fabAddFavo.apply {
            if (favo){
                tag = TAG_ADD
                setImageDrawable(
                    ContextCompat.getDrawable(
                    this@DetailActivity,R.drawable.baseline_favorite_24)
                )
            } else {
                tag = ""
                setImageDrawable(
                    ContextCompat.getDrawable(
                    this@DetailActivity,R.drawable.baseline_favorite_border_24)
                )
            }
        }
    }
    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following

        )
        private val TAG_ADD = R.string.add

    }
}