package com.nanda.githubsearch.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nanda.githubsearch.R
import com.nanda.githubsearch.ui.MainActivity.Companion.ARG_LOGIN
import com.nanda.githubsearch.database.Favorite
import com.nanda.githubsearch.databinding.ActivityFavoriteBinding
import com.nanda.githubsearch.viewmodel.ViewModelFactory
import com.nanda.githubsearch.adapter.AdapterFavorite

import com.nanda.githubsearch.viewmodel.FavoriteViewModel


class FavoriteActivity : AppCompatActivity(), AdapterFavorite.OnClickListener {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>{
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title= getString(R.string.favorite_users)
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        viewModel.getAllFavorite().observe(this) { users ->
            val adapter = AdapterFavorite(this)
            adapter.sendCategory(users.sortedBy { it.username })
            binding.rvFavorite.adapter = adapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.onlyset, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)


    }
    @SuppressLint("SuspiciousIndentation")
    override fun itemClick(data: Favorite) {
        val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ARG_LOGIN, data.username)
            startActivity(intent)

    }

}