package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketVehicleMove implements Packet {
   private double x;
   private double y;
   private double z;
   private float yaw;
   private float pitch;

   public CPacketVehicleMove() {
   }

   public CPacketVehicleMove(Entity var1) {
      this.x = var1.posX;
      this.y = var1.posY;
      this.z = var1.posZ;
      this.yaw = var1.rotationYaw;
      this.pitch = var1.rotationPitch;
   }

   public CPacketVehicleMove(Entity var1, float var2, float var3) {
      this.x = var1.posX;
      this.y = var1.posY;
      this.z = var1.posZ;
      this.yaw = var2;
      this.pitch = var3;
   }

   public CPacketVehicleMove(Entity var1, double var2, double var4, double var6) {
      this.x = var2;
      this.y = var4;
      this.z = var6;
      this.yaw = var1.rotationYaw;
      this.pitch = var1.rotationPitch;
   }

   public CPacketVehicleMove(double var1, double var3, double var5, float var7, float var8) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.yaw = var7;
      this.pitch = var8;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.x = var1.readDouble();
      this.y = var1.readDouble();
      this.z = var1.readDouble();
      this.yaw = var1.readFloat();
      this.pitch = var1.readFloat();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeDouble(this.x);
      var1.writeDouble(this.y);
      var1.writeDouble(this.z);
      var1.writeFloat(this.yaw);
      var1.writeFloat(this.pitch);
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processVehicleMove(this);
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }
}
