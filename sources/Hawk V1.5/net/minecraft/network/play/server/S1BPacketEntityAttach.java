package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1BPacketEntityAttach implements Packet {
   private int field_149408_a;
   private int field_149406_b;
   private static final String __OBFID = "CL_00001327";
   private int field_149407_c;

   public S1BPacketEntityAttach(int var1, Entity var2, Entity var3) {
      this.field_149408_a = var1;
      this.field_149406_b = var2.getEntityId();
      this.field_149407_c = var3 != null ? var3.getEntityId() : -1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149406_b = var1.readInt();
      this.field_149407_c = var1.readInt();
      this.field_149408_a = var1.readUnsignedByte();
   }

   public int func_149404_c() {
      return this.field_149408_a;
   }

   public S1BPacketEntityAttach() {
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityAttach(this);
   }

   public int func_149402_e() {
      return this.field_149407_c;
   }

   public int func_149403_d() {
      return this.field_149406_b;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeInt(this.field_149406_b);
      var1.writeInt(this.field_149407_c);
      var1.writeByte(this.field_149408_a);
   }
}
