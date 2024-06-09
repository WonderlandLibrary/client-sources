package com.wikihacks.utils;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glColor4f;

public class ColorUtils {
    public static void setColor(Color c) {
        glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
    }
}
