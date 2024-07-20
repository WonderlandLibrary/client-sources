/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseHelper {
    public int deltaX;
    public int deltaY;

    public void grabMouseCursor() {
        Mouse.setGrabbed(true);
        this.deltaX = 0;
        this.deltaY = 0;
    }

    public static boolean isHovered(double x, double y, double mouseX, double mouseY, int width, int height) {
        return (double)width > x && (double)height > y && (double)width < mouseX && (double)height < mouseY;
    }

    public void ungrabMouseCursor() {
        Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
        Mouse.setGrabbed(false);
    }

    public void mouseXYChange() {
        this.deltaX = Mouse.getDX();
        this.deltaY = Mouse.getDY();
    }
}

