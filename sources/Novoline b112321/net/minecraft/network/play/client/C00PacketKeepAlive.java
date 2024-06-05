package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C00PacketKeepAlive implements Packet {
   private int key;

   public C00PacketKeepAlive() {
   }

   public C00PacketKeepAlive(int var1) {
      this.key = var1;
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processKeepAlive(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.key = var1.readVarIntFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.key);
   }

   public int getKey() {
      return this.key;
   }

   public void setKey(int var1) {
      this.key = var1;
   }
}
