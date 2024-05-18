package net.SliceClient.Utils;

import java.awt.Color;















public class Rainbow
{
  public Rainbow() {}
  
  public static Color rainbow(float offset)
  {
    float hue = ((float)System.nanoTime() + offset) / 1.0E10F % 1.0F;
    long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
    Color c = new Color((int)color);
    return new Color(c.getRed() / 265.0F, c.getGreen() / 265.0F, c.getBlue() / 265.0F, c.getAlpha() / 265.0F);
  }
}
