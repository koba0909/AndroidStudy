package com.example.kunny_exam

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kunny_exam.dto.SearchRepoInfo

object SearchRepoBindingAdapter {
    @BindingAdapter("data")
    @JvmStatic
    fun setData(recyclerView : RecyclerView, data : MutableList<SearchRepoInfo>){
        val adapter = recyclerView.adapter as SearchRepoListAdapter
        adapter.submitList(data)
    }
}