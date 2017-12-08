package com.scopely.particulate.objects.emitters;

import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;

import com.scopely.particulate.Utils;
import com.scopely.particulate.objects.particles.Particle;
import com.scopely.particulate.objects.particles.ParticleFactory;

@SuppressWarnings({"UnusedReturnValue", "unused", "SameParameterValue"})
public class PointEmitter extends EmitterImpl {

    private double x;
    private double y;

    public PointEmitter(ParticleFactory factory) {
        super(factory);
    }

    public PointEmitter atPoint(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public PointEmitter atPoint(PointF point) {
        return atPoint(point.x, point.y);
    }

    public PointEmitter atViewCenter(View surface, View view, float ppm) {
        RectF rect = Utils.getBoundsOfView(surface, view, ppm);
        return atPoint(rect.centerX(), rect.centerY());
    }

    @Override
    protected Particle newParticle() {
        return super.newParticle().withPosition(x, y);
    }
}
