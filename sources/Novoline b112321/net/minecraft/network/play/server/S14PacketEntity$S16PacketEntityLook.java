package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S14PacketEntity;

public class S14PacketEntity$S16PacketEntityLook extends S14PacketEntity {
   public S14PacketEntity$S16PacketEntityLook() {
      this.field_149069_g = true;
   }

   public S14PacketEntity$S16PacketEntityLook(int var1, byte var2, byte var3, boolean var4) {
      super(var1);
      this.yaw = var2;
      this.pitch = var3;
      this.field_149069_g = true;
      this.onGround = var4;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      super.readPacketData(var1);
      this.yaw = var1.readByte();
      this.pitch = var1.readByte();
      this.onGround = var1.readBoolean();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      super.writePacketData(var1);
      var1.writeByte(this.yaw);
      var1.writeByte(this.pitch);
      var1.writeBoolean(this.onGround);
   }
}
