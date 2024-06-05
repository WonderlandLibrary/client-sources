package zamorozka.ui;

import java.awt.*;

public class BetterColor extends Color {

    public BetterColor(double red, double green, double blue) {
        super((int) clamp(red, 0, 255), (int) clamp(green, 0, 255), (int) clamp(blue, 0, 255));
    }

    public BetterColor(double red, double green, double blue, double alpha) {
        super((int) clamp(red, 0, 255), (int) clamp(green, 0, 255), (int) clamp(blue, 0, 255), (int) clamp(alpha, 0, 255));
    }

    public BetterColor(Color color) {
        super((int) clamp(color.getRed(), 0, 255), (int) clamp(color.getGreen(), 0, 255), (int) clamp(color.getBlue(), 0, 255));
    }

    public BetterColor(int hex) {
        super(hex);
    }

    public static final double clamp(double value, double min, double max) {
        return value > max ? max : value < min ? min : value;
    }

    public final static BetterColor getHue(double value) {
        final float hue = (float) (1.0F - value / 360.0F);
        final int color = Color.HSBtoRGB(hue, 1.0F, 1.0F);
        return new BetterColor(new Color(color));
    }

    @Override
    public final BetterColor brighter() {
        return new BetterColor(super.brighter().brighter().brighter().brighter());
    }

    @Override
    public final BetterColor darker() {
        return new BetterColor(super.darker().darker().darker().darker());
    }

    public final BetterColor brighter(int brighteningValue) {
        final Color color = this;
        Color newColor = color;
        for (int i = 0; i < brighteningValue; i++) {
            newColor = newColor.brighter();
        }
        return new BetterColor(newColor).setAlpha(color.getAlpha());
    }

    public final BetterColor darker(int darkeningValue) {
        final Color color = this;
        Color newColor = color;
        for (int i = 0; i < darkeningValue; i++) {
            newColor = newColor.darker();
        }
        return new BetterColor(newColor).setAlpha(color.getAlpha());
    }

    public final BetterColor addColoring(int value) {
        final Color color = this;
        return new BetterColor(color.getRed() + value, color.getGreen() + value, color.getBlue() + value, color.getAlpha());
    }

    public final BetterColor addColoring(int red, int green, int blue) {
        final Color color = this;
        return new BetterColor(color.getRed() + red, color.getGreen() + green, color.getBlue() + blue, color.getAlpha());
    }

    public final BetterColor setAlpha(int alpha) {
        alpha = (int) clamp(alpha, 0, 255);
        return new BetterColor(this.getRed(), this.getGreen(), this.getBlue(), alpha);
    }

    @Override
    public final String toString() {
        return String.format("[%s,%s,%s]", getRed(), getGreen(), getBlue());
    }
}
