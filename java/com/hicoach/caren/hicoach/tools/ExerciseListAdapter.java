package com.hicoach.caren.hicoach.tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hicoach.caren.hicoach.Array.ExerciseList;
import com.hicoach.caren.hicoach.R;

public class ExerciseListAdapter extends BaseAdapter {
    private Context context;   //运行上下文
    private LayoutInflater listContainer;

    public final class ListItemView {
        public ImageView image;
        public TextView text;
    }
    public ExerciseListAdapter(Context context) {
        this.context = context;
        listContainer = LayoutInflater.from(context);    //创建视图容器并设置上下文
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemView listItemView = null;
        if(convertView == null){
            listItemView = new ListItemView();
            convertView = listContainer.inflate(R.layout.listitemview,null);
            listItemView.image = (ImageView)convertView.findViewById(R.id.listitemimage);
            listItemView.text = (TextView)convertView.findViewById(R.id.listitemtext);
            convertView.setTag(listItemView);
        }else{
            listItemView = (ListItemView)convertView.getTag();
        }
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.image_array);
        listItemView.text.setText(ExerciseList.histroyInfos.get(position));
        listItemView.image.setImageResource(typedArray.getResourceId(position,0));
        return convertView;
    }
}
