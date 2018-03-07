package com.example.su.musicsystem;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Musicactivity extends AppCompatActivity  {

    MediaPlayer mp;
    String path="";
    SeekBar seekbar;
    TextView tv_play,tv_prv,tv_post;
    TextView title;
    Thread updateseekbar;
    String title1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicactivity);
        seekbar=(SeekBar)findViewById(R.id.seekbar);
        tv_play=(TextView)findViewById(R.id.tv_play);
        tv_prv=(TextView)findViewById(R.id.tv_prv);
        tv_post=(TextView)findViewById(R.id.tv_post);
        title=(TextView)findViewById(R.id.title);
        stopService(new Intent(Musicactivity.this,BackgroundSoundService.class).putExtra("path",path));
         path=getIntent().getExtras().getString("path");
        title1=getIntent().getExtras().getString("title");
        title.setText(title1);
         final int duration= Integer.parseInt(getIntent().getExtras().getString("duration"));

        tv_prv.setText("<<");
        tv_post.setText(">>");
        updateseekbar= new Thread()
        {
            @Override
            public void run() {
             //   super.run();
                int totalduration=mp.getDuration();
                int currentpostion=0;
                while(currentpostion<totalduration)
                {
                    try {
                        sleep(500);
                        currentpostion=mp.getCurrentPosition();
                        seekbar.setProgress(currentpostion);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        String title= getIntent().getExtras().getString("title");
        mp=MediaPlayer.create(getApplicationContext(), Uri.parse(path));
        mp.start();
        seekbar.setMax(mp.getDuration());
        updateseekbar.start();
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
              mp.seekTo(seekBar.getProgress());
            }
        });
        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mp.isPlaying())
                {
                    mp.pause();
                    tv_play.setText(">");


                }
                else
                {
                    mp.start();
                    tv_play.setText("||");

                }
            }
        });

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(mp.getCurrentPosition()+5000);
            }
        });
        tv_prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(mp.getCurrentPosition()-5000);
            }
        });
    }
    public void onPause() {
        super.onPause();
        this.finish();
      //  path="";
        Intent svc=new Intent(this, BackgroundSoundService.class);
        svc.setAction(Constant.ACTION.STARTFOREGROUND_ACTION);
        svc.putExtra("path",path);
        svc.putExtra("title", String.valueOf(title1));
        startService(svc);
        mp.pause();

    }
    public void onStop() {
        super.onStop();
        path="";
        finish();
        mp.pause();
    }



}
