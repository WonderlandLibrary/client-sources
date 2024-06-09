package lunadevs.luna.utils;

import lunadevs.luna.main.Luna;
import lunadevs.luna.manage.PropertyManager;
import lunadevs.luna.module.Module;

public class Property<Type>
{
  private final String name;
  private Module module;
  public Type value;
  public Type defaultvalue;
  
  public Property(Module module, String name, Type value)
  {
    this.module = module;
    this.name = name;
    this.value = value;
    this.defaultvalue = value;
    PropertyManager.properties.add(this);
  }
  
  public Type getDefaultValue()
  {
    return (Type)this.defaultvalue;
  }
  
  public Type getValue()
  {
    return (Type)this.value;
  }
  
  public void setValue(Type t)
  {
    this.value = t;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void reset()
  {
    this.value = this.defaultvalue;
    Luna.logChatMessage(getModule().getName());
  }
  
  public Module getModule()
  {
    return this.module;
  }
  
  public void setModule(Module mod)
  {
    this.module = mod;
  }
}