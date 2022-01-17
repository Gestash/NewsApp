package com.example.newstestapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsUiData(
    var author: String?,
    var url: String?,
    var title: String?,
    var imageUrl: String?,
    var content: String?,
    val publishedAt: String?
) : Parcelable