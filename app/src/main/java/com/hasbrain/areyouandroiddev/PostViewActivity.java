package com.hasbrain.areyouandroiddev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class PostViewActivity extends AppCompatActivity {

    WebView wbvPostView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        wbvPostView = (WebView) findViewById(R.id.wbvPostView);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        wbvPostView.getSettings().setJavaScriptEnabled(true);
        wbvPostView.loadUrl(url);
    }
}
