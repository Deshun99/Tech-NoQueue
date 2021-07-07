package com.technoqueue.ui.activities


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.technoqueue.R
import junit.framework.TestCase
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.technoqueue.ui.activities.DashboardActivity
import com.technoqueue.ui.fragments.MenuFragment
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class DashboardActivityTest : TestCase() {

    @Rule
    @JvmField
    val rule: ActivityTestRule<DashboardActivity> = ActivityTestRule(DashboardActivity::class.java)

    @Test
    fun testOnCreate() {
        onView(withId(R.id.navigation_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_sold_products)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_orders)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_welcome)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    @Test
    fun navigationMenu() {
        onView(withId(R.id.navigation_menu)).perform(ViewActions.click())
        onView(withId(R.id.action_edit_storefront)).perform(ViewActions.click())
        /*
        onView(withId(R.id.et_store_title)).perform(
            ViewActions.typeText("Da Shi Jia Prawn Mee"),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.et_store_description)).perform(ViewActions.typeText("Operating hours \n" +
                "Mon - Fri \n" + "11am - 7pm"),
            ViewActions.closeSoftKeyboard())

         */
        onView(withId(R.id.btn_submit_changes)).perform(ViewActions.click())
    }

}