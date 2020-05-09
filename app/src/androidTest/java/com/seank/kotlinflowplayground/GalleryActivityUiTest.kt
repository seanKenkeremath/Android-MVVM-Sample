package com.seank.kotlinflowplayground

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.seank.kotlinflowplayground.gallery.GalleryActivity
import com.seank.kotlinflowplayground.idlingresource.ViewPagerIdlingResource
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test


class GalleryActivityUiTest {

    @get:Rule var activityTestRule = ActivityTestRule(GalleryActivity::class.java)

    private val viewPagerId = R.id.viewpager

    @Test
    fun whenGalleryFinishesLoadingThenCardsAreDisplayedInViewPager() {

        //Register idling resource to wait for swipe animations
        IdlingRegistry.getInstance().register(
            ViewPagerIdlingResource(
                activityTestRule.activity.findViewById(viewPagerId) as ViewPager
            )
        )

        onView(viewPagerItemWithText("Test Card 1")).check(matches(isCompletelyDisplayed()))
        onView(viewPagerItemWithText("Test Card 2")).check(matches(not(isCompletelyDisplayed())))

        onView(withId(viewPagerId)).perform(swipeLeft());

        onView(viewPagerItemWithText("Test Card 1")).check(matches(not(isCompletelyDisplayed())))
        onView(viewPagerItemWithText("Test Card 2")).check(matches(isCompletelyDisplayed()))
    }

    private fun viewPagerItemWithText(text: String) : Matcher<View> {
        return allOf(withText(text), isDescendantOfA(withId(viewPagerId)))
    }
}
