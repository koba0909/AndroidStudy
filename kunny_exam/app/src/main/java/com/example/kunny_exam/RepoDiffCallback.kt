package com.example.kunny_exam

import androidx.recyclerview.widget.DiffUtil
import com.example.kunny_exam.dto.SearchRepoInfo

object RepoDiffCallback : DiffUtil.ItemCallback<SearchRepoInfo>() {
    override fun areItemsTheSame(
            oldItem: SearchRepoInfo,
            newItem: SearchRepoInfo
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
            oldItem: SearchRepoInfo,
            newItem: SearchRepoInfo
    ): Boolean {
        return oldItem == newItem
    }
}