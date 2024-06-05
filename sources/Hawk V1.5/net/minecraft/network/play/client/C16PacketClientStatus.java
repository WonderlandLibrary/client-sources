package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C16PacketClientStatus implements Packet {
   private static final String __OBFID = "CL_00001348";
   private C16PacketClientStatus.EnumState status;

   public void processPacket(INetHandler var1) {
      this.func_180758_a((INetHandlerPlayServer)var1);
   }

   public C16PacketClientStatus.EnumState getStatus() {
      return this.status;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.status = (C16PacketClientStatus.EnumState)var1.readEnumValue(C16PacketClientStatus.EnumState.class);
   }

   public void func_180758_a(INetHandlerPlayServer var1) {
      var1.processClientStatus(this);
   }

   public C16PacketClientStatus(C16PacketClientStatus.EnumState var1) {
      this.status = var1;
   }

   public C16PacketClientStatus() {
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.status);
   }

   public static enum EnumState {
      private static final C16PacketClientStatus.EnumState[] $VALUES = new C16PacketClientStatus.EnumState[]{PERFORM_RESPAWN, REQUEST_STATS, OPEN_INVENTORY_ACHIEVEMENT};
      PERFORM_RESPAWN("PERFORM_RESPAWN", 0),
      OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2);

      private static final C16PacketClientStatus.EnumState[] ENUM$VALUES = new C16PacketClientStatus.EnumState[]{PERFORM_RESPAWN, REQUEST_STATS, OPEN_INVENTORY_ACHIEVEMENT};
      REQUEST_STATS("REQUEST_STATS", 1);

      private static final String __OBFID = "CL_00001349";

      private EnumState(String var3, int var4) {
      }
   }
}
