package wsl.fjsoft.com.nicevideoplayermoudle.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import wsl.fjsoft.com.nicevideoplayermoudle.R;


/**
 * @author kcd
 * @package sys.fjsoft.com.parkingapplibs.Activity
 * @filename WebActivity
 * @date 2018/5/7 0007 9:48
 * @email 891705265@qq.com
 **/

public class WebActivity extends AppCompatActivity {

  static String TAG="WebActivity";

  //映射路由传过来的值
    String title;//路由传过来的标题
    String uri;//路由传过来的uri


    private WebView webView;
    private ProgressDialog dialog;
    private TextView img_web_close;
    private TextView tview_web_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

        super.onCreate(savedInstanceState);


         title=  getIntent().getExtras().getString("title");
         uri=   getIntent().getExtras().getString("url");
        setContentView(R.layout.activity_web);
        tview_web_title=findViewById(R.id.tview_web_title);
        img_web_close=findViewById(R.id.img_web_close);
        img_web_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //左边的小箭头（注意需要在setSupportActionBar(toolbar)之后才有效果）


/*       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置导航图标要在setSupportActionBar方法之后

            toolbar.setTitle("");//设置空*/
        if(title!=null&&title.length()>0){
            tview_web_title.setText(""+title);
        }


        // 副标题
    //    setSupportActionBar(toolbar);

        init();

    }

    private void init() {
        webView = (WebView) findViewById(R.id.webView);
        //WebView加载本地资源
//        webView.loadUrl("file:///android_asset/example.html");
        //WebView加载web资源
        webView.loadUrl(uri);
        //覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页可以在WebView中打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候是控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器打开

                if(url == null) return false;
                try {
                    if (url.startsWith("http:") || url.startsWith("https:"))
                    {
                        view.loadUrl(url);
                        return true;
                    }

                   /* else{
//                        http://www.baidu.com
                        ToastUtil.showLongToast("网页链接没包含http:或https:"+url,WebActivity.this);
                        return false;
                    }*/
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
                return false;
            }
            //WebViewClient帮助WebView去处理一些页面控制和请求通知
        });
        //启用支持Javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //页面加载
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    //页面加载完成，关闭ProgressDialog
                    if (!isFinishing()){
                        closeDialog();
                    }

                } else {
                    //网页正在加载，打开ProgressDialog
                    if (!isFinishing()){
                        openDialog(newProgress);
                    }

                }
            }

            private void openDialog(int newProgress) {
                if (dialog == null) {
                    dialog = new ProgressDialog(WebActivity.this);
                    dialog.setTitle("正在加载");
                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgress(newProgress);
                    dialog.setCancelable(true);
                    dialog.show();
                } else {
                    dialog.setProgress(newProgress);
                }
            }

            private void closeDialog() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
    }

  /*  //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();   //返回上一页面
                return true;
            } else {
                System.exit(0);     //退出程序
            }

        }
        return super.onKeyDown(keyCode, event);
    }*/
}