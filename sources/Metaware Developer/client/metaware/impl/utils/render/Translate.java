package client.metaware.impl.utils.render;

import client.metaware.impl.utils.util.other.MathUtils;

public final class Translate {
    private double x;
    private double y;
    private long lastMS;

    public Translate(float x, float y) {
        this.x = (double) x;
        this.y = (double) y;
        this.lastMS = System.currentTimeMillis();
    }

    public final void interpolate(double targetX, double targetY, double smoothing) {
        this.x = RenderUtil.animate(targetX, this.x, smoothing);
        this.y = RenderUtil.animate(targetY, this.y, smoothing);
    }

    public void interpolate2(float targetX, float targetY, double speed) {
        long currentMS = System.currentTimeMillis();
        long delta = currentMS - lastMS;//16.66666
        lastMS = currentMS;
        double deltaX = 0;
        double deltaY = 0;
        if(speed != 0){
            deltaX = (Math.abs(targetX - x) * 0.35f)/(10/speed);
            deltaY = (Math.abs(targetY - y) * 0.35f)/(10/speed);
        }
        x = MathUtils.calculateCompensation(targetX, (float) x, delta, deltaX);
        y = MathUtils.calculateCompensation(targetY, (float) y, delta, deltaY);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
