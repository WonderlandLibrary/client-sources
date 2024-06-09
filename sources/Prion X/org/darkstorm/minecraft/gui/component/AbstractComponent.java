package org.darkstorm.minecraft.gui.component;

import java.awt.Dimension;
import java.awt.Rectangle;
import org.darkstorm.minecraft.gui.theme.ComponentUI;

public abstract class AbstractComponent implements Component
{
  public AbstractComponent() {}
  
  private Container parent = null;
  private org.darkstorm.minecraft.gui.theme.Theme theme;
  
  protected Rectangle area = new Rectangle(0, 0, 0, 0);
  protected ComponentUI ui;
  protected java.awt.Color foreground;
  protected java.awt.Color background; protected boolean enabled = true; protected boolean visible = true;
  
  private java.util.List<org.darkstorm.minecraft.gui.listener.ComponentListener> listeners = new java.util.concurrent.CopyOnWriteArrayList();
  
  public void render() {
    if (ui == null)
      return;
    ui.render(this);
  }
  
  public void update()
  {
    if (ui == null)
      return;
    ui.handleUpdate(this);
  }
  
  protected ComponentUI getUI() {
    return theme.getUIForComponent(this);
  }
  
  public void onMousePress(int x, int y, int button)
  {
    if (ui != null) {
      for (Rectangle area : ui.getInteractableRegions(this)) {
        if (area.contains(x, y)) {
          ui.handleInteraction(this, new java.awt.Point(x, y), button);
          break;
        }
      }
    }
  }
  

  public void onMouseRelease(int x, int y, int button) {}
  
  public org.darkstorm.minecraft.gui.theme.Theme getTheme()
  {
    return theme;
  }
  
  public void setTheme(org.darkstorm.minecraft.gui.theme.Theme theme) {
    org.darkstorm.minecraft.gui.theme.Theme oldTheme = this.theme;
    this.theme = theme;
    if (theme == null) {
      ui = null;
      foreground = null;
      background = null;
      return;
    }
    
    ui = getUI();
    boolean changeArea;
    boolean changeArea; if (oldTheme != null) {
      Dimension defaultSize = oldTheme.getUIForComponent(this).getDefaultSize(this);
      changeArea = (area.width == width) && (area.height == height);
    } else {
      changeArea = area.equals(new Rectangle(0, 0, 0, 0)); }
    if (changeArea) {
      Dimension defaultSize = ui.getDefaultSize(this);
      area = new Rectangle(area.x, area.y, width, height);
    }
    foreground = ui.getDefaultForegroundColor(this);
    background = ui.getDefaultBackgroundColor(this);
  }
  
  public int getX() {
    return area.x;
  }
  
  public int getY() {
    return area.y;
  }
  
  public int getWidth() {
    return area.width;
  }
  
  public int getHeight() {
    return area.height;
  }
  
  public void setX(int x) {
    area.x = x;
  }
  
  public void setY(int y) {
    area.y = y;
  }
  
  public void setWidth(int width) {
    area.width = width;
  }
  
  public void setHeight(int height) {
    area.height = height;
  }
  
  public java.awt.Color getBackgroundColor()
  {
    return background;
  }
  
  public java.awt.Color getForegroundColor()
  {
    return foreground;
  }
  
  public void setBackgroundColor(java.awt.Color color)
  {
    background = color;
  }
  
  public void setForegroundColor(java.awt.Color color)
  {
    foreground = color;
  }
  
  public java.awt.Point getLocation() {
    return area.getLocation();
  }
  
  public Dimension getSize() {
    return area.getSize();
  }
  
  public Rectangle getArea() {
    return area;
  }
  
  public Container getParent() {
    return parent;
  }
  
  public void setParent(Container parent) {
    if ((!parent.hasChild(this)) || ((this.parent != null) && (this.parent.hasChild(this))))
      throw new IllegalArgumentException();
    this.parent = parent;
  }
  
  public void resize() {
    Dimension defaultDimension = ui.getDefaultSize(this);
    setWidth(width);
    setHeight(height);
  }
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public void setEnabled(boolean enabled) {
    if ((parent != null) && (!parent.isEnabled())) {
      this.enabled = false;
    } else
      this.enabled = enabled;
  }
  
  public boolean isVisible() {
    return visible;
  }
  
  public void setVisible(boolean visible) {
    if ((parent != null) && (!parent.isVisible())) {
      this.visible = false;
    } else
      this.visible = visible;
  }
  
  protected void addListener(org.darkstorm.minecraft.gui.listener.ComponentListener listener) {
    synchronized (listeners) {
      listeners.add(listener);
    }
  }
  
  protected void removeListener(org.darkstorm.minecraft.gui.listener.ComponentListener listener) {
    synchronized (listeners) {
      listeners.remove(listener);
    }
  }
  
  protected org.darkstorm.minecraft.gui.listener.ComponentListener[] getListeners() {
    synchronized (listeners) {
      return (org.darkstorm.minecraft.gui.listener.ComponentListener[])listeners.toArray(new org.darkstorm.minecraft.gui.listener.ComponentListener[listeners.size()]);
    }
  }
}
