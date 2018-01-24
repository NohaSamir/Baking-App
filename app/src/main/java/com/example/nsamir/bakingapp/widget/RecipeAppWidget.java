package com.example.nsamir.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.activity.MainActivity;
import com.example.nsamir.bakingapp.activity.RecipeListActivity;
import com.example.nsamir.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);

        // Instruct the widget manager to update the widget

        Intent intent = new Intent(context , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context ,0 , intent , 0);
        views.setOnClickPendingIntent(R.id.widget_layout , pendingIntent );

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,  Recipe recipe) {


        appWidgetManager.updateAppWidget(appWidgetId, getRecipeRemoteView(context , recipe));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateRecipesWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds , Recipe recipe) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId , recipe);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getRecipeRemoteView(Context context , Recipe recipe)
    {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
        views.setTextViewText(R.id.recipeTitle, recipe.getName());

        Intent intent = new Intent(context , IngredientsListViewService.class);
        views.setRemoteAdapter(R.id.ingredientsList , intent);

        Intent intentActivity = new Intent(context , RecipeListActivity.class);
        intentActivity.putExtra(RecipeListActivity.RECIPE , recipe);

        PendingIntent pendingIntent = PendingIntent.getActivity(context ,0 , intentActivity , PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_layout , pendingIntent );

        return  views;
    }

}
