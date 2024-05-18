package org.darkstorm.minecraft.gui.theme;

import org.darkstorm.minecraft.gui.component.Component;

public abstract class AbstractTheme implements Theme
{
  protected final java.util.Map<Class<? extends Component>, ComponentUI> uis;
  
  public AbstractTheme()
  {
    uis = new java.util.HashMap();
  }
  
  protected void installUI(AbstractComponentUI<?> ui) {
    uis.put(handledComponentClass, ui);
  }
  
  public ComponentUI getUIForComponent(Component component) {
    if ((component == null) || (!(component instanceof Component)))
      throw new IllegalArgumentException();
    return getComponentUIForClass(component.getClass());
  }
  

  public ComponentUI getComponentUIForClass(Class<? extends Component> componentClass)
  {
    for (Class<?> componentInterface : componentClass.getInterfaces()) {
      ComponentUI ui = (ComponentUI)uis.get(componentInterface);
      if (ui != null)
        return ui;
    }
    if (componentClass.getSuperclass().equals(Component.class)) {
      return (ComponentUI)uis.get(componentClass);
    }
    if (!Component.class.isAssignableFrom(componentClass.getSuperclass()))
      return null;
    return getComponentUIForClass(componentClass
      .getSuperclass());
  }
}
