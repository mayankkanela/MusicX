package com.mayank.musicx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class PlaySong extends AppCompatActivity {
    private Intent intent;
    private int pos;
    private int duration;
    private CardView cardView;
    private CardView cardView2;
    public  final boolean post=true;
    private Handler handler=new Handler();
    private MediaPlayer mediaPlayer;
    private MediaMetadataRetriever metadataRetriever;
    private SeekBar seekBar;
    private int  token=0;
    private View view;
    private ArrayList<Songs> songs= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        initView();
        initData();
        initListeners();
        mediaPlayer.start();
        PlaySong.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null)
                {
                    int pos =mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(pos);

                }
                handler.postDelayed(this,1000);
            }
        });
    }

    private void initListeners() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                PlaySong.this.finish();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer!=null&& b)
                {
                    mediaPlayer.seekTo(i*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(token==0)
                {
                    cardView.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);
                    token=1;
                }
                else
                {
                    cardView2.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                    token=0;
                }

            }
        });
    }

    private void initView() {
        seekBar =findViewById(R.id.Seekbar);
        view=findViewById(R.id.csLayout);
        cardView=findViewById(R.id.cdTitle);
        cardView2=findViewById(R.id.cdCard2);

    }

    private void initData() {
        intent=getIntent();
    songs=this.getIntent().getParcelableArrayListExtra("SONGS");
    pos=intent.getIntExtra("POSITION",0);
    mediaPlayer=MediaPlayer.create(this,songs.get(pos).getUri());
    metadataRetriever =new MediaMetadataRetriever();
    metadataRetriever.setDataSource(this,songs.get(pos).getUri());
    duration = Integer.parseInt(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    seekBar.setMax(duration/1000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            PlaySong.this.finish();

    }
}
