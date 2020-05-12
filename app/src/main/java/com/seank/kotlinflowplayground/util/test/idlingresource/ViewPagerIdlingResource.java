package com.seank.kotlinflowplayground.util.test.idlingresource;

import androidx.test.espresso.IdlingResource;
import androidx.viewpager.widget.ViewPager;

/**
 * Copied from
 * https://github.com/Karumi/Rosie/blob/master/sample/src/androidTest/java/com/karumi/rosie/sample/idlingresources/ViewPagerIdlingResource.java
 */

public class ViewPagerIdlingResource implements IdlingResource {

    private boolean idle = true;

    private ResourceCallback resourceCallback;
    // The name must be unique across the IR registry (e.g. multiple view pagers)
    private String resourceName;

    public ViewPagerIdlingResource(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPagerListener());
        resourceName = "View Pager Idling Resource for " + viewPager.toString();
    }

    @Override public String getName() {
        return resourceName;
    }

    @Override public boolean isIdleNow() {
        return idle;
    }

    @Override public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    private class ViewPagerListener extends ViewPager.SimpleOnPageChangeListener {

        @Override public void onPageScrollStateChanged(int state) {
            idle = (state == ViewPager.SCROLL_STATE_IDLE
                    // Treat dragging as idle, or Espresso will block itself when swiping.
                    || state == ViewPager.SCROLL_STATE_DRAGGING);
            if (idle && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
        }
    }
}