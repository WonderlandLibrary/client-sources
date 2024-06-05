package net.minecraft.src;

public class Rect2i
{
    private int rectX;
    private int rectY;
    private int rectWidth;
    private int rectHeight;
    
    public Rect2i(final int par1, final int par2, final int par3, final int par4) {
        this.rectX = par1;
        this.rectY = par2;
        this.rectWidth = par3;
        this.rectHeight = par4;
    }
    
    public Rect2i intersection(final Rect2i par1Rect2i) {
        final int var2 = this.rectX;
        final int var3 = this.rectY;
        final int var4 = this.rectX + this.rectWidth;
        final int var5 = this.rectY + this.rectHeight;
        final int var6 = par1Rect2i.getRectX();
        final int var7 = par1Rect2i.getRectY();
        final int var8 = var6 + par1Rect2i.getRectWidth();
        final int var9 = var7 + par1Rect2i.getRectHeight();
        this.rectX = Math.max(var2, var6);
        this.rectY = Math.max(var3, var7);
        this.rectWidth = Math.max(0, Math.min(var4, var8) - this.rectX);
        this.rectHeight = Math.max(0, Math.min(var5, var9) - this.rectY);
        return this;
    }
    
    public int getRectX() {
        return this.rectX;
    }
    
    public int getRectY() {
        return this.rectY;
    }
    
    public int getRectWidth() {
        return this.rectWidth;
    }
    
    public int getRectHeight() {
        return this.rectHeight;
    }
}
