package wsl.fjsoft.com.nicevideoplayermoudle;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.util.ArrayList;

/**
 * @author kcd
 * @package com.tld.robotbasis.utils
 * @filename FileDownUtils
 * @date 2019/1/22 0022 10:27
 * @email 891705265@qq.com
 **/

public class FileDownUtils {

    private static FileDownUtils mFileDownUtils;
    ArrayList<String> urls = new ArrayList<>();

    public static FileDownUtils getInstance() {
        if (mFileDownUtils == null) {

            synchronized (FileDownUtils.class) {
                if (mFileDownUtils == null) {
                    mFileDownUtils = new FileDownUtils();
                }
            }
        }
        return mFileDownUtils;
    }

    private FileDownUtils() {

    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public void addUrls(String url) {
        this.urls.add(url);
    }


    public String isHasVideo(String url) {

        File file = new File(getFilePath(url));
        if (file.exists()) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * url转md5
     *
     * @param url
     * @return
     */
    public String urlToMd5(String url) {
        return MD5Utils.MD5Encode(url, "utf-8");
    }


    public void loadListVideo(ArrayList<String> urls) {

        for (String str : urls) {
            loadVideo(str);
        }


    }

    public String getFilePath(String url) {
        String fileName = urlToMd5(url);//把

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/downloadAD";
        return path + "/" + fileName;
    }

    /**
     * 下载视频
     *
     * @param url
     */
    public void loadVideo(String url) {

            if(!TextUtils.isEmpty(FileDownUtils.getInstance().isHasVideo(url))|| TextUtils.isEmpty(url)){
                Log.d("NiceVideoPlayer###","NiceVideoPlayer 已经下载过不用再下载 ");
                return;
            }


        FileDownloader.getImpl().create(url).setPath(getFilePath(url)).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d("filedown", " pending ");
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d("filedown", " progress 下载进度:" + (soFarBytes / totalBytes) * 100);
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.d("filedown", " completed " + task.getPath());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d("filedown", " paused ");
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.d("filedown", " error ");
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Log.d("filedown", " warn ");
            }
        }).start();
    }


}
