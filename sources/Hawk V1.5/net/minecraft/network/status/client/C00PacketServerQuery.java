package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class C00PacketServerQuery implements Packet {
   private static final String __OBFID = "CL_00001393";

   public void processPacket(INetHandler var1) {
      this.func_180775_a((INetHandlerStatusServer)var1);
   }

   public void func_180775_a(INetHandlerStatusServer var1) {
      var1.processServerQuery(this);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
   }
}
