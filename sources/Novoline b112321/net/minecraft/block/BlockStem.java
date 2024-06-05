package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem$1;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStem extends BlockBush implements IGrowable {
   public static final iV P = iV.a("age", 0, 7);
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)(new BlockStem$1()));
   private final Block crop;

   protected BlockStem(Block var1) {
      this.setDefaultState(this.blockState.getBaseState().withProperty(P, Integer.valueOf(0)).withProperty(FACING, EnumFacing.UP));
      this.crop = var1;
      this.setTickRandomly(true);
      float var2 = 0.125F;
      this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.25F, 0.625F);
      this.setCreativeTab((CreativeTabs)null);
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      var1 = var1.withProperty(FACING, EnumFacing.UP);

      for(Object var5 : EnumFacing$Plane.HORIZONTAL) {
         if(var2.getBlockState(var3.offset((EnumFacing)var5)).getBlock() == this.crop) {
            var1 = var1.withProperty(FACING, (EnumFacing)var5);
            break;
         }
      }

      return var1;
   }

   protected boolean canPlaceBlockOn(Block var1) {
      return var1 == Blocks.farmland;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.updateTick(var1, var2, var3, var4);
      if(var1.getLightFromNeighbors(var2.up()) >= 9) {
         float var5 = BlockCrops.getGrowthChance(this, var1, var2);
         if(var4.nextInt((int)(25.0F / var5) + 1) == 0) {
            int var6 = ((Integer)var3.getValue(P)).intValue();
            if(var6 < 7) {
               var3 = var3.withProperty(P, Integer.valueOf(var6 + 1));
               var1.setBlockState(var2, var3, 2);
            } else {
               for(Object var8 : EnumFacing$Plane.HORIZONTAL) {
                  if(var1.getBlockState(var2.offset((EnumFacing)var8)).getBlock() == this.crop) {
                     return;
                  }
               }

               var2 = var2.offset(EnumFacing$Plane.HORIZONTAL.random(var4));
               Block var11 = var1.getBlockState(var2.down()).getBlock();
               if(var1.getBlockState(var2).getBlock().blockMaterial == Material.air && (var11 == Blocks.farmland || var11 == Blocks.dirt || var11 == Blocks.grass)) {
                  var1.setBlockState(var2, this.crop.getDefaultState());
               }
            }
         }
      }

   }

   public void growStem(World var1, BlockPos var2, IBlockState var3) {
      int var4 = ((Integer)var3.getValue(P)).intValue() + MathHelper.getRandomIntegerInRange(var1.rand, 2, 5);
      var1.setBlockState(var2, var3.withProperty(P, Integer.valueOf(Math.min(7, var4))), 2);
   }

   public int getRenderColor(IBlockState var1) {
      if(var1.getBlock() != this) {
         return super.getRenderColor(var1);
      } else {
         int var2 = ((Integer)var1.getValue(P)).intValue();
         int var3 = var2 * 32;
         int var4 = 255 - var2 * 8;
         int var5 = var2 * 4;
         return var3 << 16 | var4 << 8 | var5;
      }
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return this.getRenderColor(var1.getBlockState(var2));
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.125F;
      this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.25F, 0.625F);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.maxY = (double)((float)(((Integer)var1.getBlockState(var2).getValue(P)).intValue() * 2 + 2) / 16.0F);
      float var3 = 0.125F;
      this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, (float)this.maxY, 0.625F);
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5);
      if(!var1.isRemote) {
         Item var6 = this.getSeedItem();
         int var7 = ((Integer)var3.getValue(P)).intValue();

         for(int var8 = 0; var8 < 3; ++var8) {
            if(var1.rand.nextInt(15) <= var7) {
               spawnAsEntity(var1, var2, new ItemStack(var6));
            }
         }
      }

   }

   protected Item getSeedItem() {
      return this.crop == Blocks.pumpkin?Items.pumpkin_seeds:(this.crop == Blocks.melon_block?Items.melon_seeds:null);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public Item getItem(World var1, BlockPos var2) {
      Item var3 = this.getSeedItem();
      return var3;
   }

   public boolean canGrow(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return ((Integer)var3.getValue(P)).intValue() != 7;
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.growStem(var1, var3, var4);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(P, Integer.valueOf(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Integer)var1.getValue(P)).intValue();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{P, FACING});
   }
}
