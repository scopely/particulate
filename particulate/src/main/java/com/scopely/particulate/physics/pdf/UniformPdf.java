package com.scopely.particulate.physics.pdf;

import java.util.Random;

public class UniformPdf implements Pdf {
    private final Random random = new Random();
    private final double lowerBound;
    private final double range;

    public UniformPdf(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.range = upperBound - lowerBound;
    }


    @Override
    public double random() {
        return random.nextFloat() * range + lowerBound;
    }
}
