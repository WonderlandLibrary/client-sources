package net.augustus.utils.skid.lorious.anims;

public class DAnimation {
    private long animationStart;

    private long animationEnd;

    private double animationFromValue;

    private double animationToValue;

    public double getValue() {
        double path = (double) (System.currentTimeMillis() - this.animationStart) / (this.animationEnd - this.animationStart);
        if (path >= 1.0D)
            return this.animationToValue;
        double value = (this.animationToValue - this.animationFromValue) * path + this.animationFromValue;
        return value;
    }

    public double getTarget() {
        return this.animationToValue;
    }

    public void setValue(int value) {
        this.animationFromValue = value;
        this.animationToValue = value;
    }

    public void animate(double duration, double valueTo) {
        double path = (double) (System.currentTimeMillis() - this.animationStart) / (this.animationEnd - this.animationStart);
        this.animationStart = System.currentTimeMillis();
        this.animationEnd = (long)(System.currentTimeMillis() + duration);
        if (path < 1.0D) {
            this.animationFromValue += (this.animationToValue - this.animationFromValue) * path;
        } else {
            this.animationFromValue = this.animationToValue;
        }
        this.animationToValue = valueTo;
    }

    public boolean isDone() {
        double path = (double) (System.currentTimeMillis() - this.animationStart) / (this.animationEnd - this.animationStart);
        return (path >= 1.0D);
    }

    public boolean isAlive() {
        double path = (double) (System.currentTimeMillis() - this.animationStart) / (this.animationEnd - this.animationStart);
        return (path < 1.0D);
    }
}
