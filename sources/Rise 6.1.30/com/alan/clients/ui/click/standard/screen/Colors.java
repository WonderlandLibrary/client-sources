package com.alan.clients.ui.click.standard.screen;

import java.awt.*;

public enum Colors {
    BACKGROUND(23, 26, 33, 254),
    SECONDARY(18, 20, 25),
    TEXT(255, 255, 255),
    SECONDARY_TEXT(255, 255, 255, 220),
    TRINARY_TEXT(255, 255, 255, 130),
    OVERLAY(0, 0, 0, 50);
    private final Color color;

    Colors(int r, int g, int b, int a) {
        this.color = new Color(r, g, b, a);
    }

    Colors(int r, int g, int b) {
        this.color = new Color(r, g, b, 255);
    }

    public Color get() {
        return color;
    }

    public int getRGB() {
        return color.getRGB();
    }

    public Color getWithRed(int red) {
        return new Color(red, color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public Color getWithGreen(int green) {
        return new Color(color.getRed(), green, color.getBlue(), color.getAlpha());
    }

    public Color getWithBlue(int blue) {
        return new Color(color.getRed(), color.getGreen(), blue, color.getAlpha());
    }

    public Color getWithAlpha(int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public int getRGBWithAlpha(int alpha) {
        return getWithAlpha(alpha).getRGB();
    }
}
