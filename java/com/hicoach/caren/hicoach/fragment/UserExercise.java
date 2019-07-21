package com.hicoach.caren.hicoach.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hicoach.caren.hicoach.Array.ExerciseList;
import com.hicoach.caren.hicoach.R;
import com.hicoach.caren.hicoach.activity.UserExerciseInActivity;
import com.hicoach.caren.hicoach.tools.ExerciseListAdapter;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.lljjcoder.style.citypickerview.CityPickerView;

/**
 * Created by Carendule on 2018/5/19.
 */

public class UserExercise extends Fragment {

    private TextView tooltext;
    private Toolbar toolbar;
    private String province0,city0,district0;
    private String provinceid,cityid,districtid;
    private RelativeLayout pleaseselete,sorry;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ListView listView;
    private ExerciseListAdapter exerciseListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userexerciselayout,container,false);
        tooltext = (TextView) view.findViewById(R.id.userexercisetooltext);
        toolbar = (Toolbar)view.findViewById(R.id.userexercisetoolbar);
        pleaseselete = (RelativeLayout)view.findViewById(R.id.pleaseselete);
        sorry = (RelativeLayout)view.findViewById(R.id.sorry);
        listView = (ListView)view.findViewById(R.id.exerciselist);
        exerciseListAdapter = new ExerciseListAdapter(getContext());
        sp = getActivity().getSharedPreferences("location",0);
        editor = sp.edit();
        initView();
        listclickset();
        return view;
    }

    private void initView(){
        if (sp.getString("locationdata","请选择地区").equals("请选择地区"))
        {
            pleaseselete.setVisibility(View.VISIBLE);
            sorry.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }else{
            tooltext.setText(sp.getString("locationdata","请选择地区"));
            textchange();
        }
        CityListLoader.getInstance().loadProData(getActivity());
        final CityPickerView mPicker=new CityPickerView();
        mPicker.init(getActivity());
        tooltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityConfig cityConfig = new CityConfig.Builder().confirTextColor("#000000")
                        .showBackground(true).build();
                mPicker.setConfig(cityConfig);
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        if (province != null) {
                            province0 = new String(province.getName());
                        }
                        if (city != null) {
                            city0 = new String(city.getName());
                        }
                        if (district != null) {
                            district0 = new String(district.getName());
                        }
                        tooltext.setText(province0+city0+district0);
                        provinceid=province.getId();
                        cityid=city.getId();
                        districtid=district.getId();
                        editor.putString("locationdata",province0+city0+district0);
                        editor.putString("provinceid", provinceid);
                        editor.putString("cityid",cityid);
                        editor.putString("districtid",districtid);
                        editor.commit();
                    }
                    @Override
                    public void onCancel() {
                        ToastUtils.showLongToast(getActivity(), "已取消");
                    }
                });
                mPicker.showCityPicker( );
            }
        });

        tooltext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                textchange();
            }
        });
    }

    private void textchange(){
        if(tooltext.getText().toString().equals("四川省成都市武侯区")){
            listView.setVisibility(View.VISIBLE);
            pleaseselete.setVisibility(View.GONE);
            sorry.setVisibility(View.GONE);
        }else if(tooltext.getText().toString().equals("山西省太原市市辖区")){
            ExerciseList.histroyInfos.add("百胜健身(漪汾街店)");
            ExerciseList.histroyInfos.add("凯撒国际健身(胜利店)");
            ExerciseList.histroyInfos.add("光圈健身(太原清创未来店)");
            ExerciseList.histroyInfos.add("动岚健身(云水店)");
            ExerciseList.histroyInfos.add("英派斯健身会所(太原店)");
            listView.setAdapter(exerciseListAdapter);
            listView.setVisibility(View.VISIBLE);
            pleaseselete.setVisibility(View.GONE);
            sorry.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.GONE);
            pleaseselete.setVisibility(View.GONE);
            sorry.setVisibility(View.VISIBLE);
        }
    }

    private void listclickset(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getContext(), UserExerciseInActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                getActivity().overridePendingTransition(R.transition.in_from_right,R.transition.out_to_left);
            }
        });
    }

}
