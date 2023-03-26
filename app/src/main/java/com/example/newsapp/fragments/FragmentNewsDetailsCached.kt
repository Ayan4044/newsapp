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
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsDetailsCachedBinding
import com.example.newsapp.utils.CustomFunction
import com.example.newsapp.viewmodel.NewsViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentNewsDetailsCached.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentNewsDetailsCached : Fragment(), LifecycleObserver {
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
    private var _bindingFragmentNewsDetailsCached: FragmentNewsDetailsCachedBinding? = null
    private val getFragmentDetailsCachedBinding get() = _bindingFragmentNewsDetailsCached!!

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
        _bindingFragmentNewsDetailsCached =  FragmentNewsDetailsCachedBinding.inflate(inflater, container, false)

        return  getFragmentDetailsCachedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFragmentDetailsCachedBinding.imageButtonback.setOnClickListener {
            CustomFunction().fragmentReplace(requireActivity(),NewsFragmentCached() )
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentNewsDetailsCached.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentNewsDetailsCached().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onCreated() {



        viewModel.getArticle.observe(viewLifecycleOwner) { newsArticle ->
            if (newsArticle == null)
                return@observe
            else
            {
                Log.d("Article Get","$newsArticle")
                getFragmentDetailsCachedBinding.textViewtitle.text = newsArticle.title
                getFragmentDetailsCachedBinding.textViewDescription.text = newsArticle.summary

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
}