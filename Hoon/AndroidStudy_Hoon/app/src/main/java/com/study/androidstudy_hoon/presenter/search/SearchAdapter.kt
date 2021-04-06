package com.study.androidstudy_hoon.presenter.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.study.androidstudy_hoon.data.dto.Repo
import com.study.androidstudy_hoon.databinding.SearchResultItemBinding

class SearchAdapter(val context: Context) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val _searchRepoList = arrayListOf<Repo>()
    var searchRepoList: List<Repo>
        get() = _searchRepoList
        set(value) {
            _searchRepoList.clear()
            _searchRepoList.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchResultItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(_searchRepoList[position])

    override fun getItemCount(): Int = _searchRepoList.size

    inner class SearchViewHolder(private val binding: SearchResultItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Repo) {
            binding.repoLanguageTextView.text = data.language
            binding.repoNameTextView.text = data.name
            Glide.with(context)
                    .load(data.owner.avatarUrl)
                    .into(binding.repoThumbnailImgView)
        }
    }
}