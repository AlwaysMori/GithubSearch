package com.nanda.githubsearch.database

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity


@Parcelize
@Entity(tableName = "user_favorite")
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "avatar")
    var avatarUrl: String? = null
) : Parcelable

