package me.hexxed.mercury.values;

public class IntValue extends ModuleValue
{
  private int max;
  private int min;
  private int select;
  
  public IntValue(String name, int max, int min, int select) {
    super(name);
    this.max = max;
    this.min = min;
    this.select = select;
  }
  
  public int getMax() {
    return max;
  }
  
  public int getMin() {
    return min;
  }
  
  public int getSelected() {
    return select;
  }
  
  public boolean setSelected(int value) {
    if ((value <= getMax()) && (value >= getMin())) {
      select = value;
      return true;
    }
    return false;
  }
}
