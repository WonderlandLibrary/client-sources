package org.darkstorm.minecraft.gui.listener;

import org.darkstorm.minecraft.gui.component.Button;

public abstract interface ButtonListener
  extends ComponentListener
{
  public abstract void onButtonPress(Button paramButton);
}
