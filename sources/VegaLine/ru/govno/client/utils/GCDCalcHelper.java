/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

import net.minecraft.client.Minecraft;

public class GCDCalcHelper {
    static Minecraft mc = Minecraft.getMinecraft();

    public static float getFixedRotation(float rot) {
        return GCDCalcHelper.getDeltaMouse(rot) * GCDCalcHelper.getGCDValue();
    }

    public static float getGCDValue() {
        return (float)((double)GCDCalcHelper.getGCD() * 0.15);
    }

    public static float getGCD() {
        float f1 = (float)((double)GCDCalcHelper.mc.gameSettings.mouseSensitivity * 0.6 + 0.2);
        return f1 * f1 * f1 * 8.0f;
    }

    public static float getDeltaMouse(float delta) {
        return Math.round(delta / GCDCalcHelper.getGCDValue());
    }
}

