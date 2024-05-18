package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;

public interface IAttributeInstance {
  IAttribute getAttribute();
  
  double getBaseValue();
  
  void setBaseValue(double paramDouble);
  
  Collection<AttributeModifier> getModifiersByOperation(int paramInt);
  
  Collection<AttributeModifier> func_111122_c();
  
  boolean hasModifier(AttributeModifier paramAttributeModifier);
  
  AttributeModifier getModifier(UUID paramUUID);
  
  void applyModifier(AttributeModifier paramAttributeModifier);
  
  void removeModifier(AttributeModifier paramAttributeModifier);
  
  void removeAllModifiers();
  
  double getAttributeValue();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\attributes\IAttributeInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */