/*
 * Copyright (C) 2016 Recruit Marketing Partners Co.,Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.recruit_mp.android.circleprogressview;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class CircleProgressView extends View {
    private static final int BEGINNING_ANGLE = -90;

    @IntRange(from = 0)
    private int mForegroundStrokeWidthPx = 12;

    @IntRange(from = 0)
    private int mBackgroundStrokeWidthPx = 12;

    @FloatRange(from = 0, to = 1)
    private float mProgress = 0;

    private int mForegroundColor = Color.DKGRAY;

    private int mBackgroundColor = Color.GRAY;

    private int mCircleBackgroundColor = Color.TRANSPARENT;

    private final Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint mForegroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint mCircleBackgroundPaint = new Paint();

    private RectF mRectF = new RectF();

    public CircleProgressView(Context context) {
        super(context);
        init(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
            try {
                if (typedArray.hasValue(R.styleable.CircleProgressView_CircleProgressView_stroke)) {
                    int stroke = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_CircleProgressView_stroke, mForegroundStrokeWidthPx);
                    mForegroundStrokeWidthPx = mBackgroundStrokeWidthPx = (stroke >= 0) ? stroke : 0;
                }

                if (typedArray.hasValue(R.styleable.CircleProgressView_CircleProgressView_foregroundStroke)) {
                    int stroke = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_CircleProgressView_foregroundStroke, mForegroundStrokeWidthPx);
                    mForegroundStrokeWidthPx = (stroke >= 0) ? stroke : 0;
                }

                if (typedArray.hasValue(R.styleable.CircleProgressView_CircleProgressView_backgroundStroke)) {
                    int stroke = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_CircleProgressView_backgroundStroke, mBackgroundStrokeWidthPx);
                    mBackgroundStrokeWidthPx = (stroke >= 0) ? stroke : 0;
                }

                mProgress = typedArray.getFloat(R.styleable.CircleProgressView_CircleProgressView_progress, mProgress);
                if (mProgress < 0) {
                    mProgress = 0;
                } else if (mProgress > 1) {
                    mProgress = 1;
                }

                mForegroundColor = typedArray.getColor(R.styleable.CircleProgressView_CircleProgressView_foregroundColor, mForegroundColor);

                mBackgroundColor = typedArray.getColor(R.styleable.CircleProgressView_CircleProgressView_backgroundColor, mBackgroundColor);

                mCircleBackgroundColor = typedArray.getColor(R.styleable.CircleProgressView_CircleProgressView_circleBackgroundColor, mCircleBackgroundColor);
            } finally {
                typedArray.recycle();
            }
        }

        // Foreground Paint
        mForegroundPaint.setStyle(Paint.Style.STROKE);
        mForegroundPaint.setStrokeWidth(mForegroundStrokeWidthPx);
        mForegroundPaint.setColor(mForegroundColor);

        // Background Paint
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(mBackgroundStrokeWidthPx);
        mBackgroundPaint.setColor(mBackgroundColor);

        // Circle background Paint
        mCircleBackgroundPaint.setStyle(Paint.Style.FILL);
        mCircleBackgroundPaint.setColor(mCircleBackgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mRectF.centerX(), mRectF.centerY(), mRectF.width() / 2, mCircleBackgroundPaint);
        canvas.drawOval(mRectF, mBackgroundPaint);
        float angle = 360 * mProgress;
        canvas.drawArc(mRectF, BEGINNING_ANGLE, angle, false, mForegroundPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int min = Math.min(widthSize, heightSize);
        final int stroke = Math.max(mForegroundStrokeWidthPx, mBackgroundStrokeWidthPx);
        mRectF.set(stroke / 2, stroke / 2, min - stroke / 2, min - stroke / 2);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Deprecated
    @IntRange(from = 0)
    public int getStrokeWidthPx() {
        return mForegroundStrokeWidthPx;
    }

    public void setStrokeWidthPx(@IntRange(from = 0) int stroke) {
        setForegroundStrokeWidthPx(stroke);
        setBackgroundStrokeWidthPx(stroke);
    }

    @IntRange(from = 0)
    public int getForegroundStrokeWidthPx() {
        return mForegroundStrokeWidthPx;
    }

    public void setForegroundStrokeWidthPx(@IntRange(from = 0) int stroke) {
        mForegroundStrokeWidthPx = (stroke > 0) ? stroke : 0;
        mForegroundPaint.setStrokeWidth(mForegroundStrokeWidthPx);
        invalidate();
        requestLayout();
    }

    @IntRange(from = 0)
    public int getBackgroundStrokeWidthPx() {
        return mBackgroundStrokeWidthPx;
    }

    public void setBackgroundStrokeWidthPx(@IntRange(from = 0) int stroke) {
        mBackgroundStrokeWidthPx = (stroke > 0) ? stroke : 0;
        mBackgroundPaint.setStrokeWidth(mBackgroundStrokeWidthPx);
        invalidate();
        requestLayout();
    }

    @FloatRange(from = 0, to = 1)
    public float getProgress() {
        return mProgress;
    }

    public void setProgress(@FloatRange(from = 0, to = 1) float progress) {
        if (progress < 0) {
            mProgress = 0;
        } else if (progress > 1) {
            mProgress = 1;
        } else {
            mProgress = progress;
        }

        invalidate();
    }

    public int getForegroundColor() {
        return mForegroundColor;
    }

    public void setForegroundColor(int color) {
        mForegroundColor = color;
        mForegroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        mBackgroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public int getCircleBackgroundColor() {
        return mCircleBackgroundColor;
    }

    public void setCircleBackgroundColor(int color) {
        mCircleBackgroundColor = color;
        mCircleBackgroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public void setProgressAnimation(@FloatRange(from = 0, to = 1) float progress, long duration) {
        setProgressAnimation(progress, duration, new DecelerateInterpolator());
    }

    public void setProgressAnimation(@FloatRange(from = 0, to = 1) float progress, long duration, TimeInterpolator interpolator) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > 1) {
            progress = 1;
        }
        if (duration < 0) {
            duration = 0;
        }

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(interpolator);
        objectAnimator.start();
    }
}
