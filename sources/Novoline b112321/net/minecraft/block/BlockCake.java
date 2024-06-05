package net.minecraft.block;

import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCake extends Block {
   public static final iV P = iV.a("bites", 0, 6);

   protected BlockCake() {
      super(Material.cake);
      this.setDefaultState(this.blockState.getBaseState().withProperty(P, Integer.valueOf(0)));
      this.setTickRandomly(true);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.0625F;
      float var4 = (float)(1 + ((Integer)var1.getBlockState(var2).getValue(P)).intValue() * 2) / 16.0F;
      float var5 = 0.5F;
      this.setBlockBounds(var4, 0.0F, 0.0625F, 0.9375F, 0.5F, 0.9375F);
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.0625F;
      float var2 = 0.5F;
      this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.5F, 0.9375F);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      float var4 = 0.0625F;
      float var5 = (float)(1 + ((Integer)var3.getValue(P)).intValue() * 2) / 16.0F;
      float var6 = 0.5F;
      return new AxisAlignedBB((double)((float)var2.getX() + var5), (double)var2.getY(), (double)((float)var2.getZ() + 0.0625F), (double)((float)(var2.getX() + 1) - 0.0625F), (double)((float)var2.getY() + 0.5F), (double)((float)(var2.getZ() + 1) - 0.0625F));
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      return this.getCollisionBoundingBox(var1, var2, var1.getBlockState(var2));
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      this.eatCake(var1, var2, var3, var4);
      return true;
   }

   public void onBlockClicked(World var1, BlockPos var2, EntityPlayer var3) {
      this.eatCake(var1, var2, var1.getBlockState(var2), var3);
   }

   private void eatCake(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if(var4.canEat(false)) {
         var4.triggerAchievement(StatList.field_181724_H);
         var4.getFoodStats().addStats(2, 0.1F);
         int var5 = ((Integer)var3.getValue(P)).intValue();
         if(var5 < 6) {
            var1.setBlockState(var2, var3.withProperty(P, Integer.valueOf(var5 + 1)), 3);
         } else {
            var1.setBlockToAir(var2);
         }
      }

   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return super.canPlaceBlockAt(var1, var2) && this.canBlockStay(var1, var2);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!this.canBlockStay(var1, var2)) {
         var1.setBlockToAir(var2);
      }

   }

   private boolean canBlockStay(World var1, BlockPos var2) {
      return var1.getBlockState(var2.down()).getBlock().getMaterial().isSolid();
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.cake;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(P, Integer.valueOf(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Integer)var1.getValue(P)).intValue();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{P});
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return (7 - ((Integer)var1.getBlockState(var2).getValue(P)).intValue()) * 2;
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }
}
