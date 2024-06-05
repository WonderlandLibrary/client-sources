package net;

import net.Q5;
import net.aSv;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class bQ implements Q5 {
   C03PacketPlayer a;

   public bQ(C03PacketPlayer var1) {
      this.a = var1;
   }

   @aSv
   public void setOnGround(boolean var1) {
      this.a.setOnGround(var1);
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
      return "0x03";
   }
}
