package org.darkstorm.minecraft.gui.component;

import java.awt.Rectangle;
import java.util.Map;
import org.darkstorm.minecraft.gui.layout.Constraint;
import org.darkstorm.minecraft.gui.layout.LayoutManager;
import org.darkstorm.minecraft.gui.theme.ComponentUI;
import org.darkstorm.minecraft.gui.theme.Theme;

public abstract class AbstractContainer extends AbstractComponent implements Container
{
  private final Map<Component, Constraint[]> children = new java.util.LinkedHashMap();
  
  private LayoutManager layoutManager = new org.darkstorm.minecraft.gui.layout.BasicLayoutManager();
  
  public AbstractContainer() {}
  
  public void render() { super.render();
    
    synchronized (children) {
      for (Component child : children.keySet())
        child.render();
    }
  }
  
  public LayoutManager getLayoutManager() {
    return layoutManager;
  }
  
  public void setLayoutManager(LayoutManager layoutManager) {
    if (layoutManager == null)
      layoutManager = new org.darkstorm.minecraft.gui.layout.BasicLayoutManager();
    this.layoutManager = layoutManager;
    
    layoutChildren();
  }
  
  public Component[] getChildren() {
    synchronized (children) {
      return (Component[])children.keySet().toArray(new Component[children.size()]);
    }
  }
  
  public void add(Component child, Constraint... constraints) {
    synchronized (children) {
      Container parent = child.getParent();
      if ((parent != null) && (parent.hasChild(child)))
        parent.remove(child);
      children.put(child, constraints);
      if (!enabled)
        child.setEnabled(false);
      if (!visible)
        child.setVisible(false);
      child.setParent(this);
      child.setTheme(getTheme());
      
      layoutChildren();
    }
  }
  
  public Constraint[] getConstraints(Component child)
  {
    if (child == null)
      throw new NullPointerException();
    synchronized (children) {
      Constraint[] constraints = (Constraint[])children.get(child);
      return constraints != null ? constraints : new Constraint[0];
    }
  }
  
  public Component getChildAt(int x, int y) {
    synchronized (children) {
      for (Component child : children.keySet())
        if (child.getArea().contains(x, y))
          return child;
      return null;
    }
  }
  
  public boolean remove(Component child) {
    synchronized (children) {
      if (children.remove(child) != null) {
        layoutChildren();
        return true;
      }
      return false;
    }
  }
  
  public boolean hasChild(Component child) {
    synchronized (children) {
      return children.get(child) != null;
    }
  }
  
  public void setTheme(Theme theme)
  {
    super.setTheme(theme);
    
    synchronized (children) {
      for (Component child : children.keySet()) {
        child.setTheme(theme);
      }
    }
  }
  
  public void layoutChildren() {
    synchronized (children) {
      Component[] components = (Component[])children.keySet().toArray(
        new Component[children.size()]);
      Rectangle[] areas = new Rectangle[components.length];
      for (int i = 0; i < components.length; i++)
        areas[i] = components[i].getArea();
      Constraint[][] allConstraints = (Constraint[][])children.values().toArray(
        new Constraint[children.size()][]);
      if (getTheme() != null)
        layoutManager.reposition(ui.getChildRenderArea(this), areas, 
          allConstraints);
      for (Component child : components) {
        if ((child instanceof Container))
          ((Container)child).layoutChildren();
      }
    }
  }
  
  public void onMousePress(int x, int y, int button) {
    super.onMousePress(x, y, button);
    synchronized (children) {
      for (Component child : children.keySet()) {
        if (child.isVisible())
        {
          if (!child.getArea().contains(x, y))
          {
            Rectangle[] arrayOfRectangle;
            int j = (arrayOfRectangle = child.getTheme().getUIForComponent(child).getInteractableRegions(child)).length; for (int i = 0; i < j; i++)
            {
              Rectangle area = arrayOfRectangle[i];
              if (area.contains(x - child.getX(), y - child.getY())) {
                child.onMousePress(x - child.getX(), 
                  y - child.getY(), button);
                return;
              }
            }
          } }
      }
      for (Component child : children.keySet()) {
        if (child.isVisible())
        {
          if (child.getArea().contains(x, y)) {
            child.onMousePress(x - child.getX(), y - child.getY(), 
              button);
            return;
          }
        }
      }
    }
  }
  
  public void onMouseRelease(int x, int y, int button)
  {
    super.onMouseRelease(x, y, button);
    synchronized (children) {
      for (Component child : children.keySet()) {
        if (child.isVisible())
        {
          if (!child.getArea().contains(x, y))
          {
            Rectangle[] arrayOfRectangle;
            int j = (arrayOfRectangle = child.getTheme().getUIForComponent(child).getInteractableRegions(child)).length; for (int i = 0; i < j; i++)
            {
              Rectangle area = arrayOfRectangle[i];
              if (area.contains(x - child.getX(), y - child.getY())) {
                child.onMouseRelease(x - child.getX(), 
                  y - child.getY(), button);
                return;
              }
            }
          } }
      }
      for (Component child : children.keySet()) {
        if (child.isVisible())
        {
          if (child.getArea().contains(x, y)) {
            child.onMouseRelease(x - child.getX(), y - child.getY(), 
              button);
            return;
          }
        }
      }
    }
  }
  
  public void setEnabled(boolean enabled)
  {
    super.setEnabled(enabled);
    enabled = isEnabled();
    synchronized (children) {
      for (Component child : children.keySet()) {
        child.setEnabled(enabled);
      }
    }
  }
  
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    visible = isVisible();
    synchronized (children) {
      for (Component child : children.keySet()) {
        child.setVisible(visible);
      }
    }
  }
  
  public void update() {
    for (Component child : getChildren()) {
      child.update();
    }
  }
}
