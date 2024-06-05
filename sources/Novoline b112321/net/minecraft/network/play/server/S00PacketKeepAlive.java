package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S00PacketKeepAlive implements Packet {
   private int id;

   public S00PacketKeepAlive() {
   }

   public S00PacketKeepAlive(int var1) {
      this.id = var1;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleKeepAlive(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.id = var1.readVarIntFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.id);
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public int func_149134_c() {
      return this.id;
   }
}
