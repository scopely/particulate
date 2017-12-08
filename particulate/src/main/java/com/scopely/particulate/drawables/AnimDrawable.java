package com.scopely.particulate.drawables;

import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

public abstract class AnimDrawable extends Drawable implements Runnable {
    private static final long MS_PER_FRAME = 16;

    @Override
    public void run() {
        invalidateSelf();
        scheduleSelf(this, SystemClock.uptimeMillis() + MS_PER_FRAME);
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        final boolean changed = super.setVisible(visible, restart);
        if (visible) {
            if (restart || changed) {
                scheduleSelf(this, SystemClock.uptimeMillis() + MS_PER_FRAME);
                invalidateSelf();
            }
        } else {
            unscheduleSelf(this);
        }
        return changed;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
