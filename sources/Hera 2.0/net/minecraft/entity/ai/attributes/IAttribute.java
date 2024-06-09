package net.minecraft.entity.ai.attributes;

public interface IAttribute {
  String getAttributeUnlocalizedName();
  
  double clampValue(double paramDouble);
  
  double getDefaultValue();
  
  boolean getShouldWatch();
  
  IAttribute func_180372_d();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\attributes\IAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */