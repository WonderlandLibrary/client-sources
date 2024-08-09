/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package src.Wiksi.utils.render;

import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.utils.client.IMinecraft;
import net.minecraft.util.math.MathHelper;

public class AnimationMath
implements IMinecraft {
    public static double deltaTime() {
        return AnimationMath.mc.debugFPS > 0 ? 1.0 / (double)AnimationMath.mc.debugFPS : 1.0;
    }

    public static float fast(float f, float f2, float f3) {
        int n = "\u6d01\u5224\u64b5\u5c10\u6d06".length();
        int n2 = "\u5e1c\u5bd3".length();
        int n3 = "\u524c\u5d4f\u509a\u679f".length();
        return (1.0f - MathHelper.clamp((float)(AnimationMath.deltaTime() * (double)f3), 0.0f, 1.0f)) * f + MathHelper.clamp((float)(AnimationMath.deltaTime() * (double)f3), 0.0f, 1.0f) * f2;
    }

    public static float lerp(float f, float f2, float f3) {
        int n = "\u5956\u70f3\u65f7".length();
        return (float)((double)f + (double)(f2 - f) * MathHelper.clamp(AnimationMath.deltaTime() * (double)f3, 0.0, 1.0));
    }

    public static double lerp(double d, double d2, double d3) {
        int n = "\u6fc1\u5c1d\u54f4\u6960".length();
        int n2 = "\u5090\u61b2\u5890\u5a31\u6bd3".length();
        int n3 = "\u5300\u5a21\u661a".length();
        return d + (d2 - d) * MathHelper.clamp(AnimationMath.deltaTime() * d3, 0.0, 1.0);
    }

    public static void sizeAnimation(double d, double d2, double d3) {
        GlStateManager.translated(d, d2, 0.0);
        GlStateManager.scaled(d3, d3, d3);
        GlStateManager.translated(-d, -d2, 0.0);
    }
}

