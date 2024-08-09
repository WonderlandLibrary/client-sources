package dev.darkmoon.client.utility.render.animation.impl;

import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.Direction;

public class EaseInOutQuad extends Animation {
    public EaseInOutQuad(int ms, float endPoint) {
        super(ms, endPoint);
    }

    public EaseInOutQuad(int ms, float endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }
    @Override
    protected float getEquation(float x) {
        double x1 = x / duration;
        return (float) (x1 < 0.5 ? 2 * Math.pow(x1, 2) : 1 - Math.pow(-2 * x1 + 2, 2) / 2);
    }
}
