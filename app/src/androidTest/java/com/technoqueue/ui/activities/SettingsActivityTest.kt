package com.technoqueue.ui.activities

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
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


@RunWith(AndroidJUnit4ClassRunner::class)
class SettingsActivityTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<SettingsActivity> = ActivityTestRule(SettingsActivity::class.java)


    private fun setupNavigation() {
        onView(withId(R.id.tv_edit)).perform(click())
        onView(withId(R.id.toolbar_user_profile_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun testEditing() {
        setupNavigation()
        Thread.sleep(2500)

        onView(withId(R.id.et_first_name)).perform(typeText("John Wix"), closeSoftKeyboard())

        onView(withId(R.id.et_last_name)).perform(typeText("bin Santino D'Antonio"), closeSoftKeyboard())

        //onView(withId(R.id.et_email)).perform(typeText("santiano@antonio.com"), closeSoftKeyboard())

        onView(withId(R.id.et_mobile_number)).perform(typeText("96969696"), closeSoftKeyboard())

        onView(withId(R.id.btn_submit)).perform(click())
        Thread.sleep(2500)
        onView(withId(R.id.toolbar_settings_activity)).check(matches(isDisplayed()))
    }

}