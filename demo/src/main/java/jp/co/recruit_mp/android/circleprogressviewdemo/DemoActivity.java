package jp.co.recruit_mp.android.circleprogressviewdemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import jp.co.recruit_mp.android.circleprogressview.CircleProgressView;

public class DemoActivity extends AppCompatActivity {

    private CircleProgressView mCircleProgressView;

    private int mColorMode = 0; // 0 or 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mCircleProgressView = (CircleProgressView) findViewById(R.id.circleprogressview);
        final SeekBar progressSeekBar = (SeekBar) findViewById(R.id.seekBar_progress);
        final TextView progressTextView = (TextView) findViewById(R.id.textview_progress);
        final SeekBar strokeSeekBar = (SeekBar) findViewById(R.id.seekBar_stroke);
        final TextView strokeTextView = (TextView) findViewById(R.id.textview_stroke);

        // Progress SeekBar
        progressSeekBar.setProgress((int) (mCircleProgressView.getProgress() * 100));
        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCircleProgressView.setProgress(progress / 100f);
                progressTextView.setText(String.format("%.2f", mCircleProgressView.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        progressTextView.setText(String.format("%.2f", mCircleProgressView.getProgress()));

        // Stroke SeekBar
        strokeSeekBar.setProgress((int) (dpFromPx(this, mCircleProgressView.getStrokeWidthPx()) * 100));
        strokeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float strokeWidthDp = progress / 100f;
                mCircleProgressView.setStrokeWidthPx((int)pxFromDp(DemoActivity.this, strokeWidthDp));
                strokeTextView.setText(String.format("%.2f", strokeWidthDp));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        strokeTextView.setText(String.format("%.2f", dpFromPx(this, mCircleProgressView.getStrokeWidthPx())));

        // Animate Demo
        Button animation1Button = (Button) findViewById(R.id.button_animation1);
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
        Button color1Button = (Button) findViewById(R.id.button_color1);
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
                        mCircleProgressView.setForegroundColor(getResources().getColor(R.color.demo_orange));
                        mCircleProgressView.setBackgroundColor(getResources().getColor(R.color.demo_brown));
                        break;
                    case 1:
                    default:
                        mCircleProgressView.setForegroundColor(getResources().getColor(R.color.demo_magenta));
                        mCircleProgressView.setBackgroundColor(getResources().getColor(R.color.demo_extra_light_gray));
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
