package com.app.baking.wageeh.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.app.baking.wageeh.bakingapp.data.IngredientModel;
import com.app.baking.wageeh.bakingapp.data.RecipeModel;
import com.app.baking.wageeh.bakingapp.data.StepsModel;
import com.app.baking.wageeh.bakingapp.view.RecipeDetailsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by RamezReda on 4/6/2018.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {

    private final String RECIPE_NAME = "Cake";
    private final String STEP1_NAME = "StepsModel 1";
    private final String STEP2_NAME = "StepsModel 2";
    private final String STEP1_DESCRIPTION = "step 1 description";
    private final String STEP2_DESCRIPTION = "step 2 description";

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mRule =
            new ActivityTestRule<RecipeDetailsActivity>(RecipeDetailsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    IngredientModel[] ingredients = new IngredientModel[1];
                    ingredients[0] = new IngredientModel();
                    ingredients[0].setQuantity(200);
                    ingredients[0].setMeasure("grams");
                    ingredients[0].setIngredient("Flour");

                    StepsModel[] steps = new StepsModel[2];
                    steps[0] = new StepsModel();
                    steps[0].setId(1);
                    steps[0].setShortDescription(STEP1_NAME);
                    steps[0].setDescription(STEP1_DESCRIPTION);
                    steps[0].setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
                    steps[0].setThumbnailURL("");

                    steps[1] = new StepsModel();
                    steps[1].setId(2);
                    steps[1].setShortDescription(STEP2_NAME);
                    steps[1].setDescription(STEP2_DESCRIPTION);
                    steps[1].setVideoURL("");
                    steps[1].setThumbnailURL("");

                    RecipeModel recipe = new RecipeModel(1,
                            RECIPE_NAME,
                            ingredients,
                            steps,
                            8,
                            "");

                    Intent intent = new Intent();
                    intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE, recipe);
                    return intent;
                }
            };

    @Test
    public void recipeDetailsVideoClick() {
        onView(withId(R.id.steps_recyclerview))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.step_description)).check(matches(withText(STEP1_DESCRIPTION)));
        onView(withId(R.id.videoPlayer)).check(matches(isDisplayed()));
    }

    @Test
    public void recipeDetailsNoVideoClick() {
        onView(withId(R.id.steps_recyclerview))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.step_description)).check(matches(withText(STEP2_DESCRIPTION)));
        onView(withId(R.id.no_video)).check(matches(isDisplayed()));
    }
}
