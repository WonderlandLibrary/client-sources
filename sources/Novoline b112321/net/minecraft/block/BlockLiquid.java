package net.minecraft.block;

import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLiquid extends Block {
   public static final iV P = iV.a("level", 0, 15);

   protected BlockLiquid(Material var1) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(P, Integer.valueOf(0)));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.setTickRandomly(true);
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return this.blockMaterial != Material.lava;
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return this.blockMaterial == Material.water?BiomeColorHelper.getWaterColorAtPos(var1, var2):16777215;
   }

   public static float getLiquidHeightPercent(int var0) {
      if(var0 >= 8) {
         var0 = 0;
      }

      return (float)(var0 + 1) / 9.0F;
   }

   protected int getLevel(IBlockAccess var1, BlockPos var2) {
      return var1.getBlockState(var2).getBlock().getMaterial() == this.blockMaterial?((Integer)var1.getBlockState(var2).getValue(P)).intValue():-1;
   }

   protected int getEffectiveFlowDecay(IBlockAccess var1, BlockPos var2) {
      int var3 = this.getLevel(var1, var2);
      return var3 >= 8?0:var3;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean canCollideCheck(IBlockState var1, boolean var2) {
      return ((Integer)var1.getValue(P)).intValue() == 0;
   }

   public boolean isBlockSolid(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      Material var4 = var1.getBlockState(var2).getBlock().getMaterial();
      return var4 != this.blockMaterial && (var3 == EnumFacing.UP || var4 != Material.ice && super.isBlockSolid(var1, var2, var3));
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var1.getBlockState(var2).getBlock().getMaterial() != this.blockMaterial && (var3 == EnumFacing.UP || super.shouldSideBeRendered(var1, var2, var3));
   }

   public boolean func_176364_g(IBlockAccess var1, BlockPos var2) {
      for(int var3 = -1; var3 <= 1; ++var3) {
         for(int var4 = -1; var4 <= 1; ++var4) {
            IBlockState var5 = var1.getBlockState(var2.a(var3, 0, var4));
            Block var6 = var5.getBlock();
            Material var7 = var6.getMaterial();
            if(var7 != this.blockMaterial && !var6.isFullBlock()) {
               return true;
            }
         }
      }

      return false;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public int getRenderType() {
      return 1;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   protected Vec3 getFlowVector(IBlockAccess var1, BlockPos var2) {
      Vec3 var3 = new Vec3(0.0D, 0.0D, 0.0D);
      int var4 = this.getEffectiveFlowDecay(var1, var2);

      for(Object var6 : EnumFacing$Plane.HORIZONTAL) {
         BlockPos var7 = var2.offset((EnumFacing)var6);
         int var8 = this.getEffectiveFlowDecay(var1, var7);
         if(!var1.getBlockState(var7).getBlock().getMaterial().blocksMovement()) {
            var8 = this.getEffectiveFlowDecay(var1, var7.down());
            int var9 = var8 - (var4 - 8);
            var3 = var3.addVector((double)((var7.getX() - var2.getX()) * var9), (double)((var7.getY() - var2.getY()) * var9), (double)((var7.getZ() - var2.getZ()) * var9));
         }
      }

      if(((Integer)var1.getBlockState(var2).getValue(P)).intValue() >= 8) {
         for(Object var11 : EnumFacing$Plane.HORIZONTAL) {
            BlockPos var12 = var2.offset((EnumFacing)var11);
            if(this.isBlockSolid(var1, var12, (EnumFacing)var11) || this.isBlockSolid(var1, var12.up(), (EnumFacing)var11)) {
               var3 = var3.normalize().addVector(0.0D, -6.0D, 0.0D);
               break;
            }
         }
      }

      return var3.normalize();
   }

   public Vec3 modifyAcceleration(World var1, BlockPos var2, Entity var3, Vec3 var4) {
      return var4.add(this.getFlowVector(var1, var2));
   }

   public int tickRate(World var1) {
      return this.blockMaterial == Material.water?5:(this.blockMaterial == Material.lava?(var1.provider.getHasNoSky()?10:30):0);
   }

   public int getMixedBrightnessForBlock(IBlockAccess var1, BlockPos var2) {
      int var3 = var1.getCombinedLight(var2, 0);
      int var4 = var1.getCombinedLight(var2.up(), 0);
      int var5 = var3 & 255;
      int var6 = var4 & 255;
      int var7 = var3 >> 16 & 255;
      int var8 = var4 >> 16 & 255;
      return (var5 > var6?var5:var6) | (var7 > var8?var7:var8) << 16;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return this.blockMaterial == Material.water?EnumWorldBlockLayer.TRANSLUCENT:EnumWorldBlockLayer.SOLID;
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      double var5 = (double)var2.getX();
      double var7 = (double)var2.getY();
      double var9 = (double)var2.getZ();
      if(this.blockMaterial == Material.water) {
         int var11 = ((Integer)var3.getValue(P)).intValue();
         if(var11 < 8) {
            if(var4.nextInt(64) == 0) {
               var1.playSound(var5 + 0.5D, var7 + 0.5D, var9 + 0.5D, "liquid.water", var4.nextFloat() * 0.25F + 0.75F, var4.nextFloat() * 1.0F + 0.5F, false);
            }
         } else if(var4.nextInt(10) == 0) {
            var1.spawnParticle(EnumParticleTypes.SUSPENDED, var5 + (double)var4.nextFloat(), var7 + (double)var4.nextFloat(), var9 + (double)var4.nextFloat(), 0.0D, 0.0D, 0.0D, new int[0]);
         }
      }

      if(this.blockMaterial == Material.lava && var1.getBlockState(var2.up()).getBlock().getMaterial() == Material.air && !var1.getBlockState(var2.up()).getBlock().isOpaqueCube()) {
         if(var4.nextInt(100) == 0) {
            double var18 = var5 + (double)var4.nextFloat();
            double var13 = var7 + this.maxY;
            double var15 = var9 + (double)var4.nextFloat();
            var1.spawnParticle(EnumParticleTypes.LAVA, var18, var13, var15, 0.0D, 0.0D, 0.0D, new int[0]);
            var1.playSound(var18, var13, var15, "liquid.lavapop", 0.2F + var4.nextFloat() * 0.2F, 0.9F + var4.nextFloat() * 0.15F, false);
         }

         if(var4.nextInt(200) == 0) {
            var1.playSound(var5, var7, var9, "liquid.lava", 0.2F + var4.nextFloat() * 0.2F, 0.9F + var4.nextFloat() * 0.15F, false);
         }
      }

      if(var4.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface(var1, var2.down())) {
         Material var19 = var1.getBlockState(var2.down(2)).getBlock().getMaterial();
         if(!var19.blocksMovement() && !var19.isLiquid()) {
            double var12 = var5 + (double)var4.nextFloat();
            double var14 = var7 - 1.05D;
            double var16 = var9 + (double)var4.nextFloat();
            if(this.blockMaterial == Material.water) {
               var1.spawnParticle(EnumParticleTypes.DRIP_WATER, var12, var14, var16, 0.0D, 0.0D, 0.0D, new int[0]);
            } else {
               var1.spawnParticle(EnumParticleTypes.DRIP_LAVA, var12, var14, var16, 0.0D, 0.0D, 0.0D, new int[0]);
            }
         }
      }

   }

   public static double getFlowDirection(IBlockAccess var0, BlockPos var1, Material var2) {
      Vec3 var3 = getFlowingBlock(var2).getFlowVector(var0, var1);
      return var3.xCoord == 0.0D && var3.zCoord == 0.0D?-1000.0D:MathHelper.func_181159_b(var3.zCoord, var3.xCoord) - 1.5707963267948966D;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.checkForMixing(var1, var2, var3);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.checkForMixing(var1, var2, var3);
   }

   public boolean checkForMixing(World var1, BlockPos var2, IBlockState var3) {
      if(this.blockMaterial == Material.lava) {
         boolean var4 = false;

         for(EnumFacing var8 : EnumFacing.values()) {
            if(var8 != EnumFacing.DOWN && var1.getBlockState(var2.offset(var8)).getBlock().getMaterial() == Material.water) {
               var4 = true;
               break;
            }
         }

         Integer var10 = (Integer)var3.getValue(P);
         if(var10.intValue() == 0) {
            var1.setBlockState(var2, Blocks.obsidian.getDefaultState());
            this.triggerMixEffects(var1, var2);
            return true;
         }

         if(var10.intValue() <= 4) {
            var1.setBlockState(var2, Blocks.cobblestone.getDefaultState());
            this.triggerMixEffects(var1, var2);
            return true;
         }
      }

      return false;
   }

   protected void triggerMixEffects(World var1, BlockPos var2) {
      double var3 = (double)var2.getX();
      double var5 = (double)var2.getY();
      double var7 = (double)var2.getZ();
      var1.playSoundEffect(var3 + 0.5D, var5 + 0.5D, var7 + 0.5D, "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);

      for(int var9 = 0; var9 < 8; ++var9) {
         var1.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var3 + Math.random(), var5 + 1.2D, var7 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
      }

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

   public static BlockDynamicLiquid getFlowingBlock(Material var0) {
      if(var0 == Material.water) {
         return Blocks.flowing_water;
      } else if(var0 == Material.lava) {
         return Blocks.flowing_lava;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }

   public static BlockStaticLiquid getStaticBlock(Material var0) {
      if(var0 == Material.water) {
         return Blocks.water;
      } else if(var0 == Material.lava) {
         return Blocks.lava;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
