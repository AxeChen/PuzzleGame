package com.mg.axe.puzzlegame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mg.axe.puzzlegame.Utils.ImageSplitterUtil;
import com.mg.axe.puzzlegame.Utils.ScreenUtils;
import com.mg.axe.puzzlegame.module.ImagePiece;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author Zaifeng
 * @Create 2017/7/13 0013
 * @Description Content
 */

public class PuzzleLayout extends FrameLayout implements View.OnClickListener {

    //拼图布局为正方形，宽度为屏幕的宽度
    private int mViewWidth = 0;

    //拼图游戏每一行的图片个数(默认为三个)
    private int mCount = 3;

    //每张图片的宽度
    private int mItemWidth;

    //拼图游戏bitmap集合
    private List<ImagePiece> mImagePieces;

    private ViewGroup.LayoutParams layoutParams;

    //大图
    private Bitmap mBitmap;

    private RelativeLayout mAnimLayout;

    private int mMargin;
    private int mPadding;


    public PuzzleLayout(Context context) {
        this(context, null);
    }

    public PuzzleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PuzzleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initBitmaps();
        initBitmapsWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewWidth);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < mImagePieces.size(); i++) {
            ImageView imageView = mImagePieces.get(i).getImageView();
            imageView.layout(imageView.getLeft(), imageView.getTop(), imageView.getRight(), imageView.getBottom());
        }

        for (int i = 0; i < getChildCount(); i++) {
            if (!(getChildAt(i) instanceof ImageView)) {
                RelativeLayout relativeLayout = (RelativeLayout) getChildAt(i);
                relativeLayout.layout(0, 0, mViewWidth, mViewWidth);
            }
        }
    }

    private void init(Context context) {
        mMargin = dp2px(3);
        mViewWidth = ScreenUtils.getScreenWidth(context)[0];
        mPadding = getMinLength(getPaddingBottom(), getPaddingLeft(), getPaddingRight(), getPaddingTop());
        mItemWidth = (mViewWidth - mPadding * 2 - mMargin * (mCount - 1)) / mCount;
    }

    //初始化小图
    private void initBitmaps() {
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sdhy);
        }
        mImagePieces = ImageSplitterUtil.splitImage(getContext(), mBitmap, mCount);
        Collections.sort(mImagePieces, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece lhs, ImagePiece rhs) {
                return Math.random() > 0.5 ? 1 : -1;
            }
        });


    }

    //设置图片的宽度
    private void initBitmapsWidth() {
        int line = 0;
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        //如果是第二种模式就将空图放在最后
        ImagePiece tempImagePieces = null;
        int tempIndex = 0;
        for (int i = 0; i < mImagePieces.size(); i++) {
            ImagePiece imagePiece = mImagePieces.get(i);
            if (imagePiece.getType() == ImagePiece.TYPE_EMPTY) {
                tempImagePieces = imagePiece;
                tempIndex = i;
                break;
            }
        }
        if (tempImagePieces == null) return;
        mImagePieces.remove(tempIndex);
        mImagePieces.add(mImagePieces.size(), tempImagePieces);

        for (int i = 0; i < mImagePieces.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(mImagePieces.get(i).getBitmap());
            layoutParams = new LayoutParams(mItemWidth, mItemWidth);
            imageView.setLayoutParams(layoutParams);
            if (i != 0 && i % mCount == 0) {
                line++;
            }
            if (i % mCount == 0) {
                left = i % mCount * mItemWidth;
            } else {
                left = i % mCount * mItemWidth + (i % mCount) * mMargin;
            }
            top = mItemWidth * line + line * mMargin;
            right = left + mItemWidth;
            bottom = top + mItemWidth;
            imageView.setRight(right);
            imageView.setLeft(left);
            imageView.setBottom(bottom);
            imageView.setTop(top);
            imageView.setId(i);
            imageView.setOnClickListener(this);
            mImagePieces.get(i).setImageView(imageView);
            addView(imageView);
        }
    }

    private int getMinLength(int... params) {
        int min = params[0];
        for (int para : params) {
            if (para < min) {
                min = para;
            }
        }
        return min;
    }

    //dp px
    protected int dp2px(int dpval) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpval, getResources().getDisplayMetrics());
    }

    private ImageView mFirst;
    private ImageView mSecond;

    @Override
    public void onClick(View v) {

        //如果是确实移动图片模式，需要判断这个图片得周围有没有空图
        int line = mImagePieces.size() / mCount;//算出多少行


//        if (isAnimation) {
//            //还在运行动画的时候，不允许点击
//            return;
//        }
//
//        if (mFirst == v) {
//            //再次选同一张图片，去掉图片标识
//            mFirst.setColorFilter(null);
//            mFirst = null;
//            return;
//        }
//        if (mFirst == null) {
//            mFirst = (ImageView) v;
//            //选中之后添加一层颜色
//            mFirst.setColorFilter(Color.parseColor("#55FF0000"));
//        } else {
//            mSecond = (ImageView) v;
//            //选中第二个图片，开启动两张图片替换的动画
//            exChangeView();
//        }
    }

    private void checkEmptyImage(ImageView imageView) {
        int index = imageView.getId();
        int line = mImagePieces.size() / mCount;
        ImagePiece imagePiece = null;
        if (index < mCount) {
            //第一行
            if (index / mCount == 0) {
                //第一个
                imagePiece = getCheckEmptyImageView(index + 1);
            } else if (index / mCount == mCount - 1) {
                //最后一个
                imagePiece = getCheckEmptyImageView(index - 1);
            } else {
                imagePiece = getCheckEmptyImageView(index + 1);
                if (imagePiece == null) {
                    imagePiece = getCheckEmptyImageView(index - 1);
                }
            }

            //判断下面的图片是否为空


        } else if (index >= mCount && index < (line - 1) * mCount) {
            //中间的
            if (index / mCount == 0) {
                //第一个
                imagePiece = getCheckEmptyImageView(index + 1);
            } else if (index / mCount == mCount - 1) {
                //最后一个
                imagePiece = getCheckEmptyImageView(index - 1);
            } else {
                imagePiece = getCheckEmptyImageView(index + 1);
                if (imagePiece == null) {
                    imagePiece = getCheckEmptyImageView(index - 1);
                }
            }

            //判断上面的图片是否为空

            //判断下面的图片是否为空

        } else {
            //最后一行
            //第一行
            if (index / mCount == 0) {
                //第一个
                imagePiece = getCheckEmptyImageView(index + 1);
            } else if (index / mCount == mCount - 1) {
                //最后一个
                imagePiece = getCheckEmptyImageView(index - 1);
            } else {
                imagePiece = getCheckEmptyImageView(index + 1);
                if (imagePiece == null) {
                    imagePiece = getCheckEmptyImageView(index - 1);
                }
            }

            //判断上面的图片是否为空
        }

        if (imagePiece == null) {
            //周围没有空的imageView
        }
    }

    private ImagePiece getCheckEmptyImageView(int index) {
        ImagePiece imagePiece = mImagePieces.get(index);
        if (imagePiece.getType() == ImagePiece.TYPE_EMPTY) {
            //找到空的imageView
            return imagePiece;
        }
        return null;
    }

    private boolean isAnimation = false;

    private void exChangeView() {
        setUpAnimLayout();

        ImageView first = new ImageView(getContext());
        RelativeLayout.LayoutParams firstParams = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);
        firstParams.leftMargin = mFirst.getLeft() - mPadding;
        firstParams.topMargin = mFirst.getTop() - mPadding;
        final Bitmap firstBitmap = mImagePieces.get(mFirst.getId()).getBitmap();
        first.setImageBitmap(firstBitmap);
        first.setLayoutParams(firstParams);
        mAnimLayout.addView(first);

        ImageView second = new ImageView(getContext());
        RelativeLayout.LayoutParams secondParams = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);
        secondParams.leftMargin = mSecond.getLeft() - mPadding;
        secondParams.topMargin = mSecond.getTop() - mPadding;
        final Bitmap secondBitmap = mImagePieces.get(mSecond.getId()).getBitmap();
        second.setImageBitmap(secondBitmap);
        second.setLayoutParams(secondParams);
        mAnimLayout.addView(second);

        ObjectAnimator secondXAnimator = ObjectAnimator.ofFloat(second, "TranslationX", 0f, -(mSecond.getLeft() - mFirst.getLeft()));
        ObjectAnimator secondYAnimator = ObjectAnimator.ofFloat(second, "TranslationY", 0f, -(mSecond.getTop() - mFirst.getTop()));
        ObjectAnimator firstXAnimator = ObjectAnimator.ofFloat(first, "TranslationX", 0f, mSecond.getLeft() - mFirst.getLeft());
        ObjectAnimator firstYAnimator = ObjectAnimator.ofFloat(first, "TranslationY", 0f, mSecond.getTop() - mFirst.getTop());
        AnimatorSet secondAnimator = new AnimatorSet();
        secondAnimator.play(secondXAnimator).with(secondYAnimator).with(firstXAnimator).with(firstYAnimator);
        secondAnimator.setDuration(300);
        secondAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mFirst != null) {
                    mFirst.setColorFilter(null);
                    mFirst.setVisibility(VISIBLE);
                    mFirst.setImageBitmap(secondBitmap);
                    mImagePieces.get(mFirst.getId()).setBitmap(secondBitmap);
                }
                if (mSecond != null) {
                    mSecond.setVisibility(VISIBLE);
                    mSecond.setImageBitmap(firstBitmap);
                    mImagePieces.get(mSecond.getId()).setBitmap(firstBitmap);
                }
                mAnimLayout.removeAllViews();
                mAnimLayout.setVisibility(GONE);
                mFirst = null;
                mSecond = null;
                isAnimation = false;
                invalidate();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimation = true;
                mAnimLayout.setVisibility(VISIBLE);
                mFirst.setVisibility(INVISIBLE);
                mSecond.setVisibility(INVISIBLE);
            }
        });
        secondAnimator.start();

    }

    /**
     * 构造动画层 用于点击之后的动画
     * 为什么要做动画层？ 要保证动画在整个view上面执行。
     */
    private void setUpAnimLayout() {
        if (mAnimLayout == null) {
            mAnimLayout = new RelativeLayout(getContext());
            addView(mAnimLayout);
        }
    }
}
