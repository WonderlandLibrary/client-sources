package com.alan.clients.util;

import com.alan.clients.util.vector.Vector2d;
import org.lwjgl.input.Mouse;

public class MouseUtil implements Accessor {

    /**
     * Checks if mouse coordinates are within the bounds of given coordinates.
     *
     * @param x      The x coordinate of the top-left corner of the area.
     * @param y      The y coordinate of the top-left corner of the area.
     * @param width  The width of the area.
     * @param height The height of the area.
     * @param mouseX The x coordinate of the mouse.
     * @param mouseY The y coordinate of the mouse.
     * @return True if the mouse is within the area, false otherwise.
     */
    public static boolean isHovered(final double x, final double y, final double width, final double height, final int mouseX, final int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public static boolean isHovered(final double x, final double y, final double width, final double height) {
        Vector2d mouse = mouse();
        return mouse.x >= x && mouse.x < x + width && mouse.y >= y && mouse.y < y + height;
    }

    public static Vector2d mouse() {
        final int i1 = mc.scaledResolution.getScaledWidth();
        final int j1 = mc.scaledResolution.getScaledHeight();
        final int mouseX = Mouse.getX() * i1 / mc.displayWidth;
        final int mouseY = j1 - Mouse.getY() * j1 / mc.displayHeight - 1;

        return new Vector2d(mouseX, mouseY);
    }
}
