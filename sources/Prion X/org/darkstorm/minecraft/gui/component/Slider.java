package org.darkstorm.minecraft.gui.component;

import org.darkstorm.minecraft.gui.listener.SliderListener;

public abstract interface Slider
  extends Component, TextComponent, BoundedRangeComponent
{
  public abstract String getContentSuffix();
  
  public abstract boolean isValueChanging();
  
  public abstract void setContentSuffix(String paramString);
  
  public abstract void setValueChanging(boolean paramBoolean);
  
  public abstract void addSliderListener(SliderListener paramSliderListener);
  
  public abstract void removeSliderListener(SliderListener paramSliderListener);
}
