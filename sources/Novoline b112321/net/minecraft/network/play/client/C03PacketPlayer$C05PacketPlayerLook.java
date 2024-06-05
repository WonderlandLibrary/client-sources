package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class C03PacketPlayer$C05PacketPlayerLook extends C03PacketPlayer {
   public C03PacketPlayer$C05PacketPlayerLook() {
      this.rotating = true;
   }

   public C03PacketPlayer$C05PacketPlayerLook(float var1, float var2, boolean var3) {
      this.yaw = var1;
      this.pitch = var2;
      this.onGround = var3;
      this.rotating = true;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.yaw = var1.readFloat();
      this.pitch = var1.readFloat();
      super.readPacketData(var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeFloat(this.yaw);
      var1.writeFloat(this.pitch);
      super.writePacketData(var1);
   }
}
