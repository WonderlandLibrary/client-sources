package mods.togglesprint.me.imfr0zen.guiapi.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import mods.togglesprint.me.imfr0zen.guiapi.ClickGui;
import mods.togglesprint.me.imfr0zen.guiapi.Colors;
import mods.togglesprint.me.imfr0zen.guiapi.GuiFrame;
import mods.togglesprint.me.imfr0zen.guiapi.RenderUtil;
import mods.togglesprint.me.imfr0zen.guiapi.example.Settings;
import mods.togglesprint.me.imfr0zen.guiapi.listeners.ExtendListener;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;
import mods.togglesprint.me.jannik.utils.ColorUtils;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;

public class Button
  implements GuiComponent
{
  public static int extendedId = -1;
  private int id;
  private int textWidth;
  private int width;
  private int posX;
  private int posY;
  private String text;
  private ArrayList<ActionListener> clicklisteners = new ArrayList();
  private ArrayList<GuiComponent> guicomponents = new ArrayList();
  
  public Button(String text)
  {
    this.text = text;
    this.textWidth = ClickGui.FONTRENDERER.getStringWidth(getText());
    this.id = (++ClickGui.currentId);
  }
  
  public void addClickListener(ActionListener actionlistener)
  {
    this.clicklisteners.add(actionlistener);
  }
  
  public void addExtendListener(ExtendListener listener)
  {
    listener.addComponents();
    
    this.guicomponents.addAll(listener.getComponents());
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public int getButtonId()
  {
    return this.id;
  }
  
  public void render(int posX, int posY, int width, int mouseX, int mouseY)
  {
    this.posX = posX;
    this.posY = posY;
    
    this.width = width;
    
    int height = getHeight();
    int color;
	int index = 0;
	long x = 0;
    if ((Jannik.getModuleManager().getModuleByName(getText()) != null) && (Jannik.getModuleManager().getModuleByName(getText()).isEnabled())) {
      color = ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB();
    } else {
      color = ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB();
    }
    if (RenderUtil.isHovered(posX, posY, width, height, mouseX, mouseY))
    {
      color = ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB();
      if (Mouse.getEventButtonState()) {
        color = ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB();
      }
    }
    RenderUtil.drawRect(posX, posY, posX + width - 1, posY + height, color);
    
    RenderUtil.drawString(this.text, posX + 1, posY + 2, -5395027);
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton)
  {
    if ((GuiFrame.draggingId == -1) && (RenderUtil.isHovered(this.posX, this.posY, this.width, getHeight(), mouseX, mouseY))) {
      if (mouseButton == 1)
      {
        if (extendedId != this.id) {
          extendedId = this.id;
        } else {
          extendedId = -1;
        }
      }
      else if (mouseButton == 0) {
        for (ActionListener listener : this.clicklisteners) {
          listener.actionPerformed(new ActionEvent(this, this.id, "click", System.currentTimeMillis(), 0));
        }
      }
    }
    if (extendedId == this.id) {
      for (GuiComponent component : this.guicomponents) {
        component.mouseClicked(mouseX, mouseY, mouseButton);
      }
    }
  }
  
  public void keyTyped(int keyCode, char typedChar)
  {
    if (extendedId == this.id) {
      for (GuiComponent component : this.guicomponents) {
        component.keyTyped(keyCode, typedChar);
      }
    }
  }
  
  public int getWidth()
  {
    return this.textWidth + 5;
  }
  
  public int getHeight()
  {
    return ClickGui.FONTRENDERER.FONT_HEIGHT + 3;
  }
  
  public ArrayList<GuiComponent> getComponents()
  {
    return this.guicomponents;
  }
}
