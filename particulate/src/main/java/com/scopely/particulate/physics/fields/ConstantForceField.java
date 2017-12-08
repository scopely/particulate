package com.scopely.particulate.physics.fields;

import com.scopely.particulate.physics.Field;
import com.scopely.particulate.physics.Vector;

@SuppressWarnings("unused")
public class ConstantForceField implements Field {
    private final Vector force;

    public ConstantForceField(Vector force) {
        this.force = force;
    }

    @Override
    public void getForce(Vector v, double  t, double mass, double charge, double x, double y, double vX, double vY) {
        v.x = force.x;
        v.y = force.y;
    }
}
