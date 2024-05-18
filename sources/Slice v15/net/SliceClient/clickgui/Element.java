package net.SliceClient.clickgui;

public class Element { protected int x;
  protected int y;
  protected int width;
  protected int height;
  
  public Element() {}
  
  public void setLoc(int x, int y) { this.x = x;
    this.y = y;
  }
  

  public void drawScreen(int mouseX, int mouseY, float button) {}
  

  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
  
  public void mouseReleased(int mouseX, int mouseY, int state) {}
  
  public int getX()
  {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public void setWidth(int width) {
    this.width = width;
  }
  
  public void setHeight(int height) {
    this.height = height;
  }
}
