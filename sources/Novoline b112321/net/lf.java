package net;

import com.google.common.base.Predicate;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class lf {
   public static BlockPos c(BlockWorldState var0) {
      return var0.getPos();
   }

   public static Predicate a(Predicate var0) {
      return BlockWorldState.hasState(var0);
   }

   public static IBlockState b(BlockWorldState var0) {
      return var0.getBlockState();
   }

   public static TileEntity a(BlockWorldState var0) {
      return var0.getTileEntity();
   }
}
