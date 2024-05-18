package net.SliceClient.Utils;

import java.awt.Color;

public class Colors
{
  private static int clientColorRed = 66;
  private static int clientColorGreen = 18;
  private static int clientColorBlue = 220;
  
  public Colors() {}
  
  public static Color getClientColor() { return new Color(clientColorRed, clientColorGreen, clientColorBlue); }
  

  public static int getColor(Color color)
  {
    return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
  }
  
  public static int getColor(int red, int green, int blue, int alpha)
  {
    int color = 0;
    color |= alpha << 24;
    color |= red << 16;
    color |= green << 8;
    color |= blue;
    return color;
  }
  
  public static int getColor(int red, int green, int blue)
  {
    return getColor(red, green, blue, 255);
  }
  
  public static Color rainbow(float offset)
  {
    float hue = ((float)System.nanoTime() + offset) / 1.0E10F % 1.0F;
    long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 
      16);
    Color c = new Color((int)color);
    return new Color(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
  }
}
