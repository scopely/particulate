package com.scopely.particulate.physics;

import java.util.List;

@SuppressWarnings({"UnusedReturnValue", "unused", "WeakerAccess"})
public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector fromMagDir(double magnitude, double radians) {
        return new Vector(Math.cos(radians), Math.sin(radians)).scale(magnitude);
    }

    public double magnitude() {
        return getMagnitude(x, y);
    }

    public double angle() {
        return getAngle(x, y);
    }

    public Vector add(Vector v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector add(List<Vector> vectors) {
        int size = vectors.size();
        for(int i = 0; i < size; i ++) {
            Vector v = vectors.get(i);
            x += v.x;
            y += v.y;
        }
        return this;
    }

    public Vector sub(Vector v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vector scale(double scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public double dotProd(Vector v) {
        return x*v.x + y*v.y;
    }

    public Vector normalize() {
        double magnitude = magnitude();
        if(magnitude != 0) {
            x /= magnitude;
            y /= magnitude;
        }
        return this;
    }

    public static void set(Vector v, double x, double y) {
        v.x = x;
        v.y = y;
    }

    public static void setMagDir(Vector v, double magnitude, double angle) {
        v.x = Math.cos(angle);
        v.y = Math.sin(angle);
        v.scale(magnitude);
    }

    public static double getMagnitude(double x, double y) {
        return Math.sqrt(x*x + y*y);
    }

    public static double getAngle(double x, double y) {
        return Math.atan2(y, x);
    }
}
