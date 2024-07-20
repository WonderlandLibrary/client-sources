/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

public class HoverUtils {
    public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseY > y && mouseX < width && mouseY < height;
    }

    public static boolean isHovered(float x, float y, float width, float height, int mouseX, int mouseY) {
        return (float)mouseX > x && (float)mouseY > y && (float)mouseX < width && (float)mouseY < height;
    }
}

