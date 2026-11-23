package com.kaxtz.lab;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.ValueCallback;

public class MainActivity extends Activity {
  private static final int FILE_REQUEST=912;
  private WebView browser;
  private ValueCallback<Uri[]> fileChooser;
  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    getWindow().setStatusBarColor(Color.rgb(13,23,37));
    getWindow().setNavigationBarColor(Color.rgb(13,23,37));
    browser=new WebView(this);
    browser.setBackgroundColor(Color.rgb(13,23,37));
    browser.getSettings().setJavaScriptEnabled(true);
    browser.getSettings().setDomStorageEnabled(true);
    browser.getSettings().setAllowFileAccess(true);
    browser.getSettings().setAllowFileAccessFromFileURLs(false);
    browser.getSettings().setAllowUniversalAccessFromFileURLs(false);
    browser.getSettings().setSupportZoom(false);
    browser.setWebViewClient(new WebViewClient(){
      @Override public boolean shouldOverrideUrlLoading(WebView view,WebResourceRequest req){
        Uri url=req.getUrl();
        if("file".equals(url.getScheme()) || "about".equals(url.getScheme())) return false;
        if("http".equals(url.getScheme()) || "https".equals(url.getScheme())) {
          try{startActivity(new Intent(Intent.ACTION_VIEW,url));}catch(Exception ignore){}
          return true;
        }
        return true;
      }
    });
    browser.setWebChromeClient(new WebChromeClient(){
      @Override public boolean onShowFileChooser(WebView v,ValueCallback<Uri[]> callback,FileChooserParams params){
        if(fileChooser!=null)fileChooser.onReceiveValue(null);
        fileChooser=callback;
        try{startActivityForResult(params.createIntent(),FILE_REQUEST);return true;}
        catch(Exception e){fileChooser=null;callback.onReceiveValue(null);return false;}
      }
    });
    setContentView(browser);
    browser.loadUrl("file:///android_asset/index.html");
  }
  @Override protected void onActivityResult(int req,int result,Intent data) {
    super.onActivityResult(req,result,data);
    if(req==FILE_REQUEST&&fileChooser!=null){
      fileChooser.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result,data));
      fileChooser=null;
    }
  }
  @Override public void onBackPressed(){
    if(browser!=null&&browser.canGoBack())browser.goBack();
    else super.onBackPressed();
  }
  @Override protected void onDestroy(){if(browser!=null){browser.destroy();browser=null;}super.onDestroy();}
}
