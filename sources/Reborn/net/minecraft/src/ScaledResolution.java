package net.minecraft.src;

public class ScaledResolution
{
    private static int scaledWidth;
    private static int scaledHeight;
    private double scaledWidthD;
    private double scaledHeightD;
    private int scaleFactor;
    
    public ScaledResolution(final GameSettings par1GameSettings, final int par2, final int par3) {
        ScaledResolution.scaledWidth = par2;
        ScaledResolution.scaledHeight = par3;
        this.scaleFactor = 1;
        int var4 = par1GameSettings.guiScale;
        if (var4 == 0) {
            var4 = 1000;
        }
        while (this.scaleFactor < var4 && ScaledResolution.scaledWidth / (this.scaleFactor + 1) >= 320 && ScaledResolution.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
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
