package com.example.kunny_exam

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kunny_exam.common.ImageViewUtils
import com.example.kunny_exam.data.SearchRepoInfo
import com.example.kunny_exam.databinding.LayoutSearchItemBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SearchRepoAdapter(private val mContext : Context) : RecyclerView.Adapter<SearchRepoAdapter.SearchRepoViewHolder>() {
    private lateinit var items: List<SearchRepoInfo>
    private val onItemClickSubject : PublishSubject<SearchRepoInfo> = PublishSubject.create()

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
        notifyDataSetChanged()
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
                tvSearchFullName.text = item.full_name ?: adapter.mContext.getString(R.string.repository_no_fullname_text)
                tvSearchLanguage.text = item.language ?: adapter.mContext.getString(R.string.repository_no_launguage_text)
                ImageViewUtils.setGlideImage(ivSearchAvatar, item.ownerData!!.avatar_url)
            }
        }
    }
}
