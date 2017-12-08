package com.scopely.particulate.physics.pdf;

import java.util.Random;

@SuppressWarnings("unused")
public class GaussianPdf implements Pdf {

    private final Random random;
    private final double lowerBound;
    private final double upperBound;

    public GaussianPdf(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.random = new Random();
    }

    @Override
    public double random() {
        double range = upperBound - lowerBound;
        return random.nextGaussian() * range + lowerBound;
    }
}
