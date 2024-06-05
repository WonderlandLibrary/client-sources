package net;

import java.util.Optional;
import net.g4;
import net.t4;
import viaversion.viaversion.api.Via;

public class agc {
   public static g4 a(int var0) {
      t4.c();
      Optional var2 = g4.a(var0);
      if(!var2.isPresent()) {
         Via.getPlatform().getLogger().severe("Could not find 1.15 type id " + var0);
         return g4.ENTITY;
      } else {
         return (g4)var2.get();
      }
   }
}
