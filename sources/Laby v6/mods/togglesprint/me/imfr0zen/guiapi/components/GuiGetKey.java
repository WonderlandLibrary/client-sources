package mods.togglesprint.me.imfr0zen.guiapi.components;

import java.util.ArrayList;
import mods.togglesprint.me.imfr0zen.guiapi.Colors;
import mods.togglesprint.me.imfr0zen.guiapi.RenderUtil;
import mods.togglesprint.me.imfr0zen.guiapi.listeners.KeyListener;
import mods.togglesprint.me.jannik.utils.ColorUtils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiGetKey
  implements GuiComponent
{
  private boolean wasChanged;
  private int key;
  private int blinkState;
  private int posX;
  private int posY;
  private String text;
  private ArrayList<KeyListener> keylisteners = new ArrayList();
  
  public GuiGetKey(int key)
  {
    this("Key:", key);
  }
  
  public GuiGetKey(String text, int key)
  {
    this.key = key;
    this.blinkState = -1;
    this.wasChanged = false;
    this.text = text;
  }
  
  public void addKeyListener(KeyListener listener)
  {
    this.keylisteners.add(listener);
  }
  
  public void render(int posX, int posY, int width, int mouseX, int mouseY)
  {
    this.posX = posX;
    this.posY = posY;
    if (this.key == 211) {
      this.key = 0;
    }
    String keyString = Keyboard.getKeyName(this.key);
    
    int w = RenderUtil.getWidth(this.text);
    int w0 = RenderUtil.getWidth(keyString);
    
    int index = 0;
    long x = 0;
    int color = ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB();
    if (RenderUtil.isHovered(posX + w + 8, posY, 10, 10, mouseX, mouseY)) {
      if (Mouse.getEventButtonState()) {
        color = ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB();
      } else {
        color = ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB();
      }
    }
    int i = posX + w + w0 + 11;
    
    RenderUtil.drawRect(posX + w + 8, posY, i, posY + 11, color);
    
    RenderUtil.drawHorizontalLine(posX + w + 7, i, posY, -13224394);
    RenderUtil.drawHorizontalLine(posX + w + 7, i, posY + 11, -13224394);
    
    RenderUtil.drawVerticalLine(posX + w + 7, posY, posY + 11, -13224394);
    RenderUtil.drawVerticalLine(i, posY, posY + 11, -13224394);
    
    RenderUtil.drawString(this.text, posX + 3, posY + 2, 13158600);
    RenderUtil.drawString(keyString, posX + 10 + w, posY + 2, 13158600);
    if (this.blinkState != -1)
    {
      this.blinkState += 1;
      if (this.blinkState < 20) {
        RenderUtil.drawHorizontalLine(posX + 10 + w, posX + 9 + w + w0, posY + 10, -3618616);
      } else if (this.blinkState > 40) {
        this.blinkState = 0;
      }
    }
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton)
  {
    String keyString = Keyboard.getKeyName(this.key);
    
    int w = RenderUtil.getWidth(this.text);
    int w0 = RenderUtil.getWidth(keyString);
    
    boolean b = true;
    if ((RenderUtil.isHovered(this.posX + w + 8, this.posY, w0 + 11, 11, mouseX, mouseY)) && (this.blinkState == -1))
    {
      this.blinkState = 11;
      b = false;
    }
    if ((this.blinkState != -1) && (b)) {
      this.blinkState = -1;
    }
  }
  
  public void keyTyped(int keyCode, char typedChar)
  {
    if (this.blinkState != -1)
    {
      for (KeyListener listener : this.keylisteners) {
        listener.keyChanged(keyCode);
      }
      this.key = keyCode;
      this.blinkState = -1;
    }
  }
  
  public int getWidth()
  {
    return RenderUtil.getWidth(this.text + Keyboard.getKeyName(this.key)) + 16;
  }
  
  public int getHeight()
  {
    return 14;
  }
}
