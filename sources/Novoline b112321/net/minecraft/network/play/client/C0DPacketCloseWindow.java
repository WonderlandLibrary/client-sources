package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0DPacketCloseWindow implements Packet {
   private int windowId;

   public C0DPacketCloseWindow() {
   }

   public C0DPacketCloseWindow(int var1) {
      this.windowId = var1;
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processCloseWindow(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.windowId = var1.readByte();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.windowId);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public void setWindowId(int var1) {
      this.windowId = var1;
   }
}
