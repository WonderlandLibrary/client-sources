package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityHeadLook implements Packet {
   private byte field_149383_b;
   private int field_149384_a;
   private static final String __OBFID = "CL_00001323";

   public Entity func_149381_a(World var1) {
      return var1.getEntityByID(this.field_149384_a);
   }

   public S19PacketEntityHeadLook() {
   }

   public byte func_149380_c() {
      return this.field_149383_b;
   }

   public void processPacket(INetHandler var1) {
      this.func_180745_a((INetHandlerPlayClient)var1);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149384_a = var1.readVarIntFromBuffer();
      this.field_149383_b = var1.readByte();
   }

   public S19PacketEntityHeadLook(Entity var1, byte var2) {
      this.field_149384_a = var1.getEntityId();
      this.field_149383_b = var2;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149384_a);
      var1.writeByte(this.field_149383_b);
   }

   public void func_180745_a(INetHandlerPlayClient var1) {
      var1.handleEntityHeadLook(this);
   }
}
