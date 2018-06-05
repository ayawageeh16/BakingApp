package com.app.baking.wageeh.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.app.baking.wageeh.bakingapp.data.IngredientModel;
import com.app.baking.wageeh.bakingapp.data.RecipeModel;
import com.app.baking.wageeh.bakingapp.view.RecipeDetailsActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {
    public static RecipeModel Ingredients;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);

        if(Ingredients != null) {
            StringBuilder sIngredients = new StringBuilder();
            for (IngredientModel ing : Ingredients.getIngredients()) {
                sIngredients.append(ing.getIngredient() + " (" + ing.getQuantity() + " " +
                        ing.getMeasure() + ")");
                sIngredients.append("\n");
            }

            views.setTextViewText(R.id.recipe_name, Ingredients.getName());
            views.setTextViewText(R.id.recipe_ingredients, sIngredients);

            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE, Ingredients);
            PendingIntent pendingIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(intent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.root_layout, pendingIntent);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateIngredientsWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

