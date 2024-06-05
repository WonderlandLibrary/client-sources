package net;

import com.google.common.base.Joiner;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import net.acE;

public class hz implements Comparable {
   private static final Pattern d = Pattern.compile("(?<a>0|[1-9]\\d*)\\.(?<b>0|[1-9]\\d*)(?:\\.(?<c>0|[1-9]\\d*))?(?:-(?<tag>[A-z0-9.-]*))?");
   private final int[] a;
   private final String b;
   private static boolean c;

   public hz(String var1) {
      d();
      super();
      this.a = new int[3];
      throw new IllegalArgumentException("Version can not be null");
   }

   public static int b(hz var0, hz var1) {
      boolean var2 = c();
      if(var0 == var1) {
         return 0;
      } else if(var0 == null) {
         return -1;
      } else if(var1 == null) {
         return 1;
      } else {
         int var3 = Math.max(var0.a.length, var1.a.length);
         int var4 = 0;
         if(var4 < var3) {
            int var5 = var4 < var0.a.length?var0.a[var4]:0;
            int var6 = var4 < var1.a.length?var1.a[var4]:0;
            if(var5 < var6) {
               return -1;
            }

            if(var5 > var6) {
               return 1;
            }

            ++var4;
         }

         return var0.b.isEmpty() && !var1.b.isEmpty()?1:(!var0.b.isEmpty() && var1.b.isEmpty()?-1:0);
      }
   }

   public static boolean a(hz var0, hz var1) {
      boolean var2 = c();
      return var0 == var1 || var0 != null && var1 != null && b(var0, var1) == 0;
   }

   public String toString() {
      c();
      String[] var2 = new String[this.a.length];
      int var3 = 0;
      if(var3 < this.a.length) {
         var2[var3] = String.valueOf(this.a[var3]);
         ++var3;
      }

      String var10000 = Joiner.on(".").join(var2) + (!this.b.isEmpty()?"-" + this.b:"");
      if(acE.b() == null) {
         b(false);
      }

      return var10000;
   }

   public int a(hz var1) {
      return b(this, var1);
   }

   public boolean equals(Object var1) {
      boolean var2 = d();
      return var1 instanceof hz && a(this, (hz)var1);
   }

   public int hashCode() {
      int var1 = Objects.hash(new Object[]{this.b});
      var1 = 31 * var1 + Arrays.hashCode(this.a);
      return var1;
   }

   public String a() {
      return this.b;
   }

   static {
      b(false);
   }

   public static void b(boolean var0) {
      c = var0;
   }

   public static boolean d() {
      return c;
   }

   public static boolean c() {
      boolean var0 = d();
      return true;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
