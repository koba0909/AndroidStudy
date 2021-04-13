package com.example.kunny_exam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kunny_exam.common.ImageViewUtils
import com.example.kunny_exam.data.SearchRepoInfo
import com.example.kunny_exam.databinding.LayoutSearchItemBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject




class SearchRepoAdapter : RecyclerView.Adapter<SearchRepoAdapter.SearchRepoViewHolder>() {
    private var items: List<SearchRepoInfo>? =  null
    private var listener : ItemClickListener? = null
    private var onItemClickSubject : PublishSubject<SearchRepoInfo> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRepoViewHolder {
        val binding = LayoutSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchRepoViewHolder(binding)
    }

    fun getOnItemClickObservable() : Observable<SearchRepoInfo>{
        return onItemClickSubject
    }

    override fun getItemCount(): Int = items!!.size

    override fun onBindViewHolder(holder: SearchRepoViewHolder, position: Int) {
        holder.viewBinding.tvSearchFullName.text = items!![position].full_name ?: "no_name"
        holder.viewBinding.tvSearchLanguage.text = items!![position].language ?: "No Language Specified"
        ImageViewUtils.setGlideImage(holder.viewBinding.ivSearchAvatar, items!![position].ownerData!!.avatar_url)
        holder.viewBinding.root.setOnClickListener { onItemClickSubject.onNext(items!![position]) }
    }

    fun setData(items : List<SearchRepoInfo>){
        this.items = items
    }

    fun setListener(l : ItemClickListener) {
        this.listener = l
    }

    class SearchRepoViewHolder(val viewBinding: LayoutSearchItemBinding) : RecyclerView.ViewHolder(viewBinding.root)
}
