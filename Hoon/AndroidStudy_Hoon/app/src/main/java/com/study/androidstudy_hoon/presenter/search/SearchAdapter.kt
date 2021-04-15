package com.study.androidstudy_hoon.presenter.search

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.study.androidstudy_hoon.data.dto.Repo
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class SearchAdapter : ListAdapter<Repo, SearchViewHolder>(REPO_COMPARATOR) {

    private val _clickSubject = PublishSubject.create<Repo>()
    val clickSubject: Subject<Repo>
        get() = _clickSubject


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder = SearchViewHolder.create(parent)

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position), clickSubject)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem == newItem
        }
    }
}
