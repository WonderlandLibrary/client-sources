package net;

import java.util.HashMap;
import java.util.Optional;
import net.Dk;
import net.t4;
import viaversion.viarewind.protocol.protocol1_8to1_7_6_10.metadata.MetaIndex1_8to1_7_6_10;
import viaversion.viaversion.api.Pair;

public class s9 {
   private static final HashMap a = new HashMap();

   private static Optional b(t4 var0, int var1) {
      Dk.b();
      Pair var3 = new Pair(var0, Integer.valueOf(var1));
      return a.containsKey(var3)?Optional.of(a.get(var3)):Optional.empty();
   }

   public static MetaIndex1_8to1_7_6_10 a(t4 var0, int var1) {
      String[] var2 = Dk.b();
      Optional var4 = b(var0, var1);
      if(var4.isPresent()) {
         return (MetaIndex1_8to1_7_6_10)var4.get();
      } else {
         t4 var3 = var0.a();
         return null;
      }
   }

   static {
      for(MetaIndex1_8to1_7_6_10 var3 : MetaIndex1_8to1_7_6_10.values()) {
         a.put(new Pair(var3.b(), Integer.valueOf(var3.getNewIndex())), var3);
      }

   }
}
