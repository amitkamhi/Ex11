package com.example.kamhi.ex11.View;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import com.example.kamhi.ex11.Model.Country;
import com.example.kamhi.ex11.R;

import java.io.IOException;

/**
 * Created by Kamhi on 3/1/2017.
 */

public class DetailsFragment extends Fragment{

    boolean playing;
    TextView tVDetails;
    CountryReporter listener;
    Context context;
    static MediaPlayer mediaPlayer;
    MediaController mediaController;
    Handler handler;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.context = getActivity();
        try {
            this.listener = (CountryReporter) getActivity();
            Country country = this.listener.getCountryData();
            if(country!=null){
                changeTo(country);
            }
        }
        catch (ClassCastException e){
            throw new ClassCastException("The class " + getActivity().getClass().getName() + " must implements the interfase 'DataReporter");
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.tVDetails = (TextView)view.findViewById(R.id.details);
        tVDetails.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mediaController.show();
                return true;
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_frag, container, false);
    }

    public void changeTo(Country newCountry) {


        this.tVDetails.setText(newCountry.getDetails());

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        final int anthemResource = context.getResources().getIdentifier(newCountry.getAnthem(), "raw", context.getPackageName());
        mediaPlayer = MediaPlayer.create(context,anthemResource);
        mediaController = new MediaController(this.context);
        mediaController.setMediaPlayer(new MusicController());
        mediaController.setAnchorView(this.tVDetails);
        mediaPlayer.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!= null){
            mediaPlayer.stop();
        }
    }

    class MusicController implements MediaController.MediaPlayerControl{
        @Override
        public boolean canPause() {
            return true;
        }

        @Override
        public boolean canSeekBackward() {
            return true;
        }

        @Override
        public boolean canSeekForward() {
            return true;
        }

        @Override
        public int getAudioSessionId() {
            return mediaPlayer.getAudioSessionId();
        }

        @Override
        public int getBufferPercentage() {
            int percentage = (mediaPlayer.getCurrentPosition() * 100) / mediaPlayer.getDuration();

            return percentage;
        }

        @Override
        public int getCurrentPosition() {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public int getDuration() {
            return mediaPlayer.getDuration();
        }

        @Override
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }

        @Override
        public void pause() {
            if(mediaPlayer.isPlaying())
                mediaPlayer.pause();
        }

        @Override
        public void seekTo(int pos) {
            mediaPlayer.seekTo(pos);
        }

        @Override
        public void start() {
            mediaPlayer.start();
        }

    }

    public static interface CountryReporter{
        public Country getCountryData();
    }

}
