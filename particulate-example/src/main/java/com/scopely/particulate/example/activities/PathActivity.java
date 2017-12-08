package com.scopely.particulate.example.activities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.SeekBar;

import com.appyvet.materialrangebar.RangeBar;
import com.scopely.particulate.drawables.ParticleDrawable;
import com.scopely.particulate.example.R;
import com.scopely.particulate.objects.emitters.PathEmitter;
import com.scopely.particulate.objects.particles.ParticleFactoryImpl;
import com.scopely.particulate.physics.pdf.UniformPdf;

import static android.graphics.Path.Direction.CW;
import static java.lang.Math.PI;

public class PathActivity extends BaseActivity {

    private PathEmitter emitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Path path = new Path();
        path.addCircle(4, 6, 3, CW);

        final Path drawPath = new Path();
        drawPath.addCircle(400, 600, 300, CW);

        final Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);

        ParticleDrawable particleDrawable = new ParticleDrawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                canvas.drawPath(drawPath, paint);
                super.draw(canvas);
            }
        }.setPixelsPerMeter(100);
        surface.setImageDrawable(particleDrawable);

        ParticleFactoryImpl factory = new ParticleFactoryImpl(getResources().getDrawable(R.drawable.ic_launcher));
        emitter = (PathEmitter) new PathEmitter(factory)
                .withPath(path)
                .withSpeedPdf(new UniformPdf(3, 10))
                .withAnglePdf(new UniformPdf(0f, (float) (PI*2)))
                .withAngularSpeedPdf(new UniformPdf(0, 10))
                .setEmissionTarget(particleDrawable);

        emitter.emit(5, -1);

        seekbar.setMax(50);
        seekbar.setProgress(5);
        rangeBar2.setRangePinsByValue(6, 20);
        rangeBar1.setRangePinsByValue(25, 25);
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
