package mods.togglesprint.me.jannik.value;

import mods.togglesprint.me.jannik.files.Files;

public class Value
{
  String flaotName;
  float floatValue;
  String booleanName;
  boolean booleanValue;
  
  public Value(String flaotName, float floatValue)
  {
    this.flaotName = flaotName;
    this.floatValue = floatValue;
  }
  
  public Value(String booleanName, boolean booleanValue)
  {
    this.booleanName = booleanName;
    this.booleanValue = booleanValue;
  }
  
  public String getFloatName()
  {
    return this.flaotName;
  }
  
  public void setFloatName(String flaotName)
  {
    this.flaotName = flaotName;
  }
  
  public String getBooleanName()
  {
    return this.booleanName;
  }
  
  public void setBooleanName(String booleanName)
  {
    this.booleanName = booleanName;
  }
  
  public float getFloatValue()
  {
    return this.floatValue;
  }
  
  public void setFloatValue(float floatValue)
  {
    this.floatValue = floatValue;
    Files.saveFloatValues();
  }
  
  public boolean getBooleanValue()
  {
    return this.booleanValue;
  }
  
  public void setBooleanValue(boolean booleanValue)
  {
    this.booleanValue = booleanValue;
    Files.saveBooleanValues();
  }
}
