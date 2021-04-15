package com.study.androidstudy_hoon.presenter.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.study.androidstudy_hoon.data.dto.Repo
import com.study.androidstudy_hoon.databinding.FragmentSearchDetailBinding

class DetailFragment : Fragment() {
    private var repo: Repo? = null
    private var binding : FragmentSearchDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repo = it.getSerializable(ARG_PARAM) as? Repo
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchDetailBinding.inflate(inflater, container, false)

        binding?.apply {
            repoDetailStarTextView.text = repo?.stars.toString()
            searchDetailNameTextView.text = repo?.fullName
            Glide.with(root.context)
                    .load(repo?.owner?.avatarUrl)
                    .into(searchDetailImageView)
            repoDetailLanguageTextView.text = repo?.language
            repoDetailDescriptionTextView.text = repo?.description
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val ARG_PARAM = "repo"

        @JvmStatic
        fun newInstance(param: Repo?) =
                DetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM, param)
                    }
                }
    }
}