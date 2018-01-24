package com.example.nsamir.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.activity.RecipeListActivity;
import com.example.nsamir.bakingapp.model.Recipe;
import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private final ArrayList<Recipe> mRecipes;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Recipe recipe = (Recipe) view.getTag();
                Context context = view.getContext();
                Intent intent = new Intent(context, RecipeListActivity.class);
                intent.putExtra(RecipeListActivity.RECIPE, recipe);
                context.startActivity(intent);
        }
    };


    public RecipesAdapter(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mRecipeNameTextView.setText(mRecipes.get(position).getName());

        holder.itemView.setTag(mRecipes.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mRecipeNameTextView;

        ViewHolder(View view) {
            super(view);
            mRecipeNameTextView = view.findViewById(R.id.recipeNameTextView);

        }
    }
}
