package com.technoqueue.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.technoqueue.R
import com.technoqueue.firestore.FirestoreClass
import com.technoqueue.models.Store
import com.technoqueue.utils.Constants
import com.technoqueue.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_edit_store.*
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class AddEditStoreActivity : BaseActivity(), View.OnClickListener {

    private var mStoreDetails: Store? = null
    private var mSelectedImageFileURI: Uri? = null
    private var mStoreId: String = ""
    private var mStoreImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_store)
        setupActionBar()

        if (intent.hasExtra(Constants.EXTRA_STORE_DETAILS)) {
            mStoreDetails = intent.getParcelableExtra(Constants.EXTRA_STORE_DETAILS)!!
        }

        if (intent.hasExtra(Constants.EXTRA_STORE_ID)) {
            mStoreId =
                intent.getStringExtra(Constants.EXTRA_STORE_ID)!!
        }

        if (mStoreDetails != null) {
            GlideLoader(this@AddEditStoreActivity).loadUserPicture(mStoreDetails!!.image, iv_store_image)
            et_store_title.setText(mStoreDetails!!.title)
            et_store_description.setText(mStoreDetails!!.description)
        }

        iv_store_image.setOnClickListener(this)
        btn_submit_changes.setOnClickListener(this)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_edit_storefront_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_edit_storefront_activity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if(v != null) {
            when(v.id) {
                R.id.iv_store_image -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this@AddEditStoreActivity)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_submit_changes -> {
                    if (validateStoreDetails()) {

                        showProgressDialog(resources.getString(R.string.please_wait))

                        if (mSelectedImageFileURI != null) {

                            FirestoreClass().uploadImageToCloudStorage(
                                this@AddEditStoreActivity,
                                mSelectedImageFileURI,
                                Constants.USER_PROFILE_IMAGE
                            )
                        } else {
                            uploadStoreDetails()
                        }
                    }
                }
            }
        }
    }

    fun storeUploadSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@AddEditStoreActivity,
            resources.getString(R.string.store_uploaded_success_message),
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    fun imageUploadSuccess(imageURL: String) {
        mStoreImageURL = imageURL
        uploadStoreDetails()
    }

    private fun uploadStoreDetails() {
        if (mStoreDetails == null) {
            val username = this.getSharedPreferences(
                Constants.TECHNOQUEUE_PREFERENCES, Context.MODE_PRIVATE).getString(
                Constants.LOGGED_IN_USERNAME, "")!!

            val store = Store(
                FirestoreClass().getCurrentUserID(),
                username,
                et_store_title.text.toString().trim { it <= ' '},
                et_store_description.text.toString().trim { it <= ' '},
                mStoreImageURL
            )

            FirestoreClass().uploadStoreDetails(this@AddEditStoreActivity, store)
        } else {
            val storeHashMap = HashMap<String, Any>()

            val title = et_store_title.text.toString().trim { it <= ' ' }
            if (title != mStoreDetails!!.title) {
                storeHashMap[Constants.TITLE] = title
            }

            val description = et_store_description.text.toString().trim { it <= ' ' }
            if (description != mStoreDetails!!.description) {
                storeHashMap[Constants.DESCRIPTION] = description
            }

            if (mStoreImageURL.isNotEmpty()) {
                storeHashMap[Constants.IMAGE] = mStoreImageURL
            }

            FirestoreClass().updateStoreDetails(
                this@AddEditStoreActivity,
                storeHashMap,
                mStoreId
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    iv_store_image.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_vector_edit))

                    mSelectedImageFileURI = data.data!!

                    try {
                        GlideLoader(this).loadUserPicture(mSelectedImageFileURI!!, iv_store_image)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }


    private fun validateStoreDetails(): Boolean {
        return when {
            mSelectedImageFileURI == null -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
                false
            }

            TextUtils.isEmpty(et_store_title.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_store_title), true)
                false
            }

            TextUtils.isEmpty(et_store_description.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_store_description), true)
                false
            }
            else -> {
                true
            }

        }
    }
}