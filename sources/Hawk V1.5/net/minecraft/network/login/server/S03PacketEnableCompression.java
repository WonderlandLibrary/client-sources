package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class S03PacketEnableCompression implements Packet {
   private int field_179733_a;
   private static final String __OBFID = "CL_00002279";

   public S03PacketEnableCompression() {
   }

   public int func_179731_a() {
      return this.field_179733_a;
   }

   public void func_179732_a(INetHandlerLoginClient var1) {
      var1.func_180464_a(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179733_a = var1.readVarIntFromBuffer();
   }

   public S03PacketEnableCompression(int var1) {
      this.field_179733_a = var1;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_179733_a);
   }

   public void processPacket(INetHandler var1) {
      this.func_179732_a((INetHandlerLoginClient)var1);
   }
}
