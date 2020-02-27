package com.jorgegiance.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.util.Constants;

public class MediaActivity extends AppCompatActivity {

    private Recipe detailRecipe;
    private Bundle recipeBundle;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);


        // ....getting intent from previous Activity
        Intent detailIntent = getIntent();

        if (detailIntent.hasExtra(Constants.recipeKey) ) {

            // ....getting object <recipe> from intent
            recipeBundle = detailIntent.getBundleExtra(Constants.recipeKey);
            detailRecipe = recipeBundle.getParcelable(Constants.recipeKey);


            getSupportActionBar().setTitle(detailRecipe.getName());
        }

        if (savedInstanceState == null){

            RecipeMediaFragment recipeMediaFragment = new RecipeMediaFragment();
            recipeMediaFragment.setArguments(recipeBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_media_container, recipeMediaFragment)
                    .commit();
        }
    }


}
