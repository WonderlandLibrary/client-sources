package mods.togglesprint.me.imfr0zen.guiapi.components;

import mods.togglesprint.me.imfr0zen.guiapi.ClickGui;
import mods.togglesprint.me.imfr0zen.guiapi.RenderUtil;
import net.minecraft.client.gui.FontRenderer;

public class GuiLabel
  implements GuiComponent
{
  private String text;
  
  public GuiLabel(String text)
  {
    this.text = text;
  }
  
  public void render(int posX, int posY, int width, int mouseX, int mouseY)
  {
    RenderUtil.drawString(this.text, posX + 3, posY + 2, -5395027);
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
  
  public void keyTyped(int keyCode, char typedChar) {}
  
  public int getWidth()
  {
    return ClickGui.FONTRENDERER.getStringWidth(this.text) + 4;
  }
  
  public int getHeight()
  {
    return ClickGui.FONTRENDERER.FONT_HEIGHT + 2;
  }
}
