package com.scopely.particulate.physics.pdf;

public class ConstantPdf implements Pdf {
    private final double value;

    public ConstantPdf(double value) {
        this.value = value;
    }

    @Override
    public double random() {
        return value;
    }
}
