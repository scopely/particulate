package com.scopely.particulate.physics.fields;

import android.graphics.PointF;

import com.scopely.particulate.physics.Field;
import com.scopely.particulate.physics.Vector;

@SuppressWarnings("unused")
public class GravitationalField implements Field {
    private final PointF location;
    private final double G;
    private final double M;

    public GravitationalField(PointF location, double g, double m) {
        this.location = location;
        G = g;
        M = m;
    }

    @Override
    public void getForce(Vector v, double t, double m, double c, double x, double y, double vX, double vY) {
        double dx = location.x - x;
        double dy = location.y - y;
        double magnitude = G*M*m/(dx*dx + dy*dy);

        v.x = dx;
        v.y = dy;
        v.normalize().scale(magnitude);
    }
}
