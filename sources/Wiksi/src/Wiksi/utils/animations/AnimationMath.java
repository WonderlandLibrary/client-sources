package src.Wiksi.utils.animations;

import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.utils.client.IMinecraft;
import net.minecraft.util.math.MathHelper;

public class AnimationMath implements IMinecraft {
    public static double deltaTime() {
        return mc.debugFPS > 0 ? (1.0000 / mc.debugFPS) : 1;
    }

    public static float fast(float end, float start, float multiple) {
        return (1 - MathHelper.clamp((float) (AnimationMath.deltaTime() * multiple), 0, 1)) * end + MathHelper.clamp((float) (AnimationMath.deltaTime() * multiple), 0, 1) * start;
    }

    public static float lerp(float end, float start, float multiple) {
        return (float) (end + (start - end) * MathHelper.clamp(AnimationMath.deltaTime() * multiple, 0, 1));
    }

    public static double lerp(double end, double start, double multiple) {
        return (end + (start - end) * MathHelper.clamp(AnimationMath.deltaTime() * multiple, 0, 1));
    }

    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0);
    }
}
