package com.scopely.particulate.objects.particles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;

import com.scopely.particulate.Utils;
import com.scopely.particulate.physics.Field;
import com.scopely.particulate.physics.Vector;
import com.scopely.particulate.physics.functions.Interpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.PI;

@SuppressWarnings("unused")
public class Particle {

    private static final double DEGREES_PER_RADIAN = 180 / PI;
    private final Bitmap bitmap;
    private final int bitmapWidth;
    private final int bitmapHeight;
    private final int bitmapHalfWidth;
    private final int bitmapHalfHeight;
    private Paint paint = new Paint();

    private long birth = Utils.milliTime();
    private long tLast = birth;

    private long tickLength = 16;
    private double tickLengthS = tickLength/1000f;

    private double x;
    private double y;

    //Mass in in kg
    private double mass = 1;
    //charge is in coulombs
    private double charge = 0;
    //Velocity is in m/s
    private final Vector velocity;
    //Angular velocity is in radians/s
    private double angularSpeed = 2;
    //Fields return a force vector represented in kgm/s^2, aka newtons
    private final List<Field> fields = new ArrayList<>();

    @Nullable
    private
    Interpolator scaleXInterpolator;
    @Nullable
    private
    Interpolator scaleYInterpolator;
    @Nullable
    private
    Interpolator alphaInterpolator;
    private Rect bounds = new Rect();
    @Nullable
    private Recycler recycler;

    Particle(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.bitmapWidth = bitmap.getWidth();
        this.bitmapHalfWidth = this.bitmapWidth / 2;
        this.bitmapHeight = bitmap.getHeight();
        this.bitmapHalfHeight = this.bitmapHeight / 2;
        this.velocity = new Vector(0,0);
    }

    Particle withRecyler(Recycler recycler) {
        this.recycler = recycler;
        return this;
    }

    public Particle inFields(Field... fields) {
        return inFields(Arrays.asList(fields));
    }

    public Particle inFields(List<Field> fields) {
        this.fields.addAll(fields);
        for(int i = forces.size(); i < this.fields.size(); i++) {
            forces.add(new Vector(0,0));
        }
        return this;
    }

    public Particle withMass(double mass) {
        this.mass = mass;
        return this;
    }

    public Particle withCharge(double charge) {
        this.charge = charge;
        return this;
    }

    public Particle withPosition(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Particle withPosition(PointF position) {
        return withPosition(position.x, position.y);
    }

    public Particle withVelocity(Vector velocity) {
        Vector.set(this.velocity, velocity.x, velocity.y);
        return this;
    }

    public Particle withVelocity(double speed, double radians) {
        Vector.set(this.velocity, Math.cos(radians), Math.sin(radians));
        this.velocity.scale(speed);
        return this;
    }

    public Particle withScaleXInterpolator(Interpolator interpolator) {
        this.scaleXInterpolator = interpolator;
        return this;
    }

    public Particle withScaleYInterpolator(Interpolator interpolator) {
        this.scaleYInterpolator = interpolator;
        return this;
    }

    public Particle withScaleInterpolator(Interpolator interpolator) {
        this.scaleXInterpolator = interpolator;
        this.scaleYInterpolator = interpolator;
        return this;
    }

    public Particle withAlphaInterpolator(Interpolator interpolator) {
        this.alphaInterpolator = interpolator;
        return this;
    }

    private final List<Vector> forces = new ArrayList<>();
    private final Vector dv = new Vector(0,0);

    private float scaleY;
    private float scaleX;
    private double alpha;
    private float degrees;

    private void onTimeDelta(double t, double dt, double pixelScale) {
        //Our x and y positions will change according to our current velocity and our time spent at that velocity
        x += velocity.x * dt;
        y += -velocity.y * dt;

        //For all fields active, compute the force they impart on the particle
        int size = fields.size();
        for(int i = 0; i < size; i++) {
            fields.get(i).getForce(forces.get(i), t, mass, charge, x, y, velocity.x, velocity.y);
        }

        //Our velocity will change according to the forces acting and the duration they act for
        Vector.set(dv, 0, 0);
        dv.add(forces).scale(dt/mass);
        velocity.add(dv);

        //Compute our scale, alpha, and rotation based on how long we have been alive
        double  tRel = Utils.toFloatSeconds(Utils.milliTime() - birth);
        scaleY = scaleYInterpolator != null ? scaleYInterpolator.interpolate(tRel) : 1;
        scaleX = scaleXInterpolator != null ? scaleXInterpolator.interpolate(tRel) : 1;
        alpha = alphaInterpolator != null ? alphaInterpolator.interpolate(tRel) : 1;
        double radians = angularSpeed * t;
        degrees = (float) (radians * DEGREES_PER_RADIAN);

        //Set bounds of drawing area based on current position and pixel scale
        bounds.left = (int) (x * pixelScale - bitmapHalfWidth);
        bounds.top = (int) (y * pixelScale - bitmapHalfHeight);
        bounds.right = bounds.left + bitmapWidth;
        bounds.bottom = bounds.top + bitmapHeight;
    }

    public void onDraw(Canvas canvas) {
        if(canvas.quickReject(bounds.left, bounds.top, bounds.right, bounds.bottom, Canvas.EdgeType.AA)) return;
        canvas.save();
        int px = bounds.centerX();
        int py = bounds.centerY();
        canvas.rotate(degrees, px, py);
        canvas.scale(scaleX, scaleY, px, py);
        canvas.translate(bounds.left, bounds.top);
        paint.setAlpha((int) (255 * alpha));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Particle withAngularSpeed(double speed) {
        this.angularSpeed = speed;
        return this;
    }

    public long getAge() {
        return Utils.milliTime() - birth;
    }

    public Rect getBounds() {
        return bounds;
    }

    public void recycle() {
        if (recycler != null) {
            recycler.recycle(this);
        }
    }

    void reset() {
        fields.clear();
        Vector.set(velocity, 0, 0);
        x = 0;
        y = 0;
        bounds.set(0,0,0,0);
        mass = 1;
        charge = 0;
        angularSpeed = 2;
        birth = Utils.milliTime();
        tLast = birth - birth % tickLength;
    }

    public void updatePhysics(double t, float pixelsPerMeter) {
        long now = Utils.milliTime();
        long lastTick = now - now % tickLength;
        long dt = lastTick - tLast;
        long iterations = dt / tickLength;
        tLast = lastTick;
        double tStart = t - dt / 1000f;
        for(int i = 1; i <= iterations; i++) {
            double t1 = tStart + tickLengthS * i;
            onTimeDelta(t1, tickLengthS, pixelsPerMeter);
        }
    }

    public interface Recycler {
        void recycle(Particle particle);
    }
}
