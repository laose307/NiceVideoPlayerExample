package wsl.fjsoft.com.nicevideoplayermoudle.bean;

import java.io.Serializable;

/**
 * Created by XiaoJianjun on 2017/5/21.
 * 视频
 */
public class VideoModel implements Serializable {
    private String title="";//视频标题
    private int  adImageShowTime=5000;//图片广告轮播时间 毫秒为单位 5000，默认 3秒 ,视频广告是等待播放完
    private String imageUrl;//视频封面
    private String videoUrl;//视频地址
    private String adUrl;//广告的url
    private String adTitle;//广告的标题
    private int volume=0; //音量 默认无声



//    //屏幕适配,
//    private int designWidth=1366;
//    private int designHeight=768;

    public VideoModel() {
    }


    public VideoModel(String title, long length, String imageUrl, String videoUrl,String adUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.adUrl=adUrl;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
//    public int getDesignWidth() {
//        return designWidth;
//    }
//
//    public void setDesignWidth(int designWidth) {
//        this.designWidth = designWidth;
//    }
//
//    public int getDesignHeight() {
//        return designHeight;
//    }
//
//    public void setDesignHeight(int designHeight) {
//        this.designHeight = designHeight;
//    }

    public int getAdImageShowTime() {
        return adImageShowTime;
    }

    public void setAdImageShowTime(int adImageShowTime) {
        this.adImageShowTime = adImageShowTime;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
