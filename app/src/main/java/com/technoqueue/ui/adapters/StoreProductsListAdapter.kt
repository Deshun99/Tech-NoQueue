package com.technoqueue.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.technoqueue.R
import com.technoqueue.firestore.FirestoreClass
import com.technoqueue.models.Product
import com.technoqueue.models.Store
import com.technoqueue.ui.activities.DisplayProductsActivity
import com.technoqueue.ui.activities.ProductDetailsActivity
import com.technoqueue.utils.Constants
import com.technoqueue.utils.GlideLoader
import kotlinx.android.synthetic.main.item_list_layout.view.*

open class StoreProductsListAdapter (

    private val context: Context,
    private var list: ArrayList<Product>,
    private val activity: DisplayProductsActivity,
    private val mStoreDetails: Store
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            if (FirestoreClass().getCurrentUserID().equals(mStoreDetails.user_id)) {
                holder.itemView.ib_delete_product.visibility = View.VISIBLE
            } else {
                holder.itemView.ib_delete_product.visibility = View.INVISIBLE
            }

            GlideLoader(context).loadProductPicture(model.image, holder.itemView.iv_item_image)

            holder.itemView.tv_item_name.text = model.title
            holder.itemView.tv_item_price.text = "$${model.price}"

            holder.itemView.ib_delete_product.setOnClickListener {
                activity.deleteProduct(model.product_id)
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_PRODUCT_ID, model.product_id)
                intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, model.user_id)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}