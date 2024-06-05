package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;

public class C13PacketPlayerAbilities implements Packet {
   private boolean disabledDamage;
   private boolean flying;
   private boolean allowFlying;
   private boolean creativeMode;
   private float flySpeed;
   private float walkSpeed;

   public C13PacketPlayerAbilities() {
   }

   public C13PacketPlayerAbilities(PlayerAbilities var1) {
      this.setDisabledDamage(var1.isDisabledDamage());
      this.setFlying(var1.isFlying());
      this.setAllowFlying(var1.isAllowFlying());
      this.setCreativeMode(var1.isCreative());
      this.setFlySpeed(var1.getFlySpeed());
      this.setWalkSpeed(var1.getWalkSpeed());
   }

   public C13PacketPlayerAbilities(S39PacketPlayerAbilities var1) {
      this.setDisabledDamage(var1.isDisabledDamage());
      this.setFlying(var1.isFlying());
      this.setAllowFlying(var1.isAllowFlying());
      this.setCreativeMode(var1.isCreative());
      this.setFlySpeed(var1.getFlySpeed());
      this.setWalkSpeed(var1.getWalkSpeed());
   }

   public C13PacketPlayerAbilities(float var1, float var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      this.setFlying(var3);
      this.setAllowFlying(var4);
      this.setCreativeMode(var5);
      this.setDisabledDamage(var6);
      this.setFlySpeed(var1);
      this.setWalkSpeed(var2);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      byte var2 = var1.readByte();
      this.setDisabledDamage((var2 & 1) > 0);
      this.setFlying((var2 & 2) > 0);
      this.setAllowFlying((var2 & 4) > 0);
      this.setCreativeMode((var2 & 8) > 0);
      this.setFlySpeed(var1.readFloat());
      this.setWalkSpeed(var1.readFloat());
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      byte var2 = 0;
      if(this.isDisabledDamage()) {
         var2 = (byte)(var2 | 1);
      }

      if(this.isFlying()) {
         var2 = (byte)(var2 | 2);
      }

      if(this.isAllowFlying()) {
         var2 = (byte)(var2 | 4);
      }

      if(this.isCreativeMode()) {
         var2 = (byte)(var2 | 8);
      }

      var1.writeByte(var2);
      var1.writeFloat(this.flySpeed);
      var1.writeFloat(this.walkSpeed);
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processPlayerAbilities(this);
   }

   public void setDisabledDamage(boolean var1) {
      this.disabledDamage = var1;
   }

   public void setFlying(boolean var1) {
      this.flying = var1;
   }

   public void setAllowFlying(boolean var1) {
      this.allowFlying = var1;
   }

   public void setCreativeMode(boolean var1) {
      this.creativeMode = var1;
   }

   public void setFlySpeed(float var1) {
      this.flySpeed = var1;
   }

   public void setWalkSpeed(float var1) {
      this.walkSpeed = var1;
   }

   public boolean isDisabledDamage() {
      return this.disabledDamage;
   }

   public boolean isFlying() {
      return this.flying;
   }

   public boolean isAllowFlying() {
      return this.allowFlying;
   }

   public boolean isCreativeMode() {
      return this.creativeMode;
   }

   public float getFlySpeed() {
      return this.flySpeed;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
