package net.SliceClient.clickgui;

import net.minecraft.client.gui.FontRenderer;

public class Label
  extends Element
{
  private String text;
  private FontRenderer fr;
  
  public Label(FontRenderer fr, String text)
  {
    this.text = text;
    this.fr = fr;
  }
  
  public void drawScreen(int i, int j, float k)
  {
    fr.drawString(text, getX(), getY(), -1249039);
  }
  
  public int getHeight()
  {
    return fr.FONT_HEIGHT + 2;
  }
}
