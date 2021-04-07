package com.example.kunny_exam.data

import com.google.gson.annotations.SerializedName

data class SearchRepoInfo(
        @SerializedName("name") val name: String?,
        @SerializedName("full_name") val full_name: String?,
        @SerializedName("language") val language : String?,
        @SerializedName("stargazers_count") val stargazers_count : Int?,
        @SerializedName("owner") val ownerData : OwnerData?
)