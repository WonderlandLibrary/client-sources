package com.polarware.util.dragging;

import com.polarware.util.vector.Vector2d;
import net.minecraft.client.gui.ScaledResolution;

import static com.polarware.util.interfaces.InstanceAccess.mc;

public class Mouse {
    public static Vector2d getMouse() {
        final ScaledResolution scaledResolution = mc.scaledResolution;
        final int mouseX = org.lwjgl.input.Mouse.getX() * scaledResolution.getScaledWidth() / mc.displayWidth;
        final int mouseY = scaledResolution.getScaledHeight() - org.lwjgl.input.Mouse.getY() * scaledResolution.getScaledHeight() / mc.displayHeight - 1;

        return new Vector2d(mouseX, mouseY);
    }
}
