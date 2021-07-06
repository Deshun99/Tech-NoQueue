package com.technoqueue.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.technoqueue.R
import com.technoqueue.firestore.FirestoreClass
import com.technoqueue.models.Store
import com.technoqueue.ui.activities.AddProductActivity
import com.technoqueue.ui.activities.AddEditStoreActivity
import com.technoqueue.ui.activities.StoreDetailsActivity
import com.technoqueue.ui.adapters.StoreListAdapter
import com.technoqueue.utils.Constants
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : BaseFragment() {

    private lateinit var mRootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun successStoreListFromFireStore(storeList: ArrayList<Store>) {
        hideProgressDialog()

        if (storeList.size > 0) {
            rv_my_product_items.visibility = View.VISIBLE
            tv_no_products_found.visibility = View.GONE

            rv_my_product_items.layoutManager = LinearLayoutManager(activity)
            rv_my_product_items.setHasFixedSize(true)

            val adapterStores = StoreListAdapter(requireActivity(), storeList)
            rv_my_product_items.adapter = adapterStores

            adapterStores.setOnClickListener(object: StoreListAdapter.OnClickListener {
                override fun onClick(position: Int, store: Store) {
                    val intent = Intent(context, StoreDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_STORE_ID, store.store_id)
                    intent.putExtra(Constants.EXTRA_STORE_OWNER_ID, store.user_id)
                    startActivity(intent)
                }
            })
        } else {
            rv_my_product_items.visibility = View.GONE
            tv_no_products_found.visibility = View.VISIBLE
        }
    }

    /*
    private fun showAlertDialogToDeleteProduct(productID: String) {

        val builder = AlertDialog.Builder(requireActivity())

        builder.setTitle(resources.getString(R.string.delete_dialog_title))

        builder.setMessage(resources.getString(R.string.delete_dialog_message))
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, _ ->

            showProgressDialog(resources.getString(R.string.please_wait))

            FirestoreClass().deleteProduct(this@MenuFragment, productID)

            dialogInterface.dismiss()
        }

        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _ ->

            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun getProductListFromFireStore() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getProductsList(this)
    }
     */

    private fun getStoreListFromFireStore() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getStoreList(this)
    }

    override fun onResume() {
        super.onResume()
        getStoreListFromFireStore()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_menu, container, false)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        FirestoreClass().getAccType(this@MenuFragment, menu, inflater)

    }

    fun applyChanges(accType: String, menu: Menu, inflater: MenuInflater) {
        if (accType.equals("vendor")) {
            inflater.inflate(R.menu.add_product_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_add_product -> {

                FirestoreClass().checkStoreCreated(this@MenuFragment)
                return true

            }

            R.id.action_edit_storefront -> {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirestoreClass().checkExistingStore(this@MenuFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun checkStoreCreatedSuccess(size: Int) {
        if (size != 0) {
            startActivity(Intent(activity, AddProductActivity::class.java))
        } else {
            Toast.makeText(
                activity,
                "Please set up your store before adding products",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun verifyExistingStore(storeList: ArrayList<Store>): Boolean {

        hideProgressDialog()
        if (storeList.size > 0) {
            for (store in storeList) {
                if (store.user_id == FirestoreClass().getCurrentUserID()) {
                    val intent = Intent(activity, AddEditStoreActivity::class.java)
                    intent.putExtra(Constants.EXTRA_STORE_DETAILS, store)
                    intent.putExtra(Constants.EXTRA_STORE_ID, store.store_id)
                    startActivity(intent)
                    return true
                }
            }
            startActivity(Intent(activity, AddEditStoreActivity::class.java))
            return true
        } else {
            startActivity(Intent(activity, AddEditStoreActivity::class.java))
            return true
        }
    }
}