package com.technoqueue.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val user_id: String = "",
    val items: ArrayList<Cart> = ArrayList(),
    val title: String = "",
    val image: String = "",
    val total_amount: String = "",
    val order_datetime: Long = 0L,
    var id: String = ""
    ) : Parcelable