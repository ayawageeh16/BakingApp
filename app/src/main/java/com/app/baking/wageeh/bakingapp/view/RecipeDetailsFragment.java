package com.app.baking.wageeh.bakingapp.view;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.baking.wageeh.bakingapp.IngredientsWidgetProvider;
import com.app.baking.wageeh.bakingapp.R;
import com.app.baking.wageeh.bakingapp.adapater.RecipeStepsAdapter;
import com.app.baking.wageeh.bakingapp.data.IngredientModel;
import com.app.baking.wageeh.bakingapp.data.RecipeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailsFragment extends Fragment implements RecipeStepsAdapter.OnItemClickListener {

    @BindView(R.id.ingredients)
    TextView tvIngredients;
    @BindView(R.id.steps_recyclerview)
    RecyclerView rvSteps;

    private static final String ARG_RECIPE = "recipe";

    private RecipeModel mRecipe;

    private OnRecipeDetailInteractionListener mListener;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailsFragment newInstance(RecipeModel recipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);

        StringBuilder sIngredients = new StringBuilder();
        for (IngredientModel ing : mRecipe.getIngredients()) {
            sIngredients.append(ing.getIngredient() + " (" + ing.getQuantity() + " " +
                    ing.getMeasure() + ")");
            sIngredients.append("\n");
        }
        tvIngredients.setText(sIngredients);

        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(getActivity(), mRecipe.getSteps());
        recipeStepsAdapter.setOnStepClickListener(this);
        rvSteps.setAdapter(recipeStepsAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSteps.setNestedScrollingEnabled(false);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
        int[] appWidgetIDs = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity(), IngredientsWidgetProvider.class));
        IngredientsWidgetProvider.Ingredients = mRecipe;
        IngredientsWidgetProvider.updateIngredientsWidget(getActivity(), appWidgetManager, appWidgetIDs);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeDetailInteractionListener) {
            mListener = (OnRecipeDetailInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeDetailInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRecipeStepClicked(int position) {
        if (mListener != null) {
            mListener.onStepClicked(position);
        }
    }

    public interface OnRecipeDetailInteractionListener {
        void onStepClicked(int pos);
    }
}
