package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class C03PacketPlayer$C04PacketPlayerPosition extends C03PacketPlayer {
   public C03PacketPlayer$C04PacketPlayerPosition() {
      this.moving = true;
   }

   public C03PacketPlayer$C04PacketPlayerPosition(double var1, double var3, double var5, boolean var7) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.onGround = var7;
      this.moving = true;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.x = var1.readDouble();
      this.y = var1.readDouble();
      this.z = var1.readDouble();
      super.readPacketData(var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeDouble(this.x);
      var1.writeDouble(this.y);
      var1.writeDouble(this.z);
      super.writePacketData(var1);
   }
}
