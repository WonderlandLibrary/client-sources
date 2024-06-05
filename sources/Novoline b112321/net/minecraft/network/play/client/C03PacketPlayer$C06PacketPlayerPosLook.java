package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class C03PacketPlayer$C06PacketPlayerPosLook extends C03PacketPlayer {
   public C03PacketPlayer$C06PacketPlayerPosLook() {
      this.moving = true;
      this.rotating = true;
   }

   public C03PacketPlayer$C06PacketPlayerPosLook(double var1, double var3, double var5, float var7, float var8, boolean var9) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.yaw = var7;
      this.pitch = var8;
      this.onGround = var9;
      this.rotating = true;
      this.moving = true;
   }

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
}
