package com.mg.axe.puzzlegame.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/3/31.
 */

public class ScreenUtils {

    /**
     * 返回屏幕的宽高，用数组返回
     * 下标0，width。 下标1，height。
     *
     * @param context
     * @return
     */
    public static int[] getScreenWidth(Context context) {
        context = context.getApplicationContext();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        int[] size = new int[2];
        size[0] = width;
        size[1] = height;
        return size;
    }

}
