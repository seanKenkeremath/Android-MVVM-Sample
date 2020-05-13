package com.seank.kotlinflowplayground.util.test.idlingresource

import androidx.test.espresso.idling.CountingIdlingResource
import com.seank.kotlinflowplayground.BuildConfig

object BackgroundTaskIdlingResource {

    private const val IDLING_RESOURCE_NAME = "global_task_counter"

    @JvmField val idlingResource = CountingIdlingResource(IDLING_RESOURCE_NAME)

    fun taskStarted() {
        if (!BuildConfig.DEBUG) {
            return
        }
        idlingResource.increment()
    }

    fun taskEnded() {
        if (!BuildConfig.DEBUG) {
            return
        }
        if (!idlingResource.isIdleNow) {
            idlingResource.decrement()
        }
    }
}