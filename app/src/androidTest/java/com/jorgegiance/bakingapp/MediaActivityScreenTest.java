package com.jorgegiance.bakingapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


import com.jorgegiance.bakingapp.ui.MediaActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MediaActivityScreenTest {

    @Rule
    public ActivityTestRule<MediaActivity> mActivityTestRule =
            new ActivityTestRule<>(MediaActivity.class);


    @Test
    public void testMediaActivityDisplaysRecipeMediaFragment(){
        //onView()
    }



}
