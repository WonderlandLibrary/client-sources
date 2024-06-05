package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0EPacketSpawnObject implements Packet {
   private int entityId;
   private int x;
   private int y;
   private int z;
   private int speedX;
   private int speedY;
   private int speedZ;
   private int pitch;
   private int yaw;
   private int type;
   private int field_149020_k;

   public S0EPacketSpawnObject() {
   }

   public S0EPacketSpawnObject(Entity var1, int var2) {
      this(var1, var2, 0);
   }

   public S0EPacketSpawnObject(Entity var1, int var2, int var3) {
      this.entityId = var1.getEntityID();
      this.x = MathHelper.floor_double(var1.posX * 32.0D);
      this.y = MathHelper.floor_double(var1.posY * 32.0D);
      this.z = MathHelper.floor_double(var1.posZ * 32.0D);
      this.pitch = MathHelper.floor_float(var1.rotationPitch * 256.0F / 360.0F);
      this.yaw = MathHelper.floor_float(var1.rotationYaw * 256.0F / 360.0F);
      this.type = var2;
      this.field_149020_k = var3;
      double var4 = var1.motionX;
      double var6 = var1.motionY;
      double var8 = var1.motionZ;
      double var10 = 3.9D;
      if(var4 < -var10) {
         var4 = -var10;
      }

      if(var6 < -var10) {
         var6 = -var10;
      }

      if(var8 < -var10) {
         var8 = -var10;
      }

      if(var4 > var10) {
         var4 = var10;
      }

      if(var6 > var10) {
         var6 = var10;
      }

      if(var8 > var10) {
         var8 = var10;
      }

      this.speedX = (int)(var4 * 8000.0D);
      this.speedY = (int)(var6 * 8000.0D);
      this.speedZ = (int)(var8 * 8000.0D);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.type = var1.readByte();
      this.x = var1.readInt();
      this.y = var1.readInt();
      this.z = var1.readInt();
      this.pitch = var1.readByte();
      this.yaw = var1.readByte();
      this.field_149020_k = var1.readInt();
      if(this.field_149020_k > 0) {
         this.speedX = var1.readShort();
         this.speedY = var1.readShort();
         this.speedZ = var1.readShort();
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeByte(this.type);
      var1.writeInt(this.x);
      var1.writeInt(this.y);
      var1.writeInt(this.z);
      var1.writeByte(this.pitch);
      var1.writeByte(this.yaw);
      var1.writeInt(this.field_149020_k);
      if(this.field_149020_k > 0) {
         var1.writeShort(this.speedX);
         var1.writeShort(this.speedY);
         var1.writeShort(this.speedZ);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleSpawnObject(this);
   }

   public int getEntityID() {
      return this.entityId;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public int getSpeedX() {
      return this.speedX;
   }

   public int getSpeedY() {
      return this.speedY;
   }

   public int getSpeedZ() {
      return this.speedZ;
   }

   public int getPitch() {
      return this.pitch;
   }

   public int getYaw() {
      return this.yaw;
   }

   public int getType() {
      return this.type;
   }

   public int func_149009_m() {
      return this.field_149020_k;
   }

   public void setX(int var1) {
      this.x = var1;
   }

   public void setY(int var1) {
      this.y = var1;
   }

   public void setZ(int var1) {
      this.z = var1;
   }

   public void setSpeedX(int var1) {
      this.speedX = var1;
   }

   public void setSpeedY(int var1) {
      this.speedY = var1;
   }

   public void setSpeedZ(int var1) {
      this.speedZ = var1;
   }

   public void func_149002_g(int var1) {
      this.field_149020_k = var1;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
