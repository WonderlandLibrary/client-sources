package de.tired.api.util.render;

import lombok.Getter;
import lombok.Setter;
public class Translate {

    @Getter @Setter
    private float x, y;
    @Getter
    private long lastTimeMillis;

    public Translate(float x, float y) {
        this.x = x;
        this.y = y;
        this.lastTimeMillis = System.currentTimeMillis();
    }


    public void interpolate(float targetX, float targetY, double speed) {
        long currTime = System.currentTimeMillis();
        long delta = currTime - lastTimeMillis;
        this.lastTimeMillis = currTime;
        double deltaX = 0;
        double deltaY = 0;
        if (speed != 0) {
            deltaX = (Math.abs(targetX - x) * 0.35f) / (10 / speed);
            deltaY = (Math.abs(targetY - y) * 0.35f) / (10 / speed);
        }
        x = calc(targetX, x, delta, deltaX);
        y = calc(targetY, y, delta, deltaY);
    }


    public static float calc(float target, float current, long delta, double speed) {
        float diff = current - target;
        if (delta < 1) {
            delta = 1;
        }
        if (delta > 1000) {
            delta = 16;
        }
        final double max = Math.max(speed * delta / (1000 / 60f), 0.5);
        if (diff > speed) {
            current -= max;
            if (current < target) {
                current = target;
            }
        } else if (diff < -speed) {
            current += max;
            if (current > target) {
                current = target;
            }
        } else {
            current = target;
        }
        return current;
    }


}
