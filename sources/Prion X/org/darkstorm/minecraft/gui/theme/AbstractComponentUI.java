package org.darkstorm.minecraft.gui.theme;

import java.awt.Color;
import org.darkstorm.minecraft.gui.component.Component;

public abstract class AbstractComponentUI<T extends Component> implements ComponentUI
{
  protected final Class<T> handledComponentClass;
  protected Color foreground;
  protected Color background;
  
  public AbstractComponentUI(Class<T> handledComponentClass)
  {
    this.handledComponentClass = handledComponentClass;
  }
  
  public void render(Component component) {
    if (component == null)
      throw new NullPointerException();
    if (!handledComponentClass.isInstance(component))
      throw new IllegalArgumentException();
    if (!component.isVisible())
      return;
    renderComponent((Component)handledComponentClass.cast(component));
  }
  
  protected abstract void renderComponent(T paramT);
  
  public java.awt.Rectangle getChildRenderArea(org.darkstorm.minecraft.gui.component.Container container)
  {
    if (!org.darkstorm.minecraft.gui.component.Container.class.isAssignableFrom(handledComponentClass))
      throw new UnsupportedOperationException();
    if (container == null)
      throw new NullPointerException();
    if (!handledComponentClass.isInstance(container))
      throw new IllegalArgumentException();
    return getContainerChildRenderArea((Component)handledComponentClass.cast(container));
  }
  
  protected java.awt.Rectangle getContainerChildRenderArea(T container) {
    return new java.awt.Rectangle(new java.awt.Point(0, 0), container.getSize());
  }
  
  public java.awt.Dimension getDefaultSize(Component component)
  {
    if (component == null)
      throw new NullPointerException();
    if (!handledComponentClass.isInstance(component))
      throw new IllegalArgumentException();
    return getDefaultComponentSize((Component)handledComponentClass.cast(component));
  }
  
  protected abstract java.awt.Dimension getDefaultComponentSize(T paramT);
  
  protected void translateComponent(Component component, boolean reverse) {
    Component parent = component.getParent();
    while (parent != null) {
      org.lwjgl.opengl.GL11.glTranslated((reverse ? -1 : 1) * parent.getX(), (reverse ? -1 : 1) * parent.getY(), 0.0D);
      parent = parent.getParent();
    }
    org.lwjgl.opengl.GL11.glTranslated((reverse ? -1 : 1) * component.getX(), (reverse ? -1 : 1) * component.getY(), 0.0D);
  }
  
  public Color getDefaultBackgroundColor(Component component)
  {
    if (component == null)
      throw new NullPointerException();
    if (!handledComponentClass.isInstance(component))
      throw new IllegalArgumentException();
    return getBackgroundColor((Component)handledComponentClass.cast(component));
  }
  
  protected Color getBackgroundColor(T component) {
    return background;
  }
  
  public Color getDefaultForegroundColor(Component component)
  {
    if (component == null)
      throw new NullPointerException();
    if (!handledComponentClass.isInstance(component))
      throw new IllegalArgumentException();
    return getForegroundColor((Component)handledComponentClass.cast(component));
  }
  
  protected Color getForegroundColor(T component) {
    return foreground;
  }
  
  public java.awt.Rectangle[] getInteractableRegions(Component component)
  {
    if (component == null)
      throw new NullPointerException();
    if (!handledComponentClass.isInstance(component))
      throw new IllegalArgumentException();
    return getInteractableComponentRegions((Component)handledComponentClass.cast(component));
  }
  
  protected java.awt.Rectangle[] getInteractableComponentRegions(T component) {
    return new java.awt.Rectangle[0];
  }
  
  public void handleInteraction(Component component, java.awt.Point location, int button)
  {
    if (component == null)
      throw new NullPointerException();
    if (!handledComponentClass.isInstance(component))
      throw new IllegalArgumentException();
    handleComponentInteraction((Component)handledComponentClass.cast(component), location, button);
  }
  

  protected void handleComponentInteraction(T component, java.awt.Point location, int button) {}
  
  public void handleUpdate(Component component)
  {
    if (component == null)
      throw new NullPointerException();
    if (!handledComponentClass.isInstance(component))
      throw new IllegalArgumentException();
    handleComponentUpdate((Component)handledComponentClass.cast(component));
  }
  
  protected void handleComponentUpdate(T component) {}
}
