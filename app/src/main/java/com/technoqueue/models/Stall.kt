package com.technoqueue.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stall (
    val user_id: String = "",
    val user_name: String = "",
    val title: String = "",
    val items: ArrayList<Product> = ArrayList(),
    val description: String = "",
    val image: String = "",
    var product_id: String ="",
): Parcelable