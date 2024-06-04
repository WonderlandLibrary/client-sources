package yung.purity.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;






public class RenderUtil
{
  public RenderUtil() {}
  
  public static int width()
  {
    return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
  }
  
  public static int height()
  {
    return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
  }
  

  public static void drawBordered(double x, double y, double width, double height, double length, int innerColor, int outerColor)
  {
    Gui.drawRect(x, y, x + width, y + height, innerColor);
    Gui.drawRect(x - length, y, x, y + height, outerColor);
    Gui.drawRect(x - length, y - length, x + width, y, outerColor);
    Gui.drawRect(x + width, y - length, x + width + length, y + height + length, outerColor);
    Gui.drawRect(x - length, y + height, x + width, y + height + length, outerColor);
  }
}
