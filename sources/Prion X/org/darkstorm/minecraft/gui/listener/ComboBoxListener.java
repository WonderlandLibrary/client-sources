package org.darkstorm.minecraft.gui.listener;

import org.darkstorm.minecraft.gui.component.ComboBox;

public abstract interface ComboBoxListener
  extends ComponentListener
{
  public abstract void onComboBoxSelectionChanged(ComboBox paramComboBox);
}
