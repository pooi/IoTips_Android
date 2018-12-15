package xyz.iotips.iotips_android;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyHandler handler = new MyHandler();
    private final int MSG_CHANGE_STATUSBAR_COLOR_WHITE = 500;
    private final int MSG_CHANGE_STATUSBAR_COLOR_ORANGE = 501;
    private final int MSG_CHANGE_STATUSBAR_COLOR_DEEP_ORANGE = 502;

    private ValueCallback<Uri> filePathCallbackNormal;
    private ValueCallback<Uri[]> filePathCallbackLollipop;
    private Uri mCapturedImageURI;
    private final int INTENT_FILE_CHOOSE = 1000;

    private WebView mWebView;

    // Auto Login
    private SharedPreferences setting;
    private SharedPreferences.Editor editor;
//    private boolean isAttemptLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting = getSharedPreferences("setting", 0);
        editor = setting.edit();

        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();

        // 웹뷰에서 자바스크립트실행가능
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setGeolocationEnabled(true);

        // WebViewClient 지정
        mWebView.setWebViewClient(new WebViewClientClass());

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                if(newProgress >= 100) progressDialog.dismiss();
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, true);
            }

//            public boolean onShowFileChooser(
//                    WebView webView, ValueCallback<Uri[]> filePathCallback,
//                    WebChromeClient.FileChooserParams fileChooserParams) {
//                if (filePathCallbackLollipop != null) {
////                    filePathCallbackLollipop.onReceiveValue(null);
//                    filePathCallbackLollipop = null;
//                }
//                filePathCallbackLollipop = filePathCallback;
//
//
//                // Create AndroidExampleFolder at sdcard
//                File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AndroidExampleFolder");
//                if (!imageStorageDir.exists()) {
//                    // Create AndroidExampleFolder at sdcard
//                    imageStorageDir.mkdirs();
//                }
//
//                // Create camera captured image file path and name
//                File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
//                mCapturedImageURI = Uri.fromFile(file);
//
//                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
//
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//
//                // Create file chooser intent
//                Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
//                // Set camera intent to file chooser
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});
//
//                // On select image call onActivityResult method of activity
//                startActivityForResult(chooserIntent, INTENT_FILE_CHOOSE);
//                return true;
//
//            }

        });

        mWebView.addJavascriptInterface(new JavaScriptInterface(handler), "IoTips");

        mWebView.loadUrl("https://iotips.xyz");
//        mWebView.loadUrl("http://ldayou.asuscomm.com:54000");

//        Dexter.withActivity(MainActivity.this)
//                .withPermissions(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ).withListener(new MultiplePermissionsListener() {
//            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
//                if(report.areAllPermissionsGranted()){
//
//                }else{
//                }
//            }
//            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//
//
//            }
//        }).check();

    }

//    private void login(){
//
//        isAttemptLogin = true;
//
//        final String loginId = setting.getString("id", null);
//        final String loginPw = setting.getString("pw", null);
//
//        if(loginId != null && loginPw != null) {
//
//            mWebView.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//                        mWebView.evaluateJavascript("javascript:loginSejoingWithWebkit('" + loginId + "', '" + loginPw + "');", new ValueCallback<String>() {
//                            @Override
//                            public void onReceiveValue(String s) {
//                                Log.e("Login", s);
//                            }
//                        });
//                }
//            });
//
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == INTENT_FILE_CHOOSE) {
//            Uri[] result = new Uri[0];
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                if(resultCode == RESULT_OK){
//                    result = (data == null) ? new Uri[]{mCapturedImageURI} : WebChromeClient.FileChooserParams.parseResult(resultCode, data);
//                }
//                filePathCallbackLollipop.onReceiveValue(result);
//            }
//        }
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            Uri uri = Uri.parse(url);
            String path = uri.getPath();
            if(path.equals("/")){
                handler.sendMessage(handler.obtainMessage(MSG_CHANGE_STATUSBAR_COLOR_ORANGE));
            }else{
                handler.sendMessage(handler.obtainMessage(MSG_CHANGE_STATUSBAR_COLOR_DEEP_ORANGE));
            }

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            if(!isAttemptLogin){
//                login();
//            }
        }
    }

    private class JavaScriptInterface {

        MyHandler handler;

        public JavaScriptInterface(MyHandler handler){
            this.handler = handler;
        }

        @JavascriptInterface
        public void checkWebkit() {


        }

        @JavascriptInterface
        public void changeStatusBarBGColor(final String color) {
            switch (color){
                case "white":
                    handler.sendMessage(handler.obtainMessage(MSG_CHANGE_STATUSBAR_COLOR_WHITE));
                    break;
                case "orange":
                    handler.sendMessage(handler.obtainMessage(MSG_CHANGE_STATUSBAR_COLOR_ORANGE));
                    break;
                case "deep-orange":
                    handler.sendMessage(handler.obtainMessage(MSG_CHANGE_STATUSBAR_COLOR_DEEP_ORANGE));
                    break;
            }
        }

//        @JavascriptInterface
//        public void savePassword(final String ID, final String PW) {
//            editor.putString("id", ID);
//            editor.putString("pw", PW);
//            editor.commit();
//        }
//
//        @JavascriptInterface
//        public void logout() {
//            editor.remove("id");
//            editor.remove("pw");
//            editor.commit();
//        }

    }

    private class MyHandler extends Handler implements Serializable {

        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_CHANGE_STATUSBAR_COLOR_WHITE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.WHITE);
                    }
                    break;
                case MSG_CHANGE_STATUSBAR_COLOR_ORANGE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        float red = 71.0f/255.0f;
                        float green = 70.0f/255.0f;
                        float blue = 69.0f/255.0f;
                        window.setStatusBarColor(getColor(R.color.statusbar_orange));
                    }
                    break;
                case MSG_CHANGE_STATUSBAR_COLOR_DEEP_ORANGE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getColor(R.color.statusbar_deep_orange));
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
