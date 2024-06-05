package net.minecraft.network.play.client;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.world.WorldServer;

public class C18PacketSpectate implements Packet {
   private UUID field_179729_a;
   private static final String __OBFID = "CL_00002280";

   public void func_179728_a(INetHandlerPlayServer var1) {
      var1.func_175088_a(this);
   }

   public C18PacketSpectate(UUID var1) {
      this.field_179729_a = var1;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeUuid(this.field_179729_a);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179729_a = var1.readUuid();
   }

   public Entity func_179727_a(WorldServer var1) {
      return var1.getEntityFromUuid(this.field_179729_a);
   }

   public void processPacket(INetHandler var1) {
      this.func_179728_a((INetHandlerPlayServer)var1);
   }

   public C18PacketSpectate() {
   }
}
