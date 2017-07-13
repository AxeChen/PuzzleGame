package com.mg.axe.puzzlegame.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mg.axe.puzzlegame.R;
import com.mg.axe.puzzlegame.module.ImagePiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class ImageSplitterUtil {
    /**
     * 传入一个bitmap 返回 一个picec集合
     *
     * @param bitmap
     * @param count
     * @return
     */
    public static List<ImagePiece> splitImage(Context context, Bitmap bitmap, int count) {

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
                if (i == count - 1 && j == count - 1) {
                    imagePiece.setType(ImagePiece.TYPE_EMPTY);
                    imagePiece.setBitmap(null);
                } else {
                    imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y, picWidth, picWidth));
                }
                imagePieces.add(imagePiece);
            }
        }
        return imagePieces;
    }
}
