package net;

import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockQuartz$EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockModelShapes$8;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

class sb extends StateMapperBase {
   final BlockModelShapes b;

   sb(BlockModelShapes var1) {
      this.b = var1;
   }

   protected ModelResourceLocation getModelResourceLocation(IBlockState var1) {
      BlockQuartz$EnumType var2 = (BlockQuartz$EnumType)var1.getValue(BlockQuartz.VARIANT);
      switch(BlockModelShapes$8.$SwitchMap$net$minecraft$block$BlockQuartz$EnumType[var2.ordinal()]) {
      case 1:
      default:
         return new ModelResourceLocation("quartz_block", "normal");
      case 2:
         return new ModelResourceLocation("chiseled_quartz_block", "normal");
      case 3:
         return new ModelResourceLocation("quartz_column", "axis=y");
      case 4:
         return new ModelResourceLocation("quartz_column", "axis=x");
      case 5:
         return new ModelResourceLocation("quartz_column", "axis=z");
      }
   }
}
