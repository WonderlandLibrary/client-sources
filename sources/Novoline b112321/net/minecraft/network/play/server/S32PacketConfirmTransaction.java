package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S32PacketConfirmTransaction implements Packet {
   private int windowId;
   private short actionNumber;
   private boolean accepted;

   public S32PacketConfirmTransaction() {
   }

   public S32PacketConfirmTransaction(int var1, short var2, boolean var3) {
      this.windowId = var1;
      this.actionNumber = var2;
      this.accepted = var3;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleConfirmTransaction(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.windowId = var1.readUnsignedByte();
      this.actionNumber = var1.readShort();
      this.accepted = var1.readBoolean();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.windowId);
      var1.writeShort(this.actionNumber);
      var1.writeBoolean(this.accepted);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public short getActionNumber() {
      return this.actionNumber;
   }

   public boolean isAccepted() {
      return this.accepted;
   }

   public void setWindowId(int var1) {
      this.windowId = var1;
   }

   public void setActionNumber(short var1) {
      this.actionNumber = var1;
   }

   public void setAccepted(boolean var1) {
      this.accepted = var1;
   }
}
