package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S25PacketBlockBreakAnim implements Packet {
   private int breakerId;
   private BlockPos position;
   private int progress;

   public S25PacketBlockBreakAnim() {
   }

   public S25PacketBlockBreakAnim(int var1, BlockPos var2, int var3) {
      this.breakerId = var1;
      this.position = var2;
      this.progress = var3;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.breakerId = var1.readVarIntFromBuffer();
      this.position = var1.readBlockPos();
      this.progress = var1.readUnsignedByte();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.breakerId);
      var1.writeBlockPos(this.position);
      var1.writeByte(this.progress);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleBlockBreakAnim(this);
   }

   public int getBreakerId() {
      return this.breakerId;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public int getProgress() {
      return this.progress;
   }
}
