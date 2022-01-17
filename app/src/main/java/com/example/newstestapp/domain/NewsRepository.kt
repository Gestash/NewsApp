package com.example.newstestapp.domain

import com.example.newstestapp.constants.Constants.APIKEY
import com.example.newstestapp.constants.Constants.APIKEY_STRING
import com.example.newstestapp.constants.Constants.COUNTRY_STRING
import com.example.newstestapp.constants.Constants.DOMAINS_STRING
import com.example.newstestapp.constants.Constants.FROM_STRING
import com.example.newstestapp.constants.Constants.LANGUAGE_STRING
import com.example.newstestapp.constants.Constants.PUBLISHEDAT_STRING
import com.example.newstestapp.constants.Constants.SORTBY_STRING
import java.util.*

class NewsRepository(val service: NewsService) {
    suspend fun getNews(): NewsData? {
        val lang = Locale.getDefault().language
        val options =
            mapOf(
                DOMAINS_STRING to "techcrunch.com",
                LANGUAGE_STRING to lang,
                SORTBY_STRING to "popularity",
                APIKEY_STRING to APIKEY
            )

        return service.getNews(options)
    }

    suspend fun getTopNews(date: String): NewsData? {
        val options =
            mapOf(
                FROM_STRING to date,
                SORTBY_STRING to PUBLISHEDAT_STRING,
                COUNTRY_STRING to "us",
                APIKEY_STRING to APIKEY
            )
        return service.getTopNews(options)
    }
}