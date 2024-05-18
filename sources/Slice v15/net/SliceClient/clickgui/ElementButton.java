package net.SliceClient.clickgui;

import net.SliceClient.TTF.TTFManager;
import net.SliceClient.TTF.TTFRenderer;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;

public class ElementButton extends Element
{
  private Module module;
  private Category category;
  private boolean values;
  private float stringWidth;
  private float stringHeight;
  
  public ElementButton(Module module, Category category)
  {
    setModule(module);
    setCategory(category);
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton)
  {
    if ((mouseButton == 0) && (isHovering(mouseX, mouseY))) {
      getModule().toggleModule();
    }
  }
  

  public void drawScreen(int mouseX, int mouseY, float button)
  {
    float width = TTFManager.getInstance().getAliveFont().getWidth(getText());
    if (isHovering(mouseX, mouseY)) {
      net.SliceClient.Gui.RenderHelper.drawRect(getX() - 2, getY() + 2, getX() + getWidth(), getY() + 19, -1895825408);
    }
    module.isEnabled();
    

    TTFManager.getInstance().getStandardFont().drawString(getText(), getX() + 5, getY() + 4, module.isEnabled() ? net.SliceClient.Utils.Colors.getColor(332, 120, 889) : -1);
  }
  

  public int getHeight()
  {
    return 16;
  }
  
  public Module getModule() {
    return module;
  }
  
  public String getText() {
    return getModule().getName();
  }
  
  public Category getCategory() {
    return category;
  }
  
  public boolean getEnabled() {
    return getModule().isEnabled();
  }
  
  public boolean isHovering(int mouseX, int mouseY) {
    if ((mouseX >= getX()) && (mouseX <= getX() + getWidth()) && (mouseY >= getY()) && (mouseY <= getY() + 16)) {
      return true;
    }
    return false;
  }
  
  public boolean isHoveringSettings(int mouseX, int mouseY) {
    if ((mouseX >= getX() + getWidth() - 18) && (mouseX <= getX() + getWidth()) && (mouseY >= getY()) && (mouseY <= getY() + 16)) {
      return true;
    }
    return false;
  }
  
  public void setModule(Module module) {
    this.module = module;
  }
  
  public void setCategory(Category category) {
    this.category = category;
  }
}
