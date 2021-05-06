package com.example.kunny_exam.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kunny_exam.R
import com.example.kunny_exam.SearchRepoListAdapter
import com.example.kunny_exam.databinding.ActivityMainBinding
import com.example.kunny_exam.dto.SearchRepoInfo
import com.example.kunny_exam.model.dto.toSearchRepoInfo
import com.example.kunny_exam.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val repoAdapter by lazy { SearchRepoListAdapter(this) }
    private val fragment by lazy { SearchFragment().newInstance() }

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        showDBList()
        observeDBList()

        setContentView(binding.root)
    }

    private fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.tbMain)

        binding.fabMain.setOnClickListener {
            supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .add(binding.flMain.id, fragment)
                    .commit()
        }
    }

    private fun observeDBList(){
        viewModel.roomRepoList.observe(this,
            androidx.lifecycle.Observer { items ->
                if(items.isEmpty()){
                    binding.tvMainEmpty.visibility = View.VISIBLE
                    binding.rvMain.visibility = View.GONE
                }else{
                    binding.tvMainEmpty.visibility = View.GONE
                    binding.rvMain.visibility = View.VISIBLE

                    with(repoAdapter){
                        val entityToSearchRepo = mutableListOf<SearchRepoInfo>().also {
                            it.addAll(items.map { entity -> entity.toSearchRepoInfo() })
                        }
                        submitList(entityToSearchRepo)
                    }
                }

        })
    }

    private fun showDBList(){
        viewModel.getRoomSearchList()
    }
}