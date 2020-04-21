package wsl.fjsoft.com.nicevideoplayermoudle.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import wsl.fjsoft.com.nicevideoplayermoudle.AutoUtils;
import wsl.fjsoft.com.nicevideoplayermoudle.FullScreenVideoPlayerController;
import wsl.fjsoft.com.nicevideoplayermoudle.NiceVideoPlayer;
import wsl.fjsoft.com.nicevideoplayermoudle.NiceVideoPlayerManager;
import wsl.fjsoft.com.nicevideoplayermoudle.R;
import wsl.fjsoft.com.nicevideoplayermoudle.activity.WebActivity;
import wsl.fjsoft.com.nicevideoplayermoudle.bean.VideoModel;

/**
 * 主界面的Fragment
 *
 * @author kcd
 * @package com.tld.robotbasis.fragment
 * @filename Fragment1
 * @date 2018/7/27 0027 10:14
 * @email 891705265@qq.com
 **/

public class FragmentWeb extends Fragment {
    private static String ARG_PARAM = "FragmentWeb";
    private String mParam;
    private String title;
    private String url;
    private WebView webView;
    private ProgressDialog dialog;
    private Button imgBtn_back;
    private TextView text_title;

    public static FragmentWeb newInstance(String title, String url,int designWidth, int designHeight) {
        FragmentWeb frag = new FragmentWeb();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        bundle.putInt("designWidth", designWidth);
        bundle.putInt("designHeight", designHeight);
        frag.setArguments(bundle);   //设置参数
        return frag;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.robot_fragment_web, container, false);

        //轮播广告
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            url = bundle.getString("url");
            int designWidth_=bundle.getInt("designWidth");
            int designHeight_=bundle.getInt("designHeight");
            AutoUtils.setSize(getActivity(), false, designWidth_, designHeight_);
            AutoUtils.auto(root);

        }

        webView = root.findViewById(R.id.webView);
        text_title = root.findViewById(R.id.text_title);
        imgBtn_back = root.findViewById(R.id.imgBtn_back);
        imgBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        if (title != null && title.length() > 0) {
            text_title.setText("" + title);
        }

        init();

        return root;
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    private void init() {

        //WebView加载本地资源
//        webView.loadUrl("file:///android_asset/example.html");
        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页可以在WebView中打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候是控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器打开

                if (url == null) return false;
                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
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
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    //页面加载完成，关闭ProgressDialog
                    if (!isDetached()) {
                        closeDialog();
                    }

                } else {
                    //网页正在加载，打开ProgressDialog
                    if (!isDetached()) {
                        openDialog(newProgress);
                    }

                }
            }

            private void openDialog(int newProgress) {
                if (dialog == null) {
                    dialog = new ProgressDialog(getActivity());
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

}
