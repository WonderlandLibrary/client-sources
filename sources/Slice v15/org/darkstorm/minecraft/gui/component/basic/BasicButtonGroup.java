package org.darkstorm.minecraft.gui.component.basic;

import java.util.List;
import org.darkstorm.minecraft.gui.component.Button;

public class BasicButtonGroup implements org.darkstorm.minecraft.gui.component.ButtonGroup
{
  private List<Button> buttons = new java.util.ArrayList();
  
  public BasicButtonGroup() {}
  
  public void addButton(Button button) { if (button == null)
      throw new NullPointerException();
    synchronized (buttons) {
      buttons.add(button);
    }
  }
  
  public void removeButton(Button button)
  {
    if (button == null)
      throw new NullPointerException();
    synchronized (buttons) {
      buttons.remove(button);
    }
  }
  
  public Button[] getButtons()
  {
    synchronized (buttons) {
      return (Button[])buttons.toArray(new Button[buttons.size()]);
    }
  }
}
