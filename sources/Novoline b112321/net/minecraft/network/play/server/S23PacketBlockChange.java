package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class S23PacketBlockChange implements Packet {
   private BlockPos blockPosition;
   private IBlockState blockState;

   public S23PacketBlockChange() {
   }

   public S23PacketBlockChange(World var1, BlockPos var2) {
      this.blockPosition = var2;
      this.blockState = var1.getBlockState(var2);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.blockPosition = var1.readBlockPos();
      this.blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(var1.readVarIntFromBuffer());
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBlockPos(this.blockPosition);
      var1.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.blockState));
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleBlockChange(this);
   }

   public IBlockState getBlockState() {
      return this.blockState;
   }

   public BlockPos getBlockPosition() {
      return this.blockPosition;
   }
}
