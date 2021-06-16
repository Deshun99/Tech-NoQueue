package com.technoqueue.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.technoqueue.R
import com.technoqueue.firestore.FirestoreClass
import com.technoqueue.models.Product
import com.technoqueue.models.User
import com.technoqueue.ui.adapters.MyProductsListAdapter
import com.technoqueue.ui.adapters.StoreProductsListAdapter
import com.technoqueue.utils.Constants
import kotlinx.android.synthetic.main.activity_display_products.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class DisplayProductsActivity : BaseActivity() {

    private lateinit var mStoreOwnerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_products)

        if (intent.hasExtra(Constants.EXTRA_STORE_OWNER_ID)) {
            mStoreOwnerId = intent.getParcelableExtra(Constants.EXTRA_STORE_OWNER_ID)!!
        }

        getProductListFromFireStore(mStoreOwnerId)

        setupActionBar()
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_user_profile_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_user_profile_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getProductListFromFireStore(storeOwnerId: String) {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getStoreProductsList(this, storeOwnerId)
    }

    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
        hideProgressDialog()

        if (productsList.size > 0) {
            rv_my_product_items.visibility = View.VISIBLE
            tv_no_products_found.visibility = View.GONE

            rv_my_product_items.layoutManager = LinearLayoutManager(this)
            rv_my_product_items.setHasFixedSize(true)

            val adapterProducts = StoreProductsListAdapter(this, productsList, this)
            rv_my_product_items.adapter = adapterProducts
        } else {
            rv_my_product_items.visibility = View.GONE
            tv_no_products_found.visibility = View.VISIBLE
        }
    }
}