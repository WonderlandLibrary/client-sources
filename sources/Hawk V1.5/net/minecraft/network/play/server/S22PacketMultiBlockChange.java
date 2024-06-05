package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

public class S22PacketMultiBlockChange implements Packet {
   private static final String __OBFID = "CL_00001290";
   private ChunkCoordIntPair field_148925_b;
   private S22PacketMultiBlockChange.BlockUpdateData[] field_179845_b;

   public void processPacket(INetHandler var1) {
      this.func_180729_a((INetHandlerPlayClient)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeInt(this.field_148925_b.chunkXPos);
      var1.writeInt(this.field_148925_b.chunkZPos);
      var1.writeVarIntToBuffer(this.field_179845_b.length);
      S22PacketMultiBlockChange.BlockUpdateData[] var2 = this.field_179845_b;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         S22PacketMultiBlockChange.BlockUpdateData var5 = var2[var4];
         var1.writeShort(var5.func_180089_b());
         var1.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(var5.func_180088_c()));
      }

   }

   public S22PacketMultiBlockChange.BlockUpdateData[] func_179844_a() {
      return this.field_179845_b;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_148925_b = new ChunkCoordIntPair(var1.readInt(), var1.readInt());
      this.field_179845_b = new S22PacketMultiBlockChange.BlockUpdateData[var1.readVarIntFromBuffer()];

      for(int var2 = 0; var2 < this.field_179845_b.length; ++var2) {
         this.field_179845_b[var2] = new S22PacketMultiBlockChange.BlockUpdateData(this, var1.readShort(), (IBlockState)Block.BLOCK_STATE_IDS.getByValue(var1.readVarIntFromBuffer()));
      }

   }

   public S22PacketMultiBlockChange() {
   }

   static ChunkCoordIntPair access$0(S22PacketMultiBlockChange var0) {
      return var0.field_148925_b;
   }

   public void func_180729_a(INetHandlerPlayClient var1) {
      var1.handleMultiBlockChange(this);
   }

   public S22PacketMultiBlockChange(int var1, short[] var2, Chunk var3) {
      this.field_148925_b = new ChunkCoordIntPair(var3.xPosition, var3.zPosition);
      this.field_179845_b = new S22PacketMultiBlockChange.BlockUpdateData[var1];

      for(int var4 = 0; var4 < this.field_179845_b.length; ++var4) {
         this.field_179845_b[var4] = new S22PacketMultiBlockChange.BlockUpdateData(this, var2[var4], var3);
      }

   }

   public class BlockUpdateData {
      private final short field_180091_b;
      final S22PacketMultiBlockChange this$0;
      private static final String __OBFID = "CL_00002302";
      private final IBlockState field_180092_c;

      public IBlockState func_180088_c() {
         return this.field_180092_c;
      }

      public BlockUpdateData(S22PacketMultiBlockChange var1, short var2, IBlockState var3) {
         this.this$0 = var1;
         this.field_180091_b = var2;
         this.field_180092_c = var3;
      }

      public BlockPos func_180090_a() {
         return new BlockPos(S22PacketMultiBlockChange.access$0(this.this$0).getBlock(this.field_180091_b >> 12 & 15, this.field_180091_b & 255, this.field_180091_b >> 8 & 15));
      }

      public BlockUpdateData(S22PacketMultiBlockChange var1, short var2, Chunk var3) {
         this.this$0 = var1;
         this.field_180091_b = var2;
         this.field_180092_c = var3.getBlockState(this.func_180090_a());
      }

      public short func_180089_b() {
         return this.field_180091_b;
      }
   }
}
