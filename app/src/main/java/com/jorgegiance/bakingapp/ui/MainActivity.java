package com.jorgegiance.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.adapter.RecipeRecyclerAdapter;
import com.jorgegiance.bakingapp.model.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeRecyclerAdapter.RecipeAdapterOnClickHandler {

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


    @Override
    public void onClick( int position ) {

        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
    }

}
