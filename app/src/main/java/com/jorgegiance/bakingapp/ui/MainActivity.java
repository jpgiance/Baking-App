package com.jorgegiance.bakingapp.ui;
import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.util.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;





public class MainActivity extends AppCompatActivity  {


    RecipeListFragment recipeFragment;




    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFromWidget = false;
        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.widgetKey)){
            isFromWidget = true;
            bundle.putBoolean(Constants.widgetKey, true);
        }

        if (savedInstanceState == null){

            recipeFragment = new RecipeListFragment();

            if (isFromWidget){
                recipeFragment.setArguments(bundle);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_recycler_container, recipeFragment)
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {

        android.os.Process.killProcess(android.os.Process.myPid());
        // This above line close correctly
    }


}
