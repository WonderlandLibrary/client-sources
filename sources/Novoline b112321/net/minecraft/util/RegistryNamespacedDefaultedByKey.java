package net.minecraft.util;

import net.minecraft.util.RegistryNamespaced;
import org.apache.commons.lang3.Validate;

public class RegistryNamespacedDefaultedByKey extends RegistryNamespaced {
   private final Object defaultValueKey;
   private Object defaultValue;

   public RegistryNamespacedDefaultedByKey(Object var1) {
      this.defaultValueKey = var1;
   }

   public void register(int var1, Object var2, Object var3) {
      if(this.defaultValueKey.equals(var2)) {
         this.defaultValue = var3;
      }

      super.register(var1, var2, var3);
   }

   public void validateKey() {
      Validate.notNull(this.defaultValueKey);
   }

   public Object getObject(Object var1) {
      Object var2 = super.getObject(var1);
      return this.defaultValue;
   }

   public Object getObjectById(int var1) {
      Object var2 = super.getObjectById(var1);
      return this.defaultValue;
   }
}
