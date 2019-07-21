package com.hicoach.caren.hicoach.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hicoach.caren.hicoach.R;
import com.hicoach.caren.hicoach.activity.LoginActivity;

public class CommerceMine extends Fragment {
    private int id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commerceminelayout,container,false);
        SharedPreferences startid = getActivity().getSharedPreferences("isopened",0);
        id = startid.getInt("startid",0);
        Button button = (Button)view.findViewById(R.id.commercebackbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("startid",id);
                intent.putExtra("activityid",0);
                intent.setClass(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.transition.in_from_right,R.transition.out_to_left);
            }
        });
        return view;
    }
}
