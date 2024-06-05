package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketMoveVehicle implements Packet {
   private double x;
   private double y;
   private double z;
   private float yaw;
   private float pitch;

   public SPacketMoveVehicle() {
   }

   public SPacketMoveVehicle(Entity var1) {
      this.x = var1.posX;
      this.y = var1.posY;
      this.z = var1.posZ;
      this.yaw = var1.rotationYaw;
      this.pitch = var1.rotationPitch;
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

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleMoveVehicle(this);
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
