package com.example.musicplayer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.musicplayer.R;

public class WebActivity extends AppCompatActivity {
    private WebView myWebClient;
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wenview);
        findViews();
        loadWebPages();

        myWebClient.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
        });
    }

    private  void findViews(){
        myWebClient=(WebView)findViewById(R.id.myWebClient);
    }

    private void loadWebPages(){
//        myWebClient.loadDataWithBaseURL(null,"<a href='https://m.baidu.com'>https://m.baidu.com</a>",mimeType,encoding,null);
        myWebClient.getSettings().setJavaScriptEnabled(true);
        myWebClient.loadUrl("http://www.yinyuetai.com/");
        //支持javascript
        myWebClient.getSettings().setJavaScriptEnabled(true);
// 设置可以支持缩放
        myWebClient.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        myWebClient.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        myWebClient.getSettings().setUseWideViewPort(true);
//自适应屏幕
        myWebClient.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        myWebClient.getSettings().setLoadWithOverviewMode(true);
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode== KeyEvent.KEYCODE_BACK&&myWebClient.canGoBack()){
            myWebClient.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

}