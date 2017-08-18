package jp.co.recruit_mp.android.circleprogressviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

import jp.co.recruit_mp.android.circleprogressview.CircleProgressView;

public class DemoActivity extends AppCompatActivity {

    private CircleProgressView mCircleProgressView;

    private int mColorMode = 0; // 0 or 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mCircleProgressView = findViewById(R.id.circleprogressview);
        final SeekBar progressSeekBar = findViewById(R.id.seekBar_progress);
        final TextView progressTextView = findViewById(R.id.textview_progress);
        final SeekBar foregroundStrokeSeekBar = findViewById(R.id.seekBar_foreground_stroke);
        final TextView foregroundStrokeTextView = findViewById(R.id.textview_foreground_stroke);
        final SeekBar backgroundStrokeSeekBar = findViewById(R.id.seekBar_background_stroke);
        final TextView backgroundStrokeTextView = findViewById(R.id.textview_background_stroke);

        // Progress SeekBar
        progressSeekBar.setProgress((int) (mCircleProgressView.getProgress() * 100));
        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCircleProgressView.setProgress(progress / 100f);
                progressTextView.setText(String.format(Locale.US, "%.2f", mCircleProgressView.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        progressTextView.setText(String.format(Locale.US, "%.2f", mCircleProgressView.getProgress()));

        // Foreground Stroke SeekBar
        foregroundStrokeSeekBar.setProgress((int) (dpFromPx(this, mCircleProgressView.getForegroundStrokeWidthPx()) * 100));
        foregroundStrokeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float strokeWidthDp = progress / 100f;
                mCircleProgressView.setForegroundStrokeWidthPx((int)pxFromDp(DemoActivity.this, strokeWidthDp));
                foregroundStrokeTextView.setText(String.format(Locale.US, "%.2f", strokeWidthDp));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        foregroundStrokeTextView.setText(String.format(Locale.US, "%.2f", dpFromPx(this, mCircleProgressView.getForegroundStrokeWidthPx())));

        // Background Stroke SeekBar
        backgroundStrokeSeekBar.setProgress((int) (dpFromPx(this, mCircleProgressView.getBackgroundStrokeWidthPx()) * 100));
        backgroundStrokeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float strokeWidthDp = progress / 100f;
                mCircleProgressView.setBackgroundStrokeWidthPx((int)pxFromDp(DemoActivity.this, strokeWidthDp));
                backgroundStrokeTextView.setText(String.format(Locale.US, "%.2f", strokeWidthDp));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        backgroundStrokeTextView.setText(String.format(Locale.US, "%.2f", dpFromPx(this, mCircleProgressView.getBackgroundStrokeWidthPx())));

        // Animate Demo
        Button animation1Button = findViewById(R.id.button_animation1);
        animation1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if progress is 0, to 1.
                if (mCircleProgressView.getProgress() <= 0) {
                    mCircleProgressView.setProgressAnimation(1, 1000);
                }
                // to 0.
                else {
                    mCircleProgressView.setProgressAnimation(0, 1000);
                }
            }
        });
        // Change color demo
        Button color1Button = findViewById(R.id.button_color1);
        color1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mColorMode == 0) {
                    mColorMode = 1;
                } else {
                    mColorMode = 0;
                }

                switch (mColorMode) {
                    case 0:
                        mCircleProgressView.setForegroundColor(ResourcesCompat.getColor(getResources(), R.color.demo_orange, null));
                        mCircleProgressView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.demo_brown, null));
                        break;
                    case 1:
                    default:
                        mCircleProgressView.setForegroundColor(ResourcesCompat.getColor(getResources(), R.color.demo_magenta, null));
                        mCircleProgressView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.demo_extra_light_gray, null));
                        break;
                }
            }
        });
    }

    private static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    private static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
