package net;

import java.util.Optional;
import net.a_O;
import net.ahY;
import net.t4;
import viaversion.viaversion.api.Via;

public class aFf {
   public static ahY a(int var0, boolean var1) {
      boolean var2 = t4.d();
      if(var1) {
         Optional var3 = a_O.b(var0);
      }

      Optional var4 = ahY.a(var0);
      if(!var4.isPresent()) {
         Via.getPlatform().getLogger().severe("Could not find 1.12 type id " + var0 + " isObject=" + var1);
         return ahY.ENTITY;
      } else {
         return (ahY)var4.get();
      }
   }
}
