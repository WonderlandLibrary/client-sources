package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class C01PacketPing implements Packet {
   private long clientTime;
   private static final String __OBFID = "CL_00001392";

   public long getClientTime() {
      return this.clientTime;
   }

   public void func_180774_a(INetHandlerStatusServer var1) {
      var1.processPing(this);
   }

   public C01PacketPing() {
   }

   public C01PacketPing(long var1) {
      this.clientTime = var1;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeLong(this.clientTime);
   }

   public void processPacket(INetHandler var1) {
      this.func_180774_a((INetHandlerStatusServer)var1);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.clientTime = var1.readLong();
   }
}
