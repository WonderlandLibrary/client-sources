package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.Via;
import java.util.List;
import java.util.Set;
import net.aIa;
import net.bgR;
import net.cA;

public class ClientChunks extends cA {
   private final Set loadedChunks = Sets.newConcurrentHashSet();
   private final Set bulkChunks = Sets.newConcurrentHashSet();

   public ClientChunks(bgR var1) {
      super(var1);
   }

   public static long toLong(int var0, int var1) {
      return ((long)var0 << 32) + (long)var1 - -2147483648L;
   }

   public List a(Object var1) throws Exception {
      return ((aIa)Via.b().f().b(aIa.class)).a(var1, this);
   }

   public Set getLoadedChunks() {
      return this.loadedChunks;
   }

   public Set getBulkChunks() {
      return this.bulkChunks;
   }
}
