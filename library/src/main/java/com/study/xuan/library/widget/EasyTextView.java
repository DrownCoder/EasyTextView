package com.study.xuan.library.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.study.xuan.library.R;
import com.study.xuan.library.span.EasyVerticalCenterSpan;
import com.study.xuan.shapebuilder.shape.ShapeBuilder;

import static android.graphics.drawable.GradientDrawable.RECTANGLE;

/**
 * Author : xuan.
 * Date : 2017/12/23.
 * Description :方便使用的TextView,目前支持:
 * 1.圆角和边线颜色和宽度,soild
 * 2.iconFont配合textLeft,textRight,textPadding,iconColor等
 * 3.支持不同左中右不同字号垂直居中
 * 4.支持左中上分别设置Selector，不要设置TextColor，会覆盖（一个TextView）
 */

public class EasyTextView extends TextView {
    private static final String EMPTY_SPACE = "\u3000";
    private Context mContext;
    private float mRadius;
    private float mRadiusTopLeft, mRadiusTopRight, mRadiusBottomLeft, mRadiusBottomRight;
    private int mStrokeColor;
    private int mStrokeWidth;
    private int mSoild;
    private float mTextPadding;
    private String mTextLeft;
    private String mTextRight;
    private ColorStateList mIconColor = null;
    private int mCurIconColor;
    private String iconString;
    private ColorStateList mLeftColor = null;
    private int mCurLeftColor;
    private ColorStateList mRightColor = null;
    private int mCurRightColor;
    private float mLeftSize;
    private float mRightSize;
    //icon的index
    private int iconIndex = 0;

    public EasyTextView(Context context) {
        this(context, null);
    }

