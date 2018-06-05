package com.app.baking.wageeh.bakingapp.utils;

import com.app.baking.wageeh.bakingapp.data.IngredientModel;
import com.app.baking.wageeh.bakingapp.data.RecipeModel;
import com.app.baking.wageeh.bakingapp.data.StepsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // RecipeModel JSON objects' names
    private static final String JSON_RECIPE_ID = "id";
    private static final String JSON_RECIPE_NAME = "name";
    private static final String JSON_RECIPE_SERVINGS = "servings";
    private static final String JSON_RECIPE_IMAGE = "image";
    // IngredientModel JSON objects' names
    private static final String JSON_INGREDIENTS = "ingredients";
    private static final String JSON_INGREDIENTS_QUANTITY = "quantity";
    private static final String JSON_INGREDIENTS_MEASURE = "measure";
    private static final String JSON_INGREDIENTS_INGREDIENT = "ingredient";
    // StepsModel JSON objects' names
    private static final String JSON_STEPS = "steps";
    private static final String JSON_STEPS_ID = "id";
    private static final String JSON_STEPS_SHORT_DESCRIPTION = "shortDescription";
    private static final String JSON_STEPS_DESCRIPTION = "description";
    private static final String JSON_STEPS_VIDEO_URL = "videoURL";
    private static final String JSON_STEPS_THUMBNAIL_URL = "thumbnailURL";

    public static List<RecipeModel> parseRecipesJson(String json) {

        try {
            List<RecipeModel> recipesList = new ArrayList<>();

            JSONArray recipesJson = new JSONArray(json);

            for(int i = 0; i < recipesJson.length(); ++i) {
                JSONObject recipeJson = recipesJson.getJSONObject(i);

                int id = recipeJson.optInt(JSON_RECIPE_ID);
                String name = recipeJson.optString(JSON_RECIPE_NAME);
                int servings = recipeJson.optInt(JSON_RECIPE_SERVINGS);
                String image = recipeJson.optString(JSON_RECIPE_IMAGE);

                // Get ingredients
                IngredientModel[] ingredients = extractIngredients(recipeJson.getJSONArray(JSON_INGREDIENTS));
                // Get StepsModel
                StepsModel[] steps = extractSteps(recipeJson.getJSONArray(JSON_STEPS));

                recipesList.add(new RecipeModel(id, name, ingredients, steps, servings, image));
            }

            return recipesList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static StepsModel[] extractSteps(JSONArray stepsJson) throws JSONException {
        StepsModel[] steps = new StepsModel[stepsJson.length()];

        for(int i = 0; i < stepsJson.length(); ++i) {
            JSONObject stepJson = stepsJson.getJSONObject(i);

            steps[i] = new StepsModel();
            steps[i].setId(stepJson.optInt(JSON_STEPS_ID));
            steps[i].setDescription(stepJson.optString(JSON_STEPS_DESCRIPTION));
            steps[i].setShortDescription(stepJson.optString(JSON_STEPS_SHORT_DESCRIPTION));
            steps[i].setThumbnailURL(stepJson.optString(JSON_STEPS_THUMBNAIL_URL));
            steps[i].setVideoURL(stepJson.optString(JSON_STEPS_VIDEO_URL));
        }

        return steps;
    }

    private static IngredientModel[] extractIngredients(JSONArray ingredientsJson) throws JSONException {
        IngredientModel[] ingredients = new IngredientModel[ingredientsJson.length()];

        for(int i = 0; i < ingredientsJson.length(); ++i) {
            JSONObject ingredientJson = ingredientsJson.getJSONObject(i);

            ingredients[i] = new IngredientModel();
            ingredients[i].setIngredient(ingredientJson.optString(JSON_INGREDIENTS_INGREDIENT));
            ingredients[i].setMeasure(ingredientJson.optString(JSON_INGREDIENTS_MEASURE));
            ingredients[i].setQuantity(ingredientJson.optInt(JSON_INGREDIENTS_QUANTITY));
        }

        return ingredients;
    }
}
