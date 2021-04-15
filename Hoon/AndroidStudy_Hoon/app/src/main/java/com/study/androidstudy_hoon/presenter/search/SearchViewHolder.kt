package com.study.androidstudy_hoon.presenter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.study.androidstudy_hoon.data.dto.Repo
import com.study.androidstudy_hoon.databinding.SearchResultItemBinding
import io.reactivex.subjects.Subject

class SearchViewHolder(private val binding: SearchResultItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Repo, clickSubject: Subject<Repo>) {
        with(binding) {
            repoLanguageTextView.text = data.language
            repoNameTextView.text = data.name
            Glide.with(binding.root.context)
                .load(data.owner.avatarUrl)
                .into(repoThumbnailImgView)
            binding.searchResultItem.setOnClickListener {
                clickSubject.onNext(data)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): SearchViewHolder {
            val binding = SearchResultItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            )
            return SearchViewHolder(binding)
        }
    }

}