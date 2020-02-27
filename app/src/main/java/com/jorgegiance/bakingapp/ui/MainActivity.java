package com.jorgegiance.bakingapp.ui;
import com.jorgegiance.bakingapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;





public class MainActivity extends AppCompatActivity  {


    RecipeListFragment recipeFragment;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null){

            recipeFragment = new RecipeListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_recycler_container, recipeFragment)
                    .commit();
        }

    }


}
