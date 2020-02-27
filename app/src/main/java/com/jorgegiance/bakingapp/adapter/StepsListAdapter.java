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
import com.jorgegiance.bakingapp.model.Step;
import com.jorgegiance.bakingapp.ui.MainActivity;

import java.util.List;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.InstructionsHolder> {



    private Context ctx;
    private List<Step> stepsList;
    private final InstructionAdapterOnClickHandler mClickHandler;
    private static final String TAG = MainActivity.class.getSimpleName();


    /**
     * This interface allows ...
     */
    public interface InstructionAdapterOnClickHandler {

        void onClick( int position );
    }


    /**
     * Constructor method
     *
     * @param ctx           Context
     * @param mClickHandler A RecipeAdapterOnClickHandler object
     */
    public StepsListAdapter( Context ctx, InstructionAdapterOnClickHandler mClickHandler ) {
        this.ctx = ctx;
        this.mClickHandler = mClickHandler;
    }



    @NonNull
    @Override
    public InstructionsHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {

        LayoutInflater recipeInflater = LayoutInflater.from(ctx);
        View recipeView = recipeInflater.inflate(R.layout.recipe_step_item, parent, false);
        InstructionsHolder holder = new InstructionsHolder(recipeView);

        return holder;
    }


    @Override
    public void onBindViewHolder( @NonNull InstructionsHolder holder, int position ) {


        if (stepsList != null) {

            holder.stepInstruction.setText(stepsList.get(position).getShortDescription());


        } else {
            Log.d(TAG, "list is null");
        }

    }

    @Override
    public int getItemCount() {
        if (null == stepsList) {
            return 20;
        }
        return stepsList.size();

    }


    /**
     * This method update Steps List state
     *
     * @param steps
     */
    public void setStepsList( List<Step> steps ) {
        stepsList = steps;
        notifyDataSetChanged();
    }



    // Holder Class

    public class InstructionsHolder extends RecyclerView.ViewHolder {

        TextView stepInstruction;

        public InstructionsHolder( @NonNull View itemView ) {
            super(itemView);

            stepInstruction = itemView.findViewById(R.id.step);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    mClickHandler.onClick(getAdapterPosition());
                }
            });


        }

    }
}
