package net.minecraft.block;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockReed extends Block {
   public static final PropertyInteger field_176355_a = PropertyInteger.create("age", 0, 15);
   private static final String __OBFID = "CL_00000300";

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.func_176353_e(var1, var2, var3);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176355_a, var1);
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   protected final boolean func_176353_e(World var1, BlockPos var2, IBlockState var3) {
      if (this.func_176354_d(var1, var2)) {
         return true;
      } else {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
         return false;
      }
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.reeds;
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return var1.getBiomeGenForCoords(var2).func_180627_b(var2);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean func_176354_d(World var1, BlockPos var2) {
      return this.canPlaceBlockAt(var1, var2);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      Block var3 = var1.getBlockState(var2.offsetDown()).getBlock();
      if (var3 == this) {
         return true;
      } else if (var3 != Blocks.grass && var3 != Blocks.dirt && var3 != Blocks.sand) {
         return false;
      } else {
         Iterator var4 = EnumFacing.Plane.HORIZONTAL.iterator();

         while(var4.hasNext()) {
            EnumFacing var5 = (EnumFacing)var4.next();
            if (var1.getBlockState(var2.offset(var5).offsetDown()).getBlock().getMaterial() == Material.water) {
               return true;
            }
         }

         return false;
      }
   }

   protected BlockReed() {
      super(Material.plants);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176355_a, 0));
      float var1 = 0.375F;
      this.setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 1.0F, 0.5F + var1);
      this.setTickRandomly(true);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.reeds;
   }

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(field_176355_a);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176355_a});
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if ((var1.getBlockState(var2.offsetDown()).getBlock() == Blocks.reeds || this.func_176353_e(var1, var2, var3)) && var1.isAirBlock(var2.offsetUp())) {
         int var5;
         for(var5 = 1; var1.getBlockState(var2.offsetDown(var5)).getBlock() == this; ++var5) {
         }

         if (var5 < 3) {
            int var6 = (Integer)var3.getValue(field_176355_a);
            if (var6 == 15) {
               var1.setBlockState(var2.offsetUp(), this.getDefaultState());
               var1.setBlockState(var2, var3.withProperty(field_176355_a, 0), 4);
            } else {
               var1.setBlockState(var2, var3.withProperty(field_176355_a, var6 + 1), 4);
            }
         }
      }

   }

   public boolean isFullCube() {
      return false;
   }
}
