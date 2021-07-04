package com.technoqueue.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.technoqueue.R
import com.technoqueue.firestore.FirestoreClass
import com.technoqueue.models.SoldProduct
import com.technoqueue.models.User
import com.technoqueue.notification.FirebaseService
import com.technoqueue.notification.NotificationData
import com.technoqueue.notification.PushNotification
import com.technoqueue.notification.RetrofitInstance
import com.technoqueue.utils.Constants
import com.technoqueue.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.activity_sold_product_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SoldProductDetailsActivity : BaseActivity() {

    val TAG = "SoldProduct"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sold_product_details)

        var productDetails: SoldProduct = SoldProduct()

        if (intent.hasExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS)) {
            productDetails =
                intent.getParcelableExtra<SoldProduct>(Constants.EXTRA_SOLD_PRODUCT_DETAILS)!!
        }

        setupUI(productDetails)

        setupActionBar()

        btn_complete_order.visibility = View.VISIBLE

        btn_complete_order.setOnClickListener {
            updateDetails(productDetails)
            setupUI(productDetails)
            val title = productDetails.store
            val message = productDetails.title + " is ready for collection!"
            FirestoreClass().getToken(this@SoldProductDetailsActivity, productDetails.customer_id, title, message)
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_sold_product_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_sold_product_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupUI(productDetails: SoldProduct) {

        tv_sold_product_details_id.text = productDetails.order_id

        val dateFormat = "dd MMM yyyy HH:mm"

        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = productDetails.order_date
        tv_sold_product_details_date.text = formatter.format(calendar.time)
        tv_sold_product_details_status.text = productDetails.status

        GlideLoader(this@SoldProductDetailsActivity).loadProductPicture(
            productDetails.image,
            iv_product_item_image
        )
        tv_product_item_name.text = productDetails.title
        tv_product_item_price.text ="$${productDetails.price}"
        tv_sold_product_quantity.text = productDetails.sold_quantity
        
        tv_sold_product_total_amount.text = productDetails.total_amount
    }

    fun updateDetails(productDetails: SoldProduct) {
        FirestoreClass().updateID(this, productDetails)
        FirestoreClass().updateSoldProductDetail(this, productDetails)
    }

    fun productsUpdatedSuccessfully() {
        Toast.makeText(this@SoldProductDetailsActivity, "Product is completed.", Toast.LENGTH_SHORT)
            .show()
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if (response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    fun getTokenSuccess(user: User, title: String, message: String) {
        var recipientToken = user.token
        Log.i("token", recipientToken)
        if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
            PushNotification(
                NotificationData(title, message),
                recipientToken
            ).also {
                sendNotification(it)
            }
        }
    }
}
