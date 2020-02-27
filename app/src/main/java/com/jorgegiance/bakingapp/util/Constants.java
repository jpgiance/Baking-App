package com.jorgegiance.bakingapp.util;

import com.jorgegiance.bakingapp.R;

public class Constants {

    public static final String apiRecipeBaseUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    public static final String recipeKey = "recipeBundle";
    public static final String positionKey = "position";
    public static final String isTwoPaneKey = "isTwoPane";
    public static final String userAgentKey = "iexoplayer-bakingApp";


    public Constants() {
    }


    public static String getApiRecipeBaseUrl() {
        return apiRecipeBaseUrl;
    }

    public static int loadRecipeImage( String name ) {

        switch (name) {

            case "Nutella Pie":
                return R.drawable.nutellapie;

            case "Brownies":
                return R.drawable.brownies;

            case "Yellow Cake":
                return R.drawable.yellowcake;

            case "Cheesecake":
                return R.drawable.cheesecake;

            default:
                return R.drawable.baking_placeholder;
        }

    }
}

