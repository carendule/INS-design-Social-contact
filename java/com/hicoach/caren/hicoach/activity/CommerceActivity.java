package com.hicoach.caren.hicoach.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.hicoach.caren.hicoach.R;
import com.hicoach.caren.hicoach.fragment.CommerceHome;
import com.hicoach.caren.hicoach.fragment.CommerceMall;
import com.hicoach.caren.hicoach.fragment.CommerceManage;
import com.hicoach.caren.hicoach.fragment.CommerceMine;
import com.hicoach.caren.hicoach.tools.FragAdapter;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

/**
 * Created by Carendule on 2018/5/18.
 */

public class CommerceActivity extends FragmentActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commercelayout);
        BottomNavigation bottomNavigation = (BottomNavigation)findViewById(R.id.CommerceBottomNavigation);
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new CommerceHome());
        fragments.add(new CommerceManage());
        fragments.add(new CommerceMall());
        fragments.add(new CommerceMine());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(),fragments);

        viewPager = (ViewPager)findViewById(R.id.commercepager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        bottomNavigation.setOnMenuChangedListener(new BottomNavigation.OnMenuChangedListener() {
            @Override
            public void onMenuChanged(final BottomNavigation bottomNavigation) {
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (bottomNavigation.getSelectedIndex() != position) {
                            bottomNavigation.setSelectedIndex(position, false);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });
        bottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int itemid, int position, boolean fromuser) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {

            }
        });
    }
}
