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


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.ComboBox
 * JD-Core Version:    0.7.0.1
 */