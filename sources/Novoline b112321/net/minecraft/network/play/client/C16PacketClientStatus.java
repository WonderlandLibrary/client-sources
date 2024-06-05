package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C16PacketClientStatus$EnumState;

public class C16PacketClientStatus implements Packet {
   private C16PacketClientStatus$EnumState status;

   public C16PacketClientStatus() {
   }

   public C16PacketClientStatus(C16PacketClientStatus$EnumState var1) {
      this.status = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.status = (C16PacketClientStatus$EnumState)var1.readEnumValue(C16PacketClientStatus$EnumState.class);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.status);
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processClientStatus(this);
   }

   public C16PacketClientStatus$EnumState getStatus() {
      return this.status;
   }
}
