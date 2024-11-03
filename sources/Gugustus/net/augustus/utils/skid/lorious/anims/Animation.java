package net.augustus.utils.skid.lorious.anims;

public class Animation {
    private long animationStart;
    private double duration;
    private double animationFromValue;
    private double animationToValue;
    private Easing easing = Easings.NONE;
    private double lastValue;

    public double getValue() {
        return this.lastValue;
    }

    public void setValue(double value) {
        this.animationFromValue = value;
        this.animationToValue = value;
        this.lastValue = value;
    }

    public void animate(double value, double duration, Easing easing) {
        this.animationFromValue = this.lastValue;
        this.animationToValue = value;
        this.animationStart = System.currentTimeMillis();
        this.duration = duration;
        this.easing = easing;
    }

    public double getTarget() {
        return this.animationToValue;
    }

    private double interpolateValue(double start, double end, double pct) {
        return start + (end - start) * pct;
    }

    public boolean updateAnimation() {
        double value;
        double part = (double)(System.currentTimeMillis() - this.animationStart) / this.duration;
        if (this.isAlive()) {
            part = this.easing.ease(part);
            value = this.interpolateValue(this.animationFromValue, this.animationToValue, part);
        } else {
            this.animationStart = 0L;
            value = this.animationToValue;
        }
        this.lastValue = value;
        return this.isAlive();
    }

    public boolean isDone() {
        double part = (double)(System.currentTimeMillis() - this.animationStart) / this.duration;
        return part >= 1.0;
    }

    public boolean isAlive() {
        double part = (double)(System.currentTimeMillis() - this.animationStart) / this.duration;
        return part < 1.0;
    }
}
