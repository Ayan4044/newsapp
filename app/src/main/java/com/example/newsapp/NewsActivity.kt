package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ayan.snackymessages.SnackyMessages
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.fragments.NewsFragment
import com.example.newsapp.fragments.NewsFragmentCached

import com.example.newsapp.utils.CustomFunction
import com.example.newsapp.viewmodel.NewsViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {

    private lateinit var bindingNewsActivity: ActivityNewsBinding

    //view model repo
    private val viewModel : NewsViewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    private val snackyMessages : SnackyMessages by lazy {
        SnackyMessages()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingNewsActivity = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(bindingNewsActivity.root)





        viewModel.observeInternetConnection(this@NewsActivity)





    }

    override fun onStart() {
        super.onStart()

        viewModel.getInternetStatus.observe(this) { status ->
            if (status) {
                viewModel.loadNews("news")
                CustomFunction().fragmentReplace(this@NewsActivity,NewsFragment() )
            }
            else {
                snackyMessages.ShowSnackBarEror(
                    bindingNewsActivity.root,
                    "No Internet Connection!!",
                    R.id.newsactivity
                )
                viewModel.getLocalNews()
                //
                CustomFunction().fragmentReplace(this@NewsActivity,NewsFragmentCached() )
            }
        }




    }
}