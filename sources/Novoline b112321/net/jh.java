package net;

import java.util.Optional;
import net.N0;
import net.t4;
import viaversion.viaversion.api.Via;

public class jh {
   public static N0 a(int var0) {
      t4.c();
      Optional var2 = N0.a(var0);
      if(!var2.isPresent()) {
         Via.getPlatform().getLogger().severe("Could not find 1.14 type id " + var0);
         return N0.ENTITY;
      } else {
         return (N0)var2.get();
      }
   }
}
