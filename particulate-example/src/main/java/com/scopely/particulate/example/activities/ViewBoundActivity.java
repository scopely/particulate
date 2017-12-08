package com.scopely.particulate.example.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.appyvet.materialrangebar.RangeBar;
import com.scopely.particulate.drawables.ParticleDrawable;
import com.scopely.particulate.example.R;
import com.scopely.particulate.objects.emitters.PathEmitter;
import com.scopely.particulate.objects.particles.ParticleFactoryImpl;
import com.scopely.particulate.physics.pdf.UniformPdf;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static java.lang.Math.PI;

public class ViewBoundActivity extends BaseActivity {

    private PathEmitter emitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Button button = new Button(this);
        button.setText(R.string.view_bound_button_text);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        root.addView(button, params);

        ParticleDrawable particleDrawable = new ParticleDrawable().setPixelsPerMeter(100);
        surface.setImageDrawable(particleDrawable);

        ParticleFactoryImpl factory = new ParticleFactoryImpl(getResources().getDrawable(R.drawable.star_pink));
        emitter = (PathEmitter) new PathEmitter(factory)
                .withSpeedPdf(new UniformPdf(3, 10))
                .withAnglePdf(new UniformPdf(0f, (float) (PI*2)))
                .withAngularSpeedPdf(new UniformPdf(0, 10))
                .setEmissionTarget(particleDrawable);


        button.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                emitter.boundView(surface, button, 100, true);
                emitter.emit(Math.max(1, seekbar.getProgress()), -1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emitter.burst(30);
            }
        });

        seekbar.setMax(50);
        seekbar.setProgress(5);
        rangeBar1.setRangePinsByValue(25, 25);
        rangeBar2.setRangePinsByValue(6, 20);
        rangeBar4.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSeekBarChanged(SeekBar seekBar, int progress) {
        emitter.emit(Math.max(1, progress), -1);
    }

    @Override
    protected void onRangeBar1Changed(RangeBar rangeBar, int i, int i2) {
        float scale = (float) (2* PI/100);
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
        float scale = 1f/100;
        emitter.withPdf(new UniformPdf(i*scale, i2*scale));
    }

    @Override
    protected void onRangeBar5Changed(RangeBar rangeBar, int i, int i2) {
        //not used
    }
}
