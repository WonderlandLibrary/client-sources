package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class ScaledResolution
{
  private final double scaledWidthD;
  private final double scaledHeightD;
  private int scaledWidth;
  private int scaledHeight;
  private int scaleFactor;
  private static final String __OBFID = "CL_00000666";
  
  public ScaledResolution(Minecraft mcIn)
  {
    scaledWidth = displayWidth;
    scaledHeight = displayHeight;
    scaleFactor = 1;
    boolean var4 = mcIn.isUnicode();
    int var5 = gameSettings.guiScale;
    
    if (var5 == 0)
    {
      var5 = 1000;
    }
    
    while ((scaleFactor < var5) && (scaledWidth / (scaleFactor + 1) >= 320) && (scaledHeight / (scaleFactor + 1) >= 240))
    {
      scaleFactor += 1;
    }
    
    if ((var4) && (scaleFactor % 2 != 0) && (scaleFactor != 1))
    {
      scaleFactor -= 1;
    }
    
    scaledWidthD = (scaledWidth / scaleFactor);
    scaledHeightD = (scaledHeight / scaleFactor);
    scaledWidth = MathHelper.ceiling_double_int(scaledWidthD);
    scaledHeight = MathHelper.ceiling_double_int(scaledHeightD);
  }
  
  public int getScaledWidth()
  {
    return scaledWidth;
  }
  
  public int getScaledHeight()
  {
    return scaledHeight;
  }
  
  public double getScaledWidth_double()
  {
    return scaledWidthD;
  }
  
  public double getScaledHeight_double()
  {
    return scaledHeightD;
  }
  
  public int getScaleFactor()
  {
    return scaleFactor;
  }
}
