package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.layout.LayoutManager;

public class BasicPanel extends org.darkstorm.minecraft.gui.component.AbstractContainer implements org.darkstorm.minecraft.gui.component.Panel
{
  public BasicPanel() {}
  
  public BasicPanel(LayoutManager layoutManager)
  {
    setLayoutManager(layoutManager);
  }
}
