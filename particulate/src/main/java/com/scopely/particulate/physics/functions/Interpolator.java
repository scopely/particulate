package com.scopely.particulate.physics.functions;

public interface Interpolator {
    /**
     * @param time in seconds
     */
    float interpolate(double time);
}
