package com.example.newstestapp.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstestapp.R
import com.example.newstestapp.domain.NewsData
import com.example.newstestapp.domain.NewsRepository
import com.example.newstestapp.model.NewsUiData
import com.example.newstestapp.ui.views.NewsApiStatus
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(private val newsRepository: NewsRepository): //, private val context: Context) :
    ViewModel() {

    private val _newsData = MutableLiveData<List<NewsUiData>>()
    val listNewsData: LiveData<List<NewsUiData>>
        get() = _newsData

    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _newsTopHeadline = MutableLiveData<NewsUiData>()
    val newsTopHeadline: LiveData<NewsUiData>
        get() = _newsTopHeadline

    private val _navigateToSelectedArticle = MutableLiveData<NewsUiData>()

    val navigateToSelectedProperty: LiveData<NewsUiData>
        get() = _navigateToSelectedArticle

    init {
        getCurrentData()
    }

    private fun getCurrentData(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-d")
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun getNewsFromNet() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                parseData(newsRepository.getNews() ?: return@launch)
                _status.value = NewsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _newsData.value = ArrayList()
            }
        }
    }

    fun getTopNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                parseTopHeadlineData(newsRepository.getTopNews(getCurrentData()) ?: return@launch)
                _status.value = NewsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
            }
        }
    }

    private fun parseData(data: NewsData) {
        if (data.articles.isNotEmpty()) {
            val uiList: List<NewsUiData> = data.articles.map { article ->
                NewsUiData(
                    author = article.author,
                    url = article.url,
                    title = article.title,
                    imageUrl = article.urlToImage,
                    content = article.content,
                    publishedAt = article.publishedAt
                )
            }
            _newsData.postValue(uiList)
        }
    }

    private fun parseTopHeadlineData(data: NewsData) {
        if (data.articles.isNotEmpty()) {
            val uiList: List<NewsUiData> = data.articles.map { article ->
                NewsUiData(
                    author = article.author,
                    url = article.url,
                    title = article.title,
                    imageUrl = article.urlToImage,
                    content = article.content,
                    publishedAt = article.publishedAt
                )
            }
            _newsTopHeadline.postValue(uiList.first())
        }

    }



    fun displayPropertyDetails(data: NewsUiData) {
        _navigateToSelectedArticle.value = data
    }

    fun displayPropertyDetails() {
        _navigateToSelectedArticle.value = newsTopHeadline.value
    }

    fun displayPropertyDetailsComplete() {
//        _navigateToSelectedArticle.value = null
    }
}