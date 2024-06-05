package net.minecraft.block;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLiquid extends Block {
   private static final String __OBFID = "CL_00000265";
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return this.blockMaterial == Material.water ? BiomeColorHelper.func_180288_c(var1, var2) : 16777215;
   }

   protected void func_180688_d(World var1, BlockPos var2) {
      double var3 = (double)var2.getX();
      double var5 = (double)var2.getY();
      double var7 = (double)var2.getZ();
      var1.playSoundEffect(var3 + 0.5D, var5 + 0.5D, var7 + 0.5D, "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);

      for(int var9 = 0; var9 < 8; ++var9) {
         var1.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var3 + Math.random(), var5 + 1.2D, var7 + Math.random(), 0.0D, 0.0D, 0.0D);
      }

   }

   public EnumWorldBlockLayer getBlockLayer() {
      return this.blockMaterial == Material.water ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.SOLID;
   }

   protected int func_176362_e(IBlockAccess var1, BlockPos var2) {
      return var1.getBlockState(var2).getBlock().getMaterial() == this.blockMaterial ? (Integer)var1.getBlockState(var2).getValue(LEVEL) : -1;
   }

   public boolean canCollideCheck(IBlockState var1, boolean var2) {
      return var2 && (Integer)var1.getValue(LEVEL) == 0;
   }

   public boolean func_176364_g(IBlockAccess var1, BlockPos var2) {
      for(int var3 = -1; var3 <= 1; ++var3) {
         for(int var4 = -1; var4 <= 1; ++var4) {
            IBlockState var5 = var1.getBlockState(var2.add(var3, 0, var4));
            Block var6 = var5.getBlock();
            Material var7 = var6.getMaterial();
            if (var7 != this.blockMaterial && !var6.isFullBlock()) {
               return true;
            }
         }
      }

      return false;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(LEVEL, var1);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.func_176365_e(var1, var2, var3);
   }

   public Vec3 modifyAcceleration(World var1, BlockPos var2, Entity var3, Vec3 var4) {
      return var4.add(this.func_180687_h(var1, var2));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public static float getLiquidHeightPercent(int var0) {
      if (var0 >= 8) {
         var0 = 0;
      }

      return (float)(var0 + 1) / 9.0F;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public boolean isBlockSolid(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      Material var4 = var1.getBlockState(var2).getBlock().getMaterial();
      return var4 == this.blockMaterial ? false : (var3 == EnumFacing.UP ? true : (var4 == Material.ice ? false : super.isBlockSolid(var1, var2, var3)));
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return this.blockMaterial != Material.lava;
   }

   public boolean func_176365_e(World var1, BlockPos var2, IBlockState var3) {
      if (this.blockMaterial == Material.lava) {
         boolean var4 = false;
         EnumFacing[] var5 = EnumFacing.values();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            EnumFacing var8 = var5[var7];
            if (var8 != EnumFacing.DOWN && var1.getBlockState(var2.offset(var8)).getBlock().getMaterial() == Material.water) {
               var4 = true;
               break;
            }
         }

         if (var4) {
            Integer var9 = (Integer)var3.getValue(LEVEL);
            if (var9 == 0) {
               var1.setBlockState(var2, Blocks.obsidian.getDefaultState());
               this.func_180688_d(var1, var2);
               return true;
            }

            if (var9 <= 4) {
               var1.setBlockState(var2, Blocks.cobblestone.getDefaultState());
               this.func_180688_d(var1, var2);
               return true;
            }
         }
      }

      return false;
   }

   protected BlockLiquid(Material var1) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.setTickRandomly(true);
   }

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(LEVEL);
   }

   public int tickRate(World var1) {
      return this.blockMaterial == Material.water ? 5 : (this.blockMaterial == Material.lava ? (var1.provider.getHasNoSky() ? 10 : 30) : 0);
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   protected Vec3 func_180687_h(IBlockAccess var1, BlockPos var2) {
      Vec3 var3 = new Vec3(0.0D, 0.0D, 0.0D);
      int var4 = this.func_176366_f(var1, var2);
      Iterator var5 = EnumFacing.Plane.HORIZONTAL.iterator();

      EnumFacing var6;
      BlockPos var7;
      while(var5.hasNext()) {
         var6 = (EnumFacing)var5.next();
         var7 = var2.offset(var6);
         int var8 = this.func_176366_f(var1, var7);
         int var9;
         if (var8 < 0) {
            if (!var1.getBlockState(var7).getBlock().getMaterial().blocksMovement()) {
               var8 = this.func_176366_f(var1, var7.offsetDown());
               if (var8 >= 0) {
                  var9 = var8 - (var4 - 8);
                  var3 = var3.addVector((double)((var7.getX() - var2.getX()) * var9), (double)((var7.getY() - var2.getY()) * var9), (double)((var7.getZ() - var2.getZ()) * var9));
               }
            }
         } else if (var8 >= 0) {
            var9 = var8 - var4;
            var3 = var3.addVector((double)((var7.getX() - var2.getX()) * var9), (double)((var7.getY() - var2.getY()) * var9), (double)((var7.getZ() - var2.getZ()) * var9));
         }
      }

      if ((Integer)var1.getBlockState(var2).getValue(LEVEL) >= 8) {
         var5 = EnumFacing.Plane.HORIZONTAL.iterator();

         do {
            if (!var5.hasNext()) {
               return var3.normalize();
            }

            var6 = (EnumFacing)var5.next();
            var7 = var2.offset(var6);
         } while(!this.isBlockSolid(var1, var7, var6) && !this.isBlockSolid(var1, var7.offsetUp(), var6));

         var3 = var3.normalize().addVector(0.0D, -6.0D, 0.0D);
      }

      return var3.normalize();
   }

   public static double func_180689_a(IBlockAccess var0, BlockPos var1, Material var2) {
      Vec3 var3 = getDynamicLiquidForMaterial(var2).func_180687_h(var0, var1);
      return var3.xCoord == 0.0D && var3.zCoord == 0.0D ? -1000.0D : Math.atan2(var3.zCoord, var3.xCoord) - 1.5707963267948966D;
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      double var5 = (double)var2.getX();
      double var7 = (double)var2.getY();
      double var9 = (double)var2.getZ();
      if (this.blockMaterial == Material.water) {
         int var11 = (Integer)var3.getValue(LEVEL);
         if (var11 > 0 && var11 < 8) {
            if (var4.nextInt(64) == 0) {
               var1.playSound(var5 + 0.5D, var7 + 0.5D, var9 + 0.5D, "liquid.water", var4.nextFloat() * 0.25F + 0.75F, var4.nextFloat() * 1.0F + 0.5F, false);
            }
         } else if (var4.nextInt(10) == 0) {
            var1.spawnParticle(EnumParticleTypes.SUSPENDED, var5 + (double)var4.nextFloat(), var7 + (double)var4.nextFloat(), var9 + (double)var4.nextFloat(), 0.0D, 0.0D, 0.0D);
         }
      }

      if (this.blockMaterial == Material.lava && var1.getBlockState(var2.offsetUp()).getBlock().getMaterial() == Material.air && !var1.getBlockState(var2.offsetUp()).getBlock().isOpaqueCube()) {
         if (var4.nextInt(100) == 0) {
            double var18 = var5 + (double)var4.nextFloat();
            double var13 = var7 + this.maxY;
            double var15 = var9 + (double)var4.nextFloat();
            var1.spawnParticle(EnumParticleTypes.LAVA, var18, var13, var15, 0.0D, 0.0D, 0.0D);
            var1.playSound(var18, var13, var15, "liquid.lavapop", 0.2F + var4.nextFloat() * 0.2F, 0.9F + var4.nextFloat() * 0.15F, false);
         }

         if (var4.nextInt(200) == 0) {
            var1.playSound(var5, var7, var9, "liquid.lava", 0.2F + var4.nextFloat() * 0.2F, 0.9F + var4.nextFloat() * 0.15F, false);
         }
      }

      if (var4.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown())) {
         Material var19 = var1.getBlockState(var2.offsetDown(2)).getBlock().getMaterial();
         if (!var19.blocksMovement() && !var19.isLiquid()) {
            double var12 = var5 + (double)var4.nextFloat();
            double var14 = var7 - 1.05D;
            double var16 = var9 + (double)var4.nextFloat();
            if (this.blockMaterial == Material.water) {
               var1.spawnParticle(EnumParticleTypes.DRIP_WATER, var12, var14, var16, 0.0D, 0.0D, 0.0D);
            } else {
               var1.spawnParticle(EnumParticleTypes.DRIP_LAVA, var12, var14, var16, 0.0D, 0.0D, 0.0D);
            }
         }
      }

   }

   public int getMixedBrightnessForBlock(IBlockAccess var1, BlockPos var2) {
      int var3 = var1.getCombinedLight(var2, 0);
      int var4 = var1.getCombinedLight(var2.offsetUp(), 0);
      int var5 = var3 & 255;
      int var6 = var4 & 255;
      int var7 = var3 >> 16 & 255;
      int var8 = var4 >> 16 & 255;
      return (var5 > var6 ? var5 : var6) | (var7 > var8 ? var7 : var8) << 16;
   }

   protected int func_176366_f(IBlockAccess var1, BlockPos var2) {
      int var3 = this.func_176362_e(var1, var2);
      return var3 >= 8 ? 0 : var3;
   }

   public boolean isFullCube() {
      return false;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.func_176365_e(var1, var2, var3);
   }

   public int getRenderType() {
      return 1;
   }

   public static BlockStaticLiquid getStaticLiquidForMaterial(Material var0) {
      if (var0 == Material.water) {
         return Blocks.water;
      } else if (var0 == Material.lava) {
         return Blocks.lava;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{LEVEL});
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var1.getBlockState(var2).getBlock().getMaterial() == this.blockMaterial ? false : (var3 == EnumFacing.UP ? true : super.shouldSideBeRendered(var1, var2, var3));
   }

   public static BlockDynamicLiquid getDynamicLiquidForMaterial(Material var0) {
      if (var0 == Material.water) {
         return Blocks.flowing_water;
      } else if (var0 == Material.lava) {
         return Blocks.flowing_lava;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }
}
