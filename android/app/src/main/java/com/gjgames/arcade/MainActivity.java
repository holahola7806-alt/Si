package com.gjgames.arcade;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.JavascriptInterface;

public class MainActivity extends Activity {
 private WebView gameView;
 @Override public void onCreate(Bundle b){
  super.onCreate(b);
  getWindow().setStatusBarColor(Color.rgb(9,15,35));
  getWindow().setNavigationBarColor(Color.rgb(9,15,35));
  gameView=new WebView(this);
  gameView.setBackgroundColor(Color.rgb(9,15,35));
  gameView.getSettings().setJavaScriptEnabled(true);
  gameView.getSettings().setDomStorageEnabled(true);
  gameView.getSettings().setAllowFileAccess(true);
  gameView.getSettings().setAllowUniversalAccessFromFileURLs(false);
  gameView.getSettings().setAllowFileAccessFromFileURLs(false);
  gameView.getSettings().setSupportZoom(false);
  gameView.addJavascriptInterface(new NativeActions(),"GJNative");
  gameView.setWebChromeClient(new WebChromeClient());
  gameView.setWebViewClient(new WebViewClient(){
   @Override public boolean shouldOverrideUrlLoading(WebView view,WebResourceRequest req){return !"file".equals(req.getUrl().getScheme());}
  });
  setContentView(gameView);
  gameView.loadUrl("file:///android_asset/index.html");
 }
 private class NativeActions{
  @JavascriptInterface public void exit(){runOnUiThread(()->finish());}
 }
 @Override public void onBackPressed(){
  if(gameView!=null)gameView.evaluateJavascript("window.GJBack && window.GJBack()",null);
  else super.onBackPressed();
 }
 @Override public void onPause(){super.onPause();if(gameView!=null)gameView.onPause();}
 @Override public void onResume(){super.onResume();if(gameView!=null)gameView.onResume();}
 @Override public void onDestroy(){if(gameView!=null){gameView.destroy();gameView=null;}super.onDestroy();}
}
