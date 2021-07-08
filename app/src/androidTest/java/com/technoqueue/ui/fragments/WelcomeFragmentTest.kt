package com.technoqueue.ui.fragments


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.technoqueue.R
import com.technoqueue.ui.activities.DashboardActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.contrib.RecyclerViewActions

@RunWith(AndroidJUnit4ClassRunner::class)
class WelcomeFragmentTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<DashboardActivity> = ActivityTestRule(DashboardActivity::class.java)

    @Test
    fun setupNavigation() {
        onView(withId(R.id.navigation_welcome)).perform(click())

        onView(withId(R.id.welcome_fragments)).check(matches(isDisplayed()))

        Thread.sleep(2000)
        onView(withId(R.id.rv_dashboard_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
    }

}