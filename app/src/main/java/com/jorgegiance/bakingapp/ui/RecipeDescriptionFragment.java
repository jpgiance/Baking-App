package com.jorgegiance.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.adapter.StepsInstructionsAdapter;
import com.jorgegiance.bakingapp.model.Step;

import java.util.List;

public class RecipeDescriptionFragment extends Fragment implements StepsInstructionsAdapter.InstructionAdapterOnClickHandler{


 //   private RecipeListAdapter.RecipeAdapterOnClickHandler clickHandler;
    private StepsInstructionsAdapter mAdapter;
    private RecyclerView recycler;
    private List<Step> stepsList;
    private static final String TAG = MainActivity.class.getSimpleName();

    // Constructor
    public RecipeDescriptionFragment() {
    }


    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {


        final View rootView = inflater.inflate(R.layout.fragment_recipe_description, container, false);
        recycler = rootView.findViewById(R.id.recipe_description_recycler);

        // ....setting up RecyclerView
        mAdapter = new StepsInstructionsAdapter(getContext(), this);
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setHasFixedSize(true);

       // loadSteps();

        // Return the root view
        return rootView;
    }


    private void loadSteps(){


    }

    @Override
    public void onClick( int position ) {
        Toast.makeText(getContext(), "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        Class mediaActivityClass = MediaActivity.class;
        Intent newIntent = new Intent(getContext(), mediaActivityClass);
        //  newIntent.putExtra("movie", movie);
        startActivity(newIntent);
    }
}
