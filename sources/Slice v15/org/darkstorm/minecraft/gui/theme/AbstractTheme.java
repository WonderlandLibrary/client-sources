package org.darkstorm.minecraft.gui.theme;

import java.util.HashMap;
import java.util.Map;
import org.darkstorm.minecraft.gui.component.Component;

public abstract class AbstractTheme
  implements Theme
{
  protected final Map<Class<? extends Component>, ComponentUI> uis;
  
  public AbstractTheme()
  {
    uis = new HashMap();
  }
  
  protected void installUI(AbstractComponentUI<?> ui)
  {
    uis.put(handledComponentClass, ui);
  }
  
  public ComponentUI getUIForComponent(Component component)
  {
    if ((component == null) || (!(component instanceof Component))) {
      throw new IllegalArgumentException();
    }
    return getComponentUIForClass(component.getClass());
  }
  
  public ComponentUI getComponentUIForClass(Class<?> class1)
  {
    Class[] arrayOfClass;
    int j = (arrayOfClass = class1.getInterfaces()).length;
    for (int i = 0; i < j; i++)
    {
      Class<?> componentInterface = arrayOfClass[i];
      ComponentUI ui = (ComponentUI)uis.get(componentInterface);
      if (ui != null) {
        return ui;
      }
    }
    if (class1.getSuperclass().equals(Component.class)) {
      return (ComponentUI)uis.get(class1);
    }
    if (!Component.class.isAssignableFrom(class1.getSuperclass())) {
      return null;
    }
    return getComponentUIForClass(class1
      .getSuperclass());
  }
}
