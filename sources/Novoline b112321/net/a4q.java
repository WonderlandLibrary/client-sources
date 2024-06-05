package net;

import net.Q5;
import net.a8Z;
import net.aSv;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;

public class a4q implements Q5 {
   private C13PacketPlayerAbilities a;

   public a4q(C13PacketPlayerAbilities var1) {
      a8Z.b();
      super();
      this.a = var1;
   }

   @aSv
   public void setFlySpeed(float var1) {
      this.a.setFlySpeed(var1);
   }

   @aSv
   public void setWalkSpeed(float var1) {
      this.a.setWalkSpeed(var1);
   }

   @aSv
   public void setFlying(boolean var1) {
      this.a.setFlying(var1);
   }

   @aSv
   public void setAllowFlying(boolean var1) {
      this.a.setAllowFlying(var1);
   }

   @aSv
   public void setCreativeMode(boolean var1) {
      this.a.setCreativeMode(var1);
   }

   @aSv
   public void setInvurnerable(boolean var1) {
      this.a.setDisabledDamage(var1);
   }

   @aSv
   public Packet getPacket() {
      return this.a;
   }

   @aSv
   public String getName() {
      return "0x13";
   }
}
