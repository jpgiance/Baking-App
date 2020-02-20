package com.jorgegiance.bakingapp.adapter;

import android.content.Context;
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

import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeHolder> {


    private Context ctx;
    private List<Recipe> recipesList;
    private final RecipeAdapterOnClickHandler mClickHandler;


    /**
     * This interface allows ...
     */
    public interface RecipeAdapterOnClickHandler {
        //void onClick( Recipe recipe );
        void onClick( int position );
    }


    /**
     * Constructor method
     * @param ctx Context
     * @param mClickHandler A RecipeAdapterOnClickHandler object
     */
    public RecipeRecyclerAdapter( Context ctx, RecipeAdapterOnClickHandler mClickHandler ) {
        this.ctx = ctx;
        this.mClickHandler = mClickHandler;
    }



    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {

        LayoutInflater recipeInflater = LayoutInflater.from(ctx);
        View recipeView = recipeInflater.inflate(R.layout.recipe_card_item, parent, false);
        RecipeHolder holder = new RecipeHolder(recipeView);

//        holder.cardImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick( View v ) {
//                int adapterPosition = getAdapterPosition();
//                Toast.makeText(ctx, "Position clicked = " , Toast.LENGTH_SHORT).show();
//
//            }
//        });

        return holder;
    }

    @Override
    public void onBindViewHolder( @NonNull RecipeHolder holder, int position ) {

        holder.cardTitle.setText("Arroz con pollo");

        Glide
            .with(ctx)
            .load("https://images-gmi-pmc.edge-generalmills.com/8b79836e-e3b4-4099-bf3b-79a21257b759.jpg")
            .placeholder(R.mipmap.ic_launcher)
            .centerCrop()
            .into(holder.cardImage);

    }

    @Override
    public int getItemCount() {
        if (null == recipesList) {
            return 10;
        }
        return recipesList.size();

    }





    // Holder Class

    public class RecipeHolder extends RecyclerView.ViewHolder {

        ImageView cardImage;
        TextView cardTitle;

        public RecipeHolder( @NonNull View itemView ) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.card_recipe_image);
            cardTitle = itemView.findViewById(R.id.card_recipe_name);

            cardImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int adapterPosition = getAdapterPosition();
                    mClickHandler.onClick(adapterPosition);
                }
            });
        }




    }

}
