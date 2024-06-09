package me.hexxed.mercury.values;

import org.darkstorm.minecraft.gui.component.Frame;

public class ModuleValue
{
  private String name;
  private boolean shouldCreateFrame = true;
  private boolean shouldCreateCommand = true;
  
  public ModuleValue(String name) {
    this.name = name;
  }
  
  public String getCreateFrame() {
    return name;
  }
  
  public void setCreateFrame(boolean value) {
    shouldCreateFrame = value;
  }
  
  public String getCreateCommand() {
    return name;
  }
  
  public void setCreateCommand(boolean value) {
    shouldCreateCommand = value;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  

  public void addToFrame(Frame frame) {}
  
  public BooleanValue getBooleanValue()
  {
    return (BooleanValue)this;
  }
  
  public IntValue getIntValue() {
    return (IntValue)this;
  }
  
  public DoubleValue getDoubleValue() {
    return (DoubleValue)this;
  }
  
  public StringValue getStringValue() {
    return (StringValue)this;
  }
}
