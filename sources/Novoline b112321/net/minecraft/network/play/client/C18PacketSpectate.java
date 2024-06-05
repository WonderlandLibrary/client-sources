package net.minecraft.network.play.client;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.world.WorldServer;

public class C18PacketSpectate implements Packet {
   private UUID id;

   public UUID a() {
      return this.id;
   }

   public void a(UUID var1) {
      this.id = var1;
   }

   public C18PacketSpectate() {
   }

   public C18PacketSpectate(UUID var1) {
      this.id = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.id = var1.readUuid();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeUuid(this.id);
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.handleSpectate(this);
   }

   public Entity getEntity(WorldServer var1) {
      return var1.getEntityFromUuid(this.id);
   }
}
