package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt$DirtType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDirt extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockDirt$DirtType.class);
   public static final PropertyBool SNOWY = PropertyBool.create("snowy");

   protected BlockDirt() {
      super(Material.ground);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockDirt$DirtType.DIRT).withProperty(SNOWY, Boolean.FALSE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public MapColor getMapColor(IBlockState var1) {
      return ((BlockDirt$DirtType)var1.getValue(VARIANT)).func_181066_d();
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if(var1.getValue(VARIANT) == BlockDirt$DirtType.PODZOL) {
         Block var4 = var2.getBlockState(var3.up()).getBlock();
         var1 = var1.withProperty(SNOWY, Boolean.valueOf(var4 == Blocks.snow || var4 == Blocks.snow_layer));
      }

      return var1;
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(this, 1, BlockDirt$DirtType.DIRT.getMetadata()));
      var3.add(new ItemStack(this, 1, BlockDirt$DirtType.COARSE_DIRT.getMetadata()));
      var3.add(new ItemStack(this, 1, BlockDirt$DirtType.PODZOL.getMetadata()));
   }

   public int getDamageValue(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      return var3.getBlock() != this?0:((BlockDirt$DirtType)var3.getValue(VARIANT)).getMetadata();
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, BlockDirt$DirtType.byMetadata(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockDirt$DirtType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT, SNOWY});
   }

   public int damageDropped(IBlockState var1) {
      BlockDirt$DirtType var2 = (BlockDirt$DirtType)var1.getValue(VARIANT);
      if(var2 == BlockDirt$DirtType.PODZOL) {
         var2 = BlockDirt$DirtType.DIRT;
      }

      return var2.getMetadata();
   }
}
