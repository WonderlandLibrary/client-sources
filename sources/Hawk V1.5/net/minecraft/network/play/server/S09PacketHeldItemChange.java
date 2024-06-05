package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S09PacketHeldItemChange implements Packet {
   private static final String __OBFID = "CL_00001324";
   private int field_149387_a;

   public void processPacket(INetHandler var1) {
      this.func_180746_a((INetHandlerPlayClient)var1);
   }

   public void func_180746_a(INetHandlerPlayClient var1) {
      var1.handleHeldItemChange(this);
   }

   public S09PacketHeldItemChange(int var1) {
      this.field_149387_a = var1;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.field_149387_a);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149387_a = var1.readByte();
   }

   public S09PacketHeldItemChange() {
   }

   public int func_149385_c() {
      return this.field_149387_a;
   }
}
