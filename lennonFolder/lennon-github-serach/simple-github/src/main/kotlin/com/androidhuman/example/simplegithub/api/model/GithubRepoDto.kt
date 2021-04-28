package com.androidhuman.example.simplegithub.api.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//GithubRepo 엔티티의 데이터가 저장될 테이블 이름을 repositories로 지정
@Entity(tableName = "repositories")
data class GithubRepoDto(
        @field:SerializedName("name") val name: String,
        @ColumnInfo(name = "full_name") @field:SerializedName("full_name") @PrimaryKey
        val fullName: String,
        @Embedded val owner: GithubOwner,
        @field:SerializedName("description") val description: String?,
        @field:SerializedName("language") val language: String?,
        @field:SerializedName("updated_at") @ColumnInfo(name = "updated_at") val updatedAt: String,
        @field:SerializedName("stargazers_count") val stars: Int)