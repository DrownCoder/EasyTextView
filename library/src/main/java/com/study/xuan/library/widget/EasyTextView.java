package com.study.xuan.library.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.study.xuan.library.R;
import com.study.xuan.library.span.EasyVerticalCenterSpan;
import com.study.xuan.library.span.SpanContainer;
import com.study.xuan.shapebuilder.shape.ShapeBuilder;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.drawable.GradientDrawable.RECTANGLE;

/**
 * Author : xuan.
 * Date : 2017/12/23.
 * Description :方便使用的TextView,目前支持:
 * 1.圆角和边线颜色和宽度,soild
 * 2.iconFont配合textLeft,textRight,textPadding,iconColor等
 * 3.支持不同左中右不同字号垂直居中
 * 4.支持左中上分别设置Selector，不要设置TextColor，会覆盖（一个TextView）
 * 5.支持左右text设置span
 * <p>
 * 【注意】：
 * 多次调用建议链式调用，不会重复绘制，节省性能
 * addSpan之前记得clearSpan
 * 链式调用记得：build()
 */

public class EasyTextView extends TextView {
    private static final String EMPTY_SPACE = "\u3000";
    private Context mContext;
    private int type = RECTANGLE;
    private float mRadius;
    private float mRadiusTopLeft, mRadiusTopRight, mRadiusBottomLeft, mRadiusBottomRight;
    private int mStrokeColor;
    private int mStrokeWidth;
    private int mSoild;
    private float mTextPadding;
    private CharSequence mTextLeft;
    private CharSequence mTextRight;
    private ColorStateList mIconColor = null;
    private int mCurIconColor;
    private String iconString;
    private ColorStateList mLeftColor = null;
    private int mCurLeftColor;
    private ColorStateList mRightColor = null;
    private int mCurRightColor;
    private float mLeftSize;
    private float mRightSize;
    private List<SpanContainer> leftContainer;
    private List<SpanContainer> rightContainer;
    private int mTextLeftStyle;
    private int mTextRightStyle;
    private int mTextCenterStyle;
    private TypedValue textValue;//左右文字支持xml中设置iconFont
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
        int centerSize = iconString.length();
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(getText());
        if (!TextUtils.isEmpty(mTextLeft) || !TextUtils.isEmpty(mTextRight)) {
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
            /*
             * ==============
             * 设置字和icon间距
             * ==============
             */
            if (mTextPadding != 0) {
                //设置字和icon间距
                if (!TextUtils.isEmpty(mTextLeft)) {
                    AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) mTextPadding);
                    stringBuilder.setSpan(sizeSpan, iconIndex - 1, iconIndex, Spanned
                            .SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                if (!TextUtils.isEmpty(mTextRight)) {
                    AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) mTextPadding);
                    stringBuilder.setSpan(sizeSpan, iconIndex + centerSize, iconIndex + centerSize + 1, Spanned
                            .SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            }
            /*
             * ==============
             * 设置左边文字样式
             * ==============
             */
            setLeftTextAttr(stringBuilder);
            /*
             * ==============
             * 设置右边文字样式
             * ==============
             */
            setRightTextAttr(centerSize, stringBuilder);
        }
            /*
             * ==============
             * 设置icon和字的颜色
             * ==============
             */
        if (mIconColor != null) {
            int color = mIconColor.getColorForState(getDrawableState(), 0);
            if (color != mCurIconColor) {
                mCurIconColor = color;
            }
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mCurIconColor);
            stringBuilder.setSpan(foregroundColorSpan, iconIndex, iconIndex + centerSize, Spanned
                    .SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            mCurIconColor = getCurrentTextColor();
        }
            /*
             * ==============
             * 设置icon的字的样式
             * ==============
             */
        initTextStyle(mTextCenterStyle, stringBuilder, iconIndex, iconIndex + centerSize);
            /*
             * ==============
             * 设置左右Span，记得调用前在**所有方法**前先clearSpan(),不然直接build，上一次的span任然保留着
             * ==============
             */
        if (leftContainer != null) {
            for (SpanContainer container : leftContainer) {
                for (Object o : container.spans) {
                    try {
                        stringBuilder.setSpan(o, container.start, container.end, container.flag);
                    } catch (Exception e) {
                        //please check invoke clearSpan() method first
                    }
                }
            }
        }
        if (rightContainer != null) {
            int start = mTextPadding == 0 ? iconIndex + centerSize : iconIndex + centerSize + 1;
            for (SpanContainer container : rightContainer) {
                for (Object o : container.spans) {
                    try {
                        stringBuilder.setSpan(o, start + container.start, start + container.end, container.flag);
                    } catch (Exception e) {
                        //please check invoke clearSpan() method first
                    }
                }
            }
        }

        setText(stringBuilder);
    }

    private void setRightTextAttr(int centerSize, SpannableStringBuilder stringBuilder) {
        if (!TextUtils.isEmpty(mTextRight) && mRightSize != 0) {
            int start = mTextPadding == 0 ? iconIndex + centerSize : iconIndex + centerSize + 1;
           /*
            * ==============
            * 设置右边字的粗体和斜体
            * ==============
            */
            initTextStyle(mTextRightStyle, stringBuilder, start, stringBuilder.length());
           /*
            * ==============
            * 设置右边字的颜色
            * ==============
            */
            initTextRightColor(stringBuilder, start);
           /*
            * ==============
            * 设置右边字的大小
            * ==============
            */
            initTextSize(stringBuilder, start, stringBuilder.length(), mRightSize, mCurRightColor);
        }
    }

    private void initTextRightColor(SpannableStringBuilder stringBuilder, int start) {
        if (mRightColor != null) {
            int color = mRightColor.getColorForState(getDrawableState(), 0);
            if (color != mCurRightColor) {
                mCurRightColor = color;
            }
            ForegroundColorSpan foregroundRightColor = new ForegroundColorSpan(mCurRightColor);
            stringBuilder.setSpan(foregroundRightColor, start, stringBuilder.length()
                    , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            mCurRightColor = getCurrentTextColor();
        }
    }

    private void setLeftTextAttr(SpannableStringBuilder stringBuilder) {
        if (!TextUtils.isEmpty(mTextLeft)) {
            int end = mTextPadding == 0 ? iconIndex : iconIndex - 1;
            /*
             * ==============
             * 设置左边字的粗体和斜体
             * ==============
             */
            initTextStyle(mTextLeftStyle, stringBuilder, 0, end);
            /*
             * ==============
             * 设置左边字的颜色
             * ==============
             */
            initTextLeftColor(stringBuilder, end);
            /*
             * ==============
             * 设置左边字的大小
             * ==============
             */
            initTextSize(stringBuilder, 0, end, mLeftSize, mCurLeftColor);
        }
    }

    private void initTextSize(SpannableStringBuilder stringBuilder, int start, int end, float textSize, int mCurColor) {
        if (textSize != 0) {
            CharacterStyle sizeSpan;
            final int gravity = getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
            if (gravity == Gravity.CENTER_VERTICAL) {
                sizeSpan = new EasyVerticalCenterSpan(textSize, mCurColor);
            } else {
                sizeSpan = new AbsoluteSizeSpan((int) textSize);
            }
            stringBuilder.setSpan(sizeSpan, start, end, Spanned
                    .SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void initTextLeftColor(SpannableStringBuilder stringBuilder, int end) {
        if (mLeftColor != null) {
            int color = mLeftColor.getColorForState(getDrawableState(), 0);
            if (color != mCurLeftColor) {
                mCurLeftColor = color;
            }
            ForegroundColorSpan foregroundLeftColor = new ForegroundColorSpan(mCurLeftColor);
            stringBuilder.setSpan(foregroundLeftColor, 0, end, Spanned
                    .SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            mCurLeftColor = getCurrentTextColor();
        }
    }

    private void initTextStyle(int textStyle, SpannableStringBuilder stringBuilder, int start, int end) {
        StyleSpan span;
        if (textStyle != Typeface.NORMAL) {
            span = new StyleSpan(textStyle);
            stringBuilder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
            ShapeBuilder.create().Type(type).Radius(mRadius).Soild(mSoild).Stroke
                    (mStrokeWidth, mStrokeColor).build(this);
        } else {
            ShapeBuilder.create().Type(type).Radius(mRadiusTopLeft,
                    mRadiusTopRight, mRadiusBottomLeft, mRadiusBottomRight).Soild(mSoild).Stroke
                    (mStrokeWidth, mStrokeColor).build(this);
        }
    }

    private void clearText() {
        setText(iconString);
        iconIndex = 0;
    }

    private void initAttr(Context context, AttributeSet attrs) {
        textValue = new TypedValue();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EasyTextView);
        type = array.getInteger(R.styleable.EasyTextView_shapeType, 0);
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
        boolean has = array.getValue(R.styleable.EasyTextView_textLeft, textValue);
        if (has) {
            if (textValue.type == TypedValue.TYPE_REFERENCE) {
                //文字引用
                mTextLeft = mContext.getResources().getText(textValue.resourceId);
            } else {
                //纯文字
                mTextLeft = textValue.string;
            }
        }
        has = array.getValue(R.styleable.EasyTextView_textRight, textValue);
        if (has) {
            if (textValue.type == TypedValue.TYPE_REFERENCE) {
                //文字引用
                mTextRight = mContext.getResources().getText(textValue.resourceId);
            } else {
                //纯文字
                mTextRight = textValue.string;
            }
        }
        //mTextLeft = array.getString(R.styleable.EasyTextView_textLeft);
        //mTextRight = array.getString(R.styleable.EasyTextView_textRight);
        mIconColor = array.getColorStateList(R.styleable.EasyTextView_iconColor);
        mLeftColor = array.getColorStateList(R.styleable.EasyTextView_textLeftColor);
        mRightColor = array.getColorStateList(R.styleable.EasyTextView_textRightColor);
        mLeftSize = array.getDimensionPixelSize(R.styleable.EasyTextView_textLeftSize, 0);
        mRightSize = array.getDimensionPixelSize(R.styleable.EasyTextView_textRightSize, 0);
        mTextLeftStyle = array.getInt(R.styleable.EasyTextView_textLeftStyle, Typeface.NORMAL);
        mTextRightStyle = array.getInt(R.styleable.EasyTextView_textRightStyle, Typeface.NORMAL);
        mTextCenterStyle = array.getInt(R.styleable.EasyTextView_textCenterStyle, Typeface.NORMAL);
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
     * 设置Shape Type
     */
    public void setType(int type) {
        this.type = type;
        setShape();
    }

    /**
     * 设置边线的宽度
     */
    public void setStrokeWidth(int value) {
        this.mStrokeWidth = value;
        setShape();
    }

    /**
     * 设置边线的颜色
     */
    public void setStrokeColor(@ColorInt int color) {
        this.mStrokeColor = color;
        setShape();
    }

    /**
     * 设置shape背景颜色
     */
    public void setSolid(int soild) {
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
    public void setTextLeft(CharSequence textLeft) {
        this.mTextLeft = textLeft;
        build();
    }

    /**
     * 设置右文案
     */
    public void setTextRight(CharSequence textRight) {
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


    /**
     * span之前需要首先clear
     */
    public void clearSpan() {
        if (leftContainer != null) {
            leftContainer.clear();
        }
        if (rightContainer != null) {
            rightContainer.clear();
        }
    }

    /**
     * 设置左边文字为多个span
     */
    public void addSpanLeft(List<Object> objects, int start, int end, int flags) {
        spanLeft(objects, start, end, flags);
        build();
    }

    /**
     * 设置左边文字为span
     */
    public void addSpanLeft(Object object, int start, int end, int flags) {
        spanLeft(object, start, end, flags);
        build();
    }

    /**
     * 设置右边文字为多个span
     */
    public void addSpanRight(List<Object> objects, int start, int end, int flags) {
        spanRight(objects, start, end, flags);
        build();
    }

    /**
     * 设置右边文字为span
     */
    public void addSpanRight(Object object, int start, int end, int flags) {
        spanRight(object, start, end, flags);
        build();
    }
    //=================================链式调用##需要最后调用build()==================================

    /**
     * 设置Shape type
     */
    public EasyTextView type(int type) {
        this.type = type;
        return this;
    }

    /**
     * 设置边线的宽度
     */
    public EasyTextView strokeWidth(int width) {
        this.mStrokeWidth = width;
        return this;
    }

    /**
     * 设置边线的宽度
     */
    public EasyTextView strokeColor(@ColorInt int color) {
        this.mStrokeColor = color;
        return this;
    }

    /**
     * 设置填充的颜色
     */
    public EasyTextView solid(@ColorInt int color) {
        this.mSoild = color;
        return this;
    }


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
     * 设置右边文字为多个span
     */
    public EasyTextView spanRight(List<Object> objects, int start, int end, int flags) {
        if (rightContainer == null) {
            rightContainer = new ArrayList<>();
        }
        this.rightContainer.add(new SpanContainer(objects, start, end, flags));
        return this;
    }

    /**
     * 设置右边文字为span
     */
    public EasyTextView spanRight(Object object, int start, int end, int flags) {
        if (rightContainer == null) {
            rightContainer = new ArrayList<>();
        }
        this.rightContainer.add(new SpanContainer(object, start, end, flags));
        return this;
    }

    /**
     * 设置左边文字为多个span
     */
    public EasyTextView spanLeft(List<Object> objects, int start, int end, int flags) {
        if (leftContainer == null) {
            leftContainer = new ArrayList<>();
        }
        this.leftContainer.add(new SpanContainer(objects, start, end, flags));
        return this;
    }

    /**
     * 设置左边文字为span
     */
    public EasyTextView spanLeft(Object object, int start, int end, int flags) {
        if (leftContainer == null) {
            leftContainer = new ArrayList<>();
        }
        this.leftContainer.add(new SpanContainer(object, start, end, flags));
        return this;
    }

    /**
     * 防止重复初始化，最后调用build
     */
    public EasyTextView build() {
        clearText();
        //initIconFont();
        init();
        return this;
    }
}
