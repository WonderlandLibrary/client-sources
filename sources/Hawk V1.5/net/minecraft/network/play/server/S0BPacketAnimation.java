package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S0BPacketAnimation implements Packet {
   private int type;
   private static final String __OBFID = "CL_00001282";
   private int entityId;

   public int func_148977_d() {
      return this.type;
   }

   public void func_180723_a(INetHandlerPlayClient var1) {
      var1.handleAnimation(this);
   }

   public S0BPacketAnimation() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.type = var1.readUnsignedByte();
   }

   public void processPacket(INetHandler var1) {
      this.func_180723_a((INetHandlerPlayClient)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeByte(this.type);
   }

   public int func_148978_c() {
      return this.entityId;
   }

   public S0BPacketAnimation(Entity var1, int var2) {
      this.entityId = var1.getEntityId();
      this.type = var2;
   }
}
