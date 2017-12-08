package com.scopely.particulate;

import android.graphics.RectF;
import android.os.Looper;
import android.view.View;

import java.util.concurrent.TimeUnit;


@SuppressWarnings({"WeakerAccess", "unused"})
public class Utils {
    public static long milliTime() {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
    }

    public static double  toFloatSeconds(long millis) {
        return ((double) millis) / 1000;
    }

    public static boolean onMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static RectF getBoundsOfView(View surface, View view, float ppm) {
        int[] surfacePos = new int[2];
        int[] viewPos = new int[2];
        RectF rectF = new RectF();

        return getBoundsOfView(surface, view, ppm, surfacePos, viewPos, rectF);
    }

    public static RectF getBoundsOfView(View surface, View view, float ppm, int[] surfacePos, int[] viewPos, RectF rectF) {
        surface.getLocationInWindow(surfacePos);
        view.getLocationInWindow(viewPos);

        float x = viewPos[0] - surfacePos[0];
        float y = viewPos[1] - surfacePos[1];
        x /= ppm;
        y /= ppm;


        float width = view.getWidth() / ppm;
        float height = view.getHeight() / ppm;

        rectF.set(x, y, x + width, y + height);
        return rectF;
    }
}
