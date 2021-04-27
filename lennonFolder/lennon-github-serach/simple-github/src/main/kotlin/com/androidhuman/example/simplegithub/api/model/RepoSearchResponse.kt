package com.androidhuman.example.simplegithub.api.model

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
        @field:SerializedName("total_count")
        val totalCount: Int,
        val items: List<GithubRepoDto>)