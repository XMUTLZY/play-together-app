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
import com.example.xmut_news.pojo.UserRelease;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{
    private final Context context;
    private List<UserRelease> datas;
    private int id = 0;
    public MyRecyclerViewAdapter(Context context, List<UserRelease> data) {
        this.context = context;
        this.datas = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.item_recyclerview,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //绑定数据
        String title = datas.get(i).getTitle();
        String name = datas.get(i).getName();
        String phone = datas.get(i).getPhone();
        String imagePath = datas.get(i).getImage();
        myViewHolder.release_title.setText(title);
        myViewHolder.release_name.setText("发布人:"+name);
        myViewHolder.release_phone.setText("联系方式:"+phone);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        myViewHolder.release_image.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView release_image;
        private TextView release_title,release_name,release_phone;
        private Button release_join,release_chat;
        private LinearLayout item;
        private UserRelease userRelease;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            release_image = itemView.findViewById(R.id.release_image);
            release_title = itemView.findViewById(R.id.release_title);
            release_name = itemView.findViewById(R.id.release_name);
            release_phone = itemView.findViewById(R.id.release_phone);
            release_join = itemView.findViewById(R.id.release_join);
            release_chat = itemView.findViewById(R.id.release_chat);
            item = itemView.findViewById(R.id.item);
            //设置item的监听器，用于监听查看详情事件
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userRelease = new UserRelease();
                    userRelease = datas.get(getLayoutPosition());
                    Intent intent = new Intent(context, ReleaseShowActivity.class);
                    intent.putExtra("userRelease",userRelease);
                    context.startActivity(intent);
                }
            });
            //设置申请加入按钮的监听器
            release_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "建议你先查看详情哦！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
