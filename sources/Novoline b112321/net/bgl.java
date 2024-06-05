package net;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class bgl {
   public static boolean a(BlockPumpkin var0, World var1, BlockPos var2) {
      return var0.canDispenserPlace(var1, var2);
   }

   public static IBlockState a(BlockPumpkin var0) {
      return var0.getDefaultState();
   }
}
