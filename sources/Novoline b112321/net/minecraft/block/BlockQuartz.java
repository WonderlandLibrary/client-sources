package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockQuartz$1;
import net.minecraft.block.BlockQuartz$EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockQuartz extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockQuartz$EnumType.class);

   public BlockQuartz() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockQuartz$EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if(var7 == BlockQuartz$EnumType.LINES_Y.getMetadata()) {
         switch(BlockQuartz$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[var3.getAxis().ordinal()]) {
         case 1:
            return this.getDefaultState().withProperty(VARIANT, BlockQuartz$EnumType.LINES_Z);
         case 2:
            return this.getDefaultState().withProperty(VARIANT, BlockQuartz$EnumType.LINES_X);
         case 3:
         default:
            return this.getDefaultState().withProperty(VARIANT, BlockQuartz$EnumType.LINES_Y);
         }
      } else {
         return var7 == BlockQuartz$EnumType.CHISELED.getMetadata()?this.getDefaultState().withProperty(VARIANT, BlockQuartz$EnumType.CHISELED):this.getDefaultState().withProperty(VARIANT, BlockQuartz$EnumType.DEFAULT);
      }
   }

   public int damageDropped(IBlockState var1) {
      BlockQuartz$EnumType var2 = (BlockQuartz$EnumType)var1.getValue(VARIANT);
      return var2 != BlockQuartz$EnumType.LINES_X && var2 != BlockQuartz$EnumType.LINES_Z?var2.getMetadata():BlockQuartz$EnumType.LINES_Y.getMetadata();
   }

   protected ItemStack createStackedBlock(IBlockState var1) {
      BlockQuartz$EnumType var2 = (BlockQuartz$EnumType)var1.getValue(VARIANT);
      return var2 != BlockQuartz$EnumType.LINES_X && var2 != BlockQuartz$EnumType.LINES_Z?super.createStackedBlock(var1):new ItemStack(Item.getItemFromBlock(this), 1, BlockQuartz$EnumType.LINES_Y.getMetadata());
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(var1, 1, BlockQuartz$EnumType.DEFAULT.getMetadata()));
      var3.add(new ItemStack(var1, 1, BlockQuartz$EnumType.CHISELED.getMetadata()));
      var3.add(new ItemStack(var1, 1, BlockQuartz$EnumType.LINES_Y.getMetadata()));
   }

   public MapColor getMapColor(IBlockState var1) {
      return MapColor.quartzColor;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, BlockQuartz$EnumType.byMetadata(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockQuartz$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT});
   }
}
