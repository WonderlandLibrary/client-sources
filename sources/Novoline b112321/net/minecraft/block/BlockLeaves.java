package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLeaves extends BlockLeavesBase {
   public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
   public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
   int[] surroundings;
   protected int iconIndex;
   protected boolean isTransparent;

   public BlockLeaves() {
      super(Material.leaves, false);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setHardness(0.2F);
      this.setLightOpacity(1);
      this.setStepSound(soundTypeGrass);
   }

   public int getBlockColor() {
      return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
   }

   public int getRenderColor(IBlockState var1) {
      return ColorizerFoliage.getFoliageColorBasic();
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return BiomeColorHelper.getFoliageColorAtPos(var1, var2);
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = true;
      boolean var5 = true;
      int var6 = var2.getX();
      int var7 = var2.getY();
      int var8 = var2.getZ();
      if(var1.isAreaLoaded(new BlockPos(var6 - 2, var7 - 2, var8 - 2), new BlockPos(var6 + 2, var7 + 2, var8 + 2))) {
         for(int var9 = -1; var9 <= 1; ++var9) {
            for(int var10 = -1; var10 <= 1; ++var10) {
               for(int var11 = -1; var11 <= 1; ++var11) {
                  BlockPos var12 = var2.a(var9, var10, var11);
                  IBlockState var13 = var1.getBlockState(var12);
                  if(var13.getBlock().getMaterial() == Material.leaves && !((Boolean)var13.getValue(CHECK_DECAY)).booleanValue()) {
                     var1.setBlockState(var12, var13.withProperty(CHECK_DECAY, Boolean.TRUE), 4);
                  }
               }
            }
         }
      }

   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(!var1.isRemote && ((Boolean)var3.getValue(CHECK_DECAY)).booleanValue() && ((Boolean)var3.getValue(DECAYABLE)).booleanValue()) {
         boolean var5 = true;
         boolean var6 = true;
         int var7 = var2.getX();
         int var8 = var2.getY();
         int var9 = var2.getZ();
         boolean var10 = true;
         boolean var11 = true;
         boolean var12 = true;
         if(this.surroundings == null) {
            this.surroundings = new int['耀'];
         }

         if(var1.isAreaLoaded(new BlockPos(var7 - 5, var8 - 5, var9 - 5), new BlockPos(var7 + 5, var8 + 5, var9 + 5))) {
            BlockPos$MutableBlockPos var13 = new BlockPos$MutableBlockPos();

            for(int var14 = -4; var14 <= 4; ++var14) {
               for(int var15 = -4; var15 <= 4; ++var15) {
                  for(int var16 = -4; var16 <= 4; ++var16) {
                     Block var17 = var1.getBlockState(var13.func_181079_c(var7 + var14, var8 + var15, var9 + var16)).getBlock();
                     if(var17 != Blocks.log && var17 != Blocks.log2) {
                        if(var17.getMaterial() == Material.leaves) {
                           this.surroundings[(var14 + 16) * 1024 + (var15 + 16) * 32 + var16 + 16] = -2;
                        } else {
                           this.surroundings[(var14 + 16) * 1024 + (var15 + 16) * 32 + var16 + 16] = -1;
                        }
                     } else {
                        this.surroundings[(var14 + 16) * 1024 + (var15 + 16) * 32 + var16 + 16] = 0;
                     }
                  }
               }
            }

            for(int var19 = 1; var19 <= 4; ++var19) {
               for(int var20 = -4; var20 <= 4; ++var20) {
                  for(int var21 = -4; var21 <= 4; ++var21) {
                     for(int var22 = -4; var22 <= 4; ++var22) {
                        if(this.surroundings[(var20 + 16) * 1024 + (var21 + 16) * 32 + var22 + 16] == var19 - 1) {
                           if(this.surroundings[(var20 + 16 - 1) * 1024 + (var21 + 16) * 32 + var22 + 16] == -2) {
                              this.surroundings[(var20 + 16 - 1) * 1024 + (var21 + 16) * 32 + var22 + 16] = var19;
                           }

                           if(this.surroundings[(var20 + 16 + 1) * 1024 + (var21 + 16) * 32 + var22 + 16] == -2) {
                              this.surroundings[(var20 + 16 + 1) * 1024 + (var21 + 16) * 32 + var22 + 16] = var19;
                           }

                           if(this.surroundings[(var20 + 16) * 1024 + (var21 + 16 - 1) * 32 + var22 + 16] == -2) {
                              this.surroundings[(var20 + 16) * 1024 + (var21 + 16 - 1) * 32 + var22 + 16] = var19;
                           }

                           if(this.surroundings[(var20 + 16) * 1024 + (var21 + 16 + 1) * 32 + var22 + 16] == -2) {
                              this.surroundings[(var20 + 16) * 1024 + (var21 + 16 + 1) * 32 + var22 + 16] = var19;
                           }

                           if(this.surroundings[(var20 + 16) * 1024 + (var21 + 16) * 32 + var22 + 16 - 1] == -2) {
                              this.surroundings[(var20 + 16) * 1024 + (var21 + 16) * 32 + var22 + 16 - 1] = var19;
                           }

                           if(this.surroundings[(var20 + 16) * 1024 + (var21 + 16) * 32 + var22 + 16 + 1] == -2) {
                              this.surroundings[(var20 + 16) * 1024 + (var21 + 16) * 32 + var22 + 16 + 1] = var19;
                           }
                        }
                     }
                  }
               }
            }
         }

         int var18 = this.surroundings[16912];
         var1.setBlockState(var2, var3.withProperty(CHECK_DECAY, Boolean.FALSE), 4);
      }

   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(var1.canLightningStrike(var2.up()) && !World.doesBlockHaveSolidTopSurface(var1, var2.down()) && var4.nextInt(15) == 1) {
         double var5 = (double)((float)var2.getX() + var4.nextFloat());
         double var7 = (double)var2.getY() - 0.05D;
         double var9 = (double)((float)var2.getZ() + var4.nextFloat());
         var1.spawnParticle(EnumParticleTypes.DRIP_WATER, var5, var7, var9, 0.0D, 0.0D, 0.0D, new int[0]);
      }

   }

   private void destroy(World var1, BlockPos var2) {
      this.dropBlockAsItem(var1, var2, var1.getBlockState(var2), 0);
      var1.setBlockToAir(var2);
   }

   public int quantityDropped(Random var1) {
      return var1.nextInt(20) == 0?1:0;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.sapling);
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if(!var1.isRemote) {
         int var6 = this.getSaplingDropChance(var3);
         var6 = var6 - (2 << var5);
         if(var6 < 10) {
            var6 = 10;
         }

         if(var1.rand.nextInt(var6) == 0) {
            Item var7 = this.getItemDropped(var3, var1.rand, var5);
            spawnAsEntity(var1, var2, new ItemStack(var7, 1, this.damageDropped(var3)));
         }

         var6 = 200;
         var6 = var6 - (10 << var5);
         if(var6 < 40) {
            var6 = 40;
         }

         this.dropApple(var1, var2, var3, var6);
      }

   }

   protected void dropApple(World var1, BlockPos var2, IBlockState var3, int var4) {
   }

   protected int getSaplingDropChance(IBlockState var1) {
      return 20;
   }

   public boolean isOpaqueCube() {
      return !this.fancyGraphics;
   }

   public void setGraphicsLevel(boolean var1) {
      this.isTransparent = var1;
      this.fancyGraphics = var1;
      this.iconIndex = 0;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return this.isTransparent?EnumWorldBlockLayer.CUTOUT_MIPPED:EnumWorldBlockLayer.SOLID;
   }

   public boolean isVisuallyOpaque() {
      return false;
   }

   public abstract BlockPlanks$EnumType getWoodType(int var1);
}
