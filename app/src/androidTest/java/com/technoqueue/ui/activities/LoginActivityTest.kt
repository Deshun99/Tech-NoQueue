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
import androidx.test.core.app.ActivityScenario
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
    fun whenEmailAndPasswordIsEmpty() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.btn_login)).perform(click())
        onView(hasErrorText("Please enter email."))
    }

    @Test
    fun whenEmailIsEmpty() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.et_password)).perform(typeText("111111"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(hasErrorText("Please enter email."))
    }

    @Test
    fun whenEmailIsInvalid() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.et_email))
            .perform(typeText("gehkiang@actsmart.com"))
        onView(withId(R.id.et_password)).perform(typeText("111111"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(hasErrorText("There is no user record corresponding to this identifier. The user" +
                " may have been deleted."))
    }

    @Test
    fun whenPasswordIsWrong() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.et_email))
            .perform(typeText("john@wick.com"))
        onView(withId(R.id.et_password)).perform(typeText("222222"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(hasErrorText("The password is invalid or the user does not have a password."))
    }

    @Test
    fun whenPasswordIsEmpty() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.et_email))
            .perform(typeText("john@wick.com"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(hasErrorText("Please enter password."))
    }



    @Test
    fun whenLoginSuccessful_loadDashboardActivity() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.et_email))
            .perform(typeText("john@wick.com"), closeSoftKeyboard())
        onView(withId(R.id.et_password))
            .perform(typeText("111111"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        // TODO: USE IDLING RESOURCES INSTEAD OF MANUALLY WAITING
        // NOTE: IF IT FAILS, CONSIDER HIGHER WAITING TIME
        Thread.sleep(2500)

    }


    // For getting the string value of R.string.something
    private fun getString(@StringRes resourceId: Int): String {
        return rule.activity.getString(resourceId)
    }
}