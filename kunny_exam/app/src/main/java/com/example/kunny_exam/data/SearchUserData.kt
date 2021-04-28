package com.example.kunny_exam.data

data class SearchUserData(
    val total_count : Int,
    val incomplete_results : Boolean,
    val items : List<SearchUserInfo>
)