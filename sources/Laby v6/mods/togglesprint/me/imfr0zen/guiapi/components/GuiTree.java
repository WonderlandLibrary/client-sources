package mods.togglesprint.me.imfr0zen.guiapi.components;

import java.util.List;
import mods.togglesprint.me.imfr0zen.guiapi.Colors;
import mods.togglesprint.me.imfr0zen.guiapi.RenderUtil;
import org.lwjgl.input.Mouse;

public class GuiTree
  implements GuiComponent
{
  private boolean extended;
  private int posX;
  private int posY;
  private String text;
  private GuiComponent[] components;
  
  public GuiTree(String text, GuiComponent... components)
  {
    this.components = components;
    this.extended = false;
    this.text = text;
  }
  
  public GuiTree(String text, List<GuiComponent> components)
  {
    this(text, (GuiComponent[])components.toArray(new GuiComponent[components.size()]));
  }
  
  public void render(int posX, int posY, int width, int mouseX, int mouseY)
  {
    this.posX = posX;
    this.posY = posY;
    
    int color = 1157627904;
    if (this.extended)
    {
      color = Colors.buttonColorLight;
      if (RenderUtil.isHovered(posX + 2, posY, 10, posY + 11, mouseX, mouseY)) {
        if (Mouse.getEventButtonState()) {
          color = Colors.buttonColorDark;
        } else {
          color = Colors.buttonColor;
        }
      }
    }
    RenderUtil.drawRect(posX + 2, posY, posX + 13, posY + 11, color);
    
    RenderUtil.drawHorizontalLine(posX + 2, posX + 13, posY, -13224394);
    RenderUtil.drawHorizontalLine(posX + 2, posX + 13, posY + 11, -13224394);
    
    RenderUtil.drawVerticalLine(posX + 2, posY, posY + 11, -13224394);
    RenderUtil.drawVerticalLine(posX + 13, posY, posY + 11, -13224394);
    
    RenderUtil.drawString(this.text, posX + 16, posY + 3, 13158600);
    if (this.extended)
    {
      int height = 12;
      for (int i = 0; i < this.components.length; i++)
      {
        this.components[i].render(posX + 10, posY + height, width, mouseX, mouseY);
        height += this.components[i].getHeight();
      }
    }
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton)
  {
    if ((mouseButton == 0) && (RenderUtil.isHovered(this.posX + 2, this.posY, 11, 11, mouseX, mouseY))) {
      this.extended = (!this.extended);
    }
    if (this.extended) {
      for (int i = 0; i < this.components.length; i++) {
        this.components[i].mouseClicked(mouseX, mouseY, mouseButton);
      }
    }
  }
  
  public void keyTyped(int keyCode, char typedChar)
  {
    if (this.extended) {
      for (int i = 0; i < this.components.length; i++) {
        this.components[i].keyTyped(keyCode, typedChar);
      }
    }
  }
  
  public int getWidth()
  {
    int width = RenderUtil.getWidth(this.text) + 19;
    if (this.extended)
    {
      GuiComponent[] arrayOfGuiComponent;
      int j = (arrayOfGuiComponent = this.components).length;
      for (int i = 0; i < j; i++)
      {
        GuiComponent component = arrayOfGuiComponent[i];
        width = Math.max(width, component.getWidth() + 8);
      }
    }
    return width;
  }
  
  public int getHeight()
  {
    int i = 13;
    if (this.extended) {
      for (int j = 0; j < this.components.length; j++) {
        i += this.components[j].getHeight();
      }
    }
    return i;
  }
}
