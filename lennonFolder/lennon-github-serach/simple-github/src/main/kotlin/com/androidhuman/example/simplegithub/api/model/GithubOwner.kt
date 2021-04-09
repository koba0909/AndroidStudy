package com.androidhuman.example.simplegithub.api.model

import com.google.gson.annotations.SerializedName

data class GithubOwner(
        val login: String,
        @field:SerializedName("avatar_url")
        val avatarUrl: String)