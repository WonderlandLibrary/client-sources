package net.augustus.utils.skid.lorious.anims;

import java.awt.Color;

public class ColorAnimation {
    private DAnimation red = new DAnimation();

    private DAnimation green = new DAnimation();

    private DAnimation blue = new DAnimation();

    private DAnimation alpha = new DAnimation();

    public void animate(double duration, Color color) {
        this.red.animate(duration, color.getRed());
        this.green.animate(duration, color.getGreen());
        this.blue.animate(duration, color.getBlue());
        this.alpha.animate(duration, color.getAlpha());
    }

    public boolean isAnimated() {
        return this.red.isDone();
    }

    public Color getColor() {
        return new Color((int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue(), (int)this.alpha.getValue());
    }

    public Color getTarget() {
        return new Color((int)this.red.getTarget(), (int)this.green.getTarget(), (int)this.blue.getTarget(), (int)this.alpha.getTarget());
    }

    public void setColor(Color color) {
        this.red.setValue(color.getRed());
        this.green.setValue(color.getGreen());
        this.blue.setValue(color.getBlue());
        this.alpha.setValue(color.getAlpha());
    }

    public DAnimation getRed() {
        return this.red;
    }

    public DAnimation getGreen() {
        return this.green;
    }

    public DAnimation getBlue() {
        return this.blue;
    }

    public DAnimation getAlpha() {
        return this.alpha;
    }

    public void setRed(DAnimation red) {
        this.red = red;
    }

    public void setGreen(DAnimation green) {
        this.green = green;
    }

    public void setBlue(DAnimation blue) {
        this.blue = blue;
    }

    public void setAlpha(DAnimation alpha) {
        this.alpha = alpha;
    }
}
