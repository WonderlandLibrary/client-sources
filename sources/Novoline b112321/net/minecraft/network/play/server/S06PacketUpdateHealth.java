package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S06PacketUpdateHealth implements Packet {
   private float health;
   private int foodLevel;
   private float saturationLevel;

   public S06PacketUpdateHealth() {
   }

   public S06PacketUpdateHealth(float var1, int var2, float var3) {
      this.health = var1;
      this.foodLevel = var2;
      this.saturationLevel = var3;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.health = var1.readFloat();
      this.foodLevel = var1.readVarIntFromBuffer();
      this.saturationLevel = var1.readFloat();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeFloat(this.health);
      var1.writeVarIntToBuffer(this.foodLevel);
      var1.writeFloat(this.saturationLevel);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleUpdateHealth(this);
   }

   public float getHealth() {
      return this.health;
   }

   public int getFoodLevel() {
      return this.foodLevel;
   }

   public float getSaturationLevel() {
      return this.saturationLevel;
   }

   public void setHealth(float var1) {
      this.health = var1;
   }

   public void setFoodLevel(int var1) {
      this.foodLevel = var1;
   }

   public void setSaturationLevel(float var1) {
      this.saturationLevel = var1;
   }
}
