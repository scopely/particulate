package com.scopely.particulate.physics.fields;

import com.scopely.particulate.physics.Field;
import com.scopely.particulate.physics.Vector;

/**
 * Part of the Scopely™ Platform
 * © 2016 Scopely, Inc.
 */
@SuppressWarnings("unused")
public class Friction implements Field {

    private final double friction;

    public Friction(double friction) {
        this.friction = friction;
    }

    @Override
    public void getForce(Vector v, double t, double mass, double charge, double x, double y, double vX, double vY) {
        v.x = -vX;
        v.y = -vY;
        v.normalize().scale(friction * mass * 9.8);
    }
}
