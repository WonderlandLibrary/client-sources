package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S13PacketDestroyEntities implements Packet {
   private static final String __OBFID = "CL_00001320";
   private int[] field_149100_a;

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleDestroyEntities(this);
   }

   public int[] func_149098_c() {
      return this.field_149100_a;
   }

   public S13PacketDestroyEntities() {
   }

   public S13PacketDestroyEntities(int... var1) {
      this.field_149100_a = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149100_a = new int[var1.readVarIntFromBuffer()];

      for(int var2 = 0; var2 < this.field_149100_a.length; ++var2) {
         this.field_149100_a[var2] = var1.readVarIntFromBuffer();
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149100_a.length);

      for(int var2 = 0; var2 < this.field_149100_a.length; ++var2) {
         var1.writeVarIntToBuffer(this.field_149100_a[var2]);
      }

   }
}
