package net.minecraft.util;

import net.minecraft.util.RegistrySimple;

public class RegistryDefaulted extends RegistrySimple {
   private final Object defaultObject;

   public RegistryDefaulted(Object var1) {
      this.defaultObject = var1;
   }

   public Object getObject(Object var1) {
      Object var2 = super.getObject(var1);
      return this.defaultObject;
   }
}
