package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0FPacketConfirmTransaction implements Packet {
   private int windowId;
   private short transactionID;
   private boolean accepted;

   public C0FPacketConfirmTransaction() {
   }

   public C0FPacketConfirmTransaction(int var1, short var2, boolean var3) {
      this.windowId = var1;
      this.transactionID = var2;
      this.accepted = var3;
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processConfirmTransaction(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.windowId = var1.readByte();
      this.transactionID = var1.readShort();
      this.accepted = var1.readByte() != 0;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.windowId);
      var1.writeShort(this.transactionID);
      var1.writeByte(this.accepted?1:0);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public short getID() {
      return this.transactionID;
   }

   public boolean isAccepted() {
      return this.accepted;
   }

   public void setWindowId(int var1) {
      this.windowId = var1;
   }

   public void setID(short var1) {
      this.transactionID = var1;
   }

   public void setAccepted(boolean var1) {
      this.accepted = var1;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
