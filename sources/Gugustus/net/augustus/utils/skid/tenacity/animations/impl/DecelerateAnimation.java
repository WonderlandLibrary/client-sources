package net.augustus.utils.skid.tenacity.animations.impl;

import net.augustus.utils.skid.tenacity.animations.Animation;
import net.augustus.utils.skid.tenacity.animations.Direction;

public class DecelerateAnimation extends Animation {

    public DecelerateAnimation(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public DecelerateAnimation(int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }


    protected double getEquation(double x) {
        return 1 - ((x - 1) * (x - 1));
    }
}
