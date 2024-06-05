package net;

import net.Q5;
import net.aSv;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;

public class to implements Q5 {
   private C03PacketPlayer$C06PacketPlayerPosLook a;

   public to(C03PacketPlayer$C06PacketPlayerPosLook var1) {
      this.a = var1;
   }

   @aSv
   public void setX(double var1) {
      this.a.setX(var1);
   }

   @aSv
   public void setY(double var1) {
      this.a.setY(var1);
   }

   @aSv
   public void setZ(double var1) {
      this.a.setZ(var1);
   }

   @aSv
   public void setOnGround(boolean var1) {
      this.a.setOnGround(var1);
   }

   @aSv
   public double getX() {
      return this.a.getX();
   }

   @aSv
   public double getY() {
      return this.a.getY();
   }

   @aSv
   public double getZ() {
      return this.a.getZ();
   }

   @aSv
   public void setYaw(float var1) {
      this.a.setYaw(var1);
   }

   @aSv
   public void setPitch(float var1) {
      this.a.setPitch(var1);
   }

   @aSv
   public float getYaw() {
      return this.a.getYaw();
   }

   @aSv
   public float getPitch() {
      return this.a.getPitch();
   }

   @aSv
   public boolean isOnGround() {
      return this.a.isOnGround();
   }

   @aSv
   public Packet getPacket() {
      return this.a;
   }

   @aSv
   public String getName() {
      return "0x06";
   }
}
