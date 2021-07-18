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
class RegisterActivityTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<RegisterActivity> = ActivityTestRule(RegisterActivity::class.java)

    @Test
    fun incorrectEmailFormat() {

        val activityScenario = ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.et_first_name)).perform(typeText("Sng"))
        onView(withId(R.id.et_last_name)).perform(typeText("Boon Joon"))
        onView(withId(R.id.et_email)).perform(typeText("sngboonjoon"))
        onView(withId(R.id.et_password)).perform(typeText("111111"))
        onView(withId(R.id.et_confirm_password)).perform(typeText("111111"), closeSoftKeyboard())
        onView(withId(R.id.cb_terms_and_condition)).perform(click())
        onView(withId(R.id.btn_register)).perform(click())
        onView(hasErrorText("The email address is badly formatted."))
    }

    @Test
    fun passwordLessThan6Characters() {
        val activityScenario = ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.et_first_name)).perform(typeText("Sng"))
        onView(withId(R.id.et_last_name)).perform(typeText("Boon Joon"))
        onView(withId(R.id.et_email)).perform(typeText("sngboonjoon@gmail.com"))
        onView(withId(R.id.et_password)).perform(typeText("1111"))
        onView(withId(R.id.et_confirm_password)).perform(typeText("1111"), closeSoftKeyboard())
        onView(withId(R.id.cb_terms_and_condition)).perform(click())
        onView(withId(R.id.btn_register)).perform(click())
        onView(hasErrorText("The given password is invalid. [ Password should be at least " +
                "6 characters ]"))
    }

    @Test
    fun passwordDoesNotMatch() {
        val activityScenario = ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.et_first_name)).perform(typeText("Sng"))
        onView(withId(R.id.et_last_name)).perform(typeText("Boon Joon"))
        onView(withId(R.id.et_email)).perform(typeText("sngboonjoon@gmail.com"))
        onView(withId(R.id.et_password)).perform(typeText("111"))
        onView(withId(R.id.et_confirm_password)).perform(typeText("1111"), closeSoftKeyboard())
        onView(withId(R.id.cb_terms_and_condition)).perform(click())
        onView(withId(R.id.btn_register)).perform(click())
        onView(hasErrorText("Password and confirm password does not match."))
    }

    @Test
    fun missingTermsAndCondition() {
        val activityScenario = ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.et_first_name)).perform(typeText("Sng"))
        onView(withId(R.id.et_last_name)).perform(typeText("Boon Joon"))
        onView(withId(R.id.et_email)).perform(typeText("sngboonjoon@gmail.com"))
        onView(withId(R.id.et_password)).perform(typeText("111111"))
        onView(withId(R.id.et_confirm_password)).perform(typeText("111111"), closeSoftKeyboard())
        onView(withId(R.id.btn_register)).perform(click())
        onView(hasErrorText("Please agree terms and condition."))
    }

    @Test
    fun missingFirstName() {
        val activityScenario = ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.et_last_name)).perform(typeText("Boon Joon"))
        onView(withId(R.id.et_email)).perform(typeText("sngboonjoon@gmail.com"))
        onView(withId(R.id.et_password)).perform(typeText("111111"))
        onView(withId(R.id.et_confirm_password)).perform(typeText("111111"), closeSoftKeyboard())
        onView(withId(R.id.cb_terms_and_condition)).perform(click())
        onView(withId(R.id.btn_register)).perform(click())
        onView(hasErrorText("Please enter first name."))
    }

    @Test
    fun missingLastName() {
        val activityScenario = ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.et_last_name)).perform(typeText("Boon Joon"))
        onView(withId(R.id.et_email)).perform(typeText("sngboonjoon@gmail.com"))
        onView(withId(R.id.et_password)).perform(typeText("111111"))
        onView(withId(R.id.et_confirm_password)).perform(typeText("111111"), closeSoftKeyboard())
        onView(withId(R.id.cb_terms_and_condition)).perform(click())
        onView(withId(R.id.btn_register)).perform(click())
        onView(hasErrorText("Please enter last name."))
    }
}