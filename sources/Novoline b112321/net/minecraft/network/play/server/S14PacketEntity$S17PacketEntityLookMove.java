package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S14PacketEntity;

public class S14PacketEntity$S17PacketEntityLookMove extends S14PacketEntity {
   public S14PacketEntity$S17PacketEntityLookMove() {
      this.field_149069_g = true;
   }

   public S14PacketEntity$S17PacketEntityLookMove(int var1, byte var2, byte var3, byte var4, byte var5, byte var6, boolean var7) {
      super(var1);
      this.posX = var2;
      this.posY = var3;
      this.posZ = var4;
      this.yaw = var5;
      this.pitch = var6;
      this.onGround = var7;
      this.field_149069_g = true;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      super.readPacketData(var1);
      this.posX = var1.readByte();
      this.posY = var1.readByte();
      this.posZ = var1.readByte();
      this.yaw = var1.readByte();
      this.pitch = var1.readByte();
      this.onGround = var1.readBoolean();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      super.writePacketData(var1);
      var1.writeByte(this.posX);
      var1.writeByte(this.posY);
      var1.writeByte(this.posZ);
      var1.writeByte(this.yaw);
      var1.writeByte(this.pitch);
      var1.writeBoolean(this.onGround);
   }
}
