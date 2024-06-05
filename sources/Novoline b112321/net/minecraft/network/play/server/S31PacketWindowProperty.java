package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S31PacketWindowProperty implements Packet {
   private int windowId;
   private int varIndex;
   private int varValue;

   public S31PacketWindowProperty() {
   }

   public S31PacketWindowProperty(int var1, int var2, int var3) {
      this.windowId = var1;
      this.varIndex = var2;
      this.varValue = var3;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleWindowProperty(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.windowId = var1.readUnsignedByte();
      this.varIndex = var1.readShort();
      this.varValue = var1.readShort();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.windowId);
      var1.writeShort(this.varIndex);
      var1.writeShort(this.varValue);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public int getVarIndex() {
      return this.varIndex;
   }

   public int getVarValue() {
      return this.varValue;
   }
}
