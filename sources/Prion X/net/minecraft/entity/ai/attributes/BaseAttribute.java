package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute
{
  private final IAttribute field_180373_a;
  private final String unlocalizedName;
  private final double defaultValue;
  private boolean shouldWatch;
  private static final String __OBFID = "CL_00001565";
  
  protected BaseAttribute(IAttribute p_i45892_1_, String p_i45892_2_, double p_i45892_3_)
  {
    field_180373_a = p_i45892_1_;
    unlocalizedName = p_i45892_2_;
    defaultValue = p_i45892_3_;
    
    if (p_i45892_2_ == null)
    {
      throw new IllegalArgumentException("Name cannot be null!");
    }
  }
  
  public String getAttributeUnlocalizedName()
  {
    return unlocalizedName;
  }
  
  public double getDefaultValue()
  {
    return defaultValue;
  }
  
  public boolean getShouldWatch()
  {
    return shouldWatch;
  }
  
  public BaseAttribute setShouldWatch(boolean p_111112_1_)
  {
    shouldWatch = p_111112_1_;
    return this;
  }
  
  public IAttribute func_180372_d()
  {
    return field_180373_a;
  }
  
  public int hashCode()
  {
    return unlocalizedName.hashCode();
  }
  
  public boolean equals(Object p_equals_1_)
  {
    return ((p_equals_1_ instanceof IAttribute)) && (unlocalizedName.equals(((IAttribute)p_equals_1_).getAttributeUnlocalizedName()));
  }
}
