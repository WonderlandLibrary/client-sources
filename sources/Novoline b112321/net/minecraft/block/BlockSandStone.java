package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSandStone$EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockSandStone extends Block {
   public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockSandStone$EnumType.class);

   public BlockSandStone() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockSandStone$EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockSandStone$EnumType)var1.getValue(TYPE)).getMetadata();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(BlockSandStone$EnumType var7 : BlockSandStone$EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.getMetadata()));
      }

   }

   public MapColor getMapColor(IBlockState var1) {
      return MapColor.sandColor;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(TYPE, BlockSandStone$EnumType.byMetadata(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockSandStone$EnumType)var1.getValue(TYPE)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{TYPE});
   }
}
