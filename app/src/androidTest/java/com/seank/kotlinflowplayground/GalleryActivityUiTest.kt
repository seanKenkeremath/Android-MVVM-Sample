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
import com.seank.kotlinflowplayground.util.test.idlingresource.BackgroundTaskIdlingResource
import com.seank.kotlinflowplayground.util.test.idlingresource.ViewPagerIdlingResource
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GalleryActivityUiTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(GalleryActivity::class.java)

    private val viewPagerId = R.id.viewpager

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(
            //Register idling resource to wait for swipe animations
            ViewPagerIdlingResource(activityTestRule.activity.findViewById(R.id.viewpager) as ViewPager),
            //Idling resource for background tasks
            BackgroundTaskIdlingResource.idlingResource
        )
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(
            ViewPagerIdlingResource(activityTestRule.activity.findViewById(R.id.viewpager) as ViewPager),
            BackgroundTaskIdlingResource.idlingResource
        )
    }

    @Test
    fun whenGalleryFinishesLoadingThenCardsAreDisplayedInViewPager() {
        onView(viewPagerItemWithText("Test Card 1")).check(matches(isCompletelyDisplayed()))
        onView(viewPagerItemWithText("Test Card 2")).check(matches(not(isCompletelyDisplayed())))

        onView(withId(viewPagerId)).perform(swipeLeft());

        onView(viewPagerItemWithText("Test Card 1")).check(matches(not(isCompletelyDisplayed())))
        onView(viewPagerItemWithText("Test Card 2")).check(matches(isCompletelyDisplayed()))
    }

    private fun viewPagerItemWithText(text: String): Matcher<View> {
        return allOf(withText(text), isDescendantOfA(withId(viewPagerId)))
    }
}
