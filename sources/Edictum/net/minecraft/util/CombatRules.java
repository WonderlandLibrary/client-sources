package net.minecraft.util;

import net.minecraft.util.math.MathHelper;

public class CombatRules
{
    public static float func_189427_a(float p_189427_0_, float p_189427_1_, float p_189427_2_)
    {
        float f = 2.0F + p_189427_2_ / 4.0F;
        float f1 = MathHelper.clamp_float(p_189427_1_ - p_189427_0_ / f, p_189427_1_ * 0.2F, 20.0F);
        return p_189427_0_ * (1.0F - f1 / 25.0F);
    }

    public static float getDamageAfterMagicAbsorb(float p_188401_0_, float p_188401_1_)
    {
        float f = MathHelper.clamp_float(p_188401_1_, 0.0F, 20.0F);
        return p_188401_0_ * (1.0F - f / 25.0F);
    }
}
