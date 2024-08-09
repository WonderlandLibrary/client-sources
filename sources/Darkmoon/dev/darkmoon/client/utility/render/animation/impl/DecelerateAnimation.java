package dev.darkmoon.client.utility.render.animation.impl;

import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.Direction;

public class DecelerateAnimation extends Animation {
    public DecelerateAnimation(int ms, float endPoint) {
        super(ms, endPoint);
    }

    public DecelerateAnimation(int ms, float endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }

    protected float getEquation(float x) {
        float x1 = x / duration;
        return 1 - ((x1 - 1) * (x1 - 1));
    }
}
