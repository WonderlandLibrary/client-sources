package net.SliceClient.clickgui;

import java.util.List;
import net.SliceClient.Gui.RenderHelper;
import net.SliceClient.TTF.TTFManager;
import net.SliceClient.TTF.TTFRenderer;
import net.SliceClient.Utils.Colors;
import net.SliceClient.module.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;

public class Panel
{
  private String title;
  private Category category;
  private int x;
  private int y;
  private int x2;
  private int y2;
  private int width;
  private int height;
  public boolean open;
  private boolean drag;
  private int scroll;
  private final List<Element> elements = new java.util.ArrayList();
  private boolean scrollbar = false;
  private int dragged;
  
  public Panel(String title, Category category, int x, int y, int width, int height, boolean open) {
    this.title = title;
    this.category = category;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.open = open;
    setupItems();
  }
  
  public void setupItems() {}
  
  public int getElementsHeight()
  {
    int height = 0;
    int count = 0;
    for (Element element : elements)
      if (count < 10) {
        height += element.getHeight() + 1;
        count++;
      }
    return height;
  }
  
  public boolean isHover(int mouseX, int mouseY) {
    Minecraft.getMinecraft();float textWidth = Minecraft.fontRendererObj.getStringWidth(title) - 100.0F;
    if ((mouseX >= x) && (mouseX <= x + getWidth()) && (mouseY >= y)) if (mouseY <= y + height - (open ? 2 : 0)) {
        return true;
      }
    return false;
  }
  
  public void drag(int mouseX, int mouseY) {
    if (drag) {
      x = (x2 + mouseX);
      y = (y2 + mouseY);
    }
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    if ((mouseButton == 0) && (isHover(mouseX, mouseY))) {
      x2 = (x - mouseX);
      y2 = (y - mouseY);
      drag = true;
      return;
    }
    if ((mouseButton == 1) && (isHover(mouseX, mouseY))) {
      open = (!open);
      return;
    }
    if (!open) {
      return;
    }
    for (Element element : elements) {
      element.mouseClicked(mouseX, mouseY, mouseButton);
    }
  }
  
  public void mouseReleased(int mouseX, int mouseY, int state) {
    if (state == 0) {
      drag = false;
    }
    if (!open) {
      return;
    }
    for (Element element : elements) {
      element.mouseReleased(mouseX, mouseY, state);
    }
  }
  
  public void drawScreen(int mouseX, int mouseY, float button)
  {
    drag(mouseX, mouseY);
    float elementsHeight = open ? getElementsHeight() - 1 : 0;
    Minecraft.getMinecraft();float textHeight = Minecraft.fontRendererObj.getStringWidth(title);
    boolean scrollbar; boolean bl = scrollbar = elements.size() >= 10 ? 1 : 0;
    if (this.scrollbar != scrollbar) {
      this.scrollbar = scrollbar;
    }
    int elementWidth = 0;
    for (Element e : getElements()) {
      if (e.getWidth() > elementWidth) {
        elementWidth = e.getWidth();
      }
    }
    RenderHelper.drawRect(getX() - 4, getY(), getX() + (scrollbar ? 4 : 0) + 100, getY() + 16, Colors.getColor(96, 99, 192, 255));
    TTFManager.getInstance().getAliveFontB().drawString(getTitle(), getX() + 5, getY() + 1, -1);
    if ((Mouse.hasWheel()) && (mouseX >= getX()) && (mouseX <= getX() + 100) && (mouseY >= getY()) && (mouseY <= getY() + 19 + elementsHeight)) {
      int wheel = Mouse.getDWheel();
      if ((wheel < 0) && (scroll < elements.size() - 10)) {
        scroll += 1;
        if (scroll < 0) {
          scroll = 0;
        }
      } else if (wheel > 0) {
        scroll -= 1;
        if (scroll < 0) {
          scroll = 0;
        }
      }
      if (wheel < 0) {
        if (dragged < elements.size() - 10) {
          dragged += 1;
        }
      } else if ((wheel > 0) && (dragged >= 1)) {
        dragged -= 1;
      }
    }
    if (open)
    {
      TTFManager.getInstance().getStandardFont().drawString("-", getX() + getWidth() - 14, getY() + 1, -1);
      RenderHelper.drawRect(getX(), getY() + 16, getX() + (scrollbar ? 4 : 0) + 98, getY() + 19 + elementsHeight, -1728053248);
      RenderHelper.drawRect(getX(), getY() + 16, getX() + (scrollbar ? 4 : 0) + 98, getY() + 19 + elementsHeight, -1728053248);
      

      if (scrollbar) {
        RenderHelper.drawRect(getX() - 2, getY() + 21, getX(), getY() + 16 + elementsHeight, Colors.getColor(96, 99, 192, 255));
        RenderHelper.drawRect(getX() - 2, getY() + 30 + (elementsHeight - 24.0F) / (elements.size() - 10) * dragged - 10.0F, getX(), getY() + 40 + (elementsHeight - 24.0F) / (elements.size() - 10) * dragged, -1);
      }
      
      int y = this.y + height - 2;
      int count = 0;
      elements.size();
      for (Element element : elements) {
        count++; if ((count > scroll) && (count < scroll + 11) && (scroll < elements.size())) {
          element.setLoc(x + 2, y);
          element.setWidth(getWidth() - 4);
          element.drawScreen(mouseX, mouseY, button);
          y += element.getHeight() + 1;
        }
      }
    } else { TTFManager.getInstance().getStandardFont().drawString("I", getX() + getWidth() - 14, getY() + 1, -1);
    }
  }
  
  public String getTitle() {
    return title;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public List<Element> getElements() {
    return elements;
  }
  
  public void setX(int dragX)
  {
    x = dragX;
  }
  
  public void setY(int dragY)
  {
    y = dragY;
  }
  
  public boolean isOpen() {
    return open;
  }
  
  public Panel setOpen(boolean open)
  {
    this.open = open;
    
    return this;
  }
}
