/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import net.minecraft.world.World;

public enum Weather {
    CLEAR,
    RAIN,
    THUNDER;


    public static Weather getWeather(World world, float f) {
        float f2 = world.getThunderStrength(f);
        if (f2 > 0.5f) {
            return THUNDER;
        }
        float f3 = world.getRainStrength(f);
        return f3 > 0.5f ? RAIN : CLEAR;
    }
}

