package com.jorgegiance.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.adapter.IngredientsListAdapter;
import com.jorgegiance.bakingapp.adapter.StepsListAdapter;
import com.jorgegiance.bakingapp.model.Ingredient;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.model.Step;
import com.jorgegiance.bakingapp.util.Constants;
import com.jorgegiance.bakingapp.util.FragmentCom;

import java.util.List;

public class RecipeDescriptionFragment extends Fragment implements StepsListAdapter.InstructionAdapterOnClickHandler{


    private Context ctx;
    private StepsListAdapter stepsAdapter;
    private IngredientsListAdapter ingredientsAdapter;
    private RecyclerView stepsRecycler;
    private RecyclerView ingredientsRecycler;
    private TextView servings;
    private ImageView recipeImage;
    private Recipe detailRecipe;
    private Bundle recipeBundle;
    private List<Step> stepsList;
    private List<Ingredient> ingredientsList;
    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentCom mfragmentCom;
    private boolean isTwoPane;

    // Constructor
    public RecipeDescriptionFragment() {
    }


    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {

        if (getArguments() != null){
            recipeBundle = getArguments();
            detailRecipe = recipeBundle.getParcelable(Constants.recipeKey);
            isTwoPane = recipeBundle.getBoolean(Constants.isTwoPaneKey);
            stepsList = detailRecipe.getSteps();
            ingredientsList = detailRecipe.getIngredients();

        }

        final View rootView = inflater.inflate(R.layout.fragment_recipe_description, container, false);

        stepsRecycler = rootView.findViewById(R.id.recipe_description_recycler);
        ingredientsRecycler = rootView.findViewById(R.id.recipe_ingredients_recycler);
        recipeImage = rootView.findViewById(R.id.recipe_image);
        servings = rootView.findViewById(R.id.recipe_servings);

        servings.setText("Servings: " + detailRecipe.getServings());
        Glide
                .with(ctx)
                .load( detailRecipe.getImage())
                .placeholder(Constants.loadRecipeImage(detailRecipe.getName()))
                .centerCrop()
                .into(recipeImage);





        // ....setting up RecyclerView
        stepsAdapter = new StepsListAdapter(ctx, this);
        stepsRecycler.setAdapter(stepsAdapter);
        stepsRecycler.setLayoutManager(new LinearLayoutManager(ctx));
        stepsRecycler.setHasFixedSize(true);

        // ....setting up RecyclerView
        ingredientsAdapter = new IngredientsListAdapter(ctx);
        ingredientsRecycler.setAdapter(ingredientsAdapter);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(ctx));
        ingredientsRecycler.setHasFixedSize(true);

        if (stepsList != null){
            stepsAdapter.setStepsList(stepsList);
        }
        if (ingredientsList != null){
            ingredientsAdapter.setIngredientsList(ingredientsList);
        }



        // Return the root view
        return rootView;
    }


    @Override
    public void onAttach( Context context ) {
        super.onAttach(context);
        ctx = context;
        mfragmentCom = (FragmentCom) getActivity();
    }


    @Override
    public void onClick( int position ) {



        if (isTwoPane){

            mfragmentCom.inflateNewMediaFragment(position);

        }else{

            Class mediaActivityClass = MediaActivity.class;
            Intent newIntent = new Intent(ctx, mediaActivityClass);
            recipeBundle.putInt(Constants.positionKey, position);
            newIntent.putExtra(Constants.recipeKey, recipeBundle);
            startActivity(newIntent);
        }
    }
}
