package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.DataWatcher;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1CPacketEntityMetadata implements Packet {
   private int field_149379_a;
   private static final String __OBFID = "CL_00001326";
   private List field_149378_b;

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149379_a);
      DataWatcher.writeWatchedListToPacketBuffer(this.field_149378_b, var1);
   }

   public S1CPacketEntityMetadata(int var1, DataWatcher var2, boolean var3) {
      this.field_149379_a = var1;
      if (var3) {
         this.field_149378_b = var2.getAllWatched();
      } else {
         this.field_149378_b = var2.getChanged();
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149379_a = var1.readVarIntFromBuffer();
      this.field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(var1);
   }

   public void func_180748_a(INetHandlerPlayClient var1) {
      var1.handleEntityMetadata(this);
   }

   public S1CPacketEntityMetadata() {
   }

   public List func_149376_c() {
      return this.field_149378_b;
   }

   public int func_149375_d() {
      return this.field_149379_a;
   }

   public void processPacket(INetHandler var1) {
      this.func_180748_a((INetHandlerPlayClient)var1);
   }
}
