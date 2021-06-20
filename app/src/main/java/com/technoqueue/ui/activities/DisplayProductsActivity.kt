package com.technoqueue.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.technoqueue.R
import com.technoqueue.firestore.FirestoreClass
import com.technoqueue.models.Product
import com.technoqueue.models.Store
import com.technoqueue.ui.adapters.StoreProductsListAdapter
import com.technoqueue.utils.Constants
import kotlinx.android.synthetic.main.activity_display_products.*
import kotlinx.android.synthetic.main.activity_user_profile.*


class DisplayProductsActivity : BaseActivity() {

    private lateinit var mStoreDetails: Store

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_products)

        if (intent.hasExtra(Constants.EXTRA_STORE_DETAILS)) {
            mStoreDetails = intent.getParcelableExtra(Constants.EXTRA_STORE_DETAILS)!!
        }

        getProductListFromFireStore(mStoreDetails.user_id)

        setupActionBar()
    }

    fun deleteProduct(productID: String) {
        showAlertDialogToDeleteProduct(productID)
    }

    fun productDeleteSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@DisplayProductsActivity,
            resources.getString(R.string.product_delete_success_message),
            Toast.LENGTH_SHORT
        ).show()

        getProductListFromFireStore(mStoreDetails.user_id)
    }

    private fun showAlertDialogToDeleteProduct(productID: String) {

        val builder = AlertDialog.Builder(this@DisplayProductsActivity)

        builder.setTitle(resources.getString(R.string.delete_dialog_title))

        builder.setMessage(resources.getString(R.string.delete_dialog_message))
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, _ ->

            showProgressDialog(resources.getString(R.string.please_wait))

            FirestoreClass().deleteProduct(this@DisplayProductsActivity, productID)

            dialogInterface.dismiss()
        }

        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _ ->

            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun setupActionBar() {


        setSupportActionBar(toolbar_display_products_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_display_products_activity.setNavigationOnClickListener { onBackPressed() }
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

            val adapterProducts = StoreProductsListAdapter(this, productsList, this, mStoreDetails)
            rv_my_product_items.adapter = adapterProducts
        } else {
            rv_my_product_items.visibility = View.GONE
            tv_no_products_found.visibility = View.VISIBLE
        }
    }
}