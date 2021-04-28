package com.androidhuman.example.simplegithub.ui.search

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.androidhuman.example.simplegithub.R
import com.androidhuman.example.simplegithub.api.model.GithubRepoDto
import com.androidhuman.example.simplegithub.databinding.ItemRepositoryBinding
import com.androidhuman.example.simplegithub.ui.GlideApp

/**
 * p.245 코틀린 익스텐션은 구글이 버리는것 같아서 적용하지 않았음. 대신 뷰 바인딩을 씁시다.
 */
class SearchAdapter : RecyclerView.Adapter<SearchAdapter.RepositoryHolder>() {
    private var items: MutableList<GithubRepoDto> = mutableListOf()
    private var listener: ItemClickListener? = null

    // 뷰 바인딩을 사용하면 깔삼하게 뷰홀더는 심플
    class RepositoryHolder(private val binding: ItemRepositoryBinding,
                           listener: ItemClickListener?,
                           items: List<GithubRepoDto>) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.onItemClick(items[adapterPosition])
            }
        }

        fun bind(items: GithubRepoDto) {
            with(itemView) {
                GlideApp.with(context)
                        .load(items.owner.avatarUrl)
                        .placeholder(ColorDrawable(Color.GRAY))
                        .into(binding.ivItemRepositoryProfile)
            }

            binding.tvItemRepositoryName.text = items.fullName
            binding.tvItemRepositoryLanguage.text = if (TextUtils.isEmpty(items.language))
                Resources.getSystem().getString(R.string.no_language_specified)
            else
                items.language

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        // 뷰 바인딩 사용
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryHolder(binding, listener, items)
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItem(items: List<GithubRepoDto>) {
        setItems(items)
        notifyDataSetChanged()
    }

    fun clearResults() {
        clearItems()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
            items.size

    fun setItems(items: List<GithubRepoDto>) {
        // 인자로 받은 리스트의 형태를 어댑터 내부에서 사용하는 리스트 형태(내부자료 변경이 가능한 형태)로 변환해주어야 한단당
        this.items = items.toMutableList()
    }

    fun setItemClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    fun clearItems() {
        items.clear()
    }

    interface ItemClickListener {
        fun onItemClick(repository: GithubRepoDto)
    }
}