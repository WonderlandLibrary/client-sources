package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.ProtocolPathKey;

public class ProtocolPathKeyImpl implements ProtocolPathKey {
   private final int clientProtocolVersion;
   private final int serverProtocolVersion;

   public ProtocolPathKeyImpl(int clientProtocolVersion, int serverProtocolVersion) {
      this.clientProtocolVersion = clientProtocolVersion;
      this.serverProtocolVersion = serverProtocolVersion;
   }

   @Override
   public int clientProtocolVersion() {
      return this.clientProtocolVersion;
   }

   @Override
   public int serverProtocolVersion() {
      return this.serverProtocolVersion;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ProtocolPathKeyImpl that = (ProtocolPathKeyImpl)o;
         return this.clientProtocolVersion != that.clientProtocolVersion ? false : this.serverProtocolVersion == that.serverProtocolVersion;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.clientProtocolVersion;
      return 31 * result + this.serverProtocolVersion;
   }
}
