package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C03PacketPlayer implements Packet {
   protected double x;
   protected double y;
   protected double z;
   protected float yaw;
   protected float pitch;
   protected boolean onGround;
   protected boolean moving;
   protected boolean rotating;

   public C03PacketPlayer() {
   }

   public C03PacketPlayer(boolean var1) {
      this.onGround = var1;
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processPlayer(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.onGround = var1.readUnsignedByte() != 0;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.onGround?1:0);
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

   public boolean isOnGround() {
      return this.onGround;
   }

   public boolean isMoving() {
      return this.moving;
   }

   public boolean isRotating() {
      return this.rotating;
   }

   public void setMoving(boolean var1) {
      this.moving = var1;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public void setZ(double var1) {
      this.z = var1;
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public void setPitch(float var1) {
      this.pitch = var1;
   }

   public void setOnGround(boolean var1) {
      this.onGround = var1;
   }

   public void setRotating(boolean var1) {
      this.rotating = var1;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
