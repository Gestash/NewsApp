package com.example.newstestapp.ui.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newstestapp.R
import com.example.newstestapp.model.NewsUiData
import com.example.newstestapp.ui.home.HomeNewsAdapter
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<NewsUiData>?) {
    val adapter = recyclerView.adapter as HomeNewsAdapter
    adapter.submitList(data)
}

enum class NewsApiStatus { LOADING, ERROR, DONE }

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: NewsApiStatus?) {
    when (status) {
        NewsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NewsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        NewsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE

        }
    }
}

@BindingAdapter("date")
fun bindDate(textView: TextView, publishedAt: String?) {
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    df.timeZone = TimeZone.getTimeZone("UTC")
    val localDateTime =
        publishedAt?.let {
            df.parse(it)
        }

    val df2 = SimpleDateFormat("MMMM dd yyyy, HH:mm", Locale.getDefault())

    textView.text = localDateTime?.let { df2.format(it) }

}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (imgUrl == null) {
        imgView.visibility = View.GONE
    } else {
        imgView.visibility = View.VISIBLE
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .centerCrop()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}