package com.jorgegiance.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.adapter.RecipeRecyclerAdapter;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.util.ApiRequest;
import com.jorgegiance.bakingapp.util.Constants;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity  {





    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){

            RecipeRecyclerFragment recipeFragment = new RecipeRecyclerFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_recycler_container, recipeFragment)
                    .commit();
        }










    }



    private void getListOfRecipies(){

    }
}
