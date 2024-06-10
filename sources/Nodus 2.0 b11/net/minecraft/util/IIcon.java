package net.minecraft.util;

public abstract interface IIcon
{
  public abstract int getIconWidth();
  
  public abstract int getIconHeight();
  
  public abstract float getMinU();
  
  public abstract float getMaxU();
  
  public abstract float getInterpolatedU(double paramDouble);
  
  public abstract float getMinV();
  
  public abstract float getMaxV();
  
  public abstract float getInterpolatedV(double paramDouble);
  
  public abstract String getIconName();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.IIcon
 * JD-Core Version:    0.7.0.1
 */