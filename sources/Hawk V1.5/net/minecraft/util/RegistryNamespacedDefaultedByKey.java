package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class RegistryNamespacedDefaultedByKey extends RegistryNamespaced {
   private static final String __OBFID = "CL_00001196";
   private Object field_148761_e;
   private final Object field_148760_d;

   public void validateKey() {
      Validate.notNull(this.field_148760_d);
   }

   public Object getObjectById(int var1) {
      Object var2 = super.getObjectById(var1);
      return var2 == null ? this.field_148761_e : var2;
   }

   public Object getObject(Object var1) {
      Object var2 = super.getObject(var1);
      return var2 == null ? this.field_148761_e : var2;
   }

   public void register(int var1, Object var2, Object var3) {
      if (this.field_148760_d.equals(var2)) {
         this.field_148761_e = var3;
      }

      super.register(var1, var2, var3);
   }

   public RegistryNamespacedDefaultedByKey(Object var1) {
      this.field_148760_d = var1;
   }
}
