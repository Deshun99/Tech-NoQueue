package com.technoqueue.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Store (
    val user_id: String = "",
    val user_name: String = "",
    val title: String = "",
    val description: String = "",
    val image: String = "",
    var store_id: String = "",
): Parcelable