package com.hicoach.caren.hicoach.tools;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

/**
 * Created by Carendule on 2018/5/15.
 */

public class CustomVideoView extends VideoView {

    public CustomVideoView(Context context){
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0,widthMeasureSpec);
        int height = getDefaultSize(0,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        super.setOnPreparedListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
