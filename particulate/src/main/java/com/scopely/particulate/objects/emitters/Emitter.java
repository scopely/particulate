package com.scopely.particulate.objects.emitters;

@SuppressWarnings("SameParameterValue")
public interface Emitter {
    void burst(int particles);

    void emit(int particlesPerSecond, int duration);

    void pauseEmitting();

    Emitter setEmissionTarget(EmissionTarget emissionTarget);
}
