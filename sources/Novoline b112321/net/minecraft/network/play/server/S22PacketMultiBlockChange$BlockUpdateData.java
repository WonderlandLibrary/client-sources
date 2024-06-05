package net.minecraft.network.play.server;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class S22PacketMultiBlockChange$BlockUpdateData {
   private final short chunkPosCrammed;
   private final IBlockState blockState;
   final S22PacketMultiBlockChange this$0;

   public S22PacketMultiBlockChange$BlockUpdateData(S22PacketMultiBlockChange var1, short var2, IBlockState var3) {
      this.this$0 = var1;
      this.chunkPosCrammed = var2;
      this.blockState = var3;
   }

   public S22PacketMultiBlockChange$BlockUpdateData(S22PacketMultiBlockChange var1, short var2, Chunk var3) {
      this.this$0 = var1;
      this.chunkPosCrammed = var2;
      this.blockState = var3.getBlockState(this.getPos());
   }

   public BlockPos getPos() {
      return new BlockPos(S22PacketMultiBlockChange.access$000(this.this$0).getBlock(this.chunkPosCrammed >> 12 & 15, this.chunkPosCrammed & 255, this.chunkPosCrammed >> 8 & 15));
   }

   public short func_180089_b() {
      return this.chunkPosCrammed;
   }

   public IBlockState getBlockState() {
      return this.blockState;
   }
}
