package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S3APacketTabComplete implements Packet {
   private String[] field_149632_a;
   private static final String __OBFID = "CL_00001288";

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149632_a = new String[var1.readVarIntFromBuffer()];

      for(int var2 = 0; var2 < this.field_149632_a.length; ++var2) {
         this.field_149632_a[var2] = var1.readStringFromBuffer(32767);
      }

   }

   public S3APacketTabComplete(String[] var1) {
      this.field_149632_a = var1;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149632_a.length);
      String[] var2 = this.field_149632_a;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         var1.writeString(var5);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleTabComplete(this);
   }

   public S3APacketTabComplete() {
   }

   public String[] func_149630_c() {
      return this.field_149632_a;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }
}
