package com.example.nsamir.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.adapter.RecipeStepsAdapter;
import com.example.nsamir.bakingapp.model.Recipe;

/**
 * An activity representing a list of RecipeDetails. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailsActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    public static final String RECIPE = "recipe";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

      /*  Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        if (findViewById(R.id.recipe_details_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        Intent intent = getIntent();

        if(intent != null) {
            mRecipe = getIntent().getParcelableExtra(RECIPE);

            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null )actionBar.setTitle(mRecipe.getName());

            View recyclerView = findViewById(R.id.recipedetails_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new RecipeStepsAdapter(this, mRecipe, mTwoPane));
    }
}