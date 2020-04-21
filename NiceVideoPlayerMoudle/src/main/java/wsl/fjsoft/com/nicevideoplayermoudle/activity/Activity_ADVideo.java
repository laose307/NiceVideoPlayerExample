package wsl.fjsoft.com.nicevideoplayermoudle.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import wsl.fjsoft.com.nicevideoplayermoudle.NiceVideoPlayer;
import wsl.fjsoft.com.nicevideoplayermoudle.R;
import wsl.fjsoft.com.nicevideoplayermoudle.bean.VideoModel;
import wsl.fjsoft.com.nicevideoplayermoudle.widget.Banner;

/**
 * @author kcd
 * @package wsl.fjsoft.com.nicevideoplayermoudle.activity
 * @filename activity_niceplayer
 * @date 2018/7/24 0024 11:25
 * @email 891705265@qq.com
 **/

public class Activity_ADVideo extends Activity {

    NiceVideoPlayer mNiceVideoPlayer;
    FrameLayout ADRelativeLayout_1;
    Banner mADRelativeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nice_player);

        ADRelativeLayout_1=findViewById(R.id.RelativeLayout_1);

        mADRelativeLayout=new Banner(Activity_ADVideo.this);

        ADRelativeLayout_1.addView(mADRelativeLayout);

        mADRelativeLayout.setData(data());
        mADRelativeLayout.setShowPoint(true);



    }

    /**
     * 模拟广告数据
     * @return
     */
    public  ArrayList<VideoModel> data(){
        ArrayList<VideoModel> models=new ArrayList<>();

        //图片广告+广告链接
        VideoModel mVideoModel=new VideoModel();
        mVideoModel.setImageUrl("http://t8.baidu.com/it/u=3571592872,3353494284&fm=79&app=86&f=JPEG?w=1200&h=1290");
        mVideoModel.setAdUrl("https://www.jianshu.com/p/55e28c38388c");
        models.add(mVideoModel);

//        //图片广告+没有广告链接+图片显示时间 10秒
        mVideoModel=new VideoModel();
        mVideoModel.setImageUrl("http://f.hiphotos.baidu.com/image/pic/item/e4dde71190ef76c6d203c4c79116fdfaae51670c.jpg");
        mVideoModel.setVideoUrl("http://112.5.240.2:81/2Q2W0B784101358FBBB74AA6D6641E382A4F80BC556F_unknown_0DF0CD678166EE1AE245967E71317516F1FE97C1_9/clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        mVideoModel.setAdImageShowTime(10000);
        mVideoModel.setAdUrl("https://www.jianshu.com/p/55e28c38388c");
        models.add(mVideoModel);


        //视频广告+视频封面+广告链接
//        mVideoModel=new VideoModel();
//        mVideoModel.setImageUrl("http://f.hiphotos.baidu.com/image/pic/item/960a304e251f95cacc952852c5177f3e660952f5.jpg");
//        mVideoModel.setVideoUrl("http://pbowppvo4.bkt.clouddn.com/nnd/201807232123043179184.mp4");
//        mVideoModel.setAdUrl("https://www.jianshu.com/p/55e28c38388c");
//        models.add(mVideoModel);


        //视频广告+视频封面+广告链接+广告标题
//        mVideoModel=new VideoModel();
//        mVideoModel.setImageUrl("http://f.hiphotos.baidu.com/image/pic/item/e4dde71190ef76c6d203c4c79116fdfaae51670c.jpg");
//        mVideoModel.setVideoUrl("http://pbowppvo4.bkt.clouddn.com/nnd/201807232126104309394.mp4");
//        mVideoModel.setAdUrl("http://image.baidu.com/search/detail?z=0&word=摄影师丁键&hs=0&pn=4&spn=0&di=0&pi=55255249345&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cs=1795762180%2C2516399814&os=&simid=&adpicid=0&lpn=0&fm=&sme=&cg=&bdtype=-1&oriquery=&objurl=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F7acb0a46f21fbe09aa528ef967600c338644adcb.jpg&fromurl=&gsm=0&catename=pcindexhot&islist=&querylist=");
//        mVideoModel.setAdTitle("9999999");
//        models.add(mVideoModel);


        return models;
    }



    @Override
    protected void onStop() {
        super.onStop();
        // 在 onStop 时释放掉播放器
        mADRelativeLayout.onDestory();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
