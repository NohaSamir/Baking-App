package com.example.nsamir.bakingapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.activity.RecipeDetailsActivity;
import com.example.nsamir.bakingapp.activity.RecipeListActivity;
import com.example.nsamir.bakingapp.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a single RecipeDetails detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailsActivity}
 * on handsets.
 */
public class RecipeDetailsFragment extends Fragment //implements ExoPlayer.EventListener
{

    @Bind(R.id.videoPlayer)
    SimpleExoPlayerView mSimpleExoPlayerView;

    @Bind(R.id.recipe_description_text)
    TextView recipeDescriptionText;

    public static final String ARG_ITEM_STEP = "step";

    private Steps mStep;
    private SimpleExoPlayer mExoPlayer;
    private Context mContext;

    public RecipeDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_STEP)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mStep = getArguments().getParcelable(ARG_ITEM_STEP);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_details, container, false);
        ButterKnife.bind(this, rootView);
        mContext = rootView.getContext();

        // Show the dummy content as text in a TextView.
        if (mStep != null) {

            recipeDescriptionText.setText(mStep.getDescription());

            if (mStep.getVideoURL() != null && !mStep.getVideoURL().isEmpty()) {
                initializeExoPloyer(Uri.parse(mStep.getVideoURL()));
            } else {
                mSimpleExoPlayerView.setVisibility(View.GONE);
            }
        }

        return rootView;
    }

    private void initializeExoPloyer(Uri uri) {

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



            MediaSource mediaSource = buildMediaSource(getString(R.string.app_name) , uri);
            mExoPlayer.prepare(mediaSource, true, false);

            mSimpleExoPlayerView.setPlayer(mExoPlayer);
            mSimpleExoPlayerView.requestFocus();
            mExoPlayer.setPlayWhenReady(true);

            //mExoPlayer.seekTo(currentWindow, playbackPosition);

        }


    }
    public static MediaSource buildMediaSource(String appName , Uri uri) {

        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(appName)).
                createMediaSource(uri);
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
}
