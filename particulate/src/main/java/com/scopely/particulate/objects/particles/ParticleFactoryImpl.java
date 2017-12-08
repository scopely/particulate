package com.scopely.particulate.objects.particles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayDeque;
import java.util.Queue;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ParticleFactoryImpl implements ParticleFactory, Particle.Recycler {

    private final Queue<Particle> freeParticles;
    private final Bitmap bitmap;

    private int count;

    public ParticleFactoryImpl(Bitmap bitmap, Queue<Particle> particleQueue) {
        this.bitmap = bitmap;
        this.freeParticles = particleQueue;
    }

    public ParticleFactoryImpl(Bitmap bitmap) {
        this(bitmap, new ArrayDeque<Particle>());
    }

    public ParticleFactoryImpl(Drawable drawable) {
        this(drawable, new ArrayDeque<Particle>());
    }

    public ParticleFactoryImpl(Drawable drawable, Queue<Particle> particleQueue) {
        this(getBitmap(drawable), particleQueue);
    }

    private static Bitmap getBitmap(Drawable drawable) {
        if(drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }

    @Override
    public Particle newParticle() {
        Particle particle = freeParticles.poll();
        if (particle == null) {
            particle = new Particle(bitmap);
            count++;
        } else {
            particle.reset();
        }
        return particle.withRecyler(this);
    }

    @Override
    public void recycle(Particle particle) {
        count--;
        freeParticles.add(particle);
    }
}
