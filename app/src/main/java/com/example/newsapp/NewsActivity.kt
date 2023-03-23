package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.fragments.NewsFragment
import com.example.newsapp.viewmodel.NewsViewModel

class NewsActivity : AppCompatActivity() {

    private lateinit var bindingNewsActivity: ActivityNewsBinding

    //view model repo
    private val viewModel : NewsViewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingNewsActivity = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(bindingNewsActivity.root)


        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            NewsFragment()
        ).commit()

        viewModel.loadDoctorProfile("news")
    }
}