package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S0DPacketCollectItem implements Packet {
   private int field_149357_a;
   private static final String __OBFID = "CL_00001339";
   private int field_149356_b;

   public S0DPacketCollectItem(int var1, int var2) {
      this.field_149357_a = var1;
      this.field_149356_b = var2;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149357_a = var1.readVarIntFromBuffer();
      this.field_149356_b = var1.readVarIntFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149357_a);
      var1.writeVarIntToBuffer(this.field_149356_b);
   }

   public int func_149353_d() {
      return this.field_149356_b;
   }

   public int func_149354_c() {
      return this.field_149357_a;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleCollectItem(this);
   }

   public S0DPacketCollectItem() {
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }
}
