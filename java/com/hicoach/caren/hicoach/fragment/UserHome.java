package com.hicoach.caren.hicoach.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hicoach.caren.hicoach.R;
import com.hicoach.caren.hicoach.activity.AddDiaryActivity;
import com.hicoach.caren.hicoach.tools.FeedAdapter;
import com.hicoach.caren.hicoach.tools.Utils;

/**
 * Created by Carendule on 2018/5/19.
 */

public class UserHome extends Fragment {
    private RecyclerView rvFeed;
    private FeedAdapter feedAdapter;
    private ImageButton button;
    private Toolbar toolbar;
    private ImageView ivlogo;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userhomelayout,container,false);
        rvFeed = (RecyclerView)view.findViewById(R.id.userrecycle);
        button = (ImageButton)view.findViewById(R.id.userbtnCreate);
        toolbar = (Toolbar)view.findViewById(R.id.userhometoolbar);
        ivlogo = (ImageView)view.findViewById(R.id.userivlogo);
        initbutton();
        setupFeed();
        return view;
    }

    private void initbutton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), AddDiaryActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.transition.in_from_right,R.transition.out_to_left);
            }
        });
    }

    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvFeed.setLayoutManager(linearLayoutManager);
        feedAdapter = new FeedAdapter(getContext(),getActivity());
        rvFeed.setAdapter(feedAdapter);
        startIntroAnimation();
    }

    private static final int ANIM_DURATION_TOOLBAR = 300;

    private void startIntroAnimation() {
        button.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        int actionbarSize = Utils.dip2px(getActivity(),56);
        toolbar.setTranslationY(-actionbarSize);
        ivlogo.setTranslationY(-actionbarSize);
        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        ivlogo.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                }).start();
    }

    private void startContentAnimation() {
        button.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(200)
                .start();
        feedAdapter.updateItems();
    }

}
