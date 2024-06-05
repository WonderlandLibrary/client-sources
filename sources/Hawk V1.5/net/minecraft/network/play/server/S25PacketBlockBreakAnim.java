package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S25PacketBlockBreakAnim implements Packet {
   private BlockPos position;
   private static final String __OBFID = "CL_00001284";
   private int breakerId;
   private int progress;

   public int func_148845_c() {
      return this.breakerId;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.breakerId = var1.readVarIntFromBuffer();
      this.position = var1.readBlockPos();
      this.progress = var1.readUnsignedByte();
   }

   public void handle(INetHandlerPlayClient var1) {
      var1.handleBlockBreakAnim(this);
   }

   public int func_148846_g() {
      return this.progress;
   }

   public void processPacket(INetHandler var1) {
      this.handle((INetHandlerPlayClient)var1);
   }

   public S25PacketBlockBreakAnim(int var1, BlockPos var2, int var3) {
      this.breakerId = var1;
      this.position = var2;
      this.progress = var3;
   }

   public BlockPos func_179821_b() {
      return this.position;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.breakerId);
      var1.writeBlockPos(this.position);
      var1.writeByte(this.progress);
   }

   public S25PacketBlockBreakAnim() {
   }
}
