package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S12PacketEntityVelocity implements Packet {
   private int entityID;
   private int motionX;
   private int motionY;
   private int motionZ;

   public S12PacketEntityVelocity() {
   }

   public S12PacketEntityVelocity(Entity var1) {
      this(var1.getEntityID(), var1.motionX, var1.motionY, var1.motionZ);
   }

   public S12PacketEntityVelocity(int var1, double var2, double var4, double var6) {
      this.entityID = var1;
      double var8 = 3.9D;
      if(var2 < -var8) {
         var2 = -var8;
      }

      if(var4 < -var8) {
         var4 = -var8;
      }

      if(var6 < -var8) {
         var6 = -var8;
      }

      if(var2 > var8) {
         var2 = var8;
      }

      if(var4 > var8) {
         var4 = var8;
      }

      if(var6 > var8) {
         var6 = var8;
      }

      this.motionX = (int)(var2 * 8000.0D);
      this.motionY = (int)(var4 * 8000.0D);
      this.motionZ = (int)(var6 * 8000.0D);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityID = var1.readVarIntFromBuffer();
      this.motionX = var1.readShort();
      this.motionY = var1.readShort();
      this.motionZ = var1.readShort();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityID);
      var1.writeShort(this.motionX);
      var1.writeShort(this.motionY);
      var1.writeShort(this.motionZ);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityVelocity(this);
   }

   public int getEntityID() {
      return this.entityID;
   }

   public int getMotionX() {
      return this.motionX;
   }

   public int getMotionY() {
      return this.motionY;
   }

   public int getMotionZ() {
      return this.motionZ;
   }

   public void setMotionX(int var1) {
      this.motionX = var1;
   }

   public void setMotionY(int var1) {
      this.motionY = var1;
   }

   public void setMotionZ(int var1) {
      this.motionZ = var1;
   }
}
