package net.minecraft.block.properties;

import com.google.common.base.Objects.ToStringHelper;

public abstract class PropertyHelper implements IProperty
{
  private final Class valueClass;
  private final String name;
  private static final String __OBFID = "CL_00002018";
  
  protected PropertyHelper(String name, Class valueClass)
  {
    this.valueClass = valueClass;
    this.name = name;
  }
  
  public String getName()
  {
    return name;
  }
  



  public Class getValueClass()
  {
    return valueClass;
  }
  
  public String toString()
  {
    return com.google.common.base.Objects.toStringHelper(this).add("name", name).add("clazz", valueClass).add("values", getAllowedValues()).toString();
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (this == p_equals_1_)
    {
      return true;
    }
    if ((p_equals_1_ != null) && (getClass() == p_equals_1_.getClass()))
    {
      PropertyHelper var2 = (PropertyHelper)p_equals_1_;
      return (valueClass.equals(valueClass)) && (name.equals(name));
    }
    

    return false;
  }
  

  public int hashCode()
  {
    return 31 * valueClass.hashCode() + name.hashCode();
  }
}
