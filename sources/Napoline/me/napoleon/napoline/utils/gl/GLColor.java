package me.napoleon.napoline.utils.gl;

import org.lwjgl.opengl.GL11;

public class GLColor {
    public static void color(int color) {
        float alpha = (float)(color >> 24 & 255) / 255.0f;
        float red = (float)(color >> 16 & 255) / 255.0f;
        float green = (float)(color >> 8 & 255) / 255.0f;
        float blue = (float)(color & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
}

