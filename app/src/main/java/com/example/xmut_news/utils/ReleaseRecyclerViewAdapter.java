package com.example.xmut_news.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xmut_news.R;
import com.example.xmut_news.pojo.UserJoin;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;
import java.util.Properties;

public class ReleaseRecyclerViewAdapter extends RecyclerView.Adapter<ReleaseRecyclerViewAdapter.MyViewHolder>{
    private final Context context;
    private List<UserJoin> datas;
    private String url1;
    private String url;
    public ReleaseRecyclerViewAdapter(Context context, List<UserJoin> data) {
        this.context = context;
        this.datas = data;
        Properties properties = MyProperUtil.getProperties(context);
        url1 = properties.getProperty("serverUrl");
        url = url1 + "updateUserJoin";
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.release_item_recyclerview,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //绑定数据
        String release_title = datas.get(i).getRelease_title();
        String join_name = datas.get(i).getName();
        myViewHolder.release_title.setText(release_title);
        myViewHolder.join_name.setText(join_name+"申请加入该活动");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView join_image;
        private TextView release_title,join_name;
        private Button btn_agree,btn_reject;
        private RelativeLayout item;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            release_title = itemView.findViewById(R.id.release_title);
            join_name = itemView.findViewById(R.id.join_name);
            join_image = itemView.findViewById(R.id.join_image);
            btn_agree = itemView.findViewById(R.id.btn_agree);
            btn_reject = itemView.findViewById(R.id.btn_reject);
            item = itemView.findViewById(R.id.item);
            btn_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("确定要同意该申请吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //同意按钮监听
                                    RequestParams params = new RequestParams();
                                    params.put("state","通过");
                                    params.put("release_title",datas.get(getLayoutPosition()).getRelease_title());
                                    //提交审核信息
                                    addJoin(params);
                                    //设置按钮样式
                                    btn_agree.setEnabled(false);
                                    btn_agree.setBackgroundColor(Color.parseColor("#22000000"));
                                    btn_reject.setEnabled(false);
                                    btn_reject.setBackgroundColor(Color.parseColor("#22000000"));
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            });
            btn_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("确定拒绝该申请吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //同意按钮监听
                                            RequestParams params = new RequestParams();
                                            params.put("state","未通过");
                                            params.put("release_title",datas.get(getLayoutPosition()).getRelease_title());
                                            //提交审核信息
                                            addJoin(params);
                                            //设置按钮样式
                                            btn_agree.setEnabled(false);
                                            btn_agree.setBackgroundColor(Color.parseColor("#22000000"));
                                            btn_reject.setEnabled(false);
                                            btn_reject.setBackgroundColor(Color.parseColor("#22000000"));
                                        }
                                    })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            });

        }
    }
    private void addJoin(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(context,url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Toast.makeText(context, "系统出错了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
