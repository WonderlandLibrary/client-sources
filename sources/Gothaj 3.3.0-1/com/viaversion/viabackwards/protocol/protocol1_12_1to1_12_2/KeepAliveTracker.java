package com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2;

import com.viaversion.viaversion.api.connection.StorableObject;

public class KeepAliveTracker implements StorableObject {
   private long keepAlive = 2147483647L;

   public long getKeepAlive() {
      return this.keepAlive;
   }

   public void setKeepAlive(long keepAlive) {
      this.keepAlive = keepAlive;
   }

   @Override
   public String toString() {
      return "KeepAliveTracker{keepAlive=" + this.keepAlive + '}';
   }
}
