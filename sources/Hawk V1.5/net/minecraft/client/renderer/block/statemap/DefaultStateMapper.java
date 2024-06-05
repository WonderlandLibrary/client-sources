package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class DefaultStateMapper extends StateMapperBase {
   private static final String __OBFID = "CL_00002477";

   protected ModelResourceLocation func_178132_a(IBlockState var1) {
      return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(var1.getBlock()), this.func_178131_a(var1.getProperties()));
   }
}
