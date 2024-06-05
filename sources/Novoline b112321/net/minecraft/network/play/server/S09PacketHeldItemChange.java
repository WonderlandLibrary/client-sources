package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S09PacketHeldItemChange implements Packet {
   private int index;

   public S09PacketHeldItemChange() {
   }

   public S09PacketHeldItemChange(int var1) {
      this.index = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.index = var1.readByte();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.index);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleHeldItemChange(this);
   }

   public int getIndex() {
      return this.index;
   }

   public void setIndex(int var1) {
      this.index = var1;
   }
}
