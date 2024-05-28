package dev.vertic.util.math;

import net.minecraft.client.Minecraft;

public enum Animate {

    getInstance();

    public double animate(double current, double end, double minSpeed) {
        double movement = (end - current) * 0.065 * 240 / Minecraft.getDebugFPS();

        if (movement > 0) {
            movement = Math.max(minSpeed, movement);
            movement = Math.min(end - current, movement);
        } else if (movement < 0) {
            movement = Math.min(-minSpeed, movement);
            movement = Math.max(end - current, movement);
        }

        return current + movement;
    }
}
