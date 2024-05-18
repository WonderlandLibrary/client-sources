package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;

public class BasicProgressBar extends org.darkstorm.minecraft.gui.component.AbstractComponent
  implements org.darkstorm.minecraft.gui.component.ProgressBar {
  private double value;
  private double minimum;
  private double maximum;
  
  public BasicProgressBar() { this(0.0D); }
  
  private double increment;
  
  public BasicProgressBar(double value) { this(value, 0.0D, 100.0D); }
  
  private BoundedRangeComponent.ValueDisplay display;
  private boolean indeterminate;
  public BasicProgressBar(double value, double minimum, double maximum) { this(value, minimum, maximum, 1); }
  
  public BasicProgressBar(double value, double minimum, double maximum, int increment)
  {
    this(value, minimum, maximum, increment, BoundedRangeComponent.ValueDisplay.NONE);
  }
  
  public BasicProgressBar(double value, double minimum, double maximum, double increment, BoundedRangeComponent.ValueDisplay display) {
    this.minimum = Math.max(0.0D, Math.min(minimum, maximum));
    this.maximum = Math.max(0.0D, Math.max(minimum, maximum));
    value = Math.max(minimum, Math.min(maximum, value));
    this.value = (value - Math.round(value % increment / increment) * increment);
    this.increment = Math.min(maximum, Math.max(5.0E-4D, increment));
    this.display = (display != null ? display : BoundedRangeComponent.ValueDisplay.NONE);
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
  
  public boolean isIndeterminate()
  {
    return indeterminate;
  }
  
  public void setValue(double value)
  {
    value = Math.max(minimum, Math.min(maximum, value));
    this.value = (value - Math.round(value % increment / increment) * increment);
  }
  
  public void setMinimumValue(double minimum)
  {
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
    this.display = (display != null ? display : BoundedRangeComponent.ValueDisplay.NONE);
  }
  
  public void setIndeterminate(boolean indeterminate)
  {
    this.indeterminate = indeterminate;
  }
}
