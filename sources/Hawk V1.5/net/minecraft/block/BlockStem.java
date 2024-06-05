package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStem extends BlockBush implements IGrowable {
   private static final String __OBFID = "CL_00000316";
   private final Block cropBlock;
   public static final PropertyDirection FACING_PROP = PropertyDirection.create("facing", new Predicate() {
      private static final String __OBFID = "CL_00002059";

      public boolean apply(Object var1) {
         return this.apply((EnumFacing)var1);
      }

      public boolean apply(EnumFacing var1) {
         return var1 != EnumFacing.DOWN;
      }
   });
   public static final PropertyInteger AGE_PROP = PropertyInteger.create("age", 0, 7);

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(AGE_PROP, var1);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.maxY = (double)((float)((Integer)var1.getBlockState(var2).getValue(AGE_PROP) * 2 + 2) / 16.0F);
      float var3 = 0.125F;
      this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, (float)this.maxY, 0.5F + var3);
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.125F;
      this.setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
   }

   public int getRenderColor(IBlockState var1) {
      if (var1.getBlock() != this) {
         return super.getRenderColor(var1);
      } else {
         int var2 = (Integer)var1.getValue(AGE_PROP);
         int var3 = var2 * 32;
         int var4 = 255 - var2 * 8;
         int var5 = var2 * 4;
         return var3 << 16 | var4 << 8 | var5;
      }
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.updateTick(var1, var2, var3, var4);
      if (var1.getLightFromNeighbors(var2.offsetUp()) >= 9) {
         float var5 = BlockCrops.getGrowthChance(this, var1, var2);
         if (var4.nextInt((int)(25.0F / var5) + 1) == 0) {
            int var6 = (Integer)var3.getValue(AGE_PROP);
            if (var6 < 7) {
               var3 = var3.withProperty(AGE_PROP, var6 + 1);
               var1.setBlockState(var2, var3, 2);
            } else {
               Iterator var7 = EnumFacing.Plane.HORIZONTAL.iterator();

               while(var7.hasNext()) {
                  EnumFacing var8 = (EnumFacing)var7.next();
                  if (var1.getBlockState(var2.offset(var8)).getBlock() == this.cropBlock) {
                     return;
                  }
               }

               var2 = var2.offset(EnumFacing.Plane.HORIZONTAL.random(var4));
               Block var9 = var1.getBlockState(var2.offsetDown()).getBlock();
               if (var1.getBlockState(var2).getBlock().blockMaterial == Material.air && (var9 == Blocks.farmland || var9 == Blocks.dirt || var9 == Blocks.grass)) {
                  var1.setBlockState(var2, this.cropBlock.getDefaultState());
               }
            }
         }
      }

   }

   public Item getItem(World var1, BlockPos var2) {
      Item var3 = this.getSeedItem();
      return var3 != null ? var3 : null;
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return this.getRenderColor(var1.getBlockState(var2));
   }

   protected Item getSeedItem() {
      return this.cropBlock == Blocks.pumpkin ? Items.pumpkin_seeds : (this.cropBlock == Blocks.melon_block ? Items.melon_seeds : null);
   }

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(AGE_PROP);
   }

   protected BlockStem(Block var1) {
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE_PROP, 0).withProperty(FACING_PROP, EnumFacing.UP));
      this.cropBlock = var1;
      this.setTickRandomly(true);
      float var2 = 0.125F;
      this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
      this.setCreativeTab((CreativeTabs)null);
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      var1 = var1.withProperty(FACING_PROP, EnumFacing.UP);
      Iterator var4 = EnumFacing.Plane.HORIZONTAL.iterator();

      while(var4.hasNext()) {
         EnumFacing var5 = (EnumFacing)var4.next();
         if (var2.getBlockState(var3.offset(var5)).getBlock() == this.cropBlock) {
            var1 = var1.withProperty(FACING_PROP, var5);
            break;
         }
      }

      return var1;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{AGE_PROP, FACING_PROP});
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5);
      if (!var1.isRemote) {
         Item var6 = this.getSeedItem();
         if (var6 != null) {
            int var7 = (Integer)var3.getValue(AGE_PROP);

            for(int var8 = 0; var8 < 3; ++var8) {
               if (var1.rand.nextInt(15) <= var7) {
                  spawnAsEntity(var1, var2, new ItemStack(var6));
               }
            }
         }
      }

   }

   public boolean isStillGrowing(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return (Integer)var3.getValue(AGE_PROP) != 7;
   }

   public void growStem(World var1, BlockPos var2, IBlockState var3) {
      int var4 = (Integer)var3.getValue(AGE_PROP) + MathHelper.getRandomIntegerInRange(var1.rand, 2, 5);
      var1.setBlockState(var2, var3.withProperty(AGE_PROP, Math.min(7, var4)), 2);
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   protected boolean canPlaceBlockOn(Block var1) {
      return var1 == Blocks.farmland;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.growStem(var1, var3, var4);
   }
}
