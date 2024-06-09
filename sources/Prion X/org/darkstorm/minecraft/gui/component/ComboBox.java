package org.darkstorm.minecraft.gui.component;

import org.darkstorm.minecraft.gui.listener.ComboBoxListener;

public abstract interface ComboBox
  extends Component, SelectableComponent
{
  public abstract String[] getElements();
  
  public abstract void setElements(String... paramVarArgs);
  
  public abstract int getSelectedIndex();
  
  public abstract void setSelectedIndex(int paramInt);
  
  public abstract String getSelectedElement();
  
  public abstract void addComboBoxListener(ComboBoxListener paramComboBoxListener);
  
  public abstract void removeComboBoxListener(ComboBoxListener paramComboBoxListener);
}
