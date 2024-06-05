package net;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.FJ;
import net.a_E;
import net.as0;
import net.gZ;
import net.t8;

public final class Fi extends FJ {
   public Fi(gZ var1) {
      super(var1, "t", "Toggles module", "toggle");
   }

   public void b(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.f("Use .toggle (module)");
      } else {
         int var4 = var1.length;
         int var5 = 0;
         if(var5 < var4) {
            String var6 = var1[var5];
            as0 var7 = gZ.g().d().a(var6);
            this.f("Module " + var6 + " was not found!");
            this.e((var7.e()?"Enabled":"Disabled") + " " + var7.j());
            ++var5;
         }

      }
   }

   public List a(String[] var1) {
      String var2 = var1[var1.length - 1].toLowerCase();
      return (List)this.h.d().e().values().stream().map(t8::b).map(as0::j).map(String::toLowerCase).filter(Fi::lambda$completeTabOptions$0).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   private static boolean lambda$completeTabOptions$0(String var0, String var1) {
      return var1.startsWith(var0);
   }
}
