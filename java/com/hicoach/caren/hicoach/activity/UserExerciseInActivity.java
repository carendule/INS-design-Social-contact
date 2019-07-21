package com.hicoach.caren.hicoach.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.hicoach.caren.hicoach.R;
import com.hicoach.caren.hicoach.tools.ExerciseInListAdapter;
import com.hicoach.caren.hicoach.tools.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class UserExerciseInActivity extends Activity {

    private ListView listView;
    private ExerciseInListAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userexerciseinlayout);
        Banner banner =(Banner)findViewById(R.id.userbanner);
        listView = (ListView)findViewById(R.id.exerciseinlist);
        listAdapter = new ExerciseInListAdapter(this);
        listView.setAdapter(listAdapter);
        setBanner(banner);

    }

    @SuppressLint("ResourceType")
    private void setBanner(Banner banner){
        TypedArray typedArray = getResources().obtainTypedArray(R.array.image_array);
        List<Integer> imgs = new ArrayList<>();
        imgs.add(typedArray.getResourceId(0,0));
        imgs.add(typedArray.getResourceId(1,0));
        imgs.add(typedArray.getResourceId(2,0));
        imgs.add(typedArray.getResourceId(3,0));
        imgs.add(typedArray.getResourceId(4,0));
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imgs).setBannerStyle(BannerConfig.CIRCLE_INDICATOR).setBannerAnimation(Transformer.FlipHorizontal)
                .isAutoPlay(true).setDelayTime(2000).setIndicatorGravity(BannerConfig.CENTER);
        banner.start();

    }

}
