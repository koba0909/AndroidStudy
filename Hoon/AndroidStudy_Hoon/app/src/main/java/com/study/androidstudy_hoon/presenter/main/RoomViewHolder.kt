package com.study.androidstudy_hoon.presenter.main

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.study.androidstudy_hoon.data.dto.Repo
import com.study.androidstudy_hoon.databinding.SearchResultItemBinding

class RoomViewHolder(private val binding: SearchResultItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Repo) {
        with(binding) {
            repoLanguageTextView.text = data.language
            repoNameTextView.text = data.name
            Glide.with(binding.root.context)
                .load(data.owner?.avatarUrl)
                .into(repoThumbnailImgView)
        }
    }
}