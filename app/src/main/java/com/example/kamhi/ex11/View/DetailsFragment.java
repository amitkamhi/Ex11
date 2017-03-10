package com.example.kamhi.ex11.View;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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
    CountryReporter listener;
    Context context;
    private static MediaPlayer mediaPlayer;
    private MediaController mMediaController;
    private Handler mHandler = new Handler();

    TextView tvDetails;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.context = getActivity();
        try
        {
            this.listener = (CountryReporter)getActivity();
            Country country = listener.getCountryData();
            if(null != country){
                changeTo(country);

            }

        }catch (ClassCastException e){
            throw new ClassCastException("The class" + getActivity().getClass().getName() + " must impliments the inteface 'clickHandler' ");
        }

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mediaPlayer!=null)
            mediaPlayer.pause();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.tvDetails = (TextView) view.findViewById(R.id.details);
        final TextView button = (TextView)view.findViewById(R.id.details);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMediaController.show(10000);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        String bgColor = sp.getString("backColor", "#ff6783");
        ((View)this.tvDetails.getParent()).setBackgroundColor(Color.parseColor(bgColor));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_frag, container,false);
    }
    //changeTo
    public void changeTo(Country newCountry) {
        if(newCountry == null){
            mMediaController.hide();
            tvDetails.setText("");
            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.reset();
                mMediaController.hide();
            }
            return;
        }
        tvDetails.setText(newCountry.getDetails());
        if(mediaPlayer!=null)
        {
            mediaPlayer.reset();
        }
        int songResource = getResources().getIdentifier(newCountry.getAnthem(), "raw", getActivity().getPackageName());
        mediaPlayer = MediaPlayer.create(getActivity(), songResource);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaController = new MediaController(getActivity());
        mMediaController.setAnchorView(tvDetails);
        mMediaController.setMediaPlayer(new MusicCotrol());
        mediaPlayer.start();

/*
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mHandler.post(new Runnable() {
                    public void run() {
                        mMediaController.show(10000);
                        mediaPlayer.start();
                    }
                });
            }
        });*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
        }

    }

    public interface CountryReporter
    {
        public Country getCountryData();
    }
    private class MusicCotrol  implements MediaController.MediaPlayerControl
    {
        @Override
        public void start() {
            mediaPlayer.start();
        }

        @Override
        public void pause() {
            if(mediaPlayer.isPlaying())
                mediaPlayer.pause();
        }

        @Override
        public int getDuration() {
            return mediaPlayer.getDuration();
        }

        @Override
        public int getCurrentPosition() {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public void seekTo(int pos) {
            mediaPlayer.seekTo(pos);
        }

        @Override
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }

        @Override
        public int getBufferPercentage() {
            int percentage = (mediaPlayer.getCurrentPosition() * 100) / mediaPlayer.getDuration();
            return percentage;
        }

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

    }
}