package com.example.kunny_exam.data

import com.google.gson.annotations.SerializedName

data class SearchRepoData(
        @SerializedName("total_count") val totalCount : Int,
        @SerializedName("incomplete_results") val incompleteResults : Boolean,
        @SerializedName("items") val items : List<SearchRepoInfo>
)