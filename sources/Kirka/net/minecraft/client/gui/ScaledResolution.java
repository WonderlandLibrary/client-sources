/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

public class ScaledResolution {
    private final double scaledWidthD;
    private final double scaledHeightD;
    private static int scaledWidth;
    private static int scaledHeight;
    private int scaleFactor;
    private static final String __OBFID = "CL_00000666";

    public ScaledResolution(Minecraft mcIn, int p_i46324_2_, int p_i46324_3_) {
        scaledWidth = p_i46324_2_;
        scaledHeight = p_i46324_3_;
        this.scaleFactor = 1;
        boolean var4 = mcIn.isUnicode();
        int var5 = mcIn.gameSettings.guiScale;
        if (var5 == 0) {
            var5 = 1000;
        }
        while (this.scaleFactor < var5 && scaledWidth / (this.scaleFactor + 1) >= 320 && scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (var4 && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        this.scaledWidthD = (double)scaledWidth / (double)this.scaleFactor;
        this.scaledHeightD = (double)scaledHeight / (double)this.scaleFactor;
        scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
        scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
    }

    public static int getScaledWidth() {
        return scaledWidth;
    }

    public static int getScaledHeight() {
        return scaledHeight;
    }

    public double getScaledWidth_double() {
        return this.scaledWidthD;
    }

    public double getScaledHeight_double() {
        return this.scaledHeightD;
    }

    public int getScaleFactor() {
        return this.scaleFactor;
    }
}

