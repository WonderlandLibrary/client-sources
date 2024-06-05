package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S48PacketResourcePackSend implements Packet {
   private String url;
   private String hash;

   public S48PacketResourcePackSend() {
   }

   public S48PacketResourcePackSend(String var1, String var2) {
      this.url = var1;
      this.hash = var2;
      if(var2.length() > 40) {
         throw new IllegalArgumentException("Hash is too long (max 40, was " + var2.length() + ")");
      }
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.url = var1.a(32767);
      this.hash = var1.a(40);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.url);
      var1.writeString(this.hash);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleResourcePack(this);
   }

   public String getURL() {
      return this.url;
   }

   public String getHash() {
      return this.hash;
   }
}
