package mods.togglesprint.me.imfr0zen.guiapi.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import mods.togglesprint.me.imfr0zen.guiapi.Colors;
import mods.togglesprint.me.imfr0zen.guiapi.RenderUtil;
import mods.togglesprint.me.jannik.utils.ColorUtils;

import org.lwjgl.input.Mouse;

public class GuiToggleButton
  implements GuiComponent
{
  private boolean toggled;
  private int posX;
  private int posY;
  private String text;
  private ArrayList<ActionListener> clicklisteners = new ArrayList();
  
  public GuiToggleButton(String text)
  {
    this.text = text;
  }
  
  public void addClickListener(ActionListener actionlistener)
  {
    this.clicklisteners.add(actionlistener);
  }
  
  public boolean isToggled()
  {
    return this.toggled;
  }
  
  public void setToggled(boolean toggled)
  {
    this.toggled = toggled;
  }
  
  public void render(int posX, int posY, int width, int mouseX, int mouseY)
  {
    this.posX = posX;
    this.posY = posY;
    
    int w = RenderUtil.getWidth(this.text);
    
    int color = 1157627904;
    if (this.toggled)
    {
        int index = 0;
        long x = 0;
      color = ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB();
      if (RenderUtil.isHovered(posX + w + 8, posY, 10, 10, mouseX, mouseY)) {
        if (Mouse.getEventButtonState()) {
          color = Colors.buttonColorDark;
        } else {
          color = Colors.buttonColor;
        }
      }
    }
    int i = posX + w + 8;
    int i0 = posX + w + 18;
    int i1 = posY + 11;
    
    RenderUtil.drawRect(i + 1, posY + 2, i0, i1, color);
    
    RenderUtil.drawHorizontalLine(i, i0, posY + 1, -13224394);
    RenderUtil.drawHorizontalLine(i, i0, i1, -13224394);
    
    RenderUtil.drawVerticalLine(i, posY, i1, -13224394);
    RenderUtil.drawVerticalLine(i0, posY, i1, -13224394);
    
    RenderUtil.drawString(this.text, posX + 3, posY + 3, -5395027);
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton)
  {
    int w = RenderUtil.getWidth(this.text);
    if (RenderUtil.isHovered(this.posX + w + 8, this.posY, 10, 10, mouseX, mouseY))
    {
      this.toggled = (!this.toggled);
      for (ActionListener listener : this.clicklisteners) {
        listener.actionPerformed(new ActionEvent(this, hashCode(), "click", System.currentTimeMillis(), 0));
      }
    }
  }
  
  public void keyTyped(int keyCode, char typedChar) {}
  
  public int getWidth()
  {
    return RenderUtil.getWidth(this.text) + 22;
  }
  
  public int getHeight()
  {
    return 13;
  }
}
