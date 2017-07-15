package com.mg.axe.puzzlegame.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.mg.axe.puzzlegame.PuzzleLayout;
import com.mg.axe.puzzlegame.R;
import com.mg.axe.puzzlegame.module.ImagePiece;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class Utils {

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

    /**
     * 传入一个bitmap 返回 一个picec集合
     *
     * @param bitmap
     * @param count
     * @return
     */
    public static List<ImagePiece> splitImage(Context context, Bitmap bitmap, int count, String gameMode) {

        List<ImagePiece> imagePieces = new ArrayList<>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int picWidth = Math.min(width, height) / count;

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                ImagePiece imagePiece = new ImagePiece();
                imagePiece.setIndex(j + i * count);
                //为createBitmap 切割图片获取xy
                int x = j * picWidth;
                int y = i * picWidth;
                if (gameMode.equals(PuzzleLayout.GAME_MODE_NORMAL)) {
                    if (i == count - 1 && j == count - 1) {
                        imagePiece.setType(ImagePiece.TYPE_EMPTY);
                        Bitmap emptyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty);
                        imagePiece.setBitmap(emptyBitmap);
                    } else {
                        imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y, picWidth, picWidth));
                    }
                } else {
                    imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y, picWidth, picWidth));
                }
                imagePieces.add(imagePiece);
            }
        }
        return imagePieces;
    }

    /**
     * 读取图片，按照缩放比保持长宽比例返回bitmap对象
     * <p>
     *
     * @param scale 缩放比例(1到10, 为2时，长和宽均缩放至原来的2分之1，为3时缩放至3分之1，以此类推)
     * @return Bitmap
     */
    public synchronized static Bitmap readBitmap(Context context, int res, int scale) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            return BitmapFactory.decodeResource(context.getResources(), res, options);
        } catch (Exception e) {
            return null;
        }
    }

    public static int getMinLength(int... params) {
        int min = params[0];
        for (int para : params) {
            if (para < min) {
                min = para;
            }
        }
        return min;
    }

    //dp px
    public static int dp2px(Context context, int dpval) {
        context = context.getApplicationContext();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpval, context.getResources().getDisplayMetrics());
    }
}
