package com.jorgegiance.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.adapter.RecipeListAdapter;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.util.ApiRequest;
import com.jorgegiance.bakingapp.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeListFragment extends Fragment implements RecipeListAdapter.RecipeAdapterOnClickHandler{


    private RecipeListAdapter.RecipeAdapterOnClickHandler clickHandler;
    private RecipeListAdapter mAdapter;
    private RecyclerView recycler;
    private List<Recipe> recipesList;
    private static final String TAG = MainActivity.class.getSimpleName();


    // Constructor
    public RecipeListFragment() {

    }


//    // Override onAttach to make sure that the container activity has implemented the clickHandler
//    @Override
//    public void onAttach( Context context) {
//        super.onAttach(context);
//
//        // This makes sure that the host activity has implemented the clickHandler interface
//        // If not, it throws an exception
//        try {
//            clickHandler = (RecipeListAdapter.RecipeAdapterOnClickHandler) context;
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
        mAdapter = new RecipeListAdapter(getContext(), this);
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
      //  Context context = this;
        Class detailActivityClass = DetailActivity.class;
        Intent newIntent = new Intent(getContext(), detailActivityClass);
     //  newIntent.putExtra("movie", movie);
        startActivity(newIntent);
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
