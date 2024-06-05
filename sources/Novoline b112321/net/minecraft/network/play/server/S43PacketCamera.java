package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S43PacketCamera implements Packet {
   public int entityId;

   public S43PacketCamera() {
   }

   public S43PacketCamera(Entity var1) {
      this.entityId = var1.getEntityID();
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleCamera(this);
   }

   public Entity getEntity(World var1) {
      return var1.getEntityByID(this.entityId);
   }
}
