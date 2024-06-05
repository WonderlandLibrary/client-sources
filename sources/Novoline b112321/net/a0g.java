package net;

import net.LA;
import net.a0J;
import net.acE;

public abstract class a0g {
   public static a0g[] b = new a0g[]{(new a0J("swordTrail", "Sword Trail")).b(true), (new a0J("thirdSwordAttack", "Spin Attack")).b(true)};
   public String a;
   public String c;

   public a0g() {
      LA.b();
      super();
      this.a = "NULL";
      this.c = "NULL";
   }

   public a0g(String var1, String var2) {
      this.a = var1;
      this.c = var2;
   }

   public static a0g a(String var0) {
      LA.b();
      int var2 = 0;
      if(var2 < b.length) {
         if(b[var2].a.equalsIgnoreCase(var0)) {
            return b[var2];
         }

         ++var2;
      }

      if(acE.b() == null) {
         LA.b(new acE[2]);
      }

      return null;
   }

   public boolean a() {
      return ((a0J)this).d;
   }
}
