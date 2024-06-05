package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S04PacketEntityEquipment implements Packet {
   private int field_149394_a;
   private static final String __OBFID = "CL_00001330";
   private int field_149392_b;
   private ItemStack field_149393_c;

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149394_a);
      var1.writeShort(this.field_149392_b);
      var1.writeItemStackToBuffer(this.field_149393_c);
   }

   public int func_149388_e() {
      return this.field_149392_b;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public ItemStack func_149390_c() {
      return this.field_149393_c;
   }

   public S04PacketEntityEquipment() {
   }

   public S04PacketEntityEquipment(int var1, int var2, ItemStack var3) {
      this.field_149394_a = var1;
      this.field_149392_b = var2;
      this.field_149393_c = var3 == null ? null : var3.copy();
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityEquipment(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149394_a = var1.readVarIntFromBuffer();
      this.field_149392_b = var1.readShort();
      this.field_149393_c = var1.readItemStackFromBuffer();
   }

   public int func_149389_d() {
      return this.field_149394_a;
   }
}
