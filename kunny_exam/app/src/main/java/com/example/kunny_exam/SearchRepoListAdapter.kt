package com.example.kunny_exam

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kunny_exam.common.ImageViewUtils
import com.example.kunny_exam.dto.SearchRepoInfo
import com.example.kunny_exam.databinding.LayoutSearchItemBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SearchRepoListAdapter(private val mContext : Context)
    : ListAdapter<SearchRepoInfo, SearchRepoListAdapter.SearchRepoViewHolder>(
        RepoDiffCallback
) {
    private val onItemClickSubject : PublishSubject<SearchRepoInfo> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRepoViewHolder {
        val binding = LayoutSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchRepoViewHolder(binding, this)
    }

    fun getOnItemClickObservable() : Observable<SearchRepoInfo> {
        return onItemClickSubject
    }

    override fun onBindViewHolder(holder: SearchRepoViewHolder, position: Int) {
        when(holder){
            is SearchRepoViewHolder -> holder.bindView(currentList[position])

            else -> -1
        }
        holder.bindView(currentList[position])
    }

    class SearchRepoViewHolder(private val viewBinding: LayoutSearchItemBinding,
                               private val adapter : SearchRepoListAdapter) :
            RecyclerView.ViewHolder(viewBinding.root){
        init {
            with(viewBinding){
                with(adapter){
                    root.setOnClickListener { onItemClickSubject.onNext(currentList[adapterPosition]) }
                }
            }
        }

        fun bindView(item : SearchRepoInfo){
            with(viewBinding){
                tvSearchFullName.text = item.full_name ?: adapter.mContext.getString(R.string.repository_no_fullname_text)
                tvSearchLanguage.text = item.language ?: adapter.mContext.getString(R.string.repository_no_launguage_text)
                ImageViewUtils.setGlideImage(ivSearchAvatar, item.repoOwnerData!!.avatar_url)
            }
        }
    }
}