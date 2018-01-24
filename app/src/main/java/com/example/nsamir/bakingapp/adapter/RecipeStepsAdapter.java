package com.example.nsamir.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.activity.RecipeDetailsActivity;
import com.example.nsamir.bakingapp.fragment.RecipeDetailsFragment;
import com.example.nsamir.bakingapp.activity.RecipeListActivity;
import com.example.nsamir.bakingapp.model.Recipe;
import com.example.nsamir.bakingapp.model.Steps;
import com.example.nsamir.bakingapp.widget.RecipeIntentService;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final RecipeListActivity mParentActivity;
    private final Recipe mRecipe;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private View mLastSelected ;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(mLastSelected != null) mLastSelected.setBackgroundColor(mParentActivity.getResources().getColor(R.color.white));
            view.setBackgroundColor(mParentActivity.getResources().getColor(R.color.SelectedColor));
            mLastSelected = view;

            int position = (int) view.getTag() - 1;
            Steps step = mRecipe.getSteps().get(position);
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(RecipeDetailsFragment.ARG_ITEM_STEP, step);
                RecipeDetailsFragment fragment = new RecipeDetailsFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_details_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra(RecipeDetailsActivity.STEP_NUM ,position );
                intent.putExtra(RecipeDetailsActivity.STEPS_LIST, mRecipe.getSteps());
                context.startActivity(intent);
            }
        }
    };

    public RecipeStepsAdapter(RecipeListActivity parent,
                          Recipe recipe,
                          boolean twoPane) {
        mRecipe = recipe;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_step_list, parent, false);
            return new StepsViewHolder(view);

        } else if (viewType == TYPE_HEADER) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredients_layout, parent, false);
            return new HeaderViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

            HeaderViewHolder header = (HeaderViewHolder)holder;
            header.ingredientsList.setAdapter(new IngredientsAdapter(mRecipe.getIngredients()));
            header.ingredientsList.setVisibility(View.VISIBLE);
        }
        else {
            Steps step = mRecipe.getSteps().get(position - 1);

            String stepShortDescription;
            if (position == 1) stepShortDescription = step.getShortDescription();
            else stepShortDescription = step.getId() + ". " + step.getShortDescription();

            ((StepsViewHolder)holder).mStepDescTextView.setText(stepShortDescription);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return mRecipe.getSteps().size() + 1 ;
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.stepDescTextView)
        TextView mStepDescTextView;

        @Bind(R.id.root_layout)
        LinearLayout rootLayout;

        StepsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this , view);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ingredientsText)
        TextView ingredients;

        @Bind(R.id.ingredientsList)
        RecyclerView ingredientsList;

        @Bind(R.id.addWidgetBtn)
        ImageView addWidgetBtn;

         HeaderViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            ingredientsList.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            ingredientsList.setHasFixedSize(true);
            addWidgetBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveToSharedPreference(mRecipe);
                    RecipeIntentService.startActionDisplay(itemView.getContext() , mRecipe);
                    mParentActivity.moveTaskToBack(true);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
            if (position == 0 )
                return TYPE_HEADER;

            return TYPE_ITEM;
    }

    private void saveToSharedPreference(Recipe recipe)
    {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mParentActivity);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefsEditor.putString("Recipe", json);
        prefsEditor.commit();
    }

}

