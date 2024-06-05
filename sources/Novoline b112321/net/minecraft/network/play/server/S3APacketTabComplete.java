package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S3APacketTabComplete implements Packet {
   private String[] matches;

   public S3APacketTabComplete() {
   }

   public S3APacketTabComplete(String[] var1) {
      this.matches = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.matches = new String[var1.readVarIntFromBuffer()];

      for(int var2 = 0; var2 < this.matches.length; ++var2) {
         this.matches[var2] = var1.a(32767);
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.matches.length);

      for(String var5 : this.matches) {
         var1.writeString(var5);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleTabComplete(this);
   }

   public String[] func_149630_c() {
      return this.matches;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
