package com.study.androidstudy_hoon.data.dto

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Repo")
data class Repo(
    @PrimaryKey @field:SerializedName("full_name") val fullName: String,
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("html_url") val url: String,
    @field:SerializedName("stargazers_count") val stars: Int,
    @field:SerializedName("forks_count") val forks: Int,
    @field:SerializedName("language") val language: String?,
    @Embedded @field:SerializedName("owner") val owner: Owner?,
) : Serializable