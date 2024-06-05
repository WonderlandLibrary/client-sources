package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

public class S1EPacketRemoveEntityEffect implements Packet {
   private static final String __OBFID = "CL_00001321";
   private int field_149078_b;
   private int field_149079_a;

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149079_a);
      var1.writeByte(this.field_149078_b);
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public S1EPacketRemoveEntityEffect() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149079_a = var1.readVarIntFromBuffer();
      this.field_149078_b = var1.readUnsignedByte();
   }

   public int func_149075_d() {
      return this.field_149078_b;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleRemoveEntityEffect(this);
   }

   public int func_149076_c() {
      return this.field_149079_a;
   }

   public S1EPacketRemoveEntityEffect(int var1, PotionEffect var2) {
      this.field_149079_a = var1;
      this.field_149078_b = var2.getPotionID();
   }
}
