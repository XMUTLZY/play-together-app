package com.example.xmut_news;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xmut_news.pojo.UserRelease;
import com.example.xmut_news.utils.DateTimePickDialogUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PhotoActivity extends AppCompatActivity {
    private String url = "http://i29kvi.natappfree.cc/android_PlayAround_ssm/addUserRelease";//信息发布接口(本地)
    private RequestParams params;//存放请求数据
    public static final int CHOOSE_PHOTO = 2;
    private String imagePath;//图片路径
    private String initTime = "2019年4月7日 14:44";//初始化时间
    private String phone,name,title,detail,address,time;//发布信息的具体内容
    private EditText timeText,titleText,detailText,addressText;
    private ImageView picture;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        getSupportActionBar().hide();
        //实例化组件
        picture = findViewById(R.id.picture);
        timeText = findViewById(R.id.release_time);
        titleText = findViewById(R.id.release_title);
        detailText = findViewById(R.id.release_detail);
        addressText = findViewById(R.id.release_address);
        Button chooseFromAlbum = findViewById(R.id.choose_from_album);
        //给时间文本框赋默认值
        timeText.setText(initTime);
        //给时间文本框设置监听器
        timeText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        PhotoActivity.this, initTime);
                dateTimePicKDialog.dateTimePicKDialog(timeText);
            }
        });
        //设置图片选择按钮监听器
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //权限获取
                if (ContextCompat.checkSelfPermission(PhotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PhotoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });
        //发布按钮监听器
        Button user_release = findViewById(R.id.btn_release);
        user_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取发布信息并存放在param中
                UserRelease userRelease = getUserRelease();
                params = new RequestParams();
                params.put("phone",userRelease.getPhone());
                params.put("name",userRelease.getName());
                params.put("title",userRelease.getTitle());
                params.put("detail",userRelease.getDetail());
                params.put("image",userRelease.getImage());
                params.put("address",userRelease.getAddress());
                params.put("time",userRelease.getTime());
                addUserRelease(params);
            }
        });
    }

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }else {
                        //4.4以下系统使用这个放出处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        diaplayImage(imagePath);//根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        imagePath = getImagePath(uri,null);
        diaplayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void diaplayImage(String imagePath){
        if (imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
            //将图片路径存放在SharedPreferences中
            SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
            String phone = sp.getString("phone","");
            if(TextUtils.isEmpty(phone)){
                //本地没有保存过用户信息
            }else{
                //创建editor，存放图片路径
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("image",imagePath);
            }
        }else {
            Toast.makeText(this,"failed to get iamge",Toast.LENGTH_SHORT).show();
        }
    }
    /*
    * 获取发布信息的内容
    * */
    public UserRelease getUserRelease(){
        UserRelease userRelease = new UserRelease();
        //从SharedPreferences中获取用户信息
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        phone = sp.getString("phone","");
        name = sp.getString("name","");
        //从文本框中获取发布信息
        title = titleText.getText().toString().trim();
        detail = detailText.getText().toString().trim();
        address = addressText.getText().toString().trim();
        time = timeText.getText().toString().trim();
        userRelease.setPhone(phone);
        userRelease.setName(name);
        userRelease.setTitle(title);
        userRelease.setDetail(detail);
        userRelease.setImage(imagePath);
        userRelease.setAddress(address);
        userRelease.setTime(time);
        return userRelease;
    }
    /*
    * 发送发布信息到数据库中
    * */
    public void addUserRelease(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(this,url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                if(s.equals("true")){
                    //输入信息正确
                    Intent intent = new Intent(PhotoActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Toast.makeText(PhotoActivity.this,"信息发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
