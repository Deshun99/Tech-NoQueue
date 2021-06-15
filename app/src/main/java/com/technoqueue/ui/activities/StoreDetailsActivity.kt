package com.technoqueue.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.technoqueue.R
import com.technoqueue.firestore.FirestoreClass
import com.technoqueue.models.Store
import com.technoqueue.utils.Constants
import com.technoqueue.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_store_details.*

class StoreDetailsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mStoreDetails: Store
    private var mStoreId: String = ""
    private var mStoreOwnerId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_store_details)

        if (intent.hasExtra(Constants.EXTRA_STORE_ID)) {
            mStoreId =
                intent.getStringExtra(Constants.EXTRA_STORE_ID)!!
        }

        if (intent.hasExtra(Constants.EXTRA_STORE_OWNER_ID)) {
            mStoreOwnerId =
                intent.getStringExtra(Constants.EXTRA_STORE_OWNER_ID)!!
        }

        setupActionBar()

        if (FirestoreClass().getCurrentUserID() == mStoreOwnerId) {
            btn_edit_description.visibility = View.VISIBLE
        } else {
            btn_edit_description.visibility = View.GONE
        }

        btn_go_to_menu.setOnClickListener(this)
        btn_edit_description.setOnClickListener(this)

        getStoreDetails()
    }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {

                R.id.btn_go_to_menu->{
                    startActivity(Intent(this@StoreDetailsActivity, ProductDetailsActivity::class.java))
                }

                R.id.btn_edit_description->{
                    startActivity(Intent(this@StoreDetailsActivity, AddEditStoreActivity::class.java))
                }
            }
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_store_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_store_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getStoreDetails() {

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getStoreDetails(this@StoreDetailsActivity, mStoreId)
    }

    fun storeDetailsSuccess(store: Store) {

        mStoreDetails = store

        GlideLoader(this@StoreDetailsActivity).loadProductPicture(
            store.image,
            iv_store_detail_image
        )

        tv_title.text = store.title
        tv_store_details_description.text = store.description
    }
}