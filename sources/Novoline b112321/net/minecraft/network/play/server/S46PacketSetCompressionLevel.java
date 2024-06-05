package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S46PacketSetCompressionLevel implements Packet {
   private int compressionLevel;

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.compressionLevel = var1.readVarIntFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.compressionLevel);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleSetCompressionLevel(this);
   }

   public int getCompressionLevel() {
      return this.compressionLevel;
   }
}
