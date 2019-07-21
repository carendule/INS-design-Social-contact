package com.hicoach.caren.hicoach.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hicoach.caren.hicoach.R;

/**
 * Created by Carendule on 2018/5/19.
 */

public class UserDiscover extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userdicoverlayout,container,false);
        return view;
    }
}
