package org.darkstorm.minecraft.gui.theme;

import org.darkstorm.minecraft.gui.component.Component;

public abstract interface Theme
{
  public abstract ComponentUI getUIForComponent(Component paramComponent);
}
