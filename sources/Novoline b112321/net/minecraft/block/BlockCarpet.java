package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCarpet extends Block {
   public static final PropertyEnum COLOR = PropertyEnum.create("color", EnumDyeColor.class);

   protected BlockCarpet() {
      super(Material.carpet);
      this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBoundsFromMeta(0);
   }

   public MapColor getMapColor(IBlockState var1) {
      return ((EnumDyeColor)var1.getValue(COLOR)).getMapColor();
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBoundsFromMeta(0);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.setBlockBoundsFromMeta(0);
   }

   protected void setBlockBoundsFromMeta(int var1) {
      float var2 = 0.0625F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return super.canPlaceBlockAt(var1, var2) && this.canBlockStay(var1, var2);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.checkForDrop(var1, var2, var3);
   }

   private boolean checkForDrop(World var1, BlockPos var2, IBlockState var3) {
      if(!this.canBlockStay(var1, var2)) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
         return false;
      } else {
         return true;
      }
   }

   private boolean canBlockStay(World var1, BlockPos var2) {
      return !var1.isAirBlock(var2.down());
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.UP || super.shouldSideBeRendered(var1, var2, var3);
   }

   public int damageDropped(IBlockState var1) {
      return ((EnumDyeColor)var1.getValue(COLOR)).getMetadata();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(int var4 = 0; var4 < 16; ++var4) {
         var3.add(new ItemStack(var1, 1, var4));
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumDyeColor)var1.getValue(COLOR)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{COLOR});
   }
}
