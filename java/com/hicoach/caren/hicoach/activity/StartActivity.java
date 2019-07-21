package com.hicoach.caren.hicoach.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.hicoach.caren.hicoach.R;
import com.hicoach.caren.hicoach.tools.CustomVideoView;

import java.lang.reflect.Field;

/**
 * Created by Carendule on 2018/5/15.
 */

public class StartActivity extends AppCompatActivity{
    private CustomVideoView videoview;
    private Button coach,boss,user;
    private ImageButton sound;
    private SharedPreferences haveopened;
    private SharedPreferences.Editor editor;
    private int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
        setContentView(R.layout.startlayout);
        haveopened = getSharedPreferences("isopened",0);
        if(haveopened.getBoolean("openornot",false)){
            if(haveopened.getInt("startid",0) == 0){
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,LoginActivity.class);
                intent.putExtra("startid",0);
                startActivity(intent);
                finish();
            }else if(haveopened.getInt("startid",0) == 1){
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,LoginActivity.class);
                intent.putExtra("startid",1);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,LoginActivity.class);
                intent.putExtra("startid",2);
                startActivity(intent);
                finish();
            }
        }else{
            editor = haveopened.edit();
            editor.putBoolean("openornot",true);
        }
        initView();
    }

    private void initView(){
        videoview = (CustomVideoView)findViewById(R.id.videoview);
        coach = (Button)findViewById(R.id.imcoach);
        boss = (Button)findViewById(R.id.imboss);
        user = (Button)findViewById(R.id.imuser);
        sound = (ImageButton)findViewById(R.id.changesound);
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video));
        videoview.start();
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(i%2 ==1){
                        setVolume(0f, videoview);
                        sound.setImageResource(R.drawable.nosound);
                    }else{
                        setVolume(1f, videoview);
                        sound.setImageResource(R.drawable.sound);
                    }
                    i++;
            }
        });

        coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startid = 0;
                editor.putInt("startid",startid);
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,LoginActivity.class);
                intent.putExtra("startid",startid);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartActivity.this).toBundle());
                finish();
            }
        });
        boss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startid = 1;
                editor.putInt("startid",startid);
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,LoginActivity.class);
                intent.putExtra("startid",startid);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartActivity.this).toBundle());
                finish();
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startid = 2;
                editor.putInt("startid",startid);
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,LoginActivity.class);
                intent.putExtra("startid",startid);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartActivity.this).toBundle());
                finish();
            }
        });
    }


    public void setVolume(float volume,Object object) {
        try {
            Class<?> forName = Class.forName("android.widget.VideoView");
            Field field = forName.getDeclaredField("mMediaPlayer");
            field.setAccessible(true);
            MediaPlayer mMediaPlayer = (MediaPlayer) field.get(object);
            mMediaPlayer.setVolume(volume, volume);
        } catch (Exception e) {
        }
    }
}
