package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.lang.reflect.Field;
import java.util.Map;
import net.VN;
import net.X9;
import net.aBZ;
import net.gR;
import net.up;

public final class uw extends up {
   private static String[] d;

   protected uw(Class var1) throws X9 {
      super(var1);
   }

   protected void a(Map var1, Class var2) throws X9 {
      boolean var4 = false;
      b();
      Field[] var5 = var2.getDeclaredFields();
      int var6 = var5.length;
      int var7 = 0;
      if(var7 < var6) {
         Field var8 = var5[var7];
         if(gR.class.isAssignableFrom(var8.getType()) && var8.isAnnotationPresent(VN.class)) {
            var4 = true;
            var8.setAccessible(true);
            aBZ var9 = new aBZ(var8, (String)null);
            VN var10 = (VN)var8.getAnnotation(VN.class);
            String var11 = a(var8, var10);
            if(!var1.containsKey(var11)) {
               var1.put(var11, var9);
            }
         }

         ++var7;
      }

      if(!var4) {
         super.a(var1, var2);
      }

      if(PacketRemapper.b() == null) {
         b(new String[2]);
      }

   }

   public static String a(Field var0, VN var1) {
      return var1.value();
   }

   public static void b(String[] var0) {
      d = var0;
   }

   public static String[] b() {
      return d;
   }

   private static X9 a(X9 var0) {
      return var0;
   }

   static {
      if(b() != null) {
         b(new String[5]);
      }

   }
}
