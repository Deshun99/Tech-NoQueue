package com.technoqueue.ui.fragments

import android.app.Instrumentation
import android.content.ContentResolver
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.provider.MediaStore
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.technoqueue.R
import com.technoqueue.ui.activities.DashboardActivity
import com.technoqueue.ui.fragments.MenuFragment
import com.technoqueue.ui.fragments.WelcomeFragment
import com.technoqueue.ui.fragments.OrdersFragment
import com.technoqueue.ui.fragments.SoldProductsFragment
import kotlinx.android.synthetic.main.activity_add_product.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import androidx.test.espresso.contrib.RecyclerViewActions
import kotlinx.android.synthetic.main.activity_display_products.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf



@RunWith(AndroidJUnit4ClassRunner::class)
class MenuFragmentTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<DashboardActivity> = ActivityTestRule(DashboardActivity::class.java)

    @Test
    fun setupNavigation() {
        //SETUP

        //MenuFragment
        onView(withId(R.id.navigation_menu)).perform(click())

        onView(withId(R.id.menu_fragment)).check(matches(isDisplayed()))

        //OrderFragment
        onView(withId(R.id.navigation_orders)).perform(click())

        onView(withId(R.id.order_fragment)).check(matches(isDisplayed()))

        //SoldProductsFragment
        onView(withId(R.id.navigation_sold_products)).perform(click())

        onView(withId(R.id.sold_products_fragment)).check(matches(isDisplayed()))

        //WelcomeFragment
        onView(withId(R.id.navigation_welcome)).perform(click())

        onView(withId(R.id.welcome_fragments)).check(matches(isDisplayed()))

    }



    @Test
    fun editStoreFront() {
        val activityScenario = ActivityScenario.launch(DashboardActivity::class.java)

        //MenuFragment
        onView(withId(R.id.navigation_menu)).perform(click())

        onView(withId(R.id.menu_fragment)).check(matches(isDisplayed()))

        onView(withId(R.id.action_edit_storefront)).perform(click())

        onView(withId(R.id.et_store_title)).perform(clearText(),
            typeText("Da Shi Jia Prawn Mee"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.et_store_description)).perform(clearText(),
            typeText("Operating Hours\n Monday - Friday\n 11am - 7pm"),
            closeSoftKeyboard()
        )
    }

    @Test
    fun addProduct() {
        val activityScenario = ActivityScenario.launch(DashboardActivity::class.java)

        //MenuFragment
        onView(withId(R.id.navigation_menu)).perform(click())

        onView(withId(R.id.menu_fragment)).check(matches(isDisplayed()))

        onView(withId(R.id.action_add_product)).perform(click())

        onView(withId(R.id.toolbar_add_product_activity)).perform(click())
    }

    @Test
    fun selectStore() {

        val activityScenario = ActivityScenario.launch(DashboardActivity::class.java)

        //MenuFragment
        onView(withId(R.id.navigation_menu)).perform(click())

        onView(withId(R.id.menu_fragment)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_my_product_items)).check(matches(isDisplayed()))
        Thread.sleep(2000)
          onView(withId(R.id.rv_my_product_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        runStuff()
    }

    private fun runStuff() {
        onView(withId(R.id.btn_go_to_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_go_to_menu)).perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.rv_my_product_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withId(R.id.btn_add_to_cart)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.btn_go_to_cart)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.btn_checkout)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.btn_place_order)).perform(click())
    }

    /*
    private fun createGallery(): Instrumentation.ActivityResult {

        val resources: Resources = InstrumentationRegistry.getInstrumentation()
            .context.resources
        val imageUri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                    resources.getResourcePackageName(R.drawable.ic_launcher_foreground) + "/" +
                    resources.getResourceTypeName(R.drawable.ic_launcher_foreground) + "/" +
                    resources.getResourceEntryName(R.drawable.ic_launcher_foreground)
        )
    }

     */



}