package net;

import java.util.Arrays;
import net.FJ;
import net.a_E;
import net.as0;
import net.gZ;

public final class F7 extends FJ {
   public F7(gZ var1) {
      super(var1, "Hide", "Hides modules from arraylist", (Iterable)Arrays.asList(new String[]{"hide", "h"}));
   }

   public void b(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length == 1) {
         String var3 = var1[0];
         as0 var4 = this.h.d().a(var3);
         if(var4 == null) {
            this.f("Module " + var3 + " was not found!");
            return;
         }

         var4.a(!var4.l());
         this.e((var4.l()?"Hidden":"Shown") + " " + var4.j());
      }

      this.f("Use .hide <module> to hide a module!");
   }
}
