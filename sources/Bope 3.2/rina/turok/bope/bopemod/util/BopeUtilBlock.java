package rina.turok.bope.bopemod.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class BopeUtilBlock {
   public static final Minecraft mc = Minecraft.getMinecraft();

   public static Block get_block(BlockPos pos) {
      return get_state(pos).getBlock();
   }

   public static IBlockState get_state(BlockPos pos) {
      return mc.world.getBlockState(pos);
   }

   public static boolean is_possible(BlockPos pos) {
      return get_block(pos).canCollideCheck(get_state(pos), false);
   }
}
