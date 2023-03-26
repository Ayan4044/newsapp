package com.example.newsapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.ayan.snackymessages.SnackyMessages
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentNewsCachedBinding
import com.example.newsapp.model.Articles
import com.example.newsapp.model.DataClassNews
import com.example.newsapp.room.model.News
import com.example.newsapp.utils.CheckInternet
import com.example.newsapp.utils.CustomFunction
import com.example.newsapp.utils.ScreenState
import com.example.newsapp.viewmodel.NewsViewModel
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragmentCached.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragmentCached : Fragment(), LifecycleObserver {

    //view model repo
    private val viewModel : NewsViewModel by lazy {
        ViewModelProvider(requireActivity())[NewsViewModel::class.java]
    }

    private val snackyMessages : SnackyMessages by lazy {
        SnackyMessages()
    }

    //view binding
    private var _bindingFragmentNewsCached: FragmentNewsCachedBinding ?= null
    private val getFragmentNewsCachedBinding get() = _bindingFragmentNewsCached!!

    //arraylist
    private lateinit var newsArrayList: ArrayList<Articles>
    private  lateinit var newsAdapter: NewsAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        _bindingFragmentNewsCached =  FragmentNewsCachedBinding.inflate(inflater, container, false)

        return  getFragmentNewsCachedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsArrayList = ArrayList()
        newsAdapter = NewsAdapter(requireContext(), newsArrayList)

        getFragmentNewsCachedBinding.animationView.visibility = View.GONE

        getFragmentNewsCachedBinding.imageButtonback.setOnClickListener {
            if(CheckInternet().isConnected(requireContext())){
                viewModel.loadNews("news")
                CustomFunction().fragmentReplace(requireActivity(),NewsFragment() )
            }
            else
                snackyMessages.ShowSnackbarErrorLite(getFragmentNewsCachedBinding.root, "Please connect to the internet!!", R.id.fragmentnewschached)


        }

        newsAdapter.onItemClickListener(object: NewsAdapter.OnItemClickListener{
            override fun viewdetails(pos: Int) {


               viewModel.setArtcile(newsArrayList[pos])


                CustomFunction().fragmentReplace(requireActivity(),FragmentNewsDetailsCached() )
             //   Log.d("Article","${newsArrayList[pos]}")
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragmentCached.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragmentCached().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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


        viewModel.getInternetStatus.observe(this) { status ->
            if (status)
            {
                snackyMessages.ShowSnackbarSuccess(
                    getFragmentNewsCachedBinding.root,
                    " Internet Connection Avaialble!!",
                    R.id.fragmentnewschached
                )

            }
            else
                snackyMessages.ShowSnackBarEror(
                    getFragmentNewsCachedBinding.root,
                    "No Internet Connection!!",
                    R.id.fragmentnewschached
                )
        }

        viewModel.getLastCachedNews.observe(viewLifecycleOwner){
                lastCachedNews ->
            if(lastCachedNews == null)
                return@observe
            else{
                if(lastCachedNews.isEmpty())
                    getFragmentNewsCachedBinding.animationView.visibility = View.VISIBLE

                else{
                    getFragmentNewsCachedBinding.animationView.visibility = View.GONE
                    val dataclassnews = Gson().fromJson(
                        lastCachedNews, DataClassNews::class.java
                    )

                    viewModel.setCachedNews(dataclassnews)
                }
            }
        }


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

            }
            is ScreenState.Success ->{
                if(newsLiveData.data != null)
                {

                    //  Timber.tag("Camp List").e(Gson().toJson(campListState.data))
                    newsArrayList.clear()




                    if(newsLiveData.data.pageSize ==0){
                        snackyMessages.ShowSnackbarErrorLite(getFragmentNewsCachedBinding.root, "No LocalNews Available!!", R.id.fragmentnewschached)

                    }
                    else {
                       // Log.e("News Data Local","${Gson().toJson(newsLiveData)}")
                       // getFragmentNewsCachedBinding.recylerviewcached.visibility = View.VISIBLE
                        newsArrayList.addAll(newsLiveData.data.articles)
                        getFragmentNewsCachedBinding.recylerviewcached.adapter = newsAdapter
                    }



                }
                else {
                    snackyMessages.ShowSnackbarErrorLite(getFragmentNewsCachedBinding.root, "Something went wrong!!", R.id.fragmentnewschached)



//                    Timer().schedule(2000) {
//                        CustomFunction().fragmentReplace(requireActivity(),Fragment_newhome())
//                    }

                }
            }
            is ScreenState.Error ->{

                snackyMessages.ShowSnackbarErrorLite(getFragmentNewsCachedBinding.root, "Something went wrong!!", R.id.fragmentNews)

            }
        }

    }
}