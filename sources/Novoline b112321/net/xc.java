package net;

import net.Q5;
import net.aSv;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer$C05PacketPlayerLook;

public class xc implements Q5 {
   private C03PacketPlayer$C05PacketPlayerLook a;

   public xc(C03PacketPlayer$C05PacketPlayerLook var1) {
      this.a = var1;
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
   public void setOnGround(boolean var1) {
      this.a.setOnGround(var1);
   }

   @aSv
   public Packet getPacket() {
      return this.a;
   }

   @aSv
   public String getName() {
      return "0x05";
   }
}
