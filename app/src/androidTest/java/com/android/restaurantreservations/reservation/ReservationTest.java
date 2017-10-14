package com.android.restaurantreservations.reservation;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.android.restaurantreservations.R;
import com.android.restaurantreservations.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ReservationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void reservationTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.customers_recycler_view), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(5, click()));

        // sleep for 3 second to see the new view
        waitToRecognize();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.reservations_recycler_view), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(10, click()));

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("Book Table")));

        // sleep for 3 second to see the dialog
        waitToRecognize();

        appCompatButton.perform(scrollTo(), click());

        // sleep for 3 second to see the dialog
        waitToRecognize();

        //do click again to reserve to ensure that it is reserved
        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.reservations_recycler_view), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(10, click()));

        // sleep for 3 second to see the can't reserve error message
        waitToRecognize();

    }


    private void waitToRecognize() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
