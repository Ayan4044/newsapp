package com.example.newsapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.ayan.snackymessages.SnackyMessages
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.Articles
import com.example.newsapp.model.DataClassNews
import com.example.newsapp.utils.ScreenState
import com.example.newsapp.viewmodel.NewsViewModel
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment(), LifecycleObserver {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //view model repo
    private val viewModel : NewsViewModel by lazy {
        ViewModelProvider(requireActivity())[NewsViewModel::class.java]
    }

    private val snackyMessages : SnackyMessages by lazy {
        SnackyMessages()
    }



    //view binding
    private var _bindingFragmentNews: FragmentNewsBinding ?= null
    private val getBindingNewsFragment get() = _bindingFragmentNews!!

    //arraylist
    private lateinit var newsArrayList: ArrayList<Articles>
    private  lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingFragmentNews = FragmentNewsBinding.inflate(layoutInflater, container, false)

        return  getBindingNewsFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsArrayList = ArrayList()
        newsAdapter = NewsAdapter(requireContext(), newsArrayList)

        getBindingNewsFragment.shimmerlayoutnews.visibility = View.VISIBLE



        getBindingNewsFragment.autoNewstype.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1, arrayOf(
                    "news",
                    "tech",
                    "world",
                    "finance",
                    "politics",
                    "business",
                    "economics",
                    "entertainment",
                    "science"
                )
            )
        )

        getBindingNewsFragment.autoNewstype.setOnFocusChangeListener { v, hasFocus -> if (hasFocus) getBindingNewsFragment.autoNewstype.showDropDown() }

        getBindingNewsFragment.autoNewstype.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, pos, l ->

                viewModel.loadNews(getBindingNewsFragment.autoNewstype.text.toString())
            }


        newsAdapter.onItemClickListener(object: NewsAdapter.OnItemClickListener{
            override fun viewdetails(pos: Int) {
                var article = newsArrayList[pos]
                
            }

        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycle.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onCreated() {

        getBindingNewsFragment.recyclerviewNewslive.adapter = newsAdapter

        viewModel.getAllnews.observe(viewLifecycleOwner) { newsLiveData ->
            if (newsLiveData == null)
                return@observe
            else
                populatenws(newsLiveData)
        }
    }

    private fun populatenws(newsLiveData: ScreenState<DataClassNews?>) {

        when(newsLiveData){
            is ScreenState.Loading ->{
                getBindingNewsFragment.recyclerviewNewslive.visibility = View.GONE
                getBindingNewsFragment.shimmerlayoutnews.visibility = View.VISIBLE
                getBindingNewsFragment.shimmerlayoutnews.startShimmer()
            }
            is ScreenState.Success ->{
                if(newsLiveData.data != null)
                {

                  //  Timber.tag("Camp List").e(Gson().toJson(campListState.data))
                    newsArrayList.clear()

                    getBindingNewsFragment.shimmerlayoutnews.stopShimmer()
                    getBindingNewsFragment.shimmerlayoutnews.visibility = View.GONE


                    if(newsLiveData.data.pageSize ==0){
                        snackyMessages.ShowSnackbarErrorLite(getBindingNewsFragment.root, "No News Available!!", R.id.fragmentNews)

                    }
                    else {

                        getBindingNewsFragment.recyclerviewNewslive.visibility = View.VISIBLE
                        newsArrayList.addAll(newsLiveData.data.articles)
                    }



                }
                else {
                    snackyMessages.ShowSnackbarErrorLite(getBindingNewsFragment.root, "Something went wrong!!", R.id.fragmentNews)

                    getBindingNewsFragment.shimmerlayoutnews.stopShimmer()
                    getBindingNewsFragment.shimmerlayoutnews.visibility = View.GONE



//                    Timer().schedule(2000) {
//                        CustomFunction().fragmentReplace(requireActivity(),Fragment_newhome())
//                    }

                }
            }
            is ScreenState.Error ->{
                getBindingNewsFragment.recyclerviewNewslive.visibility = View.GONE
                getBindingNewsFragment.shimmerlayoutnews.visibility = View.GONE
                getBindingNewsFragment.shimmerlayoutnews.stopShimmer()
                snackyMessages.ShowSnackbarErrorLite(getBindingNewsFragment.root, "Something went wrong!!", R.id.fragmentNews)

            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}