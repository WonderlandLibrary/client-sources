package net;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlab$EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

class sq extends StateMapperBase {
   final BlockModelShapes b;

   sq(BlockModelShapes var1) {
      this.b = var1;
   }

   protected ModelResourceLocation getModelResourceLocation(IBlockState var1) {
      LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
      String var3 = BlockStoneSlab.VARIANT.getName((Enum)((BlockStoneSlab$EnumType)var2.remove(BlockStoneSlab.VARIANT)));
      var2.remove(BlockStoneSlab.SEAMLESS);
      String var4 = ((Boolean)var1.getValue(BlockStoneSlab.SEAMLESS)).booleanValue()?"all":"normal";
      return new ModelResourceLocation(var3 + "_double_slab", var4);
   }
}
