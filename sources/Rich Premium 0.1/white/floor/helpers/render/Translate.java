package white.floor.helpers.render;

import white.floor.helpers.render.AnimationHelper;

public class Translate {

    private float x;
    private float y;
    private long lastMS;

    public Translate(float x, float y) {
        this.x = x;
        this.y = y;
        this.lastMS = System.currentTimeMillis();
    }

    public void interpolate(float targetX, float targetY, int xSpeed, int ySpeed) {
        long currentMS = System.currentTimeMillis();
        long delta = currentMS - lastMS;//16.66666
        lastMS = currentMS;
        int deltaX = (int) (Math.abs(targetX - x) * 0.51f);
        int deltaY = (int) (Math.abs(targetY - y) * 0.51f);
        x = AnimationHelper.calculateCompensation(targetX, x, delta, deltaX);
        y = AnimationHelper.calculateCompensation(targetY, y, delta, deltaY);
    }

    public final void interpolate(float targetX, float targetY, float smoothing) {
        this.x = (float) AnimationHelper.animate(targetX, this.x, smoothing);
        this.y = (float) AnimationHelper.animate(targetY, this.y, smoothing);
    }

    public void interpolate(float targetX, float targetY, double speedX, double speedY) {
        long currentMS = System.currentTimeMillis();
        long delta = currentMS - lastMS;//16.66666
        lastMS = currentMS;
        double deltaX = 0;
        double deltaY = 0;
        if(speedX != 0 || speedY != 0){
            deltaX = (Math.abs(targetX - x) * 0.35f)/(10/speedX);
            deltaY = (Math.abs(targetY - y) * 0.35f)/(10/speedY);
        }
        x = AnimationHelper.calculateCompensation(targetX, x, delta, deltaX);
        y = AnimationHelper.calculateCompensation(targetY, y, delta, deltaY);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}