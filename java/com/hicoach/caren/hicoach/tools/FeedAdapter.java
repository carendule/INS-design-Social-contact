package com.hicoach.caren.hicoach.tools;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hicoach.caren.hicoach.R;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.sackcentury.shinebuttonlib.ShineButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.rockerhieu.emojicon.EmojiconTextView;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ANIMATED_ITEMS_COUNT = 2;

    private Context context;
    private Activity activity;
    private int lastAnimatedPosition = -1;
    private int itemsCount = 0;
    public FeedAdapter(Context context,Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.itemfeed, parent, false);
        return new CellFeedViewHolder(view);
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenSize(context)[0]);
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        runEnterAnimation(viewHolder.itemView, position);
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        holder.likebutton.init(activity);
        holder.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.likebutton.isChecked()){
                    ToastUtils.showShortToast(context,"以喜欢第"+String.valueOf(position+1)+"条消息");
                }else{
                    ToastUtils.showShortToast(context,"以取消喜欢第"+String.valueOf(position+1)+"条消息");
                }

            }
        });
        if (position % 2 == 0) {
            Glide.with(context).load("https://img4.duitang.com/uploads/item/201504/06/20150406H3041_2di8s.jpeg").into(holder.userimage);
            holder.username.setText("Carendule");
            holder.liketext.setText("105673 likes");
            holder.emojitext.setText("I \ue32d emojicon,Hicoach is really an aweson app,i will use it in a pretty long time");
            Glide.with(context).load("http://seopic.699pic.com/photo/00000/7465.jpg_wh1200.jpg").into(holder.ivFeedCenter);
        } else {
            Glide.with(context).load("https://img3.duitang.com/uploads/item/201508/02/20150802102918_UZYdH.jpeg").into(holder.userimage);
            holder.username.setText("Melbroune");
            holder.liketext.setText("9985 likes");
            Glide.with(context).load("http://seopic.699pic.com/photo/50033/1604.jpg_wh1200.jpg").into(holder.ivFeedCenter);
            holder.emojitext.setText("I \ue32d emojicon,Hicoach is really an aweson app,i will use it in a pretty long time");
        }
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.userimage)
        RoundImageView userimage;
        @InjectView(R.id.username)
        TextView username;
        @InjectView(R.id.ivFeedCenter)
        ImageView ivFeedCenter;
        @InjectView(R.id.likebutton)
        ShineButton likebutton;
        @InjectView(R.id.liketext)
        TextView liketext;
        @InjectView(R.id.emojitext)
        EmojiconTextView emojitext;

        public CellFeedViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public void updateItems() {
        itemsCount = 10;
        notifyDataSetChanged();
    }
}
