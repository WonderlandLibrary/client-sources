package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.rF;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1CPacketEntityMetadata implements Packet {
   private int entityId;
   private List field_149378_b;

   public S1CPacketEntityMetadata() {
   }

   public S1CPacketEntityMetadata(int var1, rF var2, boolean var3) {
      this.entityId = var1;
      this.field_149378_b = var2.c();
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.field_149378_b = rF.a(var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      rF.a(this.field_149378_b, var1);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityMetadata(this);
   }

   public List func_149376_c() {
      return this.field_149378_b;
   }

   public int getEntityId() {
      return this.entityId;
   }
}
