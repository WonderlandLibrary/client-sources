package net.augustus.utils.skid.lorious.anims;

public class RectAnimation {
    private final Animation xAnimation = new Animation();
    private final Animation yAnimation = new Animation();
    private final Animation widthAnimation = new Animation();
    private final Animation heightAnimation = new Animation();

    public void animate(double x, double y, double width, double height, double xDuration, double yDuration, double widthDuration, double heightDuration, Easing easing) {
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
        if (widthDuration == 0.0) {
            this.getWidthAnimation().setValue(width);
        } else {
            this.getWidthAnimation().animate(width, widthDuration, easing);
        }
        if (heightDuration == 0.0) {
            this.getHeightAnimation().setValue(height);
        } else {
            this.getHeightAnimation().animate(height, heightDuration, easing);
        }
    }

    public double getX() {
        return this.getXAnimation().getValue();
    }

    public double getY() {
        return this.getYAnimation().getValue();
    }

    public double getHeight() {
        return this.getHeightAnimation().getValue();
    }

    public double getWidth() {
        return this.getWidthAnimation().getValue();
    }

    public void update() {
        this.getYAnimation().updateAnimation();
        this.getWidthAnimation().updateAnimation();
        this.getHeightAnimation().updateAnimation();
        this.getXAnimation().updateAnimation();
    }

    public boolean isAnimated() {
        return this.getYAnimation().isDone() && this.getWidthAnimation().isDone() && this.getHeightAnimation().isDone() && this.getXAnimation().isDone();
    }

    public Animation getXAnimation() {
        return this.xAnimation;
    }

    public Animation getYAnimation() {
        return this.yAnimation;
    }

    public Animation getWidthAnimation() {
        return this.widthAnimation;
    }

    public Animation getHeightAnimation() {
        return this.heightAnimation;
    }
}
