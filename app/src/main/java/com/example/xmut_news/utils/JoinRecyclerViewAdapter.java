package com.example.xmut_news.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xmut_news.R;
import com.example.xmut_news.ReleaseShowActivity;
import com.example.xmut_news.pojo.UserJoin;
import com.example.xmut_news.pojo.UserRelease;
import java.util.List;

public class JoinRecyclerViewAdapter extends RecyclerView.Adapter<JoinRecyclerViewAdapter.MyViewHolder>{
    private final Context context;
    private List<UserJoin> datas;
    private int id = 0;
    public JoinRecyclerViewAdapter(Context context, List<UserJoin> data) {
        this.context = context;
        this.datas = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.join_item_recyclerview,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //绑定数据
        String release_title = datas.get(i).getRelease_title();
        String release_name = datas.get(i).getRelease_name();
        String release_time = datas.get(i).getRelease_time();
        String release_address = datas.get(i).getRelease_address();
        String release_phone = datas.get(i).getRelease_phone();
        String state = datas.get(i).getState();
        myViewHolder.release_title.setText(release_title);
        myViewHolder.release_name.setText("发布人:"+release_name);
        myViewHolder.release_phone.setText("联系方式:"+release_phone);
        myViewHolder.release_time.setText("约见时间:"+release_time);
        myViewHolder.release_address.setText("约见地址:"+release_address);
        myViewHolder.state.setText("状态:"+state);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView release_title,release_name,release_phone,release_address,release_time,state;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            release_title = itemView.findViewById(R.id.release_title);
            release_name = itemView.findViewById(R.id.release_name);
            release_phone = itemView.findViewById(R.id.release_phone);
            release_address = itemView.findViewById(R.id.release_address);
            release_time = itemView.findViewById(R.id.release_time);
            state = itemView.findViewById(R.id.state);
        }
    }
}
