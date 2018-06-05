package com.app.baking.wageeh.bakingapp.adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.baking.wageeh.bakingapp.R;
import com.app.baking.wageeh.bakingapp.data.RecipeModel;
import com.app.baking.wageeh.bakingapp.data.RecipeImages;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private final Context mContext;
    private final List<RecipeModel> mRecipesList;
    List<String> images = new ArrayList<>();
    RecipeImages recipeImages= new RecipeImages();


    private ItemClickListener mItemClickListener;

    public RecipesAdapter(Context context, List<RecipeModel> recipesList) {
        mContext = context;
        mRecipesList = recipesList;
    }

    public void setRecipeClickListener(ItemClickListener clicker) {
        mItemClickListener = clicker;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
            images = recipeImages.CreateImages();
        holder.bind( mRecipesList.get(position), images.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipesList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_card_image)
        ImageView image ;

        @BindView(R.id.recipe_card_name)
        TextView name ;

        @BindView(R.id.recipe_card_servings)
        TextView servings;


        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        void bind (final RecipeModel recipeModel, final String recipeImage){
            Glide.with(mContext)
                    .load(recipeImage)
                    .asBitmap()
                    .into(image);
            servings.setText(String.valueOf(recipeModel.getServings()));
            name.setText(recipeModel.getName());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onRecipeClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onRecipeClick(View view, int pos);
    }
}
