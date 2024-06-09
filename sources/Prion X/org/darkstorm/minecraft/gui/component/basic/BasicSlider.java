package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;

public class BasicSlider extends org.darkstorm.minecraft.gui.component.AbstractComponent implements org.darkstorm.minecraft.gui.component.Slider { private String text;
  private String suffix;
  private double value;
  private double minimum;
  private double maximum;
  private double increment; private BoundedRangeComponent.ValueDisplay display; private boolean changing = false;
  private double startValue;
  
  public BasicSlider() {
    this("");
  }
  
  public BasicSlider(String text) {
    this(text, 0.0D);
  }
  
  public BasicSlider(String text, double value) {
    this(text, value, 0.0D, 100.0D);
  }
  
  public BasicSlider(String text, double value, double minimum, double maximum) {
    this(text, value, minimum, maximum, 1);
  }
  
  public BasicSlider(String text, double value, double minimum, double maximum, int increment) {
    this(text, value, minimum, maximum, increment, BoundedRangeComponent.ValueDisplay.DECIMAL);
  }
  
  public BasicSlider(String text, double value, double minimum, double maximum, double increment, BoundedRangeComponent.ValueDisplay display) {
    this.text = (text != null ? text : "");
    this.minimum = Math.max(0.0D, Math.min(minimum, maximum));
    this.maximum = Math.max(0.0D, Math.max(minimum, maximum));
    value = Math.max(minimum, Math.min(maximum, value));
    this.value = (value - Math.round(value % increment / increment) * increment);
    this.increment = Math.min(maximum, Math.max(5.0E-4D, increment));
    this.display = (display != null ? display : BoundedRangeComponent.ValueDisplay.DECIMAL);
  }
  
  public String getText()
  {
    return text;
  }
  
  public void setText(String text)
  {
    this.text = (text != null ? text : "");
  }
  
  public double getValue()
  {
    return value;
  }
  
  public double getMinimumValue()
  {
    return minimum;
  }
  
  public double getMaximumValue()
  {
    return maximum;
  }
  
  public double getIncrement() {
    return increment;
  }
  
  public BoundedRangeComponent.ValueDisplay getValueDisplay()
  {
    return display;
  }
  
  public boolean isValueChanging()
  {
    return changing;
  }
  
  public String getContentSuffix()
  {
    return suffix;
  }
  
  public void setValue(double value)
  {
    double oldValue = this.value;
    value = Math.max(minimum, Math.min(maximum, value));
    this.value = (value - Math.round(value % increment / increment) * increment);
    if ((!changing) && (oldValue != this.value)) {
      fireChange();
    }
  }
  
  public void setMinimumValue(double minimum) {
    this.minimum = Math.max(0.0D, Math.min(maximum, minimum));
    setValue(value);
  }
  
  public void setMaximumValue(double maximum)
  {
    this.maximum = Math.max(maximum, minimum);
    setValue(value);
  }
  
  public void setIncrement(double increment) {
    this.increment = Math.min(maximum, Math.max(5.0E-4D, increment));
    setValue(value);
  }
  
  public void setValueDisplay(BoundedRangeComponent.ValueDisplay display) {
    this.display = (display != null ? display : BoundedRangeComponent.ValueDisplay.DECIMAL);
  }
  
  public void setValueChanging(boolean changing)
  {
    if (changing != this.changing) {
      this.changing = changing;
      if (changing) {
        startValue = value;
      } else if (startValue != value) {
        fireChange();
      }
    }
  }
  
  public void setContentSuffix(String suffix) {
    this.suffix = suffix;
  }
  
  public void addSliderListener(org.darkstorm.minecraft.gui.listener.SliderListener listener)
  {
    addListener(listener);
  }
  
  public void removeSliderListener(org.darkstorm.minecraft.gui.listener.SliderListener listener)
  {
    removeListener(listener);
  }
  
  private void fireChange() {
    for (org.darkstorm.minecraft.gui.listener.ComponentListener listener : getListeners()) {
      if ((listener instanceof org.darkstorm.minecraft.gui.listener.SliderListener)) {
        ((org.darkstorm.minecraft.gui.listener.SliderListener)listener).onSliderValueChanged(this);
      }
    }
  }
}
