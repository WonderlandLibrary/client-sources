package dev.star.utils.animations;

public class SimpleAnimation {
    public double current = 0.0f;
    public double target = 0.0f;
    public double speed;

    public SimpleAnimation(double speed) {
        this.speed = speed;
    }

    public SimpleAnimation() {
    }

    public double animate() {
        this.current = animate(this.target, this.current, this.speed);
        if (this.isFinished()) {
            this.current = this.target;
        }
        return this.current;
    }

    public static double animate(double target, double current, double speed) {
        boolean larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        current = larger ? current + factor : current - factor;
        return current;
    }

    public double animate(double speed) {
        this.speed = speed;
        return animate();
    }

    public boolean isFinished() {
        return Math.abs(this.current - this.target) < 0.01f;
    }
}