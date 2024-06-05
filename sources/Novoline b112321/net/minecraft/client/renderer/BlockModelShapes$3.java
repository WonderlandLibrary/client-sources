package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

class BlockModelShapes$3 extends StateMapperBase {
   final BlockModelShapes this$0;

   BlockModelShapes$3(BlockModelShapes var1) {
      this.this$0 = var1;
   }

   protected ModelResourceLocation getModelResourceLocation(IBlockState var1) {
      LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
      if(var1.getValue(BlockStem.FACING) != EnumFacing.UP) {
         var2.remove(BlockStem.P);
      }

      return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(var1.getBlock()), this.getPropertyString(var2));
   }
}
