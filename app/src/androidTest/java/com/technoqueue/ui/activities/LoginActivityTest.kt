package com.technoqueue.ui.activities


import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.technoqueue.ui.activities.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.annotation.StringRes
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import com.technoqueue.R
import org.junit.After
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    // This is required to fire off the Activity that you want to test
    // TODO: ENSURE USER IS NOT LOGGED IN FIRST
    @Rule
    @JvmField
    val rule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun mainActivityLoadsCorrectly() {
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
    }


    @Test
    fun whenAllFieldsEmpty_raiseError() {
        onView(withId(R.id.btn_login)).perform(click())
    }

    @Test
    fun whenEmailIsInvalid_raiseError() {
        onView(withId(R.id.et_email))
            .perform(typeText("test7"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.et_email)).check(matches(hasErrorText("Valid Email Required")))
    }

    @Test
    fun whenPasswordIsEmpty_raiseError() {
        onView(withId(R.id.et_email))
            .perform(typeText("test7@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.et_password)).check(matches(hasErrorText("Please enter password.")))
    }

    @Test
    fun whenPasswordIsTooShort_raiseError() {
        onView(withId(R.id.et_email))
            .perform(typeText("test7@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.et_password))
            .perform(typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.et_password)).check(matches(hasErrorText("6 character password required")))
    }


    @Test
    fun whenLoginSuccessful_loadDashboardActivity() {
        onView(withId(R.id.et_email))
            .perform(typeText("john@wick.com"), closeSoftKeyboard())
        onView(withId(R.id.et_password))
            .perform(typeText("111111"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        // TODO: USE IDLING RESOURCES INSTEAD OF MANUALLY WAITING
        // NOTE: IF IT FAILS, CONSIDER HIGHER WAITING TIME
        Thread.sleep(2500)
        onView(withId(R.id.action_settings)).perform(click())
        //onView(withId(R.id.dashboard_username)).check(matches(isDisplayed()))
        logout()
    }

    @Test
    fun whenLoginUnsuccessful_errorToastIsShown() {
        // TODO: NEED TO FIND A WAY TO ASSERT TOAST MESSAGES
    }

    private fun logout() {
        onView(withId(R.id.btn_logout)).perform(click())
    }

    // For getting the string value of R.string.something
    private fun getString(@StringRes resourceId: Int): String {
        return rule.activity.getString(resourceId)
    }
}