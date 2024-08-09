/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.math.MathHelper;

public class CombatRules {
    public static float getDamageAfterAbsorb(float f, float f2, float f3) {
        float f4 = 2.0f + f3 / 4.0f;
        float f5 = MathHelper.clamp(f2 - f / f4, f2 * 0.2f, 20.0f);
        return f * (1.0f - f5 / 25.0f);
    }

    public static float getDamageAfterMagicAbsorb(float f, float f2) {
        float f3 = MathHelper.clamp(f2, 0.0f, 20.0f);
        return f * (1.0f - f3 / 25.0f);
    }
}

