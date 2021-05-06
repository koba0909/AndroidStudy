package com.example.kunny_exam.dto

import com.google.gson.annotations.SerializedName

data class RepoOwnerData (
        @SerializedName("avatar_url")
        val avatar_url : String?
)