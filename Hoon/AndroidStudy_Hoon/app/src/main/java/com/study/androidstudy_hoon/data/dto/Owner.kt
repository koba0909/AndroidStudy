package com.study.androidstudy_hoon.data.dto

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Owner(
    @PrimaryKey @field:SerializedName("id") val ownerId: Long,
    @field:SerializedName("login") val login: String,
    @field:SerializedName("avatar_url") val avatarUrl: String,
) : Serializable