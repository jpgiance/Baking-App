package com.jorgegiance.bakingapp.util;



import com.jorgegiance.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {



    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

}
