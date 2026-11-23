package com.kaxtz.cartulinastudio;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Build;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.OutputStream;
import java.io.IOException;

public class MainActivity extends Activity {
    private static final int PICK_FILE = 901;
    private WebView webView;
    private ValueCallback<Uri[]> fileCallback;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(23,29,39));
        getWindow().setNavigationBarColor(Color.rgb(23,29,39));
        webView = new WebView(this);
        setContentView(webView);
        webView.setBackgroundColor(Color.rgb(23,29,39));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(false);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.addJavascriptInterface(new AndroidBridge(), "CartulinaAndroid");
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return !"file".equals(request.getUrl().getScheme());
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> callback, FileChooserParams params) {
                if (fileCallback != null) fileCallback.onReceiveValue(null);
                fileCallback = callback;
                try {
                    startActivityForResult(params.createIntent(), PICK_FILE);
                    return true;
                } catch (Exception ex) {
                    fileCallback = null;
                    callback.onReceiveValue(null);
                    return false;
                }
            }
        });
        webView.loadUrl("file:///android_asset/index.html");
    }

    @Override protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request,result,data);
        if (request == PICK_FILE) {
            if(fileCallback!=null) {
                fileCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result,data));
                fileCallback=null;
            }
        }
    }

    public final class AndroidBridge {
        @JavascriptInterface public void saveBase64(String filename,String mime,String encoded) {
            new Thread(() -> {
                try {
                    String safe = (filename==null?"Cartulina.png":filename).replaceAll("[^a-zA-Z0-9._-]","_");
                    byte[] bytes = Base64.decode(encoded, Base64.DEFAULT);
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Downloads.DISPLAY_NAME,safe);
                    values.put(MediaStore.Downloads.MIME_TYPE,mime==null?"application/octet-stream":mime);
                    values.put(MediaStore.Downloads.RELATIVE_PATH,Environment.DIRECTORY_DOWNLOADS+"/CartulinaStudio");
                    values.put(MediaStore.Downloads.IS_PENDING,1);
                    Uri target=getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI,values);
                    if(target==null)throw new IOException("No pude crear el archivo");
                    try(OutputStream out=getContentResolver().openOutputStream(target)) {
                        if(out==null)throw new IOException("No hay acceso al archivo");
                        out.write(bytes);
                    }
                    values.clear();
                    values.put(MediaStore.Downloads.IS_PENDING,0);
                    getContentResolver().update(target,values,null,null);
                    runOnUiThread(()->Toast.makeText(MainActivity.this,"Guardado en Descargas/CartulinaStudio",Toast.LENGTH_LONG).show());
                } catch(Exception ex) {
                    runOnUiThread(()->Toast.makeText(MainActivity.this,"Error al guardar: "+ex.getMessage(),Toast.LENGTH_LONG).show());
                }
            }).start();
        }
    }
    @Override public void onBackPressed() {
        if(webView!=null&&webView.canGoBack())webView.goBack();
        else super.onBackPressed();
    }
    @Override protected void onDestroy(){if(webView!=null){webView.destroy();webView=null;}super.onDestroy();}
}
