package com.example.kunny_exam.data

import com.example.kunny_exam.RepoEntity
import com.google.gson.annotations.SerializedName

data class SearchRepoInfo(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String?,
        @SerializedName("full_name") val full_name: String?,
        @SerializedName("language") val language : String?,
        @SerializedName("stargazers_count") val stargazers_count : Int?,
        @SerializedName("owner") val ownerData : OwnerData?
)

fun SearchRepoInfo.toRepoEntity() =
        RepoEntity(
                id = id,
                name = name ?: "",
                fullName = full_name ?: "",
                language = language ?: "No language Specified",
                stars = stargazers_count ?: 0,
                owner = ownerData
        )