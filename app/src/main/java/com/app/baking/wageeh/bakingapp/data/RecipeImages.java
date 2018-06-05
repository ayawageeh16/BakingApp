package com.app.baking.wageeh.bakingapp.data;

import java.util.ArrayList;
import java.util.List;

public class RecipeImages {

    public List<String> CreateImages (){

        String image ;
        List<String> images = new ArrayList<>();

        image = "http://static.ngengs.com/images/udacity/image_nutella_pie.webp";
        images.add(image);

        image = "http://static.ngengs.com/images/udacity/image_brownies.webp";
        images.add(image);

        image = "http://static.ngengs.com/images/udacity/image_cheese_cake.webp";
        images.add(image);

        image = "http://static.ngengs.com/images/udacity/image_yellow_cake.webp";
        images.add(image);

        return images ;
    }



}
