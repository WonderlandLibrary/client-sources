package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0APacketAnimation implements Packet {
   private static final String __OBFID = "CL_00001345";

   public void readPacketData(PacketBuffer var1) throws IOException {
   }

   public void processPacket(INetHandler var1) {
      this.func_179721_a((INetHandlerPlayServer)var1);
   }

   public void func_179721_a(INetHandlerPlayServer var1) {
      var1.func_175087_a(this);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
   }
}
