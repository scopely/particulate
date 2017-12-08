package com.scopely.particulate.physics.fields;

import com.scopely.particulate.physics.Field;
import com.scopely.particulate.physics.Vector;

@SuppressWarnings("WeakerAccess")
public class AirResistance implements Field {

    private final double C;

    public AirResistance(double airConstant) {
        this.C = airConstant;
    }

    @Override
    public void getForce(Vector v, double  t, double mass, double charge, double x, double y, double vX, double vY) {
        double speedSquared = vX*vX + vY*vY;
        double angle = Vector.getAngle(vX, vY);
        Vector.setMagDir(v, C*speedSquared, angle + Math.PI);
    }
}
