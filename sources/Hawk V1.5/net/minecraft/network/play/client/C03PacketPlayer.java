package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C03PacketPlayer implements Packet {
   public float pitch;
   public float yaw;
   public boolean rotating;
   public boolean field_149480_h;
   private static final String __OBFID = "CL_00001360";
   public double x;
   public boolean onground;
   public double z;
   public double y;

   public float getPitch() {
      return this.pitch;
   }

   public double getPositionY() {
      return this.y;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.onground ? 1 : 0);
   }

   public boolean func_149466_j() {
      return this.field_149480_h;
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processPlayer(this);
   }

   public void func_149469_a(boolean var1) {
      this.field_149480_h = var1;
   }

   public C03PacketPlayer() {
   }

   public C03PacketPlayer(boolean var1) {
      this.onground = var1;
   }

   public double getPositionX() {
      return this.x;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayServer)var1);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.onground = var1.readUnsignedByte() != 0;
   }

   public float getYaw() {
      return this.yaw;
   }

   public boolean func_149465_i() {
      return this.onground;
   }

   public double getPositionZ() {
      return this.z;
   }

   public boolean getRotating() {
      return this.rotating;
   }

   public static class C05PacketPlayerLook extends C03PacketPlayer {
      private static final String __OBFID = "CL_00001363";

      public C05PacketPlayerLook() {
         this.rotating = true;
      }

      public void processPacket(INetHandler var1) {
         super.processPacket((INetHandlerPlayServer)var1);
      }

      public void writePacketData(PacketBuffer var1) throws IOException {
         var1.writeFloat(this.yaw);
         var1.writeFloat(this.pitch);
         super.writePacketData(var1);
      }

      public void readPacketData(PacketBuffer var1) throws IOException {
         this.yaw = var1.readFloat();
         this.pitch = var1.readFloat();
         super.readPacketData(var1);
      }

      public C05PacketPlayerLook(float var1, float var2, boolean var3) {
         this.yaw = var1;
         this.pitch = var2;
         this.onground = var3;
         this.rotating = true;
      }
   }

   public static class C06PacketPlayerPosLook extends C03PacketPlayer {
      private static final String __OBFID = "CL_00001362";

      public void readPacketData(PacketBuffer var1) throws IOException {
         this.x = var1.readDouble();
         this.y = var1.readDouble();
         this.z = var1.readDouble();
         this.yaw = var1.readFloat();
         this.pitch = var1.readFloat();
         super.readPacketData(var1);
      }

      public void writePacketData(PacketBuffer var1) throws IOException {
         var1.writeDouble(this.x);
         var1.writeDouble(this.y);
         var1.writeDouble(this.z);
         var1.writeFloat(this.yaw);
         var1.writeFloat(this.pitch);
         super.writePacketData(var1);
      }

      public C06PacketPlayerPosLook() {
         this.field_149480_h = true;
         this.rotating = true;
      }

      public C06PacketPlayerPosLook(double var1, double var3, double var5, float var7, float var8, boolean var9) {
         this.x = var1;
         this.y = var3;
         this.z = var5;
         this.yaw = var7;
         this.pitch = var8;
         this.onground = var9;
         this.rotating = true;
         this.field_149480_h = true;
      }

      public void processPacket(INetHandler var1) {
         super.processPacket((INetHandlerPlayServer)var1);
      }
   }

   public static class C04PacketPlayerPosition extends C03PacketPlayer {
      private static final String __OBFID = "CL_00001361";

      public void processPacket(INetHandler var1) {
         super.processPacket((INetHandlerPlayServer)var1);
      }

      public C04PacketPlayerPosition() {
         this.field_149480_h = true;
      }

      public void writePacketData(PacketBuffer var1) throws IOException {
         var1.writeDouble(this.x);
         var1.writeDouble(this.y);
         var1.writeDouble(this.z);
         super.writePacketData(var1);
      }

      public void readPacketData(PacketBuffer var1) throws IOException {
         this.x = var1.readDouble();
         this.y = var1.readDouble();
         this.z = var1.readDouble();
         super.readPacketData(var1);
      }

      public C04PacketPlayerPosition(double var1, double var3, double var5, boolean var7) {
         this.x = var1;
         this.y = var3;
         this.z = var5;
         this.onground = var7;
         this.field_149480_h = true;
      }
   }
}
