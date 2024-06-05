package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import java.io.InputStream;
import net.aPX;

public class ah4 {
   private static int b;

   public static CompoundTag a(InputStream var0) {
      return aPX.a(var0);
   }

   public static CompoundTag a(String var0) {
      return aPX.a(var0);
   }

   public static String a(CompoundTag var0) {
      return aPX.a(var0);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 8;
   }

   static {
      if(a() == 0) {
         b(26);
      }

   }
}
