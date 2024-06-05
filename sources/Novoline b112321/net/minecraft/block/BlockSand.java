package net.minecraft.block;

import java.util.List;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand$EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockSand extends BlockFalling {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockSand$EnumType.class);

   public BlockSand() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockSand$EnumType.SAND));
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockSand$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(BlockSand$EnumType var7 : BlockSand$EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.getMetadata()));
      }

   }

   public MapColor getMapColor(IBlockState var1) {
      return ((BlockSand$EnumType)var1.getValue(VARIANT)).getMapColor();
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, BlockSand$EnumType.byMetadata(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockSand$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT});
   }
}
