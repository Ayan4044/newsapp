package com.example.newsapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.newsapp.api.RetrofitSingleton
import com.example.newsapp.model.Articles
import com.example.newsapp.model.DataClassNews
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.room.dao.NewsDao
import com.example.newsapp.room.database.LocalDatabase
import com.example.newsapp.room.model.News

import com.example.newsapp.utils.CheckInternet
import com.example.newsapp.utils.ScreenState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class NewsViewModel (application: Application): AndroidViewModel(application) {

    companion object {
       // const val AuthKey = "izsiOpnkJLbJxrtTjgm8jEJnuHPiDHZaCsDvVITfJzc"
        const val AuthKey="tk_WzzXXqaC7swDNXkB4nSo9kVP0egS_6eH20xN14ko"
    }

    private  var newsRepository: NewsRepository
    init {
       val newsRoomDatabase = LocalDatabase.getDatabase(application)
        var roomDao: NewsDao = newsRoomDatabase.newsDao()
        newsRepository = NewsRepository(
            RetrofitSingleton,
          roomDao
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

    private var   _livedLocalNewsList: LiveData<List<News>?> = MutableLiveData()
    val getAllNewsList: LiveData<List<News>?> get() = _livedLocalNewsList

    private var   _liveLastChachedNews: LiveData<String?> = MutableLiveData()
    val getLastCachedNews: LiveData<String?> get() = _liveLastChachedNews





    fun setCachedNews(dataClassNews: DataClassNews){
        _liveDataNews.postValue(ScreenState.Success(dataClassNews))
    }



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

    //room db calls
    fun insertNewsLocal(dataClassNewsLocal: News) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.insertNews(dataClassNewsLocal)
            Log.e("Data","Inserted")
        }
    }

    fun getLocalNews() {

        _livedLocalNewsList = newsRepository.getCachedNews

        _liveLastChachedNews = newsRepository.getLastSavedNews

        Log.e("Data","$_livedLocalNewsList")
        //Gson().fromJson(decrypted, DataClassDeptUnitRes::class.java)
//       val liveAllNews: LiveData<List<LocalNews?>?> = newsRepository.getAllSavednews
//
//        _livedLocalNewsList = newsRepository.getAllSavednews
//
//        Log.e("Data All:"," ${liveAllNews?.value}" )
//         var liveSavedLocalNews: LiveData<String?>? = newsRepository.getCachedNews
//        Log.e("Data:"," ${liveSavedLocalNews?.value}" )
//        viewModelScope.launch(Dispatchers.IO) {
//            if(liveSavedLocalNews?.value == null){
//                Log.e("Msg","Null Data")
//
//            }
//            else {
//                val dataClassNews = Gson().fromJson(
//                    liveSavedLocalNews?.value,
//                    DataClassNews::class.java
//                )
//                _liveDataNews.postValue(
//                    ScreenState.Success(dataClassNews)
//                )
//            }
        }


}