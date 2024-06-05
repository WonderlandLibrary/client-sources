package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;

public interface IAttributeInstance {
   void removeModifier(AttributeModifier var1);

   boolean func_180374_a(AttributeModifier var1);

   void removeAllModifiers();

   IAttribute getAttribute();

   AttributeModifier getModifier(UUID var1);

   void applyModifier(AttributeModifier var1);

   void setBaseValue(double var1);

   Collection getModifiersByOperation(int var1);

   Collection func_111122_c();

   double getAttributeValue();

   double getBaseValue();
}
