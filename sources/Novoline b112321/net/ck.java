package net;

import com.google.common.reflect.TypeToken;
import java.util.Collection;
import net.AN;
import net.X9;
import net.ZV;
import net.akH;
import net.axu;
import net.gR;
import net.tj;

public final class ck implements ZV {
   private static int[] b;

   public void a(TypeToken var1, gR var2, akH var3) {
      int[] var4 = b();
      if(var2.a() == null) {
         var3.a((Object)null);
      } else {
         var3.a(var2.a());
      }
   }

   public gR a(TypeToken var1, akH var2) throws X9 {
      int[] var3 = b();
      switch(AN.a[var2.a().ordinal()]) {
      case 1:
         return axu.a((Collection)var2.a((TypeToken)(new tj(this))));
      case 2:
         Object var4 = var2.o();
         if(var4 instanceof CharSequence) {
            return axu.a(var4.toString());
         } else if(var4 instanceof Integer) {
            return axu.b(Integer.valueOf(((Integer)var4).intValue()));
         } else if(var4 instanceof Double) {
            return axu.a(Double.valueOf(((Double)var4).doubleValue()));
         } else if(var4 instanceof Boolean) {
            return axu.a(Boolean.valueOf(((Boolean)var4).booleanValue()));
         } else if(var4 instanceof Float) {
            return axu.a(Float.valueOf(((Float)var4).floatValue()));
         } else if(var4 instanceof Long) {
            return axu.a(Long.valueOf(((Long)var4).longValue()));
         }
      default:
         return null;
      case 3:
         throw new X9("Unable to deserialize map property");
      }
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   private static X9 a(X9 var0) {
      return var0;
   }

   static {
      if(b() != null) {
         b(new int[3]);
      }

   }
}
