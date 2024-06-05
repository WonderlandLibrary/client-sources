package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2EPacketCloseWindow implements Packet {
   private int field_148896_a;
   private static final String __OBFID = "CL_00001292";

   public S2EPacketCloseWindow(int var1) {
      this.field_148896_a = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_148896_a = var1.readUnsignedByte();
   }

   public void func_180731_a(INetHandlerPlayClient var1) {
      var1.handleCloseWindow(this);
   }

   public S2EPacketCloseWindow() {
   }

   public void processPacket(INetHandler var1) {
      this.func_180731_a((INetHandlerPlayClient)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.field_148896_a);
   }
}
