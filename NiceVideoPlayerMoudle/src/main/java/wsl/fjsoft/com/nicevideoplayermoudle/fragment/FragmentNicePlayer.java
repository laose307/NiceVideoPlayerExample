package wsl.fjsoft.com.nicevideoplayermoudle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import wsl.fjsoft.com.nicevideoplayermoudle.FullScreenVideoPlayerController;
import wsl.fjsoft.com.nicevideoplayermoudle.NiceVideoPlayer;
import wsl.fjsoft.com.nicevideoplayermoudle.NiceVideoPlayerManager;
import wsl.fjsoft.com.nicevideoplayermoudle.R;
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

public class FragmentNicePlayer extends Fragment {
    private static String ARG_PARAM = "FragmentNicePlayer";
    private String mParam;
    private  NiceVideoPlayer niceVideoPlayer;
    public static FragmentNicePlayer newInstance(VideoModel mVideoModel) {
        FragmentNicePlayer frag = new FragmentNicePlayer();
        Bundle bundle = new Bundle();
        bundle.putSerializable("values", mVideoModel);
        frag.setArguments(bundle);   //设置参数
        return frag;
    }


    FrameLayout RelativeLayout_1;
    TextView text_title;
    Button imgBtn_back;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.robot_fragment_nice_player, container, false);
        //轮播广告

        Bundle bundle = getArguments();
        if (bundle != null) {
            VideoModel mVideoModel = (VideoModel) bundle.getSerializable("values");
//            AutoUtils.setSize(getActivity(), false, mVideoModel.getDesignWidth(), mVideoModel.getDesignHeight());
//            AutoUtils.auto(root);
            RelativeLayout_1 = root.findViewById(R.id.RelativeLayout_1);
            text_title = root.findViewById(R.id.text_title);
            imgBtn_back = root.findViewById(R.id.imgBtn_back);

            text_title.setText(""+mVideoModel.getTitle());
            ViewGroup.LayoutParams params = RelativeLayout_1.getLayoutParams();
            // 将图片控件添加到容器
            RelativeLayout_1.addView(createNiceVideoPlayer(getContext(), mVideoModel), params);

            if(!TextUtils.isEmpty(mVideoModel.getVideoUrl())){
                niceVideoPlayer.start();
            }


        }
        imgBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return root;
    }


    @Override
    public void onStop() {
        //释放播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        RelativeLayout_1.removeAllViews();
        super.onStop();
    }


    /**
     * 创建NiceVideoPlayer
     * @param mVideoModel
     * @return
     */
    private NiceVideoPlayer createNiceVideoPlayer(Context mContext, final VideoModel mVideoModel) {

        niceVideoPlayer = new NiceVideoPlayer(mContext);
        niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        niceVideoPlayer.setUp(mVideoModel.getVideoUrl(), null);
        niceVideoPlayer.setImageShowTime(mVideoModel.getAdImageShowTime());
        FullScreenVideoPlayerController controller = new FullScreenVideoPlayerController(mContext);
        controller.setTitle("");
        if (mVideoModel.getVideoUrl() == null || mVideoModel.getVideoUrl().length() == 0) {
            niceVideoPlayer.setTag(null);
            controller.setVideo(false);
        } else {
            niceVideoPlayer.setTag("videoAD");
            controller.setVideo(true);
        }

        if (mVideoModel.getImageUrl() != null && mVideoModel.getImageUrl().length() > 0) {
            //加载封面
            Glide.with(getActivity())
                    .load(mVideoModel.getImageUrl())
                    .into(controller.imageView());
        }
        niceVideoPlayer.setController(controller);
        controller.setCallBack(new FullScreenVideoPlayerController.CallBack() {
            @Override
            public void OnCompletionListener() {//播放结束关闭当前界面

                getFragmentManager().popBackStack();

            }

            @Override
            public void OnErrorListener() {

            }

            @Override
            public void OnClickADListener() {

            }
        });
        return niceVideoPlayer;
    }
}
