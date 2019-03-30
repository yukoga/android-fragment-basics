package com.codepath.mypizza;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import com.codepath.mypizza.R;

/**
 * Created by Shyam Rokde on 8/9/16.
 */
public class MainActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void testSetupPizzaMenuFragment() {
        activityTestRule.getActivity().getFragmentManager().beginTransaction();
    }

    @Test
    public void testListViewItems() {
        onView(withId(R.id.lvItems)).check(matches((isDisplayed())));
    }
}
