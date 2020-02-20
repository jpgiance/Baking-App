package com.jorgegiance.bakingapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

public class RecipeRecyclerFragment extends Fragment {


    private RecipeRecyclerAdapter.RecipeAdapterOnClickHandler clickHandler;
    private RecyclerView recycler;

    // Constructor
    public RecipeRecyclerFragment() {

    }


    // Override onAttach to make sure that the container activity has implemented the clickHandler
    @Override
    public void onAttach( Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the clickHandler interface
        // If not, it throws an exception
        try {
            clickHandler = (RecipeRecyclerAdapter.RecipeAdapterOnClickHandler) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RecipeAdapterOnClickHandler");
        }
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_recycler, container, false);
        recycler = rootView.findViewById(R.id.recipe_recycler_view);

        // ....setting up RecyclerView
        RecipeRecyclerAdapter mAdapter = new RecipeRecyclerAdapter(getContext(), clickHandler);
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns()));



        // Return the root view
        return rootView;
    }


    private int numberOfColumns() {


        return 1;
    }

}
