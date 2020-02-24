package com.jorgegiance.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.jorgegiance.bakingapp.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if (savedInstanceState == null){

            RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_description_container, recipeDescriptionFragment)
                    .commit();
        }
    }
}
