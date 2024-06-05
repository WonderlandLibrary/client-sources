package net;

import net.Ql;
import net.as0;
import net.t8;

public final class tm extends t8 {
   private tm(String var1, as0 var2) {
      super(var1, var2);
   }

   public static tm a(String var0, as0 var1) {
      Ql.a(var0, "name");
      Ql.a((Object)var1, "module");
      return new tm(var0, var1);
   }

   public void a(as0 var1) {
      Ql.a((Object)var1, "module");
      this.a = var1;
   }
}
