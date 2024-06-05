package net;

import java.util.Iterator;
import java.util.Set;
import net.minecraft.util.RegistryNamespaced;

public class vl {
   public static Set a(RegistryNamespaced var0) {
      return var0.getKeys();
   }

   public static Object c(RegistryNamespaced var0, Object var1) {
      return var0.getObject(var1);
   }

   public static int a(RegistryNamespaced var0, Object var1) {
      return var0.getIDForObject(var1);
   }

   public static Iterator b(RegistryNamespaced var0) {
      return var0.iterator();
   }

   public static Object b(RegistryNamespaced var0, Object var1) {
      return var0.getNameForObject(var1);
   }

   public static Object a(RegistryNamespaced var0, int var1) {
      return var0.getObjectById(var1);
   }

   public static void a(RegistryNamespaced var0, int var1, Object var2, Object var3) {
      var0.register(var1, var2, var3);
   }
}
