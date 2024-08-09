package dev.darkmoon.client.utility.render.animation;

import dev.darkmoon.client.utility.math.MathUtility;
import net.minecraft.client.Minecraft;

public class AnimationMath {
    public static double deltaTime() {
        return Minecraft.getDebugFPS() > 0 ? (1.0000 / Minecraft.getDebugFPS()) : 1;
    }

    public static float fast(float end, float start, float multiple) {
        return (1 - MathUtility.clamp((float) (AnimationMath.deltaTime() * multiple), 0, 1)) * end + MathUtility.clamp((float) (AnimationMath.deltaTime() * multiple), 0, 1) * start;
    }
}
