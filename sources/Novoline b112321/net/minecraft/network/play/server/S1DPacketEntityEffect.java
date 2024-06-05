package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

public class S1DPacketEntityEffect implements Packet {
   private int entityId;
   private byte effectId;
   private byte amplifier;
   private int duration;
   private byte hideParticles;
   private PotionEffect effect;

   public S1DPacketEntityEffect() {
   }

   public S1DPacketEntityEffect(int var1, PotionEffect var2) {
      this.effect = var2;
      this.entityId = var1;
      this.effectId = (byte)(var2.getPotionID() & 255);
      this.amplifier = (byte)(var2.getAmplifier() & 255);
      this.duration = Math.min(var2.getDuration(), 32767);
      this.hideParticles = (byte)(var2.getIsShowParticles()?1:0);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.effectId = var1.readByte();
      this.amplifier = var1.readByte();
      this.duration = var1.readVarIntFromBuffer();
      this.hideParticles = var1.readByte();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeByte(this.effectId);
      var1.writeByte(this.amplifier);
      var1.writeVarIntToBuffer(this.duration);
      var1.writeByte(this.hideParticles);
   }

   public boolean func_149429_c() {
      return this.duration == 32767;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityEffect(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public byte getEffectId() {
      return this.effectId;
   }

   public byte getAmplifier() {
      return this.amplifier;
   }

   public int getDuration() {
      return this.duration;
   }

   public boolean func_179707_f() {
      return this.hideParticles != 0;
   }

   public PotionEffect getEffect() {
      return this.effect;
   }

   public void setEntityId(int var1) {
      this.entityId = var1;
   }

   public void setEffectId(byte var1) {
      this.effectId = var1;
   }

   public void setAmplifier(byte var1) {
      this.amplifier = var1;
   }

   public void setDuration(int var1) {
      this.duration = var1;
   }

   public void setHideParticles(byte var1) {
      this.hideParticles = var1;
   }

   public void setEffect(PotionEffect var1) {
      this.effect = var1;
   }
}
