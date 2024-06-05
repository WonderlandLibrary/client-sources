package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockStoneBrick extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockStoneBrick$EnumType.class);
   public static final int DEFAULT_META = BlockStoneBrick$EnumType.DEFAULT.getMetadata();
   public static final int MOSSY_META = BlockStoneBrick$EnumType.MOSSY.getMetadata();
   public static final int CRACKED_META = BlockStoneBrick$EnumType.CRACKED.getMetadata();
   public static final int CHISELED_META = BlockStoneBrick$EnumType.CHISELED.getMetadata();

   public BlockStoneBrick() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockStoneBrick$EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockStoneBrick$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(BlockStoneBrick$EnumType var7 : BlockStoneBrick$EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.getMetadata()));
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, BlockStoneBrick$EnumType.byMetadata(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockStoneBrick$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT});
   }
}
