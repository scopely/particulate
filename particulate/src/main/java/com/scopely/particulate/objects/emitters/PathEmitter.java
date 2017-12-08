package com.scopely.particulate.objects.emitters;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.view.View;

import com.scopely.particulate.Utils;
import com.scopely.particulate.objects.particles.Particle;
import com.scopely.particulate.objects.particles.ParticleFactory;
import com.scopely.particulate.physics.Vector;
import com.scopely.particulate.physics.pdf.Pdf;
import com.scopely.particulate.physics.pdf.UniformPdf;

import static android.graphics.Path.Direction.CW;

@SuppressWarnings({"UnusedReturnValue", "SameParameterValue"})
public class PathEmitter extends EmitterImpl {

    private Path path;
    private Pdf pdf;
    private PathMeasure pm;

    private final float[] pos = new float[2];
    private final float[] tan = new float[2];
    private final Vector tangent = new Vector(0,0);

    public PathEmitter(ParticleFactory factory) {
        super(factory);
    }

    public PathEmitter withPath(Path path) {
        this.path = path;
        this.pm = new PathMeasure(path, false);
        return this;
    }

    public PathEmitter boundView(View surface, View view, float ppm, boolean roundCorners) {
        Path path = new Path();
        RectF rect = Utils.getBoundsOfView(surface, view, ppm);
        float radius = roundCorners ? Math.min(rect.width(), rect.height()) * 0.5f : 0;
        path.addRoundRect(rect, radius, radius, CW);
        return withPath(path);
    }

    public PathEmitter withPdf(Pdf pdf) {
        this.pdf = pdf;
        return this;
    }

    @Override
    protected Particle newParticle() {
        if(pdf == null) pdf = new UniformPdf(0, 1);
        Particle particle = super.newParticle();
        double fraction = pdf.random();
        pm.getPosTan((float) (fraction*pm.getLength()), pos, tan);
        Vector.set(tangent, tan[0], -tan[1]);
        Vector velocity = particle.getVelocity();
        return particle
                .withPosition(pos[0], pos[1])
                .withVelocity(velocity.magnitude(), velocity.angle() + tangent.angle());
    }

    @Override
    protected boolean configuredToEmit() {
        return path != null && pm != null;
    }
}
