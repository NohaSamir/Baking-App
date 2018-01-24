package com.example.nsamir.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.model.Recipe;

public class RecipeIntentService extends IntentService {

    private static final String ACTION_DISPLAY_RECIPE = "com.example.nsamir.bakingapp.service.action.DISPLAY";

    private static final String RECIPE_PARAM = "com.example.nsamir.bakingapp.service.extra.recipe";

    public RecipeIntentService() {
        super("RecipeIntentService");
    }


    public static void startActionDisplay(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeIntentService.class);
        intent.setAction(ACTION_DISPLAY_RECIPE);
        intent.putExtra(RECIPE_PARAM, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DISPLAY_RECIPE.equals(action)) {
                final Recipe recipe = intent.getParcelableExtra(RECIPE_PARAM);
                handleActionDisplayRecipe(recipe);
            }
        }
    }

    private void handleActionDisplayRecipe(Recipe recipe) {

        AppWidgetManager appWidgetManager =  AppWidgetManager.getInstance(this);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this , RecipeAppWidget.class));
        RecipeAppWidget.updateRecipesWidget( this , appWidgetManager , widgetIds , recipe );
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds , R.id.ingredientsList);
    }
}
