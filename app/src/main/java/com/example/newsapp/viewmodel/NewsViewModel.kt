package com.example.newsapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.newsapp.api.RetrofitSingleton
import com.example.newsapp.model.Articles
import com.example.newsapp.model.DataClassNews
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.CheckInternet
import com.example.newsapp.utils.ScreenState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketTimeoutException

class NewsViewModel (application: Application): AndroidViewModel(application) {

    companion object {
        const val AuthKey = "izsiOpnkJLbJxrtTjgm8jEJnuHPiDHZaCsDvVITfJzc"
    }

    private lateinit var newsRepository: NewsRepository
    init {
//        val drugRoomDatabase = DrugDataBase.getDatabase(application)
//        var roomDao: RoomDao = drugRoomDatabase.drugDao()
        newsRepository = NewsRepository(
            RetrofitSingleton,
        )
    }



    val context: Context = application.applicationContext

    private val gson: Gson by lazy {
        Gson()
    }

    //connection data
    private var _checkInternetLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val getInternetStatus: LiveData<Boolean> get() = _checkInternetLiveData

    //live data news
    private var _liveDataNews: MutableLiveData<ScreenState<DataClassNews?>> =
        MutableLiveData()
    val getAllnews: LiveData<ScreenState<DataClassNews?>> get() = _liveDataNews

    //live data
    private var _liveDataArticle: MutableLiveData<Articles?> =
        MutableLiveData()
    val getArticle: LiveData<Articles?> get() = _liveDataArticle




    fun loadNews(topic: String ) {
        _liveDataNews.postValue(ScreenState.Loading(null))


        val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
            throwable.printStackTrace()
            _liveDataNews.postValue(ScreenState.Error(throwable.message.toString(), null))
        }



            viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
                val callDoctorProfileResponse = newsRepository.getAllNews(
                    AuthKey,  topic
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
    }


    fun observeInternetConnection(context:Context){

        if(CheckInternet().checkConnection(context))
            _checkInternetLiveData.postValue(true)
        else
            _checkInternetLiveData.postValue(false)

    }


    fun setArtcile(articles: Articles){
        _liveDataArticle.postValue(articles)
    }
}