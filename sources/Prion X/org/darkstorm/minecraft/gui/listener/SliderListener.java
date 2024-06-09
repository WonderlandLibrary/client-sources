package org.darkstorm.minecraft.gui.listener;

import org.darkstorm.minecraft.gui.component.Slider;

public abstract interface SliderListener
  extends ComponentListener
{
  public abstract void onSliderValueChanged(Slider paramSlider);
}
