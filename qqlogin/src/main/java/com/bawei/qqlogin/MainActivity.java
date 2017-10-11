package com.bawei.qqlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private UMShareAPI mShareAPI = null;
    private SHARE_MEDIA platform = null;
    private ImageView mIvLog;
    private ImageView mIvShare;
    /**
     * 姓名
     */
    private TextView mTv;
    private ImageView mIvqqtouxiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShareAPI = UMShareAPI.get(this);
        initView();
    }

    private void initView() {
        mIvLog = (ImageView) findViewById(R.id.ivLog);
        mIvLog.setOnClickListener(this);
        mIvShare = (ImageView) findViewById(R.id.ivShare);
        mTv = (TextView) findViewById(R.id.tv);
        mIvqqtouxiang = (ImageView) findViewById(R.id.ivqqtouxiang);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLog:
                platform = SHARE_MEDIA.QQ;
                mShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                mShareAPI.deleteOauth(MainActivity.this, platform, umAuthListener);
                break;
        }
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
                    if (string.equals("screen_name")) {
                        //获取登录的名字
                        screenname = data.get("screen_name");
                        mTv.setText(screenname);
                    }
                    if (string.equals("profile_image_url")) {
                        //获取登录的图片
                        iconurl = data.get("profile_image_url");
                        ImageLoaderUtils.setImageView(iconurl, MainActivity.this, mIvqqtouxiang);
                    }
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

    //记得要重写这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }


}
