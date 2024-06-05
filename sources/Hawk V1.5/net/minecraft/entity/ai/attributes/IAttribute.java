package net.minecraft.entity.ai.attributes;

public interface IAttribute {
   String getAttributeUnlocalizedName();

   double clampValue(double var1);

   IAttribute func_180372_d();

   boolean getShouldWatch();

   double getDefaultValue();
}
