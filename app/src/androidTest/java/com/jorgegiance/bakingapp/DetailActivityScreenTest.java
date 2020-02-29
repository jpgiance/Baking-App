package com.jorgegiance.bakingapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.jorgegiance.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class DetailActivityScreenTest {

//    @Rule
//    public ActivityTestRule<MainActivity> activityRule =
//            new ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void listGoesOverTheFold() {
//        onView(withText("Hello world!")).check(matches(isDisplayed()));
//    }
}
