package com.example.nsamir.bakingapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.fragment.RecipeDetailsFragment;
import com.example.nsamir.bakingapp.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import java.util.ArrayList;
import butterknife.OnClick;

/**
 * An activity representing a single RecipeDetails detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailsActivity extends AppCompatActivity {

    //Views
    private Button previousBtn;
    private Button nextBtn;
    private SimpleExoPlayerView mSimpleExoPlayerView;

    //Constants
    public static String STEP_NUM = "step_num";
    public static String STEPS_LIST = "step_list";
    public static String CURRENT_VIDEO_POSITION = "current_position";

    //Variables
    private ArrayList<Steps> mRecipeSteps;
    private RecipeDetailsFragment mRecipeDetailsFragment;
    private SimpleExoPlayer mExoPlayer;
    private Context mContext;
    private int mStepNum;
    private long mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int mDeviceOrientation = getResources().getConfiguration().orientation;

        if (savedInstanceState == null) {

            Intent intent = getIntent();
            if(intent != null) {
                mStepNum = intent.getIntExtra(STEP_NUM, 0);
                mRecipeSteps = intent.getParcelableArrayListExtra(STEPS_LIST);
            }
        }else
        {
            mStepNum = savedInstanceState.getInt(STEP_NUM);
            mRecipeSteps = savedInstanceState.getParcelableArrayList(STEPS_LIST);
            if(savedInstanceState.containsKey(CURRENT_VIDEO_POSITION))
                mCurrentPosition = savedInstanceState.getLong(CURRENT_VIDEO_POSITION);
        }
        mContext = getApplicationContext();
        if(mDeviceOrientation == Configuration.ORIENTATION_LANDSCAPE && ! mRecipeSteps.get(mStepNum).getVideoURL().isEmpty())
        {
            bindLandscape();

        }else
        {
            bindPortrait();
        }
        setActionBarTitle(mRecipeSteps.get(mStepNum).getShortDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
           onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.previousBtn)
    public void getPreviousStep()
    {
        mStepNum --;
        if(mStepNum == 0 ) hidePreviousBtn();
        showNextBtn();
        replaceFragment();
    }

    private void replaceFragment()
    {
        setActionBarTitle(mRecipeSteps.get(mStepNum).getShortDescription());

        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeDetailsFragment.ARG_ITEM_STEP, mRecipeSteps.get(mStepNum));
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_container, fragment)
                .commit();
    }

    private void hideNextBtn()
    {
        nextBtn.setVisibility(View.INVISIBLE);
    }

    private void hidePreviousBtn()
    {
        previousBtn.setVisibility(View.INVISIBLE);
    }

    private void showNextBtn()
    {
        nextBtn.setVisibility(View.VISIBLE);
    }

    private void showPreviousBtn()
    {
        previousBtn.setVisibility(View.VISIBLE);
    }

    private void setActionBarTitle(String title)
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STEP_NUM , mStepNum);
        outState.putParcelableArrayList(STEPS_LIST , mRecipeSteps);
        if(mExoPlayer != null ) outState.putLong(CURRENT_VIDEO_POSITION , mExoPlayer.getCurrentPosition());
    }

    public void getNextStep(View view) {
        mStepNum ++;
        if(mStepNum == mRecipeSteps.size()-1) hideNextBtn();
        showPreviousBtn();
        replaceFragment();
    }

    public void getPreviousStep(View view) {

        mStepNum --;
        if(mStepNum == 0 ) hidePreviousBtn();
        showNextBtn();
        replaceFragment();
    }

    private void initializeExoPlayer(Uri uri) {
        //Uri temp = Uri.parse("https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4");

        if (mExoPlayer == null) {
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            // a factory to create an AdaptiveVideoTrackSelection
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(mContext),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                    new DefaultLoadControl());

            mSimpleExoPlayerView.setPlayer(mExoPlayer);
            mSimpleExoPlayerView.requestFocus();

            MediaSource mediaSource = RecipeDetailsFragment.buildMediaSource( getString(R.string.app_name) , uri);
            mExoPlayer.prepare(mediaSource, true, false);

            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(mCurrentPosition);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mExoPlayer != null)
        {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void bindPortrait()
    {
        setContentView(R.layout.activity_recipe_details);
        previousBtn = findViewById(R.id.previousBtn);
        nextBtn = findViewById(R.id.nextBtn);

        if (mStepNum == 0) previousBtn.setVisibility(View.GONE);
        if (mStepNum == mRecipeSteps.size() - 1) nextBtn.setVisibility(View.GONE);

        // Create the detail fragment and add it to the activity
        // using a fragment transaction.

        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeDetailsFragment.ARG_ITEM_STEP, mRecipeSteps.get(mStepNum));

        if(mRecipeDetailsFragment != null )
        {
            mRecipeDetailsFragment = new RecipeDetailsFragment();
            mRecipeDetailsFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_details_container, mRecipeDetailsFragment)
                    .commit();
        }else
        {
            replaceFragment();
        }
    }

    private void bindLandscape()
    {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe_details_land);
        mSimpleExoPlayerView = findViewById(R.id.videoPlayer);
        initializeExoPlayer(Uri.parse(mRecipeSteps.get(mStepNum).getVideoURL()));

    }
}
