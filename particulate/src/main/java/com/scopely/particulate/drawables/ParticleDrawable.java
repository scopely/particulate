package com.scopely.particulate.drawables;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.scopely.particulate.Utils;
import com.scopely.particulate.objects.emitters.EmissionTarget;
import com.scopely.particulate.objects.particles.Particle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ParticleDrawable extends AnimDrawable implements EmissionTarget {
    private final List<Particle> particles = Collections.synchronizedList(new ArrayList<Particle>());
    private final long t0 = Utils.milliTime();
    private int maximumParticleCount = 200;
    private long maximumAge = SECONDS.toMillis(3);
    private float pixelsPerMeter = 1;

    @Override
    public void draw(@NonNull Canvas canvas) {
        synchronized (particles) {
            double t = Utils.toFloatSeconds(Utils.milliTime() - t0);

            for (int i = 0; i < particles.size(); i++) {
                Particle particle = particles.get(i);
                if(particle.getAge() > maximumAge) {
                    particles.remove(i);
                    particle.recycle();
                    i--;
                } else {
                    particle.updatePhysics(t, pixelsPerMeter);
                    particle.onDraw(canvas);
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public int getMaximumParticleCount() {
        return maximumParticleCount;
    }

    public ParticleDrawable setMaximumParticleCount(int maximumParticleCount) {
        this.maximumParticleCount = maximumParticleCount;
        return this;
    }

    @SuppressWarnings("unused")
    public float getPixelsPerMeter() {
        return pixelsPerMeter;
    }

    @SuppressWarnings("SameParameterValue")
    public ParticleDrawable setPixelsPerMeter(float pixelsPerMeter) {
        this.pixelsPerMeter = pixelsPerMeter;
        return this;
    }

    @Override
    public void onEmission(Particle... particles) {
        this.particles.addAll(0, Arrays.asList(particles));
        for(int i = this.particles.size(); i > maximumParticleCount; i--) {
            this.particles.get(i).recycle();
            this.particles.remove(i);
        }
    }

    @Override
    public void onEmission(Particle particle) {
        this.particles.add(0, particle);
        for(int i = this.particles.size(); i > maximumParticleCount; i--) {
            this.particles.get(i - 1).recycle();
            this.particles.remove(i - 1);
        }
    }

    @SuppressWarnings("SameParameterValue")
    public ParticleDrawable setMaximumParticleAge(int maximumParticleAge) {
        this.maximumAge = maximumParticleAge;
        return this;
    }
}
