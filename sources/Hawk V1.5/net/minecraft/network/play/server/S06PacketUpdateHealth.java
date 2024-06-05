package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S06PacketUpdateHealth implements Packet {
   private static final String __OBFID = "CL_00001332";
   private float saturationLevel;
   private float health;
   private int foodLevel;

   public void func_180750_a(INetHandlerPlayClient var1) {
      var1.handleUpdateHealth(this);
   }

   public float getSaturationLevel() {
      return this.saturationLevel;
   }

   public float getHealth() {
      return this.health;
   }

   public S06PacketUpdateHealth() {
   }

   public S06PacketUpdateHealth(float var1, int var2, float var3) {
      this.health = var1;
      this.foodLevel = var2;
      this.saturationLevel = var3;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeFloat(this.health);
      var1.writeVarIntToBuffer(this.foodLevel);
      var1.writeFloat(this.saturationLevel);
   }

   public int getFoodLevel() {
      return this.foodLevel;
   }

   public void processPacket(INetHandler var1) {
      this.func_180750_a((INetHandlerPlayClient)var1);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.health = var1.readFloat();
      this.foodLevel = var1.readVarIntFromBuffer();
      this.saturationLevel = var1.readFloat();
   }
}
