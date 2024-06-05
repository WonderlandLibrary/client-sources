package cc.novoline.modules.configurations.property.exception;

import cc.novoline.modules.configurations.property.Property;
import net.acE;

public class PropertyException extends RuntimeException {
   private final Property property;
   private static int b;

   public PropertyException(Property var1) {
      this.property = var1;
   }

   public PropertyException(String var1, Property var2) {
      super(var1);
      this.property = var2;
   }

   public PropertyException(String var1, Throwable var2, Property var3) {
      super(var1, var2);
      b();
      this.property = var3;
   }

   public PropertyException(Throwable var1, Property var2) {
      super(var1);
      int var10000 = c();
      this.property = var2;
      int var3 = var10000;
      if(acE.b() == null) {
         ++var3;
         b(var3);
      }

   }

   public Property getProperty() {
      return this.property;
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 92;
   }

   private static PropertyException a(PropertyException var0) {
      return var0;
   }

   static {
      if(c() != 0) {
         b(54);
      }

   }
}
