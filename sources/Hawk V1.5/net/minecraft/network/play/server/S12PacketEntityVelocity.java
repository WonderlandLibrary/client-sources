package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S12PacketEntityVelocity implements Packet {
   private int field_149417_a;
   private static final String __OBFID = "CL_00001328";
   private int field_149414_d;
   private int field_149415_b;
   private int field_149416_c;

   public void setMotionY(int var1) {
      this.field_149416_c = var1;
   }

   public void setMotionZ(int var1) {
      this.field_149414_d = var1;
   }

   public int func_149409_f() {
      return this.field_149414_d;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149417_a);
      var1.writeShort(this.field_149415_b);
      var1.writeShort(this.field_149416_c);
      var1.writeShort(this.field_149414_d);
   }

   public S12PacketEntityVelocity(Entity var1) {
      this(var1.getEntityId(), var1.motionX, var1.motionY, var1.motionZ);
   }

   public void setMotionX(int var1) {
      this.field_149415_b = var1;
   }

   public int func_149412_c() {
      return this.field_149417_a;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149417_a = var1.readVarIntFromBuffer();
      this.field_149415_b = var1.readShort();
      this.field_149416_c = var1.readShort();
      this.field_149414_d = var1.readShort();
   }

   public int func_149410_e() {
      return this.field_149416_c;
   }

   public int func_149411_d() {
      return this.field_149415_b;
   }

   public S12PacketEntityVelocity(int var1, double var2, double var4, double var6) {
      this.field_149417_a = var1;
      double var8 = 3.9D;
      if (var2 < -var8) {
         var2 = -var8;
      }

      if (var4 < -var8) {
         var4 = -var8;
      }

      if (var6 < -var8) {
         var6 = -var8;
      }

      if (var2 > var8) {
         var2 = var8;
      }

      if (var4 > var8) {
         var4 = var8;
      }

      if (var6 > var8) {
         var6 = var8;
      }

      this.field_149415_b = (int)(var2 * 8000.0D);
      this.field_149416_c = (int)(var4 * 8000.0D);
      this.field_149414_d = (int)(var6 * 8000.0D);
   }

   public S12PacketEntityVelocity() {
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityVelocity(this);
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }
}
