package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S13PacketDestroyEntities implements Packet {
   private int[] entityIDs;

   public S13PacketDestroyEntities() {
   }

   public S13PacketDestroyEntities(int... var1) {
      this.entityIDs = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityIDs = new int[var1.readVarIntFromBuffer()];

      for(int var2 = 0; var2 < this.entityIDs.length; ++var2) {
         this.entityIDs[var2] = var1.readVarIntFromBuffer();
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityIDs.length);

      for(int var5 : this.entityIDs) {
         var1.writeVarIntToBuffer(var5);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleDestroyEntities(this);
   }

   public int[] getEntityIDs() {
      return this.entityIDs;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
