package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ayan.snackymessages.SnackyMessages
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.fragments.NewsFragment
import com.example.newsapp.utils.CustomFunction
import com.example.newsapp.viewmodel.NewsViewModel

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

        CustomFunction().fragmentReplace(this@NewsActivity,NewsFragment() )


        viewModel.observeInternetConnection(this@NewsActivity)

    }

    override fun onStart() {
        super.onStart()
        viewModel.getInternetStatus.observe(this){status ->
            if(status)
                viewModel.loadNews("news")
            else
                snackyMessages.ShowSnackBarEror(bindingNewsActivity.root,"Internet Disconneted!!",R.id.newsactivity)
        }
    }
}