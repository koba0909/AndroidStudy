package com.study.androidstudy_hoon.data.dto

import com.google.gson.annotations.SerializedName

data class Owner (
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("login") val login: String,
    @field:SerializedName("avatar_url") val avatarUrl: String,
)