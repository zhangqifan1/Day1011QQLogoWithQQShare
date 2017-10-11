package com.bawei.day1011qqlogowithqqshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bawei.day1011qqlogowithqqshare.Adapters.ListViewAdapter;
import com.bawei.day1011qqlogowithqqshare.CustomView.RoundImageView;
import com.bawei.day1011qqlogowithqqshare.JavaBean.Bean;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnLongClickListener {
    private String path = "http://huixinguiyu.cn/Assets/js/data.js";
    private ListView mLv;
    private Bean bean;
    private UMShareAPI mShareAPI = null;
    private SHARE_MEDIA platform = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShareAPI = UMShareAPI.get(this);
        initView();
        RequestDataFromNet();

    }

    private void RequestDataFromNet() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Request request = new Request.Builder().url(path).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                bean = new Gson().fromJson(string, Bean.class);
                runOnUiThread(new Runnable() {

                    private RoundImageView roundImage;
                    private View view;

                    @Override
                    public void run() {
                        ListViewAdapter adapter = new ListViewAdapter(bean, MainActivity.this);
                        view = View.inflate(MainActivity.this, R.layout.headerview, null);
                        mLv.addHeaderView(view);
                        mLv.setAdapter(adapter);
                        mLv.setOnItemClickListener(MainActivity.this);
                        roundImage = view.findViewById(R.id.roundImage);
                        roundImage.setOnLongClickListener(MainActivity.this);
                    }
                });
            }
        });

    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.lv);
    }
    private void showShare(int position) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(path);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("给儿子的一封信");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(bean.apk.get(position).iconUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(path);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(path);

        // 启动分享GUI
        oks.show(MainActivity.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showShare(position);
    }

    @Override
    public boolean onLongClick(View v) {
        platform = SHARE_MEDIA.QQ;
        mShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
        mShareAPI.deleteOauth(MainActivity.this, platform, umAuthListener);

        return false;
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA arg0, int arg1,
                               Map<String, String> data) {
            if (data != null) {
                Set<String> keySet = data.keySet();
                //得到头像
                String iconurl = new String();
                //得到昵称
                String screenname = new String();
                for (String string : keySet) {
//                    if (string.equals("screen_name")) {
//                        //获取登录的名字
//                        screenname = data.get("screen_name");
//                        mTv.setText(screenname);
//                    }
//                    if (string.equals("profile_image_url")) {
//                        //获取登录的图片
//                        iconurl = data.get("profile_image_url");
//                        ImageLoaderUtius.setImageView(iconurl, MainActivity.this, mIvqqtouxiang);
//                    }
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(getApplicationContext(), "授权取消", Toast.LENGTH_SHORT).show();
        }
    };

}
