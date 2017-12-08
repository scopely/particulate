package com.scopely.particulate.physics.functions;

import com.scopely.particulate.physics.Vector;

public interface VectorInterpolator {
    /**
     * @param v the vector to be mutated
     * @param time in seconds
     */
    void interpolate(Vector v, double  time);
}
