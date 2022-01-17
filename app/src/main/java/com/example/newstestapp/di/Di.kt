package com.example.newstestapp.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newstestapp.domain.NewsRepository
import com.example.newstestapp.domain.NewsService
import com.example.newstestapp.ui.home.HomeViewModel
import com.example.newstestapp.ui.views.MarginDecoration
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Di {

    private const val OPEN_NEWS_API = "https://newsapi.org/v2/"

    private val viewModelModule = module {
        viewModel { HomeViewModel(get()) }
        single { MarginDecoration(androidContext()) }
    }

    private val repositoriesModule = module {
        single { NewsRepository(get()) }
    }

    private val servicesModule = module {
        single {
            val retrofit: Retrofit = get()
            retrofit.create(NewsService::class.java)
        }
    }

    private val dataSourceModule = module {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        single {
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(OPEN_NEWS_API)
                .client(client)
                .build()
        }
    }

    val koinModules = arrayListOf(viewModelModule, repositoriesModule, servicesModule, dataSourceModule)

}