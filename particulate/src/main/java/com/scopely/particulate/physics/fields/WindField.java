package com.scopely.particulate.physics.fields;

import com.scopely.particulate.physics.Vector;
import com.scopely.particulate.physics.functions.VectorInterpolator;

public class WindField extends AirResistance {

    private final VectorInterpolator windVelocityInterpolator;
    private final Vector windVelocity = new Vector(0,0);

    public WindField(VectorInterpolator windVelocityInterpolator) {
        super(0.05f);
        this.windVelocityInterpolator = windVelocityInterpolator;
    }

    @Override
    public void getForce(Vector v, double  t, double mass, double charge, double x, double y, double vX, double vY) {
        windVelocityInterpolator.interpolate(windVelocity, t);
        double relX = vX - windVelocity.x;
        double relY = vY - windVelocity.y;
        super.getForce(v, t, mass, charge, x, y, relX, relY);
    }
}
