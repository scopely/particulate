package com.scopely.particulate.objects.emitters;

import com.scopely.particulate.OnTouchListener;
import com.scopely.particulate.TouchRegisterable;
import com.scopely.particulate.objects.particles.ParticleFactory;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_OUTSIDE;
import static android.view.MotionEvent.ACTION_UP;

public class TouchPointEmitter extends PointEmitter {
    private int particlesPerSecond;
    private double ppm;

    public TouchPointEmitter(ParticleFactory factory, final double pixelsPerMeter, TouchRegisterable tR) {
        super(factory);
        this.ppm = pixelsPerMeter;
        tR.registerTouchListener(new OnTouchListener() {
            @Override
            public void onTouch(int action, double x, double y) {
                switch (action) {
                    case ACTION_DOWN:
                        atPoint(x/ppm, y/ppm);
                        emit(particlesPerSecond, -1);
                        break;
                    case ACTION_UP:
                    case ACTION_CANCEL:
                    case ACTION_OUTSIDE:
                        pauseEmitting();
                        break;
                    case ACTION_MOVE:
                        atPoint(x/ppm, y/ppm);
                        break;
                }
            }
        });
    }

    public TouchPointEmitter withParticlesPerSecond(int particlesPerSecond) {
        this.particlesPerSecond = particlesPerSecond;
        return this;
    }
}
