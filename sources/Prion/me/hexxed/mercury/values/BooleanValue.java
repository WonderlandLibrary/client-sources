package me.hexxed.mercury.values;

public class BooleanValue extends ModuleValue
{
  private boolean select;
  
  public BooleanValue(String name, boolean select) {
    super(name);
    this.select = select;
  }
  
  public boolean getSelected() {
    return select;
  }
  
  public void setSelected(boolean select) {
    this.select = select;
  }
}
