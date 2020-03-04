package com.jorgegiance.bakingapp.ui;
import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.util.Constants;
import com.jorgegiance.bakingapp.util.SimpleIdlingResource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.IdlingResource;

import android.content.Intent;
import android.os.Bundle;





public class MainActivity extends AppCompatActivity  {


    RecipeListFragment recipeFragment;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;




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


    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }




}
