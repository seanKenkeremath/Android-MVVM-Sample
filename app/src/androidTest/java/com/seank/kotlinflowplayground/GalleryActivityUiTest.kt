package com.seank.kotlinflowplayground

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.seank.kotlinflowplayground.gallery.GalleryActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

import org.junit.Test

import org.junit.Rule

class GalleryActivityUiTest {

    @get:Rule var activityTestRule = ActivityTestRule(GalleryActivity::class.java)

    @Test
    fun whenGalleryFinishesLoadingThenCardsAreDisplayedInViewPager() {
        Thread.sleep(2000)
        onView(viewPagerItemWithText("Test Card 1")).check(matches(isCompletelyDisplayed()))
        onView(viewPagerItemWithText("Test Card 2")).check(matches(not(isCompletelyDisplayed())))

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        Thread.sleep(2000)
        onView(viewPagerItemWithText("Test Card 1")).check(matches(not(isCompletelyDisplayed())))
        onView(viewPagerItemWithText("Test Card 2")).check(matches(isCompletelyDisplayed()))
    }

    private fun viewPagerItemWithText(text: String) : Matcher<View> {
        return allOf(withText(text), isDescendantOfA(withId(R.id.viewpager)))
    }
}
