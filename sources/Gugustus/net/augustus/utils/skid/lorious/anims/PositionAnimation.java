package net.augustus.utils.skid.lorious.anims;

public class PositionAnimation {
    private final Animation xAnimation = new Animation();
    private final Animation yAnimation = new Animation();

    public void animate(double x, double y, double xDuration, double yDuration, Easing easing) {
        if (xDuration == 0.0) {
            this.getXAnimation().setValue(x);
        } else {
            this.getXAnimation().animate(x, xDuration, easing);
        }
        if (yDuration == 0.0) {
            this.getYAnimation().setValue(y);
        } else {
            this.getYAnimation().animate(y, yDuration, easing);
        }
    }

    public double getX() {
        return this.getXAnimation().getValue();
    }

    public double getY() {
        return this.getYAnimation().getValue();
    }

    public void update() {
        this.getYAnimation().updateAnimation();
        this.getXAnimation().updateAnimation();
    }

    public boolean isAnimated() {
        return this.getXAnimation().isDone() && this.getYAnimation().isDone();
    }

    public Animation getXAnimation() {
        return this.xAnimation;
    }

    public Animation getYAnimation() {
        return this.yAnimation;
    }
}
