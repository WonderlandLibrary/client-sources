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


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.Slider
 * JD-Core Version:    0.7.0.1
 */