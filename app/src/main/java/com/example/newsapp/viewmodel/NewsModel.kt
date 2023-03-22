package com.example.newsapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.model.DataClassNews
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.ScreenState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import retrofit2.awaitResponse
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketTimeoutException

class NewsModel (application: Application): AndroidViewModel(application) {

    private lateinit var newsRepository: NewsRepository

    val context: Context = application.applicationContext

    private val gson: Gson by lazy {
        Gson()
    }

    val authkey: String =""

    //live data
    private var _liveDataNews: MutableLiveData<ScreenState<DataClassNews?>> =
        MutableLiveData()
    val getAllnews: LiveData<ScreenState<DataClassNews?>> get() = _liveDataNews


    fun loadDoctorProfile(topic: String ) {
        _liveDataNews.postValue(ScreenState.Loading(null))


        val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
            throwable.printStackTrace()
            _liveDataNews.postValue(ScreenState.Error(throwable.message.toString(), null))
        }



        try {


            viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
                val callDoctorProfileResponse = newsRepository.getAllNews(
                    authkey,  topic
                ).awaitResponse()

                if (callDoctorProfileResponse.isSuccessful)
                    _liveDataNews.postValue(ScreenState.Success(callDoctorProfileResponse.body()))
                else {
                    _liveDataNews.postValue(
                        ScreenState.Error(
                            callDoctorProfileResponse.code().toString(),
                            null
                        )
                    )

                }
            }
        } catch (ex: ConnectException) {
            _liveDataNews.postValue(ScreenState.Error(ex.message.toString(), null))

        } catch (ex: ProtocolException) {
            _liveDataNews.postValue(ScreenState.Error(ex.message.toString(), null))

        } catch (ex: SocketTimeoutException) {
            _liveDataNews.postValue(ScreenState.Error(ex.message.toString(), null))

        }
    }
}