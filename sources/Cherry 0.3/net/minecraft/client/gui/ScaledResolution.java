package net.minecraft.client.gui;

import net.minecraft.util.MathHelper;
import net.minecraft.client.Minecraft;

public class ScaledResolution
{
    private final double scaledWidthD;
    private final double scaledHeightD;
    private static int scaledWidth;
    private static int scaledHeight;
    private int scaleFactor;
    
    public ScaledResolution(final Minecraft mcIn, final int p_i46324_2, final int p_i46324_3) {
        ScaledResolution.scaledWidth = p_i46324_2;
        ScaledResolution.scaledHeight = p_i46324_3;
        this.scaleFactor = 1;
        final boolean var4 = mcIn.isUnicode();
        int var5 = Minecraft.gameSettings.guiScale;
        if (var5 == 0) {
            var5 = 1000;
        }
        while (this.scaleFactor < var5 && ScaledResolution.scaledWidth / (this.scaleFactor + 1) >= 320 && ScaledResolution.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (var4 && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        this.scaledWidthD = ScaledResolution.scaledWidth / this.scaleFactor;
        this.scaledHeightD = ScaledResolution.scaledHeight / this.scaleFactor;
        ScaledResolution.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
        ScaledResolution.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
    }
    
    public static int getScaledWidth() {
        return ScaledResolution.scaledWidth;
    }
    
    public static int getScaledHeight() {
        return ScaledResolution.scaledHeight;
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
