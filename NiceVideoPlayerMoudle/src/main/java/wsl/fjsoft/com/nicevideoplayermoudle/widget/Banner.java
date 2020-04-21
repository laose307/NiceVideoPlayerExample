package wsl.fjsoft.com.nicevideoplayermoudle.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.DrawableRes;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;

import wsl.fjsoft.com.nicevideoplayermoudle.FileDownUtils;
import wsl.fjsoft.com.nicevideoplayermoudle.FullScreenVideoPlayerController;
import wsl.fjsoft.com.nicevideoplayermoudle.NiceVideoPlayer;
import wsl.fjsoft.com.nicevideoplayermoudle.NiceVideoPlayerManager;
import wsl.fjsoft.com.nicevideoplayermoudle.R;
import wsl.fjsoft.com.nicevideoplayermoudle.activity.WebActivity;
import wsl.fjsoft.com.nicevideoplayermoudle.adapter.ADPagerAdapter;
import wsl.fjsoft.com.nicevideoplayermoudle.bean.VideoModel;


/**
 * 说明：图片广告跟视频广告混合体
 * 1.广告链接，点击就会跳转到广告界面
 * 2.图片跟视频可以进行混合轮播，视频播放完就会进行翻页，图片有个显示时间值，时间一到就会翻页。
 *
 *
 *
 * @author kcd
 * @package wsl.fjsoft.com.nicevideoplayermoudle.widget
 * @filename ADRelativeLayout
 * @date 2018/7/24 0024 15:13
 * @email 891705265@qq.com
 **/

public class Banner extends RelativeLayout {
    String tag="ADRelativeLayout";
    private Context mContext;
    private int index=0;
    private ArrayList<VideoModel> models;
    private  boolean isShowPoint=false;//是否显示翻页的圆点
    private Handler handler=new Handler(Looper.getMainLooper());
    public Banner(Context context) {
        super(context);

        mContext=context;
        init();
    }

    public boolean isShowPoint() {
        return isShowPoint;
    }

    /**
     *  显示底部小圆点
     * @param showPoint
     */
    public void setShowPoint(boolean showPoint) {
        isShowPoint = showPoint;
        pointgroup(showPoint);
    }

    /**
     * 设置背景色
     * @param resid
     */
    public void setBackground(@DrawableRes int resid){
        relat_bg.setBackgroundResource(resid);
    }

    private void  pointgroup(boolean showPoint ){
        if(showPoint){
            pointgroup_ad.setVisibility(View.VISIBLE);
        }else{
            pointgroup_ad.setVisibility(View.GONE);
        }
    }

