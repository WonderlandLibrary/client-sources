package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLeaves extends BlockLeavesBase {
   protected int field_150127_b;
   int[] field_150128_a;
   public static final PropertyBool field_176236_b = PropertyBool.create("check_decay");
   protected boolean field_176238_O;
   private static final String __OBFID = "CL_00000263";
   public static final PropertyBool field_176237_a = PropertyBool.create("decayable");

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.isRemote) {
         int var6 = this.func_176232_d(var3);
         if (var5 > 0) {
            var6 -= 2 << var5;
            if (var6 < 10) {
               var6 = 10;
            }
         }

         if (var1.rand.nextInt(var6) == 0) {
            Item var7 = this.getItemDropped(var3, var1.rand, var5);
            spawnAsEntity(var1, var2, new ItemStack(var7, 1, this.damageDropped(var3)));
         }

         var6 = 200;
         if (var5 > 0) {
            var6 -= 10 << var5;
            if (var6 < 40) {
               var6 = 40;
            }
         }

         this.func_176234_a(var1, var2, var3, var6);
      }

   }

   public int getBlockColor() {
      return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
   }

   public void setGraphicsLevel(boolean var1) {
      this.field_176238_O = var1;
      this.field_150121_P = var1;
      this.field_150127_b = var1 ? 0 : 1;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.isRemote && (Boolean)var3.getValue(field_176236_b) && (Boolean)var3.getValue(field_176237_a)) {
         byte var5 = 4;
         int var6 = var5 + 1;
         int var7 = var2.getX();
         int var8 = var2.getY();
         int var9 = var2.getZ();
         byte var10 = 32;
         int var11 = var10 * var10;
         int var12 = var10 / 2;
         if (this.field_150128_a == null) {
            this.field_150128_a = new int[var10 * var10 * var10];
         }

         int var13;
         if (var1.isAreaLoaded(new BlockPos(var7 - var6, var8 - var6, var9 - var6), new BlockPos(var7 + var6, var8 + var6, var9 + var6))) {
            var13 = -var5;

            label133:
            while(true) {
               int var14;
               int var15;
               if (var13 > var5) {
                  var13 = 1;

                  while(true) {
                     if (var13 > 4) {
                        break label133;
                     }

                     for(var14 = -var5; var14 <= var5; ++var14) {
                        for(var15 = -var5; var15 <= var5; ++var15) {
                           for(int var17 = -var5; var17 <= var5; ++var17) {
                              if (this.field_150128_a[(var14 + var12) * var11 + (var15 + var12) * var10 + var17 + var12] == var13 - 1) {
                                 if (this.field_150128_a[(var14 + var12 - 1) * var11 + (var15 + var12) * var10 + var17 + var12] == -2) {
                                    this.field_150128_a[(var14 + var12 - 1) * var11 + (var15 + var12) * var10 + var17 + var12] = var13;
                                 }

                                 if (this.field_150128_a[(var14 + var12 + 1) * var11 + (var15 + var12) * var10 + var17 + var12] == -2) {
                                    this.field_150128_a[(var14 + var12 + 1) * var11 + (var15 + var12) * var10 + var17 + var12] = var13;
                                 }

                                 if (this.field_150128_a[(var14 + var12) * var11 + (var15 + var12 - 1) * var10 + var17 + var12] == -2) {
                                    this.field_150128_a[(var14 + var12) * var11 + (var15 + var12 - 1) * var10 + var17 + var12] = var13;
                                 }

                                 if (this.field_150128_a[(var14 + var12) * var11 + (var15 + var12 + 1) * var10 + var17 + var12] == -2) {
                                    this.field_150128_a[(var14 + var12) * var11 + (var15 + var12 + 1) * var10 + var17 + var12] = var13;
                                 }

                                 if (this.field_150128_a[(var14 + var12) * var11 + (var15 + var12) * var10 + (var17 + var12 - 1)] == -2) {
                                    this.field_150128_a[(var14 + var12) * var11 + (var15 + var12) * var10 + (var17 + var12 - 1)] = var13;
                                 }

                                 if (this.field_150128_a[(var14 + var12) * var11 + (var15 + var12) * var10 + var17 + var12 + 1] == -2) {
                                    this.field_150128_a[(var14 + var12) * var11 + (var15 + var12) * var10 + var17 + var12 + 1] = var13;
                                 }
                              }
                           }
                        }
                     }

                     ++var13;
                  }
               }

               for(var14 = -var5; var14 <= var5; ++var14) {
                  for(var15 = -var5; var15 <= var5; ++var15) {
                     Block var16 = var1.getBlockState(new BlockPos(var7 + var13, var8 + var14, var9 + var15)).getBlock();
                     if (var16 != Blocks.log && var16 != Blocks.log2) {
                        if (var16.getMaterial() == Material.leaves) {
                           this.field_150128_a[(var13 + var12) * var11 + (var14 + var12) * var10 + var15 + var12] = -2;
                        } else {
                           this.field_150128_a[(var13 + var12) * var11 + (var14 + var12) * var10 + var15 + var12] = -1;
                        }
                     } else {
                        this.field_150128_a[(var13 + var12) * var11 + (var14 + var12) * var10 + var15 + var12] = 0;
                     }
                  }
               }

               ++var13;
            }
         }

         var13 = this.field_150128_a[var12 * var11 + var12 * var10 + var12];
         if (var13 >= 0) {
            var1.setBlockState(var2, var3.withProperty(field_176236_b, false), 4);
         } else {
            this.func_176235_d(var1, var2);
         }
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      byte var4 = 1;
      int var5 = var4 + 1;
      int var6 = var2.getX();
      int var7 = var2.getY();
      int var8 = var2.getZ();
      if (var1.isAreaLoaded(new BlockPos(var6 - var5, var7 - var5, var8 - var5), new BlockPos(var6 + var5, var7 + var5, var8 + var5))) {
         for(int var9 = -var4; var9 <= var4; ++var9) {
            for(int var10 = -var4; var10 <= var4; ++var10) {
               for(int var11 = -var4; var11 <= var4; ++var11) {
                  BlockPos var12 = var2.add(var9, var10, var11);
                  IBlockState var13 = var1.getBlockState(var12);
                  if (var13.getBlock().getMaterial() == Material.leaves && !(Boolean)var13.getValue(field_176236_b)) {
                     var1.setBlockState(var12, var13.withProperty(field_176236_b, true), 4);
                  }
               }
            }
         }
      }

   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var1.func_175727_C(var2.offsetUp()) && !World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) && var4.nextInt(15) == 1) {
         double var5 = (double)((float)var2.getX() + var4.nextFloat());
         double var7 = (double)var2.getY() - 0.05D;
         double var9 = (double)((float)var2.getZ() + var4.nextFloat());
         var1.spawnParticle(EnumParticleTypes.DRIP_WATER, var5, var7, var9, 0.0D, 0.0D, 0.0D);
      }

   }

   public int quantityDropped(Random var1) {
      return var1.nextInt(20) == 0 ? 1 : 0;
   }

   public int getRenderColor(IBlockState var1) {
      return ColorizerFoliage.getFoliageColorBasic();
   }

   protected void func_176234_a(World var1, BlockPos var2, IBlockState var3, int var4) {
   }

   public boolean isVisuallyOpaque() {
      return false;
   }

   public BlockLeaves() {
      super(Material.leaves, false);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setHardness(0.2F);
      this.setLightOpacity(1);
      this.setStepSound(soundTypeGrass);
   }

   public boolean isOpaqueCube() {
      return !this.field_150121_P;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.sapling);
   }

   private void func_176235_d(World var1, BlockPos var2) {
      this.dropBlockAsItem(var1, var2, var1.getBlockState(var2), 0);
      var1.setBlockToAir(var2);
   }

   protected int func_176232_d(IBlockState var1) {
      return 20;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return this.field_176238_O ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return BiomeColorHelper.func_180287_b(var1, var2);
   }

   public abstract BlockPlanks.EnumType func_176233_b(int var1);
}
