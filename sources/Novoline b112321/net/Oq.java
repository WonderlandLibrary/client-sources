package net;

import java.util.Optional;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;

public class Oq {
   public static Optional a(int var0) {
      return SpawnEggRewriter.getEntityId(var0);
   }

   public static int a(String var0) {
      return SpawnEggRewriter.getSpawnEggId(var0);
   }
}
