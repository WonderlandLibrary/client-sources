/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render;

import com.mojang.blaze3d.platform.GlStateManager;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.util.math.MathHelper;

public class AnimationMath
implements IMinecraft {
    public static double deltaTime() {
        return AnimationMath.mc.debugFPS > 0 ? 1.0 / (double)AnimationMath.mc.debugFPS : 1.0;
    }

    public static float fast(float f, float f2, float f3) {
        return (1.0f - MathHelper.clamp((float)(AnimationMath.deltaTime() * (double)f3), 0.0f, 1.0f)) * f + MathHelper.clamp((float)(AnimationMath.deltaTime() * (double)f3), 0.0f, 1.0f) * f2;
    }

    public static float lerp(float f, float f2, float f3) {
        return (float)((double)f + (double)(f2 - f) * MathHelper.clamp(AnimationMath.deltaTime() * (double)f3, 0.0, 1.0));
    }

    public static double lerp(double d, double d2, double d3) {
        return d + (d2 - d) * MathHelper.clamp(AnimationMath.deltaTime() * d3, 0.0, 1.0);
    }

    public static void sizeAnimation(double d, double d2, double d3) {
        GlStateManager.translated(d, d2, 0.0);
        GlStateManager.scaled(d3, d3, d3);
        GlStateManager.translated(-d, -d2, 0.0);
    }
}

