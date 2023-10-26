package com.dzakyadlh.storytell.ui.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dzakyadlh.storytell.R
import com.dzakyadlh.storytell.ui.home.HomeActivity
import com.dzakyadlh.storytell.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {
    private val dummyEmail = "adminyangkedua@gmail.com"
    private val dummyPassword = "adminyangkedua"

    @get:Rule
    val activity = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loginTest() {
        Intents.init()
        onView(withId(R.id.emailEditText)).perform(typeText(dummyEmail), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText)).perform(typeText(dummyPassword), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).check(matches(isEnabled()))
        onView(withId(R.id.loginButton)).perform(click())
        intended(hasComponent(HomeActivity::class.java.name))

        onView(withId(R.id.storyList)).check(matches(isDisplayed()))
    }

    @Test
    fun logoutTest() {
        loginTest()
        onView(withId(R.id.logout)).perform(click())
        onView(withId(R.id.background)).check(matches(isDisplayed()))
    }
}