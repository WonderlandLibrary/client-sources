package dev.star.utils.animations.impl;

import dev.star.utils.animations.SimpleAnimation;

public class PosAnimation {
    private final SimpleAnimation xAnim;
    private final SimpleAnimation yAnim;
    private final SimpleAnimation zAnim;

    public PosAnimation(double speed) {
        xAnim = new SimpleAnimation(speed);
        yAnim = new SimpleAnimation(speed);
        zAnim = new SimpleAnimation(speed);
    }

    public PosAnimation() {
        xAnim = new SimpleAnimation();
        yAnim = new SimpleAnimation();
        zAnim = new SimpleAnimation();
    }

    public void setCurrent(double x, double y, double z) {
        xAnim.current = x;
        yAnim.current = y;
        zAnim.current = z;
    }

    public void setTarget(double x, double y, double z) {
        xAnim.target = x;
        yAnim.target = y;
        zAnim.target = z;
    }

    public double[] getCurrent() {
        return new double[]{xAnim.current, yAnim.current, zAnim.current};
    }

    public double[] animate() {
        return new double[]{xAnim.animate(), yAnim.animate(), zAnim.animate()};
    }

    public double[] animate(double speed) {
        return new double[]{xAnim.animate(speed), yAnim.animate(speed), zAnim.animate(speed)};
    }
}
