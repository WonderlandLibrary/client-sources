package lunadevs.luna.gui.button;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class BlackButtons
  extends GuiButton
{
  private int x;
  private int y;
  private int x1;
  private int y1;
  public String displayString;
  int alphaInc;
  int alpha;
  int size;
  
  public BlackButtons(int par1, int par2, int par3, int par4, int par5, String par6Str)
  {
    super(par1, par2, par3, par4, par5, par6Str);
    this.alphaInc = 100;
    this.alpha = 0;
    this.size = 0;
    this.x = par2;
    this.y = par3;
    this.x1 = par4;
    this.y1 = par5;
    this.displayString = par6Str;
  }
  
  public BlackButtons(int i, int d, int k, String stringParams)
  {
    this(i, d, k, 200, 20, stringParams);
  }
  
  public void drawButton(Minecraft mc, int mouseX, int mouseY)
  {
    boolean isOverButton = (mouseX >= this.x) && (mouseX <= this.x + this.x1) && (mouseY >= this.y) && (
      mouseY <= this.y + this.y1);
    if ((isOverButton) && (this.alphaInc >= 100))
    {
      this.alphaInc -= 30;
      this.alpha = (this.alphaInc << 24);
    }
    else if ((!isOverButton) && (this.alphaInc <= 150))
    {
      this.alphaInc += 25;
      this.alpha = (this.alphaInc << 24);
    }
    if (this.alphaInc > 150) {
      this.alphaInc = 150;
    } else if (this.alphaInc < 100) {
      this.alphaInc = 100;
    }
    if ((isOverButton) && (this.size >= 0)) {
      this.size -= 0;
    } else if ((!isOverButton) && (this.size <= 0.9D)) {
      this.size += 0;
    }
    if (!isOverButton)
    {
      Gui.drawRect(this.x - this.size, this.y - this.size, this.x + this.x1 + this.size, 
        this.y + this.y1 + this.size, this.alpha);
      drawCenteredString(Minecraft.fontRendererObj, this.displayString, this.x + this.x1 / 2, 
        this.y + this.y1 / 2 - 4, -1);
    }
    else
    {
      Gui.drawRect(this.x - this.size + 1, this.y - this.size + 1, this.x + this.x1 + this.size - 1, 
        this.y + this.y1 + this.size - 1, YAAAAAA(20000000000000L, 1.0F).getRGB());
      drawCenteredString(Minecraft.fontRendererObj, this.displayString, this.x + this.x1 / 2, 
        this.y + this.y1 / 2 - 4, -1);
    }
  }
  
  public static Color YAAAAAA(long offset, float fade)
  {
    float hue = (float)(System.nanoTime() + offset) / 1.0E10F % 1.0F;
    long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 
      16);
    Color c = new Color((int)color);
    return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, 
      c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
  }
}
