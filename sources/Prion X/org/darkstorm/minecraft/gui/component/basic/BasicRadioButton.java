package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.listener.SelectableComponentListener;

public class BasicRadioButton extends BasicButton implements org.darkstorm.minecraft.gui.component.RadioButton
{
  private boolean selected = false;
  
  public BasicRadioButton() {}
  
  public BasicRadioButton(String text)
  {
    this.text = text;
  }
  
  public void press()
  {
    selected = true;
    for (org.darkstorm.minecraft.gui.component.Button button : getGroup().getButtons()) {
      if ((!equals(button)) && ((button instanceof org.darkstorm.minecraft.gui.component.RadioButton)))
      {
        ((org.darkstorm.minecraft.gui.component.RadioButton)button).setSelected(false); }
    }
    super.press();
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
        try
        {
          ((SelectableComponentListener)listener).onSelectedStateChanged(this);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }
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
