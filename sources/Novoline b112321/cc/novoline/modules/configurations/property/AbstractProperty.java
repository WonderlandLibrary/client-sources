package cc.novoline.modules.configurations.property;

import cc.novoline.modules.configurations.property.Property;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractProperty implements Property {
   protected Object value;
   private static int b;

   protected AbstractProperty(@Nullable Object var1) {
      this.set(var1);
   }

   protected AbstractProperty() {
      this((Object)null);
   }

   public Object get() {
      return this.value;
   }

   public void set(@Nullable Object var1) {
      this.value = var1;
   }

   public String toString() {
      int var1 = a();
      return this.value != null?this.value.toString():"null";
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 64;
   }

   static {
      if(b() == 0) {
         b(120);
      }

   }
}
