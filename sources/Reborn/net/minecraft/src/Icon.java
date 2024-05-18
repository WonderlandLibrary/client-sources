package net.minecraft.src;

public interface Icon
{
    int getOriginX();
    
    int getOriginY();
    
    float getMinU();
    
    float getMaxU();
    
    float getInterpolatedU(final double p0);
    
    float getMinV();
    
    float getMaxV();
    
    float getInterpolatedV(final double p0);
    
    String getIconName();
    
    int getSheetWidth();
    
    int getSheetHeight();
}
