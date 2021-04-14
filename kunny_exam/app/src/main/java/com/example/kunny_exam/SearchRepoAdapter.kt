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
    private lateinit var items: List<SearchRepoInfo>
    private var onItemClickSubject : PublishSubject<SearchRepoInfo> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRepoViewHolder {
        val binding = LayoutSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchRepoViewHolder(binding, this)
    }

    fun getOnItemClickObservable() : Observable<SearchRepoInfo>{
        return onItemClickSubject
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SearchRepoViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    fun setData(items : List<SearchRepoInfo>){
        this.items = items
    }

    class SearchRepoViewHolder(private val viewBinding: LayoutSearchItemBinding,
    private val adapter : SearchRepoAdapter) :
        RecyclerView.ViewHolder(viewBinding.root){
        init {
            with(viewBinding){
                with(adapter){
                    root.setOnClickListener { onItemClickSubject.onNext(items[adapterPosition]) }
                }
            }
        }

        fun bindView(item : SearchRepoInfo){
            with(viewBinding){
                tvSearchFullName.text = item.full_name ?: "no_name"
                tvSearchLanguage.text = item.language ?: "No Language Specified"
                ImageViewUtils.setGlideImage(ivSearchAvatar, item.ownerData!!.avatar_url)
            }
        }
    }
}
