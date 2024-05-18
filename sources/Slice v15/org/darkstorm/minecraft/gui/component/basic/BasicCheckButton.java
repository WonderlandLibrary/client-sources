package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.listener.SelectableComponentListener;

public class BasicCheckButton extends BasicButton implements org.darkstorm.minecraft.gui.component.CheckButton
{
  private boolean selected = false;
  
  public BasicCheckButton() {}
  
  public BasicCheckButton(String text)
  {
    this.text = text;
  }
  
  public void press()
  {
    selected = (!selected);
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
        try {
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
