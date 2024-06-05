package net.minecraft.block;

import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrops extends BlockBush implements IGrowable {
   public static final iV P = iV.a("age", 0, 7);

   protected BlockCrops() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(P, Integer.valueOf(0)));
      this.setTickRandomly(true);
      float var1 = 0.5F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
      this.setCreativeTab((CreativeTabs)null);
      this.setHardness(0.0F);
      this.setStepSound(soundTypeGrass);
      this.disableStats();
   }

   protected boolean canPlaceBlockOn(Block var1) {
      return var1 == Blocks.farmland;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.updateTick(var1, var2, var3, var4);
      if(var1.getLightFromNeighbors(var2.up()) >= 9) {
         int var5 = ((Integer)var3.getValue(P)).intValue();
         if(var5 < 7) {
            float var6 = getGrowthChance(this, var1, var2);
            if(var4.nextInt((int)(25.0F / var6) + 1) == 0) {
               var1.setBlockState(var2, var3.withProperty(P, Integer.valueOf(var5 + 1)), 2);
            }
         }
      }

   }

   public void grow(World var1, BlockPos var2, IBlockState var3) {
      int var4 = ((Integer)var3.getValue(P)).intValue() + MathHelper.getRandomIntegerInRange(var1.rand, 2, 5);
      if(var4 > 7) {
         var4 = 7;
      }

      var1.setBlockState(var2, var3.withProperty(P, Integer.valueOf(var4)), 2);
   }

   protected static float getGrowthChance(Block var0, World var1, BlockPos var2) {
      float var3 = 1.0F;
      BlockPos var4 = var2.down();

      for(int var5 = -1; var5 <= 1; ++var5) {
         for(int var6 = -1; var6 <= 1; ++var6) {
            float var7 = 0.0F;
            IBlockState var8 = var1.getBlockState(var4.a(var5, 0, var6));
            if(var8.getBlock() == Blocks.farmland) {
               var7 = 1.0F;
               if(((Integer)var8.getValue(BlockFarmland.P)).intValue() > 0) {
                  var7 = 3.0F;
               }
            }

            var7 = var7 / 4.0F;
            var3 += var7;
         }
      }

      BlockPos var12 = var2.north();
      BlockPos var13 = var2.south();
      BlockPos var15 = var2.west();
      BlockPos var16 = var2.east();
      if(var0 != var1.getBlockState(var15).getBlock() && var0 != var1.getBlockState(var16).getBlock()) {
         boolean var17 = false;
      } else {
         boolean var10000 = true;
      }

      if(var0 != var1.getBlockState(var12).getBlock() && var0 != var1.getBlockState(var13).getBlock()) {
         boolean var19 = false;
      } else {
         boolean var18 = true;
      }

      var3 = var3 / 2.0F;
      return var3;
   }

   public boolean canBlockStay(World var1, BlockPos var2, IBlockState var3) {
      return (var1.getLight(var2) >= 8 || var1.canSeeSky(var2)) && this.canPlaceBlockOn(var1.getBlockState(var2.down()).getBlock());
   }

   protected Item getSeed() {
      return Items.wheat_seeds;
   }

   protected Item getCrop() {
      return Items.wheat;
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      super.dropBlockAsItemWithChance(var1, var2, var3, var4, 0);
      if(!var1.isRemote) {
         int var6 = ((Integer)var3.getValue(P)).intValue();
         if(var6 >= 7) {
            int var7 = 3 + var5;

            for(int var8 = 0; var8 < var7; ++var8) {
               if(var1.rand.nextInt(15) <= var6) {
                  spawnAsEntity(var1, var2, new ItemStack(this.getSeed(), 1, 0));
               }
            }
         }
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return ((Integer)var1.getValue(P)).intValue() == 7?this.getCrop():this.getSeed();
   }

   public Item getItem(World var1, BlockPos var2) {
      return this.getSeed();
   }

   public boolean canGrow(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return ((Integer)var3.getValue(P)).intValue() < 7;
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.grow(var1, var3, var4);
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
