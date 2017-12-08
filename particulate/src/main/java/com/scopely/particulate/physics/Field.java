package com.scopely.particulate.physics;

public interface Field {
    /**
     *
     * @param v the force vector to modify in the method. Passed in instead of being a return value for performance reasons. After the method executes this vector should reflect the force, in newtons, acting on the referenced particle
     * @param t the time, in seconds, relative to a global start date
     * @param mass the mass of the particle
     * @param charge the charge of the particle
     * @param x the x coordinate of the particle
     * @param y the y coordinate of the particle
     * @param vX the X component of the particle's velocity
     * @param vY the Y component of the particle's velocity
     */
    void getForce(Vector v, double t, double mass, double charge, double x, double y, double vX, double vY);
}
