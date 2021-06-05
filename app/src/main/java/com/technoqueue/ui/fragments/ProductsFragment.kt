package com.technoqueue.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.technoqueue.R
import com.technoqueue.ui.activities.AddProductActivity
import com.technoqueue.ui.activities.SettingsActivity

class ProductsFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_products, container, false)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_add_product -> {

                startActivity(Intent(activity, AddProductActivity::class.java))

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}