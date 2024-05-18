package mods.togglesprint.me.imfr0zen.guiapi.components;

import java.util.ArrayList;
import mods.togglesprint.me.imfr0zen.guiapi.Colors;
import mods.togglesprint.me.imfr0zen.guiapi.MathUtil;
import mods.togglesprint.me.imfr0zen.guiapi.RenderUtil;
import mods.togglesprint.me.imfr0zen.guiapi.listeners.ValueListener;
import mods.togglesprint.me.jannik.utils.ColorUtils;

import org.lwjgl.input.Mouse;

public class GuiSlider
  implements GuiComponent
{
  private static int dragId = -1;
  private boolean wasSliding;
  private float min;
  private float max;
  private float current;
  private int c;
  private int round;
  private int id;
  private String text;
  private ArrayList<ValueListener> vallisteners = new ArrayList();
  
  public GuiSlider(String text, float min, float max, float current)
  {
    this(text, min, max, current, 0);
  }
  
  public GuiSlider(String text, float min, float max, float current, int round)
  {
    this.text = text;
    this.min = min;
    this.max = max;
    this.current = current;
    this.c = ((int)(current > max ? 50.0F : current * 50.0F / max));
    this.round = round;
    this.id = (mods.togglesprint.me.imfr0zen.guiapi.ClickGui.currentId++);
  }
  
  public void addValueListener(ValueListener vallistener)
  {
    this.vallisteners.add(vallistener);
  }
  
  public void render(int posX, int posY, int width, int mouseX, int mouseY)
  {
    int w = RenderUtil.getWidth(this.text);
    
    boolean hovered = RenderUtil.isHovered(posX + w + 7, posY + 1, 50, 12, mouseX, mouseY);
    if ((Mouse.isButtonDown(0)) && ((dragId == this.id) || ((dragId == -1) && (hovered))))
    {
      if (mouseX < posX + w + 7)
      {
        this.current = this.min;
        this.c = 0;
      }
      else if (mouseX > posX + w + 57)
      {
        this.current = this.max;
        this.c = 50;
      }
      else
      {
        if (this.round == 0) {
          this.current = Math.round((mouseX - posX - w - 7.0F) / 50.0F * this.max);
        } else {
          this.current = MathUtil.round((mouseX - posX - w - 7) / 50.0F * this.max, this.round);
        }
        this.current += (this.current + this.min >= this.max ? 0.0F : this.min);
        
        this.c = (mouseX - posX - w - 7);
      }
      dragId = this.id;
      for (ValueListener listener : this.vallisteners) {
        listener.valueUpdated(this.current);
      }
      this.wasSliding = true;
    }
    else if ((!Mouse.isButtonDown(0)) && (this.wasSliding))
    {
      for (ValueListener listener : this.vallisteners) {
        listener.valueChanged(this.current);
      }
      dragId = -1;
      this.wasSliding = false;
    }
    int height = posY + 12;
    int i = posX + this.c + 11 + w;
    
    int index = 0;
    long x = 0;
    RenderUtil.drawRect(posX + this.c + 11 + w, posY + 1, posX + w + 61, height, ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB());
    RenderUtil.drawRect(posX + w + 8, posY + 1, i, height, -1155588321);
    RenderUtil.drawRect(posX + w + this.c + 8, posY + 1, i, height, -16777216);
    
    RenderUtil.drawHorizontalLine(posX + w + 7, posX + w + 61, posY, -13224394);
    RenderUtil.drawHorizontalLine(posX + w + 7, posX + w + 61, height, -13224394);
    
    RenderUtil.drawVerticalLine(posX + w + 7, posY, height, -13224394);
    RenderUtil.drawVerticalLine(posX + w + 61, posY, height, -13224394);
    
    RenderUtil.drawString(this.text, posX + 3, posY + 3, -5395027);
    String value;
    if (this.round == 0) {
      value = Integer.toString(Math.round(this.current));
    } else {
      value = Float.toString(MathUtil.round(this.current, this.round));
    }
    RenderUtil.drawString(value, posX + w + 64, posY + 3, -5395027);
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
  
  public void keyTyped(int keyCode, char typedChar) {}
  
  public int getWidth()
  {
    return RenderUtil.getWidth(this.text + (this.round == 0 ? Integer.toString(Math.round(this.current)) : 
      Float.toString(MathUtil.round(this.current, this.round)))) + 68;
  }
  
  public int getHeight()
  {
    return 15;
  }
}
