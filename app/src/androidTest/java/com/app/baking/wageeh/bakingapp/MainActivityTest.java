package com.app.baking.wageeh.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.app.baking.wageeh.bakingapp.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by RamezReda on 4/6/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recipesRecyclerViewIsVisible() {
        onView(withId(R.id.recipes_recyclerview)).check(matches(isDisplayed()));
    }

    @Test
    public void recipeClickOpensRecipeDetails() {
        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ingredients)).check(matches(isDisplayed()));
        onView(withId(R.id.steps_recyclerview)).check(matches(isDisplayed()));
    }


}
