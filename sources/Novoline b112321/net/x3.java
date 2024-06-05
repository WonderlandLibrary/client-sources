package net;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class x3 {
   public static IBlockState c(BlockFlower var0) {
      return var0.getDefaultState();
   }

   public static IProperty b(BlockFlower var0) {
      return var0.getTypeProperty();
   }

   public static boolean a(BlockFlower var0, World var1, BlockPos var2, IBlockState var3) {
      return var0.canBlockStay(var1, var2, var3);
   }

   public static Material a(BlockFlower var0) {
      return var0.getMaterial();
   }
}
