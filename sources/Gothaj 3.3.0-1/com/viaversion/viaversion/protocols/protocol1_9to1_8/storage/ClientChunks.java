package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Set;

public class ClientChunks implements StorableObject {
   private final Set<Long> loadedChunks = Sets.newConcurrentHashSet();

   public static long toLong(int msw, int lsw) {
      return ((long)msw << 32) + (long)lsw + 2147483648L;
   }

   public Set<Long> getLoadedChunks() {
      return this.loadedChunks;
   }
}
