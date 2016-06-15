package com.ycl.sportsing.ui;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.sportsing.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView=(WebView)this.findViewById(R.id.webview_newbie);

        Intent intent=this.getIntent();
        String url=intent.getStringExtra("webUrl");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
                  @Override
                 public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                       handler.proceed();
                //            handler.cancel();
                //            handler.handleMessage(null);
                }
         });

        ImageView imageViewBack=(ImageView)this.findViewById(R.id.imageview_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });

        TextView title=(TextView)this.findViewById(R.id.textview_title);
        title.setText(intent.getStringExtra("title"));

    }
}
