package com.example.kunny_exam.dto

import com.example.kunny_exam.model.dto.RepoEntity
import com.google.gson.annotations.SerializedName

data class SearchRepoInfo(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String?,
        @SerializedName("full_name") val full_name: String?,
        @SerializedName("language") val language : String?,
        @SerializedName("stargazers_count") val stargazers_count : Int?,
        @SerializedName("owner") val repoOwnerData : RepoOwnerData?
)

fun SearchRepoInfo.toRepoEntity() =
        RepoEntity(
                id = id,
                name = name ?: "",
                fullName = full_name ?: "",
                language = language ?: "No language Specified",
                stars = stargazers_count ?: 0,
                repoOwner = repoOwnerData
        )