package com.app.baking.wageeh.bakingapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.baking.wageeh.bakingapp.R;
import com.app.baking.wageeh.bakingapp.data.RecipeModel;

public class RecipeStepDetailsActivity extends AppCompatActivity  implements RecipeStepDetailsFragment.OnRecipeStepDetailInteractionListener {

    public static final String ARG_RECIPE = "recipe";
    public static final String ARG_STEP_ID = "step_id";
    private static final String TAG_STEP_FRAGMENT = "step_fragment";

    private RecipeStepDetailsFragment mStepDetailsFragment;
    private RecipeModel mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);

        Bundle bundle = getIntent().getExtras();
        int stepId;

        if (bundle == null) {
            return;
        }

        mRecipe = bundle.getParcelable(ARG_RECIPE);
        stepId = bundle.getInt(ARG_STEP_ID);

        getSupportActionBar().setTitle(mRecipe.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment retainedFragment = fragmentManager.findFragmentByTag(TAG_STEP_FRAGMENT);
        if(retainedFragment == null) {
            mStepDetailsFragment = RecipeStepDetailsFragment.newInstance(
                    mRecipe.getSteps()[stepId], mRecipe.getSteps().length - 1);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_steps_details, mStepDetailsFragment, TAG_STEP_FRAGMENT)
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing()) {
            if(mStepDetailsFragment == null)
                return;

            getSupportFragmentManager().beginTransaction().remove(mStepDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onChangeToStep(int pos) {
        mStepDetailsFragment = RecipeStepDetailsFragment.newInstance(mRecipe.getSteps()[pos],
                mRecipe.getSteps().length - 1);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.recipe_steps_details, mStepDetailsFragment, TAG_STEP_FRAGMENT);
        transaction.commit();
    }
}
