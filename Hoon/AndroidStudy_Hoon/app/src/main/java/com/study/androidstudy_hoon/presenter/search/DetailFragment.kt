package com.study.androidstudy_hoon.presenter.search

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.study.androidstudy_hoon.data.dto.Repo
import com.study.androidstudy_hoon.databinding.FragmentSearchDetailBinding
import com.study.androidstudy_hoon.domain.base.BaseFragment

class DetailFragment :
    BaseFragment<FragmentSearchDetailBinding>(FragmentSearchDetailBinding::inflate) {

    lateinit var repo: Repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repo = it.getSerializable(ARG_PARAM) as Repo
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            repoDetailStarTextView.text = repo.stars.toString()
            searchDetailNameTextView.text = repo.fullName
            Glide.with(root.context)
                .load(repo.owner?.avatarUrl)
                .into(searchDetailImageView)
            repoDetailLanguageTextView.text = repo.language
            repoDetailDescriptionTextView.text = repo.description
            searchDetailMainContainer.setOnTouchListener { _, _ -> true }
        }

    }

    companion object {
        private const val ARG_PARAM = "repo"

        @JvmStatic
        fun newInstance(param: Repo) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM, param)
                }
            }
    }
}