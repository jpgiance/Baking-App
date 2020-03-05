package com.jorgegiance.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.jorgegiance.bakingapp.R;
import com.jorgegiance.bakingapp.model.Recipe;
import com.jorgegiance.bakingapp.model.Step;
import com.jorgegiance.bakingapp.util.Constants;

import java.util.List;

public class RecipeMediaFragment extends Fragment {


    private Recipe detailRecipe;
    private List<Step> stepsList;
    private int stepPosition = 0;
    private String videoURL;
    private String imageURL;
    private String thumbnailURL;
    private PlayerView mediaPlayerView;
    private SimpleExoPlayer mediaPlayer;
    private ImageButton forwardButton;
    private ImageButton backwardButton;
    private ImageView recipeImage;
    private TextView stepDescription;
    private Context ctx;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private PlaybackStateListener playbackStateListener;


    private static final String TAG = MainActivity.class.getSimpleName();


    // Constructor
    public RecipeMediaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_media, container, false);

        mediaPlayerView = rootView.findViewById(R.id.player_view);
        recipeImage = rootView.findViewById(R.id.recipe_player_image);
        forwardButton = rootView.findViewById(R.id.icon_forward);
        backwardButton = rootView.findViewById(R.id.icon_backward);
        stepDescription = rootView.findViewById(R.id.step_description);

        if (savedInstanceState == null ){


            if (getArguments() != null){
                detailRecipe = getArguments().getParcelable(Constants.recipeKey);
                stepsList = detailRecipe != null ? detailRecipe.getSteps() : null;
                stepPosition = getArguments().getInt(Constants.positionKey);
                videoURL = stepsList.get(stepPosition).getVideoURL();
                thumbnailURL = stepsList.get(stepPosition).getThumbnailURL();
                imageURL = detailRecipe.getImage();

            }
        }else if (savedInstanceState.containsKey(Constants.positionKey)){

            if (savedInstanceState.containsKey(Constants.playbackPositionKey) || savedInstanceState.containsKey(Constants.playerStateKey)){
                playbackPosition = savedInstanceState.getLong(Constants.playbackPositionKey);
                playWhenReady = savedInstanceState.getBoolean(Constants.playerStateKey);
            }

            if (getArguments() != null){
                detailRecipe = getArguments().getParcelable(Constants.recipeKey);
                stepsList = detailRecipe != null ? detailRecipe.getSteps() : null;
                stepPosition = getArguments().getInt(Constants.positionKey);
                videoURL = stepsList.get(stepPosition).getVideoURL();
                thumbnailURL = stepsList.get(stepPosition).getThumbnailURL();
                imageURL = detailRecipe.getImage();

            }
           stepPosition = savedInstanceState.getInt(Constants.positionKey);
            updateCurrentStep();
        }

        playbackStateListener = new PlaybackStateListener();




        forwardButton.setOnClickListener(v -> {
            if (stepPosition < (stepsList.size()-1)){
                stepPosition++;
                releasePlayer();
                updateCurrentStep();
                playWhenReady = true;
                playbackPosition = 0;
                setVisibilityForMediaView();
            }

            if(stepPosition == (stepsList.size()-1)){
                forwardButton.setVisibility(View.INVISIBLE);
            }else{forwardButton.setVisibility(View.VISIBLE);}
            if(stepPosition == 0){
                backwardButton.setVisibility(View.INVISIBLE);
            }else{backwardButton.setVisibility(View.VISIBLE);}
        });

        backwardButton.setOnClickListener(v -> {
            if (stepPosition > 0){
                stepPosition--;
                releasePlayer();
                updateCurrentStep();
                playWhenReady = true;
                playbackPosition = 0;
                setVisibilityForMediaView();
            }

            if(stepPosition == 0){
                backwardButton.setVisibility(View.INVISIBLE);
            }else{backwardButton.setVisibility(View.VISIBLE);}
            if(stepPosition == (stepsList.size()-1)){
                forwardButton.setVisibility(View.INVISIBLE);
            }else{forwardButton.setVisibility(View.VISIBLE);}
        });


        if (detailRecipe != null){
            setVisibilityForMediaView();
        }


        return rootView;
    }


    private void setVisibilityForMediaView(){

        stepDescription.setText(stepsList.get(stepPosition).getDescription());

        if ( videoURL != null && !videoURL.isEmpty() ){

            if(mediaPlayer == null){
                initMediaPlayer();
            }
            mediaPlayerView.setVisibility(View.VISIBLE);
            recipeImage.setVisibility(View.GONE);




        }else if(thumbnailURL != null && !thumbnailURL.isEmpty()){

            mediaPlayerView.setVisibility(View.GONE);
            recipeImage.setVisibility(View.VISIBLE);

            Glide
                    .with(ctx)
                    .load(thumbnailURL)
                    .placeholder(Constants.loadRecipeImage(detailRecipe.getName()))
                    .centerCrop()
                    .into(recipeImage);
        }else {
            mediaPlayerView.setVisibility(View.GONE);
            recipeImage.setVisibility(View.VISIBLE);

            Glide
                    .with(ctx)
                    .load(imageURL)
                    .placeholder(Constants.loadRecipeImage(detailRecipe.getName()))
                    .centerCrop()
                    .into(recipeImage);
        }


    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach(context);
        ctx = context;
    }


    private void updateCurrentStep(){

        videoURL = stepsList.get(stepPosition).getVideoURL();
        thumbnailURL = stepsList.get(stepPosition).getThumbnailURL();
    }


    @Override
    public void onSaveInstanceState( @NonNull Bundle outState ) {


        outState.putLong(Constants.playbackPositionKey, playbackPosition);
        outState.putBoolean(Constants.playerStateKey, playWhenReady);


        outState.putInt(Constants.positionKey, stepPosition);
        super.onSaveInstanceState(outState);
    }

    private void initMediaPlayer(){

        if(mediaPlayer == null){

            mediaPlayer = ExoPlayerFactory.newSimpleInstance(ctx);
            mediaPlayerView.setPlayer(mediaPlayer);

            Uri uri = Uri.parse(videoURL);
            MediaSource mediaSource = buildMediaSource(uri);

            mediaPlayer.setPlayWhenReady(playWhenReady);
            mediaPlayer.seekTo(currentWindow, playbackPosition);
            mediaPlayer.prepare(mediaSource, false, false);

            mediaPlayer.addListener(playbackStateListener);
            mediaPlayer.prepare(mediaSource, false, false);
        }

   }


    private void releasePlayer() {

        if (mediaPlayer != null) {
            playWhenReady = mediaPlayer.getPlayWhenReady();
            playbackPosition = mediaPlayer.getCurrentPosition();
            currentWindow = mediaPlayer.getCurrentWindowIndex();
            mediaPlayer.removeListener(playbackStateListener);
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private MediaSource buildMediaSource( Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(ctx, Constants.userAgentKey);

        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            setVisibilityForMediaView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || mediaPlayer == null)) {
            setVisibilityForMediaView();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }









    private class PlaybackStateListener implements Player.EventListener{

        @Override
        public void onPlayerStateChanged( boolean playWhenReady, int playbackState ) {

            String stateString;

            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }

            Log.d(TAG, "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }


    }
}
