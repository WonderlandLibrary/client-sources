package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.a66;
import net.ayx;

public class AbstractProtocol$Packet {
   private final a66 a;
   private final int packetId;

   public AbstractProtocol$Packet(a66 var1, int var2) {
      this.a = var1;
      this.packetId = var2;
   }

   public a66 a() {
      return this.a;
   }

   public int getPacketId() {
      return this.packetId;
   }

   public String toString() {
      return "Packet{state=" + this.a + ", packetId=" + this.packetId + '}';
   }

   public boolean equals(Object var1) {
      PacketRemapper[] var2 = ayx.h();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         AbstractProtocol$Packet var3 = (AbstractProtocol$Packet)var1;
         return this.packetId == var3.packetId && this.a == var3.a;
      } else {
         return false;
      }
   }

   public int hashCode() {
      PacketRemapper[] var1 = ayx.h();
      int var2 = this.a != null?this.a.hashCode():0;
      var2 = 31 * var2 + this.packetId;
      return var2;
   }
}
