package com.jorgegiance.bakingapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.model.Ingredient;
import com.jorgegiance.bakingapp.model.Step;
import com.jorgegiance.bakingapp.ui.MainActivity;

import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsHolder> {



    private Context ctx;
    private List<Ingredient> ingredientsList;
    private static final String TAG = MainActivity.class.getSimpleName();




    /**
     * Constructor method
     *
     * @param ctx           Context
     */
    public IngredientsListAdapter( Context ctx) {
        this.ctx = ctx;
    }



    @NonNull
    @Override
    public IngredientsHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {

        LayoutInflater recipeInflater = LayoutInflater.from(ctx);
        View ingredientView = recipeInflater.inflate(R.layout.recipe_ingredient_item, parent, false);
        IngredientsHolder holder = new IngredientsHolder(ingredientView);

        return holder;
    }


    @Override
    public void onBindViewHolder( @NonNull IngredientsHolder holder, int position ) {


        if (ingredientsList != null) {

            holder.ingredientQuantity.setText("("
                    + ingredientsList.get(position).getQuantity()
                    + " "
                    + ingredientsList.get(position).getMeasure()
                    + ")" );

            holder.ingredientName.setText(ingredientsList.get(position).getIngredient());


        } else {
            Log.d(TAG, "list is null");
        }

    }

    @Override
    public int getItemCount() {
        if (null == ingredientsList) {
            return 0;
        }
        return ingredientsList.size();

    }


    /**
     * This method update Ingredients List state
     *
     * @param ingredients
     */
    public void setIngredientsList( List<Ingredient> ingredients ) {
        ingredientsList = ingredients;
        notifyDataSetChanged();
    }



// Holder Class

    public class IngredientsHolder extends RecyclerView.ViewHolder {

        TextView ingredientQuantity;
        TextView ingredientName;

        public IngredientsHolder( @NonNull View itemView ) {
            super(itemView);

            ingredientQuantity = itemView.findViewById(R.id.ingredient_quantity);
            ingredientName = itemView.findViewById(R.id.ingredient_name);

        }

    }
}
