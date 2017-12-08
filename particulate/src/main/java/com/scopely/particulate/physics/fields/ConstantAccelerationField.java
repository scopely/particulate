package com.scopely.particulate.physics.fields;

import com.scopely.particulate.physics.Field;
import com.scopely.particulate.physics.Vector;

@SuppressWarnings("unused")
public class ConstantAccelerationField implements Field {
    private final Vector force;

    public ConstantAccelerationField(Vector force) {
        this.force = force;
    }

    public ConstantAccelerationField(double constant, double direction) {
        this(Vector.fromMagDir(constant, direction));
    }

    @Override
    public void getForce(Vector v, double  t, double mass, double charge, double x, double y, double vX, double vY) {
        v.x = force.x;
        v.y = force.y;
        v.scale(mass);
    }
}
