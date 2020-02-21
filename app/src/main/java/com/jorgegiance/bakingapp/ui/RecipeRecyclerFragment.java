package com.jorgegiance.bakingapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.adapter.RecipeRecyclerAdapter;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.util.ApiRequest;
import com.jorgegiance.bakingapp.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRecyclerFragment extends Fragment implements RecipeRecyclerAdapter.RecipeAdapterOnClickHandler{


    private RecipeRecyclerAdapter.RecipeAdapterOnClickHandler clickHandler;
    private RecipeRecyclerAdapter mAdapter;
    private RecyclerView recycler;
    private List<Recipe> recipesList;
    private static final String TAG = MainActivity.class.getSimpleName();


    // Constructor
    public RecipeRecyclerFragment() {

    }


//    // Override onAttach to make sure that the container activity has implemented the clickHandler
//    @Override
//    public void onAttach( Context context) {
//        super.onAttach(context);
//
//        // This makes sure that the host activity has implemented the clickHandler interface
//        // If not, it throws an exception
//        try {
//            clickHandler = (RecipeRecyclerAdapter.RecipeAdapterOnClickHandler) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement RecipeAdapterOnClickHandler");
//        }
//    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_recycler, container, false);
        recycler = rootView.findViewById(R.id.recipe_recycler_view);

        // ....setting up RecyclerView
        mAdapter = new RecipeRecyclerAdapter(getContext(), this);
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns()));
        recycler.setHasFixedSize(true);

        loadRecipes();

        // Return the root view
        return rootView;
    }


    private int numberOfColumns() {

        //get Display orientation
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            return 1;
        }
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            return 2;
        }

        return 1;
    }


    @Override
    public void onClick( int position ) {

        Toast.makeText(getContext(), "Position clicked = " + position, Toast.LENGTH_SHORT).show();

    }


    private void loadRecipes(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.getApiRecipeBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest client =  retrofit.create(ApiRequest.class);



        Call<List<Recipe>> call = client.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse( Call<List<Recipe>> call, Response<List<Recipe>> response ) {

                if (!response.isSuccessful()){

                    Log.d(TAG, "API response unsuccessful");

                }else {

                    recipesList = response.body();
                    mAdapter.setRecipesList(recipesList);
                    Log.d(TAG, "list is not null " + recipesList.get(1).getIngredients().get(1).getIngredient());

                }

            }

            @Override
            public void onFailure( Call<List<Recipe>> call, Throwable t ) {

                Log.d(TAG, "API response Failure" + t.getMessage());
            }
        });

    }

}
