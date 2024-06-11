package Hydro.util.animations.impl;

import Hydro.util.animations.Animation;
import Hydro.util.animations.Direction;

public class LinearAnimation extends Animation {

    public LinearAnimation(int ms, double endPoint, Enum<Direction> direction) {
        super(ms, endPoint, direction);
    }

    public LinearAnimation(int ms, double endPoint) {
        super(ms, endPoint);
    }

    protected double getEquation(double x) {
        return x / duration; //TODO Entirely broken even though it's the easiest animation to make. How? I have no idea
    }

}
