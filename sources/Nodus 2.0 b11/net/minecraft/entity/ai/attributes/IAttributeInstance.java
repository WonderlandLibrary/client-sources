package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;

public abstract interface IAttributeInstance
{
  public abstract IAttribute getAttribute();
  
  public abstract double getBaseValue();
  
  public abstract void setBaseValue(double paramDouble);
  
  public abstract Collection func_111122_c();
  
  public abstract AttributeModifier getModifier(UUID paramUUID);
  
  public abstract void applyModifier(AttributeModifier paramAttributeModifier);
  
  public abstract void removeModifier(AttributeModifier paramAttributeModifier);
  
  public abstract void removeAllModifiers();
  
  public abstract double getAttributeValue();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.attributes.IAttributeInstance
 * JD-Core Version:    0.7.0.1
 */