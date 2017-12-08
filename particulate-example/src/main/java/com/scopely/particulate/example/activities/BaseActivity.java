package com.scopely.particulate.example.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.appyvet.materialrangebar.RangeBar;
import com.scopely.particulate.example.R;

public abstract class BaseActivity extends Activity {
    protected RangeBar rangeBar1;
    protected RangeBar rangeBar2;
    protected RangeBar rangeBar3;
    protected RangeBar rangeBar4;
    protected RangeBar rangeBar5;
    protected SeekBar seekbar;

    protected ImageView surface;
    protected RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        surface = findViewById(R.id.surface);
        root = findViewById(R.id.root);
        rangeBar1 = findViewById(R.id.rangebar1);
        rangeBar2 = findViewById(R.id.rangebar2);
        rangeBar3 = findViewById(R.id.rangebar3);
        rangeBar4 = findViewById(R.id.rangebar4);
        rangeBar5 = findViewById(R.id.rangebar5);
        seekbar = findViewById(R.id.seekbar);

        rangeBar1.setOnRangeBarChangeListener(new CorrectedRangeBarChangeListener() {
            @Override
            public void onRangeChange(RangeBar rangeBar, int lower, int upper) {
                onRangeBar1Changed(rangeBar, lower, upper);
            }
        });
        rangeBar2.setOnRangeBarChangeListener(new CorrectedRangeBarChangeListener() {
            @Override
            public void onRangeChange(RangeBar rangeBar, int lower, int upper) {
                onRangeBar2Changed(rangeBar, lower, upper);
            }
        });
        rangeBar3.setOnRangeBarChangeListener(new CorrectedRangeBarChangeListener() {
            @Override
            public void onRangeChange(RangeBar rangeBar, int lower, int upper) {
                onRangeBar3Changed(rangeBar, lower, upper);
            }
        });
        rangeBar4.setOnRangeBarChangeListener(new CorrectedRangeBarChangeListener() {
            @Override
            public void onRangeChange(RangeBar rangeBar, int lower, int upper) {
                onRangeBar4Changed(rangeBar, lower, upper);
            }
        });
        rangeBar5.setOnRangeBarChangeListener(new CorrectedRangeBarChangeListener() {
            @Override
            public void onRangeChange(RangeBar rangeBar, int lower, int upper) {
                onRangeBar5Changed(rangeBar, lower, upper);
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onSeekBarChanged(seekBar, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    protected abstract void onSeekBarChanged(SeekBar seekBar, int progress);

    protected abstract void onRangeBar1Changed(RangeBar rangeBar, int i, int i2);

    protected abstract void onRangeBar2Changed(RangeBar rangeBar, int i, int i2);

    protected abstract void onRangeBar3Changed(RangeBar rangeBar, int i, int i2);

    protected abstract void onRangeBar4Changed(RangeBar rangeBar, int i, int i2);

    protected abstract void onRangeBar5Changed(RangeBar rangeBar, int i, int i2);

    private static abstract  class CorrectedRangeBarChangeListener implements RangeBar.OnRangeBarChangeListener {

        @Override
        public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
            double v = rangeBar.getTickInterval() * leftPinIndex + rangeBar.getTickStart();
            double v1 = rangeBar.getTickInterval() * rightPinIndex + rangeBar.getTickStart();
            onRangeChange(rangeBar, (int) v, (int) v1);
        }

        public abstract void onRangeChange(RangeBar rangeBar, int lower, int upper);
    }
}
