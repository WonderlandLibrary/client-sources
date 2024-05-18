package org.darkstorm.minecraft.gui.component;

public abstract interface BoundedRangeComponent extends Component { public abstract double getValue();
  
  public static enum ValueDisplay { DECIMAL, 
    INTEGER, 
    PERCENTAGE, 
    NONE;
  }
  
  public abstract double getMinimumValue();
  
  public abstract double getMaximumValue();
  
  public abstract double getIncrement();
  
  public abstract ValueDisplay getValueDisplay();
  
  public abstract void setValue(double paramDouble);
  
  public abstract void setMinimumValue(double paramDouble);
  
  public abstract void setMaximumValue(double paramDouble);
  
  public abstract void setIncrement(double paramDouble);
  
  public abstract void setValueDisplay(ValueDisplay paramValueDisplay);
}
