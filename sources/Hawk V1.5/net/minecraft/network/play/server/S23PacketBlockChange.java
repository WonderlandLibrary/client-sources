package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class S23PacketBlockChange implements Packet {
   private BlockPos field_179828_a;
   private IBlockState field_148883_d;
   private static final String __OBFID = "CL_00001287";

   public BlockPos func_179827_b() {
      return this.field_179828_a;
   }

   public void processPacket(INetHandler var1) {
      this.func_180727_a((INetHandlerPlayClient)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBlockPos(this.field_179828_a);
      var1.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.field_148883_d));
   }

   public S23PacketBlockChange(World var1, BlockPos var2) {
      this.field_179828_a = var2;
      this.field_148883_d = var1.getBlockState(var2);
   }

   public IBlockState func_180728_a() {
      return this.field_148883_d;
   }

   public S23PacketBlockChange() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179828_a = var1.readBlockPos();
      this.field_148883_d = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(var1.readVarIntFromBuffer());
   }

   public void func_180727_a(INetHandlerPlayClient var1) {
      var1.handleBlockChange(this);
   }
}
