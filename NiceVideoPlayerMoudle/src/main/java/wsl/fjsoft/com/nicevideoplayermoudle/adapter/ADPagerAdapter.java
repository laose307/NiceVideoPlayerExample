package wsl.fjsoft.com.nicevideoplayermoudle.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

import wsl.fjsoft.com.nicevideoplayermoudle.NiceVideoPlayer;
import wsl.fjsoft.com.nicevideoplayermoudle.bean.VideoModel;

/**
 * @author kcd
 * @package wsl.fjsoft.com.nicevideoplayermoudle.adapter
 * @filename ADPagerAdapter
 * @date 2018/7/24 0024 15:35
 * @email 891705265@qq.com
 **/

public class ADPagerAdapter extends PagerAdapter {
    private  ArrayList<NiceVideoPlayer> array;
    public ADPagerAdapter(ArrayList<NiceVideoPlayer> mNiceVideoPlayerArray){
        array=mNiceVideoPlayerArray;
    }
    @Override
    public int getCount() {
        // 返回整数的最大值
        return array.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // return super.instantiateItem(container, position);
        // 修改position
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        position = position % array.size();
        // 将图片控件添加到容器
        container.addView(array.get(position),params);
        // 返回
        return array.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}