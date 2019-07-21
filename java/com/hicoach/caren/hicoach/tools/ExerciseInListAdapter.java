package com.hicoach.caren.hicoach.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hicoach.caren.hicoach.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseInListAdapter extends BaseAdapter {
    private Context context;   //运行上下文
    private LayoutInflater listContainer;
    private List<String> comment;

    public final class ListItemView {
        public TextView text;
    }

    public ExerciseInListAdapter(Context context){
        this.context = context;
        listContainer = LayoutInflater.from(context);
        comment = new ArrayList<String>();
        comment.add("这家健身房真不错,下次一定还会再来");
        comment.add("我喜欢这家健身房，无论是装修还是其他的都如此有格调");
        comment.add("强烈推荐这家健身房，我每周都会来这里健身");
        comment.add("来吧，这个选择绝对没错");
        comment.add("我认为这家健身房还行，没有想象中那么棒");
    }


    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ExerciseInListAdapter.ListItemView listItemView = null;
        if(convertView == null){
            listItemView = new ExerciseInListAdapter.ListItemView();
            convertView = listContainer.inflate(R.layout.exerciseinlistitem,null);
            listItemView.text = (TextView)convertView.findViewById(R.id.initemtext);
            convertView.setTag(listItemView);
        }else{
            listItemView = (ExerciseInListAdapter.ListItemView)convertView.getTag();
        }
        listItemView.text.setText(comment.get(position));
        return convertView;
    }
}
