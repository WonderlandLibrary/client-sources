package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S22PacketMultiBlockChange$BlockUpdateData;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

public class S22PacketMultiBlockChange implements Packet {
   private ChunkCoordIntPair chunkPosCoord;
   private S22PacketMultiBlockChange$BlockUpdateData[] changedBlocks;

   public S22PacketMultiBlockChange() {
   }

   public S22PacketMultiBlockChange(int var1, short[] var2, Chunk var3) {
      this.chunkPosCoord = new ChunkCoordIntPair(var3.xPosition, var3.zPosition);
      this.changedBlocks = new S22PacketMultiBlockChange$BlockUpdateData[var1];

      for(int var4 = 0; var4 < this.changedBlocks.length; ++var4) {
         this.changedBlocks[var4] = new S22PacketMultiBlockChange$BlockUpdateData(this, var2[var4], var3);
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.chunkPosCoord = new ChunkCoordIntPair(var1.readInt(), var1.readInt());
      this.changedBlocks = new S22PacketMultiBlockChange$BlockUpdateData[var1.readVarIntFromBuffer()];

      for(int var2 = 0; var2 < this.changedBlocks.length; ++var2) {
         this.changedBlocks[var2] = new S22PacketMultiBlockChange$BlockUpdateData(this, var1.readShort(), (IBlockState)Block.BLOCK_STATE_IDS.getByValue(var1.readVarIntFromBuffer()));
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeInt(this.chunkPosCoord.chunkXPos);
      var1.writeInt(this.chunkPosCoord.chunkZPos);
      var1.writeVarIntToBuffer(this.changedBlocks.length);

      for(S22PacketMultiBlockChange$BlockUpdateData var5 : this.changedBlocks) {
         var1.writeShort(var5.func_180089_b());
         var1.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(var5.getBlockState()));
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleMultiBlockChange(this);
   }

   public S22PacketMultiBlockChange$BlockUpdateData[] getChangedBlocks() {
      return this.changedBlocks;
   }

   static ChunkCoordIntPair access$000(S22PacketMultiBlockChange var0) {
      return var0.chunkPosCoord;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
