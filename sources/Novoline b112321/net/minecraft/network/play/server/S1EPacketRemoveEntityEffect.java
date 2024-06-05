package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

public class S1EPacketRemoveEntityEffect implements Packet {
   private int entityId;
   private int effectId;

   public S1EPacketRemoveEntityEffect() {
   }

   public S1EPacketRemoveEntityEffect(int var1, PotionEffect var2) {
      this.entityId = var1;
      this.effectId = var2.getPotionID();
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.effectId = var1.readUnsignedByte();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeByte(this.effectId);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleRemoveEntityEffect(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public int getEffectId() {
      return this.effectId;
   }
}
