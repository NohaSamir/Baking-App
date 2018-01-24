package com.example.nsamir.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.model.Ingredients;
import com.example.nsamir.bakingapp.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;

public class IngredientsListViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetIngredientsAdapter(getApplicationContext());
    }
}

class WidgetIngredientsAdapter implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Ingredients> mRecipeIngredients;
    private Context mContext;


     WidgetIngredientsAdapter(Context context) {

        this.mContext = context;

         SharedPreferences appSharedPrefs = PreferenceManager
                 .getDefaultSharedPreferences(context);
         Gson gson = new Gson();
         String json = appSharedPrefs.getString("Recipe", "");
        try {
            Recipe recipe = gson.fromJson(json, Recipe.class);
            mRecipeIngredients = recipe.getIngredients();
        }
        catch (JsonIOException e)
        {
            e.printStackTrace();
        }catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if( mRecipeIngredients != null )
            return mRecipeIngredients.size();
        else return 0;

    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName() , R.layout.widget_list_item );

        if(mRecipeIngredients != null) {
            Ingredients ingredient = mRecipeIngredients.get(position);

            String ingredientText = ingredient.getQuantity() + " " + ingredient.getMeasure() +
                    " - " + ingredient.getIngredient();

            remoteViews.setTextViewText(R.id.textView, ingredientText);
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
