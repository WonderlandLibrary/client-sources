package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S36PacketSignEditorOpen implements Packet {
   private static final String __OBFID = "CL_00001316";
   private BlockPos field_179778_a;

   public S36PacketSignEditorOpen(BlockPos var1) {
      this.field_179778_a = var1;
   }

   public S36PacketSignEditorOpen() {
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleSignEditorOpen(this);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBlockPos(this.field_179778_a);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179778_a = var1.readBlockPos();
   }

   public BlockPos func_179777_a() {
      return this.field_179778_a;
   }
}
