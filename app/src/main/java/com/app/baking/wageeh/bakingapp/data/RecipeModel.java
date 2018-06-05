package com.app.baking.wageeh.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeModel implements Parcelable {
    private final int id;
    private final String name;
    private final IngredientModel[] ingredients;
    private final StepsModel[] steps;
    private final int servings;
    private final String image;

    public RecipeModel(int id, String name, IngredientModel[] ingredients, StepsModel[] steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    private RecipeModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArray(IngredientModel.CREATOR);
        steps = in.createTypedArray(StepsModel.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public IngredientModel[] getIngredients() {
        return ingredients;
    }

    public StepsModel[] getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedArray(ingredients, i);
        parcel.writeTypedArray(steps, i);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }
}
