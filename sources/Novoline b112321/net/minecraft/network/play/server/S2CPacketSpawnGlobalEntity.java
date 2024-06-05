package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S2CPacketSpawnGlobalEntity implements Packet {
   private int entityId;
   private int x;
   private int y;
   private int z;
   private int type;

   public S2CPacketSpawnGlobalEntity() {
   }

   public S2CPacketSpawnGlobalEntity(Entity var1) {
      this.entityId = var1.getEntityID();
      this.x = MathHelper.floor_double(var1.posX * 32.0D);
      this.y = MathHelper.floor_double(var1.posY * 32.0D);
      this.z = MathHelper.floor_double(var1.posZ * 32.0D);
      if(var1 instanceof EntityLightningBolt) {
         this.type = 1;
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.type = var1.readByte();
      this.x = var1.readInt();
      this.y = var1.readInt();
      this.z = var1.readInt();
   }

   public int getEntityId() {
      return this.entityId;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeByte(this.type);
      var1.writeInt(this.x);
      var1.writeInt(this.y);
      var1.writeInt(this.z);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleSpawnGlobalEntity(this);
   }

   public int func_149052_c() {
      return this.entityId;
   }

   public int func_149051_d() {
      return this.x;
   }

   public int func_149050_e() {
      return this.y;
   }

   public int func_149049_f() {
      return this.z;
   }

   public int func_149053_g() {
      return this.type;
   }
}
