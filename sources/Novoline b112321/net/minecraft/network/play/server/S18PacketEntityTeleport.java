package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S18PacketEntityTeleport implements Packet {
   private int entityId;
   private int posX;
   private int posY;
   private int posZ;
   private byte yaw;
   private byte pitch;
   private boolean onGround;

   public S18PacketEntityTeleport() {
   }

   public S18PacketEntityTeleport(Entity var1) {
      this.entityId = var1.getEntityID();
      this.posX = MathHelper.floor_double(var1.posX * 32.0D);
      this.posY = MathHelper.floor_double(var1.posY * 32.0D);
      this.posZ = MathHelper.floor_double(var1.posZ * 32.0D);
      this.yaw = (byte)((int)(var1.rotationYaw * 256.0F / 360.0F));
      this.pitch = (byte)((int)(var1.rotationPitch * 256.0F / 360.0F));
      this.onGround = var1.onGround;
   }

   public S18PacketEntityTeleport(int var1, int var2, int var3, int var4, byte var5, byte var6, boolean var7) {
      this.entityId = var1;
      this.posX = var2;
      this.posY = var3;
      this.posZ = var4;
      this.yaw = var5;
      this.pitch = var6;
      this.onGround = var7;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.posX = var1.readInt();
      this.posY = var1.readInt();
      this.posZ = var1.readInt();
      this.yaw = var1.readByte();
      this.pitch = var1.readByte();
      this.onGround = var1.readBoolean();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeInt(this.posX);
      var1.writeInt(this.posY);
      var1.writeInt(this.posZ);
      var1.writeByte(this.yaw);
      var1.writeByte(this.pitch);
      var1.writeBoolean(this.onGround);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityTeleport(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public int getX() {
      return this.posX;
   }

   public int getY() {
      return this.posY;
   }

   public int getZ() {
      return this.posZ;
   }

   public byte getYaw() {
      return this.yaw;
   }

   public byte getPitch() {
      return this.pitch;
   }

   public boolean getOnGround() {
      return this.onGround;
   }
}
