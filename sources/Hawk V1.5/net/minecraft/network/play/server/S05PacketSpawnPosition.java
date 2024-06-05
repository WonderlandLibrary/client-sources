package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S05PacketSpawnPosition implements Packet {
   private static final String __OBFID = "CL_00001336";
   private BlockPos field_179801_a;

   public BlockPos func_179800_a() {
      return this.field_179801_a;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179801_a = var1.readBlockPos();
   }

   public S05PacketSpawnPosition(BlockPos var1) {
      this.field_179801_a = var1;
   }

   public void func_180752_a(INetHandlerPlayClient var1) {
      var1.handleSpawnPosition(this);
   }

   public void processPacket(INetHandler var1) {
      this.func_180752_a((INetHandlerPlayClient)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBlockPos(this.field_179801_a);
   }

   public S05PacketSpawnPosition() {
   }
}
