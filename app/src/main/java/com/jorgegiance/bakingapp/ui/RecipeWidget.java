package com.jorgegiance.bakingapp.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.model.Ingredient;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.util.Constants;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget( Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId, String recipeName, String ingredients ) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.widgetKey, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);

        views.setTextViewText(R.id.widget_recipe_name, recipeName);
        views.setTextViewText(R.id.widget_ingredients, ingredients);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateAppWidget( Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.widgetKey, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateRecipeWidget( Context context, AppWidgetManager appWidgetManager,
                                 Recipe recipeSelected, int[] appWidgetIds ) {

        String recipeName = recipeSelected.getName();
        List<Ingredient> ingredients = recipeSelected.getIngredients();
        String ingredientsParsed;
        StringBuilder sb = new StringBuilder();

        for (Ingredient ingredient : ingredients){

            sb.append("* ");
            sb.append(ingredient.getQuantity());
            sb.append(ingredient.getMeasure());
            sb.append(" - ");
            sb.append(ingredient.getIngredient());
            sb.append("\n");
        }

        ingredientsParsed = sb.toString();

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, ingredientsParsed);
        }
    }

    @Override
    public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds ) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled( Context context ) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled( Context context ) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

