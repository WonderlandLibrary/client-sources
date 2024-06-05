package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S48PacketResourcePackSend implements Packet {
   private String hash;
   private String url;
   private static final String __OBFID = "CL_00002293";

   public S48PacketResourcePackSend() {
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.url);
      var1.writeString(this.hash);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.url = var1.readStringFromBuffer(32767);
      this.hash = var1.readStringFromBuffer(40);
   }

   public S48PacketResourcePackSend(String var1, String var2) {
      this.url = var1;
      this.hash = var2;
      if (var2.length() > 40) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("Hash is too long (max 40, was ")).append(var2.length()).append(")")));
      }
   }

   public String func_179783_a() {
      return this.url;
   }

   public String func_179784_b() {
      return this.hash;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.func_175095_a(this);
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }
}
