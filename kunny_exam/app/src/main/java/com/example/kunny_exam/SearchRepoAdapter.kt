package com.example.kunny_exam

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kunny_exam.Common.ImageViewUtils
import com.example.kunny_exam.data.SearchRepoInfo
import com.example.kunny_exam.databinding.LayoutSearchItemBinding

class SearchRepoAdapter() : RecyclerView.Adapter<SearchRepoAdapter.SearchRepoViewHolder>() {
    private var items: List<SearchRepoInfo>? =  null
    private var listener : ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRepoViewHolder {
        val binding = LayoutSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchRepoViewHolder(binding)
    }

    override fun getItemCount(): Int = items!!.size

    override fun onBindViewHolder(holder: SearchRepoViewHolder, position: Int) {
        holder.viewBinding.tvSearchFullName.text = items!![position].full_name ?: "no_name"
        holder.viewBinding.tvSearchLanguage.text = items!![position].language ?: "No Language Specified"
//        ImageViewUtils.setGlideImage(holder.viewBinding.ivSearchAvatar, items!![position].owner!!.avatar_url)
    }

    fun setData(items : List<SearchRepoInfo>){
        this.items = items
    }

    fun setListener(l : ItemClickListener) {
        this.listener = l
    }

    inner class SearchRepoViewHolder(val viewBinding: LayoutSearchItemBinding) : RecyclerView.ViewHolder(viewBinding.root)
}
