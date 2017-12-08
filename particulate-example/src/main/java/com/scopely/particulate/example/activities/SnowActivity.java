package com.scopely.particulate.example.activities;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.appyvet.materialrangebar.RangeBar;
import com.scopely.particulate.drawables.ParticleDrawable;
import com.scopely.particulate.example.R;
import com.scopely.particulate.objects.emitters.Emitter;
import com.scopely.particulate.objects.emitters.LineEmitter;
import com.scopely.particulate.objects.particles.ParticleFactoryImpl;
import com.scopely.particulate.physics.Vector;
import com.scopely.particulate.physics.fields.ConstantAccelerationField;
import com.scopely.particulate.physics.fields.WindField;
import com.scopely.particulate.physics.functions.VectorInterpolator;
import com.scopely.particulate.physics.pdf.ConstantPdf;
import com.scopely.particulate.physics.pdf.UniformPdf;

import static java.lang.Math.PI;

public class SnowActivity extends BaseActivity {

    private Emitter emitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParticleDrawable particleDrawable = new ParticleDrawable()
                .setPixelsPerMeter(100)
                .setMaximumParticleAge(10000)
                .setMaximumParticleCount(300);
        surface.setImageDrawable(particleDrawable);

        ParticleFactoryImpl factory = new ParticleFactoryImpl(getResources().getDrawable(R.drawable.snowflake2));

        emitter = new LineEmitter(factory)
                .betweenPoints(new PointF(-3, 0), new PointF(getWindowManager().getDefaultDisplay().getWidth() / 100 + 3, 0))
                .inFields(
                        new ConstantAccelerationField(9.8f, -PI / 2),
                        new WindField(new VectorInterpolator() {
                            @Override
                            public void interpolate(Vector v, double time) {
                                Vector.setMagDir(v, 3 * Math.sin(time), 0);
                            }
                        })
                )
                .withSpeedPdf(new ConstantPdf(0))
                .withAngularSpeedPdf(new UniformPdf(0, 10))
                .withMassPdf(new UniformPdf(0.01f, 0.10f))
                .setEmissionTarget(particleDrawable);

        emitter.emit(5, -1);

        seekbar.setMax(50);
        seekbar.setProgress(5);

        rangeBar1.setVisibility(View.GONE);
        rangeBar2.setVisibility(View.GONE);
        rangeBar3.setVisibility(View.GONE);
    }

    @Override
    protected void onSeekBarChanged(SeekBar seekBar, int progress) {
        emitter.emit(Math.max(1, progress), -1);
    }

    @Override
    protected void onRangeBar1Changed(RangeBar rangeBar, int i, int i2) {
        //not used
    }

    @Override
    protected void onRangeBar2Changed(RangeBar rangeBar, int i, int i2) {
        //not used
    }

    @Override
    protected void onRangeBar3Changed(RangeBar rangeBar, int i, int i2) {
        //not used
    }

    @Override
    protected void onRangeBar4Changed(RangeBar rangeBar, int i, int i2) {
        //not used
    }

    @Override
    protected void onRangeBar5Changed(RangeBar rangeBar, int i, int i2) {
        //not used
    }
}
