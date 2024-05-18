package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.listener.SelectableComponentListener;

public class BasicComboBox extends org.darkstorm.minecraft.gui.component.AbstractComponent implements org.darkstorm.minecraft.gui.component.ComboBox
{
  private String[] elements;
  private int selectedIndex;
  private boolean selected;
  
  public BasicComboBox() {
    elements = new String[0];
  }
  
  public BasicComboBox(String... elements) {
    this.elements = elements;
  }
  
  public String[] getElements()
  {
    return elements;
  }
  
  public void setElements(String... elements)
  {
    selectedIndex = 0;
    this.elements = elements;
  }
  
  public int getSelectedIndex()
  {
    return selectedIndex;
  }
  
  public void setSelectedIndex(int selectedIndex)
  {
    this.selectedIndex = selectedIndex;
    for (org.darkstorm.minecraft.gui.listener.ComponentListener listener : getListeners()) {
      if ((listener instanceof org.darkstorm.minecraft.gui.listener.ComboBoxListener)) {
        try
        {
          ((org.darkstorm.minecraft.gui.listener.ComboBoxListener)listener).onComboBoxSelectionChanged(this);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }
  }
  
  public String getSelectedElement()
  {
    return elements[selectedIndex];
  }
  
  public boolean isSelected()
  {
    return selected;
  }
  
  public void setSelected(boolean selected)
  {
    this.selected = selected;
    for (org.darkstorm.minecraft.gui.listener.ComponentListener listener : getListeners()) {
      if ((listener instanceof SelectableComponentListener)) {
        try {
          ((SelectableComponentListener)listener).onSelectedStateChanged(this);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }
  }
  
  public void addComboBoxListener(org.darkstorm.minecraft.gui.listener.ComboBoxListener listener)
  {
    addListener(listener);
  }
  
  public void removeComboBoxListener(org.darkstorm.minecraft.gui.listener.ComboBoxListener listener)
  {
    removeListener(listener);
  }
  
  public void addSelectableComponentListener(SelectableComponentListener listener)
  {
    addListener(listener);
  }
  
  public void removeSelectableComponentListener(SelectableComponentListener listener)
  {
    removeListener(listener);
  }
}
