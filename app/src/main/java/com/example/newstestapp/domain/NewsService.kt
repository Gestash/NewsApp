package com.example.newstestapp.domain

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsService {
    @GET("everything")
    suspend fun getNews(@QueryMap options: Map<String, String>): NewsData

    @GET("top-headlines")
    suspend fun getTopNews(@QueryMap options: Map<String, String>): NewsData
}