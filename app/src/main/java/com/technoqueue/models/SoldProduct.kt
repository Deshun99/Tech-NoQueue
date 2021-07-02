package com.technoqueue.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SoldProduct(
    val user_id: String = "",
    val customer_id: String = "",
    val store: String = "",
    val title: String = "",
    val price: String = "",
    val sold_quantity: String = "",
    val image: String = "",
    val order_id: String = "",
    val order_date: Long = 0L,
    val total_amount: String = "",
    val status: String = "",
    var id: String = "",
) : Parcelable