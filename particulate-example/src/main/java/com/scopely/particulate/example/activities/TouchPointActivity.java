package com.scopely.particulate.example.activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.appyvet.materialrangebar.RangeBar;
import com.scopely.particulate.OnTouchListener;
import com.scopely.particulate.TouchRegisterable;
import com.scopely.particulate.drawables.ParticleDrawable;
import com.scopely.particulate.example.R;
import com.scopely.particulate.objects.emitters.TouchPointEmitter;
import com.scopely.particulate.objects.particles.ParticleFactoryImpl;
import com.scopely.particulate.physics.pdf.UniformPdf;

public class TouchPointActivity extends BaseActivity implements TouchRegisterable {

    private TouchPointEmitter emitter;
    private OnTouchListener onTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParticleDrawable particleDrawable = new ParticleDrawable().setPixelsPerMeter(100);
        surface.setImageDrawable(particleDrawable);

        ParticleFactoryImpl factory = new ParticleFactoryImpl(getResources().getDrawable(R.drawable.ic_launcher));
        emitter = (TouchPointEmitter) new TouchPointEmitter(factory, 100, this)
                .withParticlesPerSecond(5)
                .withSpeedPdf(new UniformPdf(3, 10))
                .withAnglePdf(new UniformPdf(0f, (float) (Math.PI*2)))
                .withAngularSpeedPdf(new UniformPdf(0, 10))
                .setEmissionTarget(particleDrawable);

        seekbar.setMax(50);
        seekbar.setProgress(5);
        rangeBar2.setRangePinsByValue(6, 20);
    }

    @Override
    protected void onSeekBarChanged(SeekBar seekBar, int progress) {
        emitter.withParticlesPerSecond(Math.max(1, progress));
    }

    @Override
    protected void onRangeBar1Changed(RangeBar rangeBar, int i, int i2) {
        float scale = (float) (2*Math.PI/100);
        emitter.withAnglePdf(new UniformPdf(i*scale, i2*scale));
    }

    @Override
    protected void onRangeBar2Changed(RangeBar rangeBar, int i, int i2) {
        float scale = 50f/100;
        emitter.withSpeedPdf(new UniformPdf(i*scale, i2*scale));
    }

    @Override
    protected void onRangeBar3Changed(RangeBar rangeBar, int i, int i2) {
        float scale = 10f/100;
        emitter.withAngularSpeedPdf(new UniformPdf(i*scale, i2*scale));
    }

    @Override
    protected void onRangeBar4Changed(RangeBar rangeBar, int i, int i2) {
        //not used
    }

    @Override
    protected void onRangeBar5Changed(RangeBar rangeBar, int i, int i2) {
        //not used
    }

    @Override
    public void registerTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(onTouchListener != null) {
            int[] pos = new int[2];
            surface.getLocationInWindow(pos);
            onTouchListener.onTouch(ev.getActionMasked(), ev.getX() - pos[0], ev.getY() - pos[1]);
        }
        return super.dispatchTouchEvent(ev);
    }
}
