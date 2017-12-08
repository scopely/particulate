package com.scopely.particulate.objects.emitters;

import com.scopely.particulate.objects.particles.Particle;

public interface EmissionTarget {
    void onEmission(Particle... particles);
    void onEmission(Particle particle);
}
