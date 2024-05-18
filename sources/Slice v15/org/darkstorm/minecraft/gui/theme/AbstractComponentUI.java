package org.darkstorm.minecraft.gui.theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Container;
import org.lwjgl.opengl.GL11;


public abstract class AbstractComponentUI<T extends Component>
  implements ComponentUI
{
  protected final Class<T> handledComponentClass;
  protected Color foreground;
  protected Color background;
  
  public AbstractComponentUI(Class<T> handledComponentClass)
  {
    this.handledComponentClass = handledComponentClass;
  }
  
  public void render(Component component)
  {
    if (component == null) {
      throw new NullPointerException();
    }
    if (!handledComponentClass.isInstance(component)) {
      throw new IllegalArgumentException();
    }
    if (!component.isVisible()) {
      return;
    }
    renderComponent((Component)handledComponentClass.cast(component));
  }
  
  protected abstract void renderComponent(Component paramComponent);
  
  public Rectangle getChildRenderArea(Container container)
  {
    if (!Container.class.isAssignableFrom(handledComponentClass)) {
      throw new UnsupportedOperationException();
    }
    if (container == null) {
      throw new NullPointerException();
    }
    if (!handledComponentClass.isInstance(container)) {
      throw new IllegalArgumentException();
    }
    return getContainerChildRenderArea((Component)handledComponentClass.cast(container));
  }
  
  protected Rectangle getContainerChildRenderArea(Component component)
  {
    return new Rectangle(new Point(0, 0), component.getSize());
  }
  
  public Dimension getDefaultSize(Component component)
  {
    if (component == null) {
      throw new NullPointerException();
    }
    if (!handledComponentClass.isInstance(component)) {
      throw new IllegalArgumentException();
    }
    return getDefaultComponentSize((Component)handledComponentClass.cast(component));
  }
  
  protected abstract Dimension getDefaultComponentSize(Component paramComponent);
  
  protected void translateComponent(Component component, boolean reverse)
  {
    Component parent = component.getParent();
    while (parent != null)
    {
      GL11.glTranslated((reverse ? -1 : 1) * parent.getX(), (reverse ? -1 : 1) * parent.getY(), 0.0D);
      parent = parent.getParent();
    }
    GL11.glTranslated((reverse ? -1 : 1) * component.getX(), (reverse ? -1 : 1) * component.getY(), 0.0D);
  }
  
  public Color getDefaultBackgroundColor(Component component)
  {
    if (component == null) {
      throw new NullPointerException();
    }
    if (!handledComponentClass.isInstance(component)) {
      throw new IllegalArgumentException();
    }
    return getBackgroundColor((Component)handledComponentClass.cast(component));
  }
  
  protected Color getBackgroundColor(Component component)
  {
    return background;
  }
  
  public Color getDefaultForegroundColor(Component component)
  {
    if (component == null) {
      throw new NullPointerException();
    }
    if (!handledComponentClass.isInstance(component)) {
      throw new IllegalArgumentException();
    }
    return getForegroundColor((Component)handledComponentClass.cast(component));
  }
  
  protected Color getForegroundColor(Component component)
  {
    return foreground;
  }
  
  public Rectangle[] getInteractableRegions(Component component)
  {
    if (component == null) {
      throw new NullPointerException();
    }
    if (!handledComponentClass.isInstance(component)) {
      throw new IllegalArgumentException();
    }
    return getInteractableComponentRegions((Component)handledComponentClass.cast(component));
  }
  
  protected Rectangle[] getInteractableComponentRegions(Component component)
  {
    return new Rectangle[0];
  }
  
  public void handleInteraction(Component component, Point location, int button)
  {
    if (component == null) {
      throw new NullPointerException();
    }
    if (!handledComponentClass.isInstance(component)) {
      throw new IllegalArgumentException();
    }
    handleComponentInteraction((Component)handledComponentClass.cast(component), location, button);
  }
  
  protected void handleComponentInteraction(Component component, Point location, int button) {}
  
  public void handleUpdate(Component component)
  {
    if (component == null) {
      throw new NullPointerException();
    }
    if (!handledComponentClass.isInstance(component)) {
      throw new IllegalArgumentException();
    }
    handleComponentUpdate((Component)handledComponentClass.cast(component));
  }
  
  protected void handleComponentUpdate(Component component) {}
}
