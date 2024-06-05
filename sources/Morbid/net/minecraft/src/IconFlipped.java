package net.minecraft.src;

public class IconFlipped implements Icon
{
    private final Icon baseIcon;
    private final boolean flipU;
    private final boolean flipV;
    
    public IconFlipped(final Icon par1Icon, final boolean par2, final boolean par3) {
        this.baseIcon = par1Icon;
        this.flipU = par2;
        this.flipV = par3;
    }
    
    @Override
    public int getOriginX() {
        return this.baseIcon.getOriginX();
    }
    
    @Override
    public int getOriginY() {
        return this.baseIcon.getOriginY();
    }
    
    @Override
    public float getMinU() {
        return this.flipU ? this.baseIcon.getMaxU() : this.baseIcon.getMinU();
    }
    
    @Override
    public float getMaxU() {
        return this.flipU ? this.baseIcon.getMinU() : this.baseIcon.getMaxU();
    }
    
    @Override
    public float getInterpolatedU(final double par1) {
        final float var3 = this.getMaxU() - this.getMinU();
        return this.getMinU() + var3 * ((float)par1 / 16.0f);
    }
    
    @Override
    public float getMinV() {
        return this.flipV ? this.baseIcon.getMinV() : this.baseIcon.getMinV();
    }
    
    @Override
    public float getMaxV() {
        return this.flipV ? this.baseIcon.getMinV() : this.baseIcon.getMaxV();
    }
    
    @Override
    public float getInterpolatedV(final double par1) {
        final float var3 = this.getMaxV() - this.getMinV();
        return this.getMinV() + var3 * ((float)par1 / 16.0f);
    }
    
    @Override
    public String getIconName() {
        return this.baseIcon.getIconName();
    }
    
    @Override
    public int getSheetWidth() {
        return this.baseIcon.getSheetWidth();
    }
    
    @Override
    public int getSheetHeight() {
        return this.baseIcon.getSheetHeight();
    }
}