    public EasyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttr(context, attrs);
        init();
    }

    private void init() {
        initIconFont();
        initShape();
    }

    private void initIconFont() {
        try {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "iconfont.ttf"));
        } catch (Exception e) {
            return;
        }
        iconString = getText().toString();
        if (!TextUtils.isEmpty(mTextLeft) || !TextUtils.isEmpty(mTextRight)) {
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(getText());
            //增加空格
            if (!TextUtils.isEmpty(mTextLeft)) {
                if (mTextPadding != 0) {
                    stringBuilder.insert(0, EMPTY_SPACE);
                    iconIndex++;
                }
                stringBuilder.insert(0, mTextLeft);
                iconIndex += mTextLeft.length();
            }

            if (!TextUtils.isEmpty(mTextRight)) {
                if (mTextPadding != 0) {
                    stringBuilder.append(EMPTY_SPACE);
                }
                stringBuilder.append(mTextRight);
            }
            if (mTextPadding != 0) {
                //设置字和icon间距
                if (!TextUtils.isEmpty(mTextLeft)) {
                    AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) mTextPadding);
                    stringBuilder.setSpan(sizeSpan, iconIndex - 1, iconIndex, Spanned
                            .SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                if (!TextUtils.isEmpty(mTextRight)) {
                    AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) mTextPadding);
                    stringBuilder.setSpan(sizeSpan, iconIndex + 1, iconIndex + 2, Spanned
                            .SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            }
            //设置icon和字的颜色
            if (mIconColor != null) {
                int color = mIconColor.getColorForState(getDrawableState(), 0);
                if (color != mCurIconColor) {
                    mCurIconColor = color;
                }
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mCurIconColor);
                stringBuilder.setSpan(foregroundColorSpan, iconIndex, iconIndex + 1, Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (!TextUtils.isEmpty(mTextLeft) && mLeftColor != null) {
                int color = mLeftColor.getColorForState(getDrawableState(), 0);
                if (color != mCurLeftColor) {
                    mCurLeftColor = color;
                }
                int end = mTextPadding == 0 ? iconIndex : iconIndex - 1;
                ForegroundColorSpan foregroundLeftColor = new ForegroundColorSpan(mCurLeftColor);
                stringBuilder.setSpan(foregroundLeftColor, 0, end, Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (!TextUtils.isEmpty(mTextRight) && mRightColor != null) {
                int color = mRightColor.getColorForState(getDrawableState(), 0);
                if (color != mCurRightColor) {
                    mCurRightColor = color;
                }
                int start = mTextPadding == 0 ? iconIndex + 1 : iconIndex + 2;
                ForegroundColorSpan foregroundRightColor = new ForegroundColorSpan(mCurRightColor);
                stringBuilder.setSpan(foregroundRightColor, start, stringBuilder.length()
                        , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            //设置字的大小
            if (!TextUtils.isEmpty(mTextLeft) && mLeftSize != 0) {
                int end = mTextPadding == 0 ? iconIndex : iconIndex - 1;
                CharacterStyle sizeSpan;
                final int gravity = getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
                if (gravity == Gravity.CENTER_VERTICAL) {
                    sizeSpan = new EasyVerticalCenterSpan(mLeftSize,mCurLeftColor);
                }else{
                    sizeSpan = new AbsoluteSizeSpan((int) mLeftSize);
                }
                stringBuilder.setSpan(sizeSpan, 0, end, Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (!TextUtils.isEmpty(mTextRight) && mRightSize != 0) {
                int start = mTextPadding == 0 ? iconIndex + 1 : iconIndex + 2;
                CharacterStyle sizeSpan;
                final int gravity = getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
                if (gravity == Gravity.CENTER_VERTICAL) {
                    sizeSpan = new EasyVerticalCenterSpan(mRightSize,mCurRightColor);
                }else{
                    sizeSpan = new AbsoluteSizeSpan((int) mRightSize);
                }
                stringBuilder.setSpan(sizeSpan, start, stringBuilder.length(), Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            setText(stringBuilder);
        }
    }

    private void initShape() {
        if (mRadius == -0 && mRadiusTopLeft == 0 && mRadiusTopRight == 0 && mRadiusBottomLeft == 0
                && mRadiusBottomRight == 0 && mStrokeColor == -1 && mStrokeWidth == 0 && mSoild ==
                -1) {
            return;
        } else {
            setShape();
        }
    }

    private void setShape() {
        if (mRadius != 0) {
            ShapeBuilder.create().Type(RECTANGLE).Radius(mRadius).Soild(mSoild).Stroke
                    (mStrokeWidth, mStrokeColor).build(this);
        } else {
            ShapeBuilder.create().Type(RECTANGLE).Radius(mRadiusTopLeft,
                    mRadiusTopRight, mRadiusBottomLeft, mRadiusBottomRight).Soild(mSoild).Stroke
                    (mStrokeWidth, mStrokeColor).build(this);
        }
    }

    private void clearText() {
        setText(iconString);
        iconIndex = 0;
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EasyTextView);
        mRadius = array.getDimensionPixelOffset(R.styleable.EasyTextView_totalRadius, 0);
        mRadiusTopLeft = array.getDimensionPixelSize(R.styleable.EasyTextView_radiusTopLeft, 0);
        mRadiusTopRight = array.getDimensionPixelSize(R.styleable.EasyTextView_radiusTopRight, 0);
        mRadiusBottomLeft = array.getDimensionPixelSize(R.styleable.EasyTextView_radiusBottomLeft,
                0);
        mRadiusBottomRight = array.getDimensionPixelSize(R.styleable
                .EasyTextView_radiusBottomRight, 0);
        mStrokeColor = array.getColor(R.styleable.EasyTextView_strokeColor, -1);
        mStrokeWidth = array.getDimensionPixelOffset(R.styleable.EasyTextView_strokeWidth, 0);
        mSoild = array.getColor(R.styleable.EasyTextView_soildBac, -1);
        mTextPadding = array.getDimensionPixelOffset(R.styleable.EasyTextView_textPadding, 0);
        mTextLeft = array.getString(R.styleable.EasyTextView_textLeft);
        mTextRight = array.getString(R.styleable.EasyTextView_textRight);
        mIconColor = array.getColorStateList(R.styleable.EasyTextView_iconColor);
        mLeftColor = array.getColorStateList(R.styleable.EasyTextView_textLeftColor);
        mRightColor = array.getColorStateList(R.styleable.EasyTextView_textRightColor);
        mLeftSize = array.getDimensionPixelSize(R.styleable.EasyTextView_textLeftSize, 0);
        mRightSize = array.getDimensionPixelSize(R.styleable.EasyTextView_textRightSize, 0);
        array.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        if (mIconColor != null && mIconColor.isStateful()
                || mLeftColor != null && mLeftColor.isStateful()
                || mRightColor != null && mRightColor.isStateful()) {
            clearText();
            initIconFont();
        }
        super.drawableStateChanged();
    }

    /**
     * 设置shape背景颜色
     */
    public void setBackgroundSold(int soild) {
        this.mSoild = soild;
        setShape();
    }

    /**
     * 设置icon颜色
     */
    public void setIconColor(int color) {
        this.mIconColor = ColorStateList.valueOf(color);
        build();
    }

    /**
     * 设置左文案
     */
    public void setTextLeft(String textLeft) {
        this.mTextLeft = textLeft;
        build();
    }

    /**
     * 设置右文案
     */
    public void setTextRight(String textRight) {
        this.mTextRight = textRight;
        build();
    }

    /**
     * 设置左文案颜色
     */
    public void setTextLeftColor(int color) {
        this.mLeftColor = ColorStateList.valueOf(color);
        build();
    }

    /**
     * 设置右文案颜色
     */
    public void setTextRightColor(int color) {
        this.mRightColor = ColorStateList.valueOf(color);
        build();
    }

    /**
     * 设置左文案字号大小
     */
    public void setTextLeftSize(float leftSize) {
        this.mLeftSize = leftSize;
        build();
    }

    /**
     * 设置右文案字号大小
     */
    public void setTextRightSize(float rightSize) {
        this.mRightSize = rightSize;
        build();
    }

    /**
     * 设置Icon
     */
    public void setIcon(String iconText) {
        this.iconString = iconText;
        build();
    }
    //=================================链式调用##需要最后调用build()==================================

    /**
     * 设置icon颜色
     */
    public EasyTextView iconColor(int color) {
        this.mIconColor = ColorStateList.valueOf(color);
        return this;
    }

    /**
     * 设置左文案
     */
    public EasyTextView textLeft(String textLeft) {
        this.mTextLeft = textLeft;
        return this;
    }

    /**
     * 设置右文案
     */
    public EasyTextView textRight(String textRight) {
        this.mTextRight = textRight;
        return this;
    }

    /**
     * 设置左文案颜色
     */
    public EasyTextView textLeftColor(int color) {
        this.mLeftColor = ColorStateList.valueOf(color);
        return this;
    }

    /**
     * 设置右文案颜色
     */
    public EasyTextView textRightColor(int color) {
        this.mRightColor = ColorStateList.valueOf(color);
        return this;
    }

    /**
     * 设置左文案字号大小
     */
    public EasyTextView textLeftSize(float leftSize) {
        this.mLeftSize = leftSize;
        return this;
    }

    /**
     * 设置右文案字号大小
     */
    public EasyTextView textRightSize(float rightSize) {
        this.mRightSize = rightSize;
        return this;
    }

    /**
     * 设置Icon
     */
    public EasyTextView icon(String iconText) {
        this.iconString = iconText;
        return this;
    }

    /**
     * 防止重复初始化，最后调用build
     */
    public EasyTextView build() {
        clearText();
        initIconFont();
        return this;
    }
}