    ViewPager viewpager_ad;
    LinearLayout pointgroup_ad;
    RelativeLayout relat_bg;
    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.activity_ad, this, true);
        viewpager_ad=findViewById(R.id.viewpager_ad);
        relat_bg=findViewById(R.id.relat_bg);
        pointgroup_ad=findViewById(R.id.pointgroup_ad);
    }

    public void setData(ArrayList<VideoModel> models_){
        models=models_;
        createView();
    }

   private ArrayList<NiceVideoPlayer> mNiceVideoPlayerArray;
    public void createView(){
        if(pointgroup_ad==null){
            return;
        }


        mNiceVideoPlayerArray=new ArrayList<>();
        //画圆点
        for (int i = 0; i < models.size(); i++) {

//            NiceVideoPlayer niceVideoPlayer=new NiceVideoPlayer(mContext);
            //创建图片跟视频控件
            mNiceVideoPlayerArray.add(createNiceVideoPlayer(models.get(i)));

                // 制作底部小圆点
                ImageView pointImage = new ImageView(mContext);
                pointImage.setImageResource(R.drawable.viewpager_point);

                // 设置小圆点的布局参数
                int PointSize = 20;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PointSize, PointSize);

                if (i > 0) {
                    params.leftMargin =20;
                    pointImage.setSelected(false);
                } else {
                    pointImage.setSelected(true);
                }
                pointImage.setLayoutParams(params);
                // 添加到容器里
                pointgroup_ad.addView(pointImage);

                pointgroup(isShowPoint);

        }


        // 对ViewPager设置滑动监听
        viewpager_ad.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {




            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //滑动停止所有视频播放
              //  stopNiceVideoPlayer();
                if(mNiceVideoPlayerArray.get(position)!=null&&position==index){

                    //移除
                    handler.removeCallbacks(runnable);
                    if(mNiceVideoPlayerArray.get(position).getTag()!=null){//有视频
                        mNiceVideoPlayerArray.get(position).start();

                    }else{//图片

                         handler.postDelayed(runnable,mNiceVideoPlayerArray.get(position).getImageShowTime());

                    }

                }

                Log.d("ADRelativeLayout","position:"+position +" positionOffset:"+positionOffset +" positionOffsetPixels:"+positionOffsetPixels);
            }

            int lastPosition;
            @Override
            public void onPageSelected(int position) {

                if(pointgroup_ad!=null&& pointgroup_ad.getChildAt(position)!=null){
                    // 页面被选中
                    // 设置当前页面选中
                    pointgroup_ad.getChildAt(position).setSelected(true);
                    // 设置前一页不选中
                    pointgroup_ad.getChildAt(lastPosition).setSelected(false);

                }

                if(mNiceVideoPlayerArray.get(lastPosition)!=null){
                    mNiceVideoPlayerArray.get(lastPosition).release();
                }
                // 替换位置
                lastPosition = position;
                index=lastPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewpager_ad.setAdapter( new ADPagerAdapter(mNiceVideoPlayerArray));

    }


    private  Runnable runnable=new Runnable() {
        @Override
        public void run() {
            nextPage(setIndex());
        }
    };

    /**
     * actvity 关闭调用方法
     */
    public void onDestory() {
          NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    /**
     * viewpager_ad 的下一页
     * @param lastView
     */
    private void nextPage(int lastView){
        viewpager_ad.setCurrentItem(lastView,true);
    }

    /**
     * 停止播放
     * 释放资源
     */
    public void stopNiceVideoPlayer(){

        if(mNiceVideoPlayerArray!=null){
            for (NiceVideoPlayer NiceVideoPlayer_:mNiceVideoPlayerArray){
                NiceVideoPlayer_.release();
                NiceVideoPlayer_.releasePlayer();
            }
        }
        }


    /**
     * 创建NiceVideoPlayer
     * @param mVideoModel
     * @return
     */
    private NiceVideoPlayer createNiceVideoPlayer(final VideoModel mVideoModel){

        final  NiceVideoPlayer niceVideoPlayer=new NiceVideoPlayer(mContext);
        niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        niceVideoPlayer.setVolume(mVideoModel.getVolume());//
        niceVideoPlayer.setUp(mVideoModel.getVideoUrl(), null);
        //查找本地是否有资源，有的话就播放本地
        if(mVideoModel.getVideoUrl()!=null&&!TextUtils.isEmpty(mVideoModel.getVideoUrl())){
            if(!TextUtils.isEmpty(FileDownUtils.getInstance().isHasVideo(mVideoModel.getVideoUrl()))){
                Log.d("NiceVideoPlayer###","NiceVideoPlayer isHasVideo "+mVideoModel.getVideoUrl());
                mVideoModel.setVideoUrl(FileDownUtils.getInstance().getFilePath(mVideoModel.getVideoUrl()));
            }else{//没缓存就下载
                Log.d("NiceVideoPlayer###","NiceVideoPlayer 开始缓存视频"+mVideoModel.getVideoUrl());
                FileDownUtils.getInstance().loadVideo(mVideoModel.getVideoUrl());
            }
        }


        niceVideoPlayer.setImageShowTime(mVideoModel.getAdImageShowTime());
         FullScreenVideoPlayerController controller = new FullScreenVideoPlayerController(mContext);
        controller.setTitle(mVideoModel.getTitle());
        if(mVideoModel.getVideoUrl()==null||mVideoModel.getVideoUrl().length()==0){
            niceVideoPlayer.setTag(null);
            controller.setVideo(false);
        }else{
            niceVideoPlayer.setTag("videoAD");
            controller.setVideo(true);
        }

        if(mVideoModel.getImageUrl()!=null&&mVideoModel.getImageUrl().length()>0){
            Log.d("NiceVideoPlayer###","加载封面 "+mVideoModel.getImageUrl());

//            controller.imageView().setBackgroundResource(R.drawable.battery_full);
            //加载封面
            Glide.with(mContext).load("http://goo.gl/gEgYUd").into(controller.imageView());

            Glide.with(mContext)
                    .load(mVideoModel.getImageUrl())
                    .into(controller.imageView());
        }


        niceVideoPlayer.setController(controller);
        controller.setCallBack(new FullScreenVideoPlayerController.CallBack() {
            @Override
            public void OnCompletionListener() {


                nextPage(setIndex());
                Log.d(tag,"OnCompletionListener:"+niceVideoPlayer.getTag()+"" +" index:"+index);
            }

            @Override
            public void OnErrorListener() {
                Log.d(tag,"OnErrorListener:"+niceVideoPlayer.getTag()+"");

            }

            @Override
            public void OnClickADListener() {

                //
                if(mVideoModel.getAdUrl()!=null&&mVideoModel.getAdUrl().length()!=0){//有广告链接

                    if(!TextUtils.isEmpty(mVideoModel.getVideoUrl())){//点击视频
                        niceVideoPlayer.releasePlayer();
                    }else if(TextUtils.isEmpty(mVideoModel.getImageUrl())){//点击图片

                    }
                    Intent intent=new Intent(mContext, WebActivity.class);
                intent.putExtra("url",mVideoModel.getAdUrl());
                intent.putExtra("title",mVideoModel.getAdTitle());
                mContext.startActivity(intent);
                }


                if(mVideoModel.getAdUrl()==null||mVideoModel.getAdUrl().length()==0){

                    if(!TextUtils.isEmpty(mVideoModel.getVideoUrl())){//点击视频才有作用
//                        EventBus.getDefault().post(new MainMessageBean(7));
//                        点击放大图片
//                        EventBus.getDefault().post(new MainMessageBean(1,
//                                mVideoModel.getVideoUrl(), ""));

                     //   niceVideoPlayer.releasePlayer();
                    }

                    return;
                }

//
//                Intent intent=new Intent(mContext, WebActivity.class);
//                intent.putExtra("url",mVideoModel.getAdUrl());
//                intent.putExtra("title",mVideoModel.getAdTitle());
//                mContext.startActivity(intent);

            }
        });
        return niceVideoPlayer;
    }


    private int setIndex(){
        ++index;
        if (index>(mNiceVideoPlayerArray.size()-1)){
            index=0;
        }
        return index;
    }



}
