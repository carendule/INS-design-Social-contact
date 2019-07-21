package com.hicoach.caren.hicoach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.hicoach.caren.hicoach.R;

public class AddDiaryActivity extends Activity {

    private BootstrapButton ok,cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddiary);
        ok = (BootstrapButton)findViewById(R.id.diaryconfirm);
        cancel = (BootstrapButton)findViewById(R.id.diarycancel);
        initview();
    }

    private void initview(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
