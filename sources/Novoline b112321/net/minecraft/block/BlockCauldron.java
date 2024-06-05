package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCauldron extends Block {
   public static final iV P = iV.a("level", 0, 3);

   public BlockCauldron() {
      super(Material.iron, MapColor.stoneColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(P, Integer.valueOf(0)));
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      float var7 = 0.125F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBoundsForItemRender();
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      int var5 = ((Integer)var3.getValue(P)).intValue();
      float var6 = (float)var2.getY() + (6.0F + (float)(3 * var5)) / 16.0F;
      if(!var1.isRemote && var4.isBurning() && var4.getEntityBoundingBox().minY <= (double)var6) {
         var4.extinguish();
         this.setWaterLevel(var1, var2, var3, var5 - 1);
      }

   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(var1.isRemote) {
         return true;
      } else {
         ItemStack var9 = var4.inventory.getCurrentItem();
         return true;
      }
   }

   public void setWaterLevel(World var1, BlockPos var2, IBlockState var3, int var4) {
      var1.setBlockState(var2, var3.withProperty(P, Integer.valueOf(MathHelper.clamp_int(var4, 0, 3))), 2);
      var1.updateComparatorOutputLevel(var2, this);
   }

   public void fillWithRain(World var1, BlockPos var2) {
      if(var1.rand.nextInt(20) == 1) {
         IBlockState var3 = var1.getBlockState(var2);
         if(((Integer)var3.getValue(P)).intValue() < 3) {
            var1.setBlockState(var2, var3.cycleProperty(P), 2);
         }
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.cauldron;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.cauldron;
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return ((Integer)var1.getBlockState(var2).getValue(P)).intValue();
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
}
