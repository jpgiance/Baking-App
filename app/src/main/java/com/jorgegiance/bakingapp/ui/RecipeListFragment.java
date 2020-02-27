package com.jorgegiance.bakingapp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.adapter.RecipeListAdapter;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.util.ApiRequest;
import com.jorgegiance.bakingapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeListFragment extends Fragment implements RecipeListAdapter.RecipeAdapterOnClickHandler, SwipeRefreshLayout.OnRefreshListener{


    private Context ctx;
    private RecipeListAdapter.RecipeAdapterOnClickHandler clickHandler;
    private RecipeListAdapter mAdapter;
    private RecyclerView recycler;
    private ArrayList<Recipe> recipesList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = MainActivity.class.getSimpleName();


    // Constructor
    public RecipeListFragment() {

    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {



        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        recycler = rootView.findViewById(R.id.recipe_recycler_view);

        // ....setting up RecyclerView
        mAdapter = new RecipeListAdapter(ctx, this);
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new GridLayoutManager(ctx, numberOfColumns()));
        recycler.setHasFixedSize(true);

        // ....setting up the swipe refresh action
        swipeRefreshLayout = rootView.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);


        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.recipeKey)){
            recipesList = savedInstanceState.getParcelableArrayList(Constants.recipeKey);
            mAdapter.setRecipesList(recipesList);
        }else {

            loadRecipes();
        }


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
    public void onClick(Recipe recipe ) {


        Class detailActivityClass = DetailActivity.class;
        Intent newIntent = new Intent(ctx, detailActivityClass);
        newIntent.putExtra(Constants.recipeKey, recipe);
        startActivity(newIntent);
    }


    @Override
    public void onAttach( Context context ) {
        super.onAttach(context);
        ctx = context;
    }


    @Override
    public void onSaveInstanceState( @NonNull Bundle outState ) {
        if (recipesList != null){
            outState.putParcelableArrayList(Constants.recipeKey, recipesList);
        }

        super.onSaveInstanceState(outState);
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
                    swipeRefreshLayout.setRefreshing(false);

                }else {

                    recipesList = (ArrayList<Recipe>) response.body();
                    mAdapter.setRecipesList(recipesList);
                    Log.d(TAG, "list is not null " + recipesList.get(1).getIngredients().get(1).getIngredient());
                    swipeRefreshLayout.setRefreshing(false);


                }

            }

            @Override
            public void onFailure( Call<List<Recipe>> call, Throwable t ) {

                Log.d(TAG, "API response Failure" + t.getMessage());
                swipeRefreshLayout.setRefreshing(false);


                new AlertDialog.Builder(ctx)
                        .setTitle("No internet connection")
                        .setMessage("Please swipe to refresh")
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

    }

    @Override
    public void onRefresh() {
        loadRecipes();
    }
}
