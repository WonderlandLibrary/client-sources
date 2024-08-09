/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;

public final class EffectUtils {
    public static String getPotionDurationString(EffectInstance effectInstance, float f) {
        if (effectInstance.getIsPotionDurationMax()) {
            return "**:**";
        }
        int n = MathHelper.floor((float)effectInstance.getDuration() * f);
        return StringUtils.ticksToElapsedTime(n);
    }

    public static boolean hasMiningSpeedup(LivingEntity livingEntity) {
        return livingEntity.isPotionActive(Effects.HASTE) || livingEntity.isPotionActive(Effects.CONDUIT_POWER);
    }

    public static int getMiningSpeedup(LivingEntity livingEntity) {
        int n = 0;
        int n2 = 0;
        if (livingEntity.isPotionActive(Effects.HASTE)) {
            n = livingEntity.getActivePotionEffect(Effects.HASTE).getAmplifier();
        }
        if (livingEntity.isPotionActive(Effects.CONDUIT_POWER)) {
            n2 = livingEntity.getActivePotionEffect(Effects.CONDUIT_POWER).getAmplifier();
        }
        return Math.max(n, n2);
    }

    public static boolean canBreatheUnderwater(LivingEntity livingEntity) {
        return livingEntity.isPotionActive(Effects.WATER_BREATHING) || livingEntity.isPotionActive(Effects.CONDUIT_POWER);
    }
}

