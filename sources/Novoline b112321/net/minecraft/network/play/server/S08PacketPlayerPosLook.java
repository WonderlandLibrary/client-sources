package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Set;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S08PacketPlayerPosLook$EnumFlags;

public class S08PacketPlayerPosLook implements Packet {
   private double x;
   private double y;
   private double z;
   private float yaw;
   private float pitch;
   public Set field_179835_f;

   public S08PacketPlayerPosLook() {
   }

   public S08PacketPlayerPosLook(double var1, double var3, double var5, float var7, float var8, Set var9) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.yaw = var7;
      this.pitch = var8;
      this.field_179835_f = var9;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.x = var1.readDouble();
      this.y = var1.readDouble();
      this.z = var1.readDouble();
      this.yaw = var1.readFloat();
      this.pitch = var1.readFloat();
      this.field_179835_f = S08PacketPlayerPosLook$EnumFlags.func_180053_a(var1.readUnsignedByte());
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeDouble(this.x);
      var1.writeDouble(this.y);
      var1.writeDouble(this.z);
      var1.writeFloat(this.yaw);
      var1.writeFloat(this.pitch);
      var1.writeByte(S08PacketPlayerPosLook$EnumFlags.func_180056_a(this.field_179835_f));
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handlePlayerPosLook(this);
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

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setPitch(float var1) {
      this.pitch = var1;
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

   public Set func_179834_f() {
      return this.field_179835_f;
   }
}
