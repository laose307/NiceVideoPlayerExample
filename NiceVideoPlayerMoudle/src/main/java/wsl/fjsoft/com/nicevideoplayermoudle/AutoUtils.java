package wsl.fjsoft.com.nicevideoplayermoudle;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * UI适配工具类
 * 要优先设置设置版面：例如：AutoUtils.setSize(indexmain.this, true, 1024, 768);
 * Created by fxyy-pc on 2017/9/20.
 */

public class AutoUtils {
    public static int displayWidth;
    public static int displayHeight;

    public static int designWidth=1024;
    public static int designHeight=768;

    public static double textPixelsRate;


    public static void setDisplay(Activity act, boolean hasStatusBar){
        Display display = act.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        if (hasStatusBar) {
            height -= getStatusBarHeight(act);
        }

        AutoUtils.displayWidth=width;
        AutoUtils.displayHeight=height;
    }


    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        try {
            int resourceId = context.getResources().getIdentifier(
                    "status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(
                        resourceId);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setSize(Activity act, boolean hasStatusBar, int designWidth, int designHeight){
        if(act==null || designWidth<1 || designHeight<1)return;

        Display display = act.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        if (hasStatusBar) {
            height -= getStatusBarHeight(act);
        }

        AutoUtils.displayWidth=width;
        AutoUtils.displayHeight=height;

        AutoUtils.designWidth=designWidth;
        AutoUtils.designHeight=designHeight;

        double displayDiagonal=Math.sqrt(Math.pow(AutoUtils.displayWidth, 2)+Math.pow(AutoUtils.displayHeight, 2));
        double designDiagonal=Math.sqrt(Math.pow(AutoUtils.designWidth, 2)+Math.pow(AutoUtils.designHeight, 2));
        AutoUtils.textPixelsRate=displayDiagonal/designDiagonal;
    }

    public static void auto(Activity act){
        if(act==null || displayWidth<1 || displayHeight<1)return;

        View view=act.getWindow().getDecorView();
        auto(view);
    }

    public static void auto(View view){
        if(view==null || displayWidth<1 || displayHeight<1)return;

        AutoUtils.autoTextSize(view);
        AutoUtils.autoSize(view);
        AutoUtils.autoPadding(view);
        AutoUtils.autoMargin(view);

        if(view instanceof ViewGroup){
            auto((ViewGroup)view);
        }

    }

    private static void auto(ViewGroup viewGroup){
        int count = viewGroup.getChildCount();

        for (int i = 0; i < count; i++) {

            View child = viewGroup.getChildAt(i);

            if(child!=null){
                auto(child);
            }
        }
    }

    public static void autoMargin(View view){
        if (!(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams))
            return;

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if(lp == null)return ;

        lp.leftMargin = getDisplayWidthValue(lp.leftMargin);
        lp.topMargin = getDisplayHeightValue(lp.topMargin);
        lp.rightMargin = getDisplayWidthValue(lp.rightMargin);
        lp.bottomMargin = getDisplayHeightValue(lp.bottomMargin);

    }

    public static void autoPadding(View view){
        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();

        l = getDisplayWidthValue(l);
        t = getDisplayHeightValue(t);
        r = getDisplayWidthValue(r);
        b = getDisplayHeightValue(b);

        view.setPadding(l, t, r, b);
    }

    public static void autoSize(View view){
        ViewGroup.LayoutParams lp = view.getLayoutParams();

        if (lp == null) return;

        if(lp.width>0){
            lp.width = getDisplayWidthValue(lp.width);
        }

        if(lp.height>0){
            lp.height = getDisplayHeightValue(lp.height);
        }

    }

    public static void autoTextSize(View view){
        if(view instanceof TextView){
            double designPixels=((TextView) view).getTextSize();
            double displayPixels=textPixelsRate*designPixels;
            ((TextView) view).setIncludeFontPadding(false);
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) displayPixels);
        }
    }

    public static int getDisplayWidthValue(int designWidthValue){
        int values=designWidthValue;
        try {
            if(designWidthValue<2){
                return designWidthValue;
            }
            values= designWidthValue * displayWidth / designWidth;
        } catch (ArithmeticException e) {
            // TODO: handle exception
        }

        return values;

    }

    public static int getDisplayHeightValue(int designHeightValue){

        int values=designHeightValue;

        try {
            if(designHeightValue<2){
                return designHeightValue;
            }
            values= designHeightValue * displayHeight / designHeight;
        } catch (ArithmeticException e) {
            // TODO: handle exception
        }
        return values;

    }
}
