package com.example.obligatoriskopgave

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.obligatoriskopgave.R
import com.google.firebase.auth.FirebaseAuth
import org.junit.Rule
import org.junit.Test

@LargeTest
class LoginFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testFirebaseAuth() {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword("luhavenkvist@yahoo.dk", "test123")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Login successfull!")
                } else {
                    println("Login failed: ${task.exception?.message}")
                }
            }
    }

    @Test
    fun testLoginSuccess() {
        onView(withId(R.id.email)).perform(typeText("test@test.test"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("test123"), closeSoftKeyboard())

        onView(withId(R.id.login)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.button_sort)).check(matches(isDisplayed()))
    }


    @Test
    fun testLoginFailure() {
        onView(withId(R.id.email)).perform(typeText("invalid@test.test"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("invalid123"), closeSoftKeyboard())

        onView(withId(R.id.login)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.loginFailed)).check(matches(isDisplayed()))
    }
}
