package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt$DirtType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

class BlockModelShapes$5 extends StateMapperBase {
   final BlockModelShapes this$0;

   BlockModelShapes$5(BlockModelShapes var1) {
      this.this$0 = var1;
   }

   protected ModelResourceLocation getModelResourceLocation(IBlockState var1) {
      LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
      String var3 = BlockDirt.VARIANT.getName((Enum)((BlockDirt$DirtType)var2.remove(BlockDirt.VARIANT)));
      if(BlockDirt$DirtType.PODZOL != var1.getValue(BlockDirt.VARIANT)) {
         var2.remove(BlockDirt.SNOWY);
      }

      return new ModelResourceLocation(var3, this.getPropertyString(var2));
   }
}
