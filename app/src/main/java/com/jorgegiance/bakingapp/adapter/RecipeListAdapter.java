package com.jorgegiance.bakingapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.ui.MainActivity;
import com.jorgegiance.bakingapp.util.Constants;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {


    private Context ctx;
    private List<Recipe> recipesList;
    private final RecipeAdapterOnClickHandler mClickHandler;
    private static final String TAG = MainActivity.class.getSimpleName();


    /**
     * This interface allows ...
     */
    public interface RecipeAdapterOnClickHandler {
        void onClick( Recipe recipe );
        //void onClick( int position );
    }


    /**
     * Constructor method
     *
     * @param ctx           Context
     * @param mClickHandler A RecipeAdapterOnClickHandler object
     */
    public RecipeListAdapter( Context ctx, RecipeAdapterOnClickHandler mClickHandler ) {
        this.ctx = ctx;
        this.mClickHandler = mClickHandler;
    }


    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {

        LayoutInflater recipeInflater = LayoutInflater.from(ctx);
        View recipeView = recipeInflater.inflate(R.layout.recipe_list_item, parent, false);
        RecipeHolder holder = new RecipeHolder(recipeView);

        return holder;
    }

    @Override
    public void onBindViewHolder( @NonNull RecipeHolder holder, int position ) {


        if (recipesList != null) {

            String recipeName = recipesList.get(position).getName();
            holder.cardTitle.setText(recipeName);


            Glide
                    .with(ctx)
                    .load(recipesList.get(position).getImage())         // "https://images-gmi-pmc.edge-generalmills.com/8b79836e-e3b4-4099-bf3b-79a21257b759.jpg"
                    .placeholder(Constants.loadRecipeImage(recipeName))
                    .centerCrop()
                    .into(holder.cardImage);


        } else {
            Log.d(TAG, "list is null");
        }

    }

    @Override
    public int getItemCount() {
        if (null == recipesList) {
            return 10;
        }
        return recipesList.size();

    }


    /**
     * This method update Recipes List state
     *
     * @param recipes
     */
    public void setRecipesList( List<Recipe> recipes ) {
        recipesList = recipes;
        notifyDataSetChanged();
    }





    // Holder Class

    public class RecipeHolder extends RecyclerView.ViewHolder {

        ImageView cardImage;
        TextView cardTitle;

        public RecipeHolder( @NonNull View itemView ) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.card_recipe_image);
            cardTitle = itemView.findViewById(R.id.card_recipe_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int adapterPosition = getAdapterPosition();

                    if (recipesList != null){
                        mClickHandler.onClick(recipesList.get(adapterPosition));
                    }else{
                        Toast.makeText(ctx, "Recipe is not available" , Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }


}

