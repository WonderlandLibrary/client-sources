package net;

import net.Q5;
import net.aSv;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0CPacketInput;

public class ar0 implements Q5 {
   private C0CPacketInput a;

   public ar0(C0CPacketInput var1) {
      this.a = var1;
   }

   @aSv
   public float getStrafeSpeed() {
      return this.a.getStrafeSpeed();
   }

   @aSv
   public float getForwardSpeed() {
      return this.a.getForwardSpeed();
   }

   @aSv
   public boolean isSneaking() {
      return this.a.isSneaking();
   }

   @aSv
   public boolean isJumping() {
      return this.a.isJumping();
   }

   @aSv
   public void setStrafeSpeed(float var1) {
      this.a.b(var1);
   }

   @aSv
   public void setForwardSpeed(float var1) {
      this.a.a(var1);
   }

   @aSv
   public void setSneaking(boolean var1) {
      this.a.b(var1);
   }

   @aSv
   public void setJumping(boolean var1) {
      this.a.a(var1);
   }

   @aSv
   public Packet getPacket() {
      return this.a;
   }

   @aSv
   public String getName() {
      return "0x0C";
   }
}
