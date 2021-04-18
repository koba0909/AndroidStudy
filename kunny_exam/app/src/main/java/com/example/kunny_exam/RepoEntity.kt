package com.example.kunny_exam

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kunny_exam.data.OwnerData
import com.example.kunny_exam.data.SearchRepoInfo
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repositories")
class RepoEntity(
        @PrimaryKey
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("full_name")
        @ColumnInfo(name = "full_name") val fullName: String,
        @SerializedName("language") val language: String?,
        @SerializedName("stargazers_count") val stars: Int,
        @Embedded val owner: OwnerData?
)

fun RepoEntity.toSearchRepoInfo() =
        SearchRepoInfo(
            id = id,
            name = name,
            full_name = fullName,
            language = language,
            stargazers_count = stars,
            ownerData = owner
        )
