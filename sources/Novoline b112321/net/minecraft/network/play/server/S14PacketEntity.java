package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S14PacketEntity implements Packet {
   protected int entityId;
   protected byte posX;
   protected byte posY;
   protected byte posZ;
   protected byte yaw;
   protected byte pitch;
   protected boolean onGround;
   protected boolean field_149069_g;

   public S14PacketEntity() {
   }

   public S14PacketEntity(int var1) {
      this.entityId = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityMovement(this);
   }

   public String toString() {
      return "Entity_" + super.toString();
   }

   public Entity getEntity(World var1) {
      return var1.getEntityByID(this.entityId);
   }

   public int getEntityID() {
      return this.entityId;
   }

   public byte getX() {
      return this.posX;
   }

   public byte getY() {
      return this.posY;
   }

   public byte getZ() {
      return this.posZ;
   }

   public byte getYaw() {
      return this.yaw;
   }

   public byte getPitch() {
      return this.pitch;
   }

   public boolean func_149060_h() {
      return this.field_149069_g;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public void setOnGround(boolean var1) {
      this.onGround = var1;
   }
}
