package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S0DPacketCollectItem implements Packet {
   private int collectedItemEntityId;
   private int entityId;

   public S0DPacketCollectItem() {
   }

   public S0DPacketCollectItem(int var1, int var2) {
      this.collectedItemEntityId = var1;
      this.entityId = var2;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.collectedItemEntityId = var1.readVarIntFromBuffer();
      this.entityId = var1.readVarIntFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.collectedItemEntityId);
      var1.writeVarIntToBuffer(this.entityId);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleCollectItem(this);
   }

   public int getCollectedItemEntityID() {
      return this.collectedItemEntityId;
   }

   public int getEntityID() {
      return this.entityId;
   }
}
