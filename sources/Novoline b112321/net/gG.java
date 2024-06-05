package net;

import java.util.Collection;
import net.minecraft.block.properties.IProperty;

public class gG {
   public static String b(IProperty var0) {
      return var0.getName();
   }

   public static String a(IProperty var0, Comparable var1) {
      return var0.getName(var1);
   }

   public static Collection a(IProperty var0) {
      return var0.getAllowedValues();
   }

   public static Class c(IProperty var0) {
      return var0.getValueClass();
   }
}
