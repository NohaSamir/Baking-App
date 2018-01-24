package com.example.nsamir.bakingapp.activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.nsamir.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.adapter.RecipesAdapter;
import com.example.nsamir.bakingapp.data.RecipesRequests;
import com.example.nsamir.bakingapp.model.Recipe;
import com.example.nsamir.bakingapp.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityActions {

    @Bind(R.id.recipesRecycler)
    RecyclerView mRecipesRecycler;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.errorTextView)
    TextView errorTextView;

    private Activity mActivity;
    private static final String TAG = MainActivity.class.getSimpleName();
    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        ButterKnife.bind(this);
        setUpRecycler(getResources().getBoolean(R.bool.is_phone));

        if (NetworkUtils.isNetworkAvailable(mActivity)) {
            showProgress();
            loadRecipes();
        } else {
            showErrorMsg(R.string.no_internet);
        }
    }


    private void loadRecipes()
    {
        // The IdlingResource is null in production.
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        RecipesRequests.getRecipes(mActivity, new RecipesRequests.ArrayVolleyResponseListener() {
            @Override
            public void onSuccess(JSONArray jsonObject) {

                hideProgress();
                hideErrorMsg();
                try {

                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Recipe>>() {}.getType();
                    Log.i(TAG , jsonObject.toString());
                    ArrayList<Recipe> recipesList = gson.fromJson(String.valueOf(jsonObject), type);
                    bindRecipesList(recipesList);
                    if (mIdlingResource != null) {
                        mIdlingResource.setIdleState(true);
                    }


                }catch (JsonIOException e)
                {
                    e.printStackTrace();

                }catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyError volleyError) {
                hideProgress();
                showErrorMsg(R.string.error_message);
            }
        });
    }

    @Override
    public void bindRecipesList(ArrayList<Recipe> recipes) {
        mRecipesRecycler.setAdapter(new RecipesAdapter(recipes));
        mRecipesRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg(int msgId) {
        errorTextView.setText(getString(msgId));
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorMsg() {
        errorTextView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    private void setUpRecycler(boolean isPhone)
    {
        if(isPhone)
        {
            mRecipesRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        }
        else
        {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity , calculateNoOfColumns(mActivity));
            mRecipesRecycler.setLayoutManager(gridLayoutManager);
        }
        mRecipesRecycler.setHasFixedSize(true);
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
