package me.hexxed.mercury.values;

public class DoubleValue extends ModuleValue
{
  private double max;
  private double min;
  private double select;
  
  public DoubleValue(String name, double max, double min, double select) {
    super(name);
    this.max = max;
    this.min = min;
    this.select = select;
  }
  
  public double getMax() {
    return max;
  }
  
  public double getMin() {
    return min;
  }
  
  public double getSelected() {
    return select;
  }
  
  public boolean setSelected(double value) {
    if ((value <= getMax()) && (value >= getMin())) {
      select = value;
      return true;
    }
    return false;
  }
}
