package com.example.kunny_exam

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kunny_exam.data.OwnerData
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repositories")
class RepoEntity (
    @SerializedName("name") val name: String,
    @SerializedName("full_name")
    @PrimaryKey @ColumnInfo(name = "full_name") val fullName: String,
    @SerializedName("language") val language: String?,
    @SerializedName("stargazers_count") val stars: Int,
    @Embedded val owner: OwnerData?
)
