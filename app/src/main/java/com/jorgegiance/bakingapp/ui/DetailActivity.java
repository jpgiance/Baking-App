package com.jorgegiance.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.util.Constants;
import com.jorgegiance.bakingapp.util.FragmentCom;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements FragmentCom {

    private Recipe detailRecipe;
    private Bundle recipeBundle;
    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        // ....getting intent from previous Activity
        Intent detailIntent = getIntent();

        if (detailIntent.hasExtra(Constants.recipeKey)) {

            // ....getting object <recipe> from intent
            recipeBundle = detailIntent.getExtras();
            detailRecipe = detailIntent.getParcelableExtra(Constants.recipeKey);

            Objects.requireNonNull(getSupportActionBar()).setTitle(detailRecipe.getName());
        }

        if (findViewById(R.id.recipe_media_container ) == null){


            if (savedInstanceState == null){

                RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
                recipeDescriptionFragment.setArguments(recipeBundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_description_container, recipeDescriptionFragment)
                        .commit();
            }
        }else{

            // This LinearLayout will only initially exist in the two-pane tablet case


            if (savedInstanceState == null){

                recipeBundle.putBoolean(Constants.isTwoPaneKey, true);
                RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
                recipeDescriptionFragment.setArguments(recipeBundle);
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_description_container, recipeDescriptionFragment)
                        .commit();

                RecipeMediaFragment recipeMediaFragment = new RecipeMediaFragment();
                recipeMediaFragment.setArguments(recipeBundle);
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_media_container, recipeMediaFragment)
                        .commit();


            }
        }





    }

    @Override
    public void inflateNewMediaFragment( int position ) {

        RecipeMediaFragment newRecipeMediaFragment = new RecipeMediaFragment();
        recipeBundle.putInt(Constants.positionKey, position);
        newRecipeMediaFragment.setArguments(recipeBundle);
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_media_container, newRecipeMediaFragment)
                .commit();
    }
}
