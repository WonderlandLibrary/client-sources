package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.listener.ButtonListener;

public class BasicButton extends org.darkstorm.minecraft.gui.component.AbstractComponent implements org.darkstorm.minecraft.gui.component.Button
{
  protected String text = "";
  protected org.darkstorm.minecraft.gui.component.ButtonGroup group;
  
  public BasicButton() {}
  
  public BasicButton(String text)
  {
    this.text = text;
  }
  
  public String getText()
  {
    return text;
  }
  
  public void setText(String text)
  {
    this.text = text;
  }
  
  public void press()
  {
    for (org.darkstorm.minecraft.gui.listener.ComponentListener listener : getListeners()) {
      ((ButtonListener)listener).onButtonPress(this);
    }
  }
  
  public void addButtonListener(ButtonListener listener) {
    addListener(listener);
  }
  
  public void removeButtonListener(ButtonListener listener)
  {
    removeListener(listener);
  }
  
  public org.darkstorm.minecraft.gui.component.ButtonGroup getGroup()
  {
    return group;
  }
  
  public void setGroup(org.darkstorm.minecraft.gui.component.ButtonGroup group)
  {
    this.group = group;
  }
}
