package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S00PacketKeepAlive implements Packet {
   private int field_149136_a;
   private static final String __OBFID = "CL_00001303";

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleKeepAlive(this);
   }

   public int func_149134_c() {
      return this.field_149136_a;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149136_a = var1.readVarIntFromBuffer();
   }

   public S00PacketKeepAlive(int var1) {
      this.field_149136_a = var1;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149136_a);
   }

   public S00PacketKeepAlive() {
   }
}
