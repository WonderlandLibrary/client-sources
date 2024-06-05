package net;

import java.util.HashMap;
import java.util.Optional;
import net.arP;
import net.t4;
import viaversion.viaversion.api.Pair;
import viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetaIndex;

public class Ka {
   private static final HashMap a = new HashMap();

   private static Optional b(t4 var0, int var1) {
      arP.a();
      Pair var3 = new Pair(var0, Integer.valueOf(var1));
      return a.containsKey(var3)?Optional.of(a.get(var3)):Optional.empty();
   }

   public static MetaIndex a(t4 var0, int var1) {
      boolean var2 = arP.b();
      Optional var4 = b(var0, var1);
      if(var4.isPresent()) {
         return (MetaIndex)var4.get();
      } else {
         t4 var3 = var0.a();
         return null;
      }
   }

   static {
      for(MetaIndex var3 : MetaIndex.values()) {
         a.put(new Pair(var3.c(), Integer.valueOf(var3.getNewIndex())), var3);
      }

   }
}
