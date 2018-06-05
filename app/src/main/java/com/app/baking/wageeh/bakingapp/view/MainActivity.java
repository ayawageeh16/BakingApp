package com.app.baking.wageeh.bakingapp.view;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.baking.wageeh.bakingapp.R;
import com.app.baking.wageeh.bakingapp.adapater.RecipesAdapter;
import com.app.baking.wageeh.bakingapp.data.RecipeModel;
import com.app.baking.wageeh.bakingapp.utils.RecipeListLoader;
import com.app.baking.wageeh.bakingapp.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity   implements LoaderManager.LoaderCallbacks<String>,
        RecipesAdapter.ItemClickListener {

    @BindView(R.id.root_layout)
    ConstraintLayout rootLayout;
    @BindView(R.id.loading)
    TextView tvLoading;
    @BindView(R.id.recipes_recyclerview)
    RecyclerView rvRecipes;

    private final String TAG = "MainActivity";
    private static final int RECIPES_LOADER_ID = 1;
    private List<RecipeModel> mRecipes;
    private final static String BACKEND
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes();
    }

    private void loadRecipes() {
        getSupportLoaderManager().restartLoader(RECIPES_LOADER_ID, null, this).forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new RecipeListLoader(this, BACKEND);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        if(data.isEmpty()) {
            Snackbar.make(rootLayout, R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadRecipes();
                        }
                    }).show();
            return;
        }

        tvLoading.setVisibility(View.GONE);

        mRecipes = JsonUtils.parseRecipesJson(data);
        RecipesAdapter adapter = new RecipesAdapter(this, mRecipes);
        adapter.setRecipeClickListener(this);
        rvRecipes.setAdapter(adapter);
        if(getResources().getBoolean(R.bool.isTablet))
            rvRecipes.setLayoutManager(new GridLayoutManager(this, 3));
        else
            rvRecipes.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onRecipeClick(View view, int pos) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE, mRecipes.get(pos));
        startActivity(intent);
    }
}
