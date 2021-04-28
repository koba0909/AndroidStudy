package com.androidhuman.example.simplegithub.api.model

import com.google.gson.annotations.SerializedName

data class GithubOwner(
        @field:SerializedName("login")
        val login: String,
        @field:SerializedName("avatar_url")
        val avatarUrl: String)