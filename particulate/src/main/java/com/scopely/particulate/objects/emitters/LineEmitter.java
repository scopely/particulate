package com.scopely.particulate.objects.emitters;

import android.graphics.Path;
import android.graphics.PointF;

import com.scopely.particulate.objects.particles.ParticleFactory;

public class LineEmitter extends PathEmitter {
    public LineEmitter(ParticleFactory factory) {
        super(factory);
    }

    public LineEmitter betweenPoints(PointF start, PointF end) {
        Path path = new Path();
        path.moveTo(start.x, start.y);
        path.lineTo(end.x, end.y);
        return withPath(path);
    }

    @Override
    public LineEmitter withPath(Path path) {
        super.withPath(path);
        return this;
    }
}
