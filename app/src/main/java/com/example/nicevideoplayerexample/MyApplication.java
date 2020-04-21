package com.example.nicevideoplayerexample;

import android.app.Application;

//import com.liulishuo.filedownloader.FileDownloader;
//import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;

public class MyApplication extends Application {

    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        FileDownloader.setupOnApplicationOnCreate(this)
//                .connectionCreator(new FileDownloadUrlConnection
//                        .Creator(new FileDownloadUrlConnection.Configuration()
//                        .connectTimeout(15000) // set connection timeout.
//                        .readTimeout(15000) // set read timeout.
//                ))
//                .commit();
    }
}
