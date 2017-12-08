package com.scopely.particulate.objects.emitters;

import android.support.annotation.Nullable;

import com.scopely.particulate.objects.particles.Particle;
import com.scopely.particulate.objects.particles.ParticleFactory;
import com.scopely.particulate.physics.Field;
import com.scopely.particulate.physics.functions.Interpolator;
import com.scopely.particulate.physics.pdf.ConstantPdf;
import com.scopely.particulate.physics.pdf.Pdf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.scopely.particulate.objects.emitters.Defaults.ANGLE;
import static com.scopely.particulate.objects.emitters.Defaults.ANGULAR_SPEED;
import static com.scopely.particulate.objects.emitters.Defaults.CHARGE;
import static com.scopely.particulate.objects.emitters.Defaults.MASS;
import static com.scopely.particulate.objects.emitters.Defaults.SPEED;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class EmitterImpl implements Emitter {

    private Pdf speedPdf;
    private Pdf anglePdf;
    private Pdf angularSpeedPdf;
    private Pdf massPdf;
    private Pdf chargePdf;
    private Interpolator scaleXInterpolator;
    private Interpolator scaleYInterpolator;
    private Interpolator alphaInterpolator;
    private List<Field> fields = new ArrayList<>();

    private EmissionTarget emissionTarget;
    @Nullable
    private Timer timer;
    @Nullable
    private TimerTask timerTask;
    @Nullable
    private TimerTask pauseTask;
    private final ParticleFactory factory;

    public EmitterImpl(ParticleFactory factory) {
        this.factory = factory;
    }

    private void emit(int count) {
        if(emissionTarget != null) {
            if(count > 1) {
                Particle[] particles = new Particle[count];
                for (int i = 0; i < count; i++) {
                    particles[i] = newParticle();
                }
                emissionTarget.onEmission(particles);
            } else {
                emissionTarget.onEmission(newParticle());
            }
        }
    }

    public EmitterImpl withSpeedPdf(Pdf pdf) {
        this.speedPdf = pdf;
        return this;
    }

    public EmitterImpl withAnglePdf(Pdf pdf) {
        this.anglePdf = pdf;
        return this;
    }

    public EmitterImpl withAngularSpeedPdf(Pdf pdf) {
        this.angularSpeedPdf = pdf;
        return this;
    }

    public EmitterImpl withMassPdf(Pdf pdf) {
        this.massPdf = pdf;
        return this;
    }

    public EmitterImpl withChargePdf(Pdf pdf) {
        this.chargePdf = pdf;
        return this;
    }

    public EmitterImpl withScaleXInterpolator(Interpolator interpolator) {
        this.scaleXInterpolator = interpolator;
        return this;
    }

    public EmitterImpl withScaleYInterpolator(Interpolator interpolator) {
        this.scaleYInterpolator = interpolator;
        return this;
    }

    public EmitterImpl withScaleInterpolator(Interpolator interpolator) {
        this.scaleXInterpolator = interpolator;
        this.scaleYInterpolator = interpolator;
        return this;
    }

    public EmitterImpl withAlphaInterpolator(Interpolator interpolator) {
        this.alphaInterpolator = interpolator;
        return this;
    }

    public EmitterImpl inFields(Field... fields) {
        Collections.addAll(this.fields, fields);
        return this;
    }

    public EmitterImpl inFields(List<Field> fields) {
        this.fields.addAll(fields);
        return this;
    }

    @Override
    public void burst(int particles) {
        emit(particles);
    }

    @Override
    public void emit(int particlesPerSecond, int duration) {
        if(!configuredToEmit()) return;
        pauseEmitting();
        final int gap;
        final int particlesPerEmission;
        if(particlesPerSecond <= 1000) {
            gap = 1000/particlesPerSecond;
            particlesPerEmission = 1;
        } else {
            //Jesus christ why are you here, how many particles do you need?
            int numerator = 10;
            int denominator = particlesPerSecond/100;
            if(numerator % 2 == 0 && denominator % 2 == 0) {
                numerator = numerator/2;
                denominator = denominator/2;
            }
            if(numerator % 5 == 0 && denominator % 5 == 0) {
                numerator = numerator/5;
                denominator = denominator/5;
            }
            gap = numerator;
            particlesPerEmission = denominator;
        }
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                emit(particlesPerEmission);
            }
        };
        timer.schedule(timerTask, 0, gap);
        if(duration > 0){
            pauseTask = new TimerTask() {
                @Override
                public void run() {
                    pauseEmitting();
                }
            };
            timer.schedule(pauseTask, 0, duration);
        }
    }

    protected boolean configuredToEmit() {
        return true;
    }

    @Override
    public void pauseEmitting() {
        if(timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if(timerTask != null){
            timerTask.cancel();
            timerTask = null;
        }
        if(pauseTask != null) {
            pauseTask.cancel();
            pauseTask = null;
        }
    }

    protected Particle newParticle() {
        if(speedPdf == null) speedPdf = new ConstantPdf(SPEED);
        if(anglePdf == null) anglePdf = new ConstantPdf(ANGLE);
        if(angularSpeedPdf == null) angularSpeedPdf = new ConstantPdf(ANGULAR_SPEED);
        if(massPdf == null) massPdf = new ConstantPdf(MASS);
        if(chargePdf == null) chargePdf = new ConstantPdf(CHARGE);
        return factory.newParticle()
                .withScaleXInterpolator(scaleXInterpolator)
                .withScaleYInterpolator(scaleYInterpolator)
                .withAlphaInterpolator(alphaInterpolator)
                .withMass(massPdf.random())
                .withCharge(chargePdf.random())
                .withVelocity(speedPdf.random(), anglePdf.random())
                .inFields(fields)
                .withAngularSpeed(angularSpeedPdf.random());
    }

    @Override
    public EmitterImpl setEmissionTarget(EmissionTarget emissionTarget){
        this.emissionTarget = emissionTarget;
        return this;
    }
}
