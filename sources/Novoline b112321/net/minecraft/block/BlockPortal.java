package net.minecraft.block;

import com.google.common.cache.LoadingCache;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockPortal$Size;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockPattern$PatternHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.EnumFacing$AxisDirection;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPortal extends BlockBreakable {
   public static final PropertyEnum AXIS = PropertyEnum.create("axis", EnumFacing$Axis.class, (Enum[])(new EnumFacing$Axis[]{EnumFacing$Axis.X, EnumFacing$Axis.Z}));

   public BlockPortal() {
      super(Material.portal, false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing$Axis.X));
      this.setTickRandomly(true);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.updateTick(var1, var2, var3, var4);
      if(var1.provider.isSurfaceWorld() && var1.getGameRules().getBoolean("doMobSpawning") && var4.nextInt(2000) < var1.getDifficulty().getDifficultyId()) {
         int var5 = var2.getY();

         BlockPos var6;
         for(var6 = var2; !World.doesBlockHaveSolidTopSurface(var1, var6) && var6.getY() > 0; var6 = var6.down()) {
            ;
         }

         if(!var1.getBlockState(var6.up()).getBlock().isNormalCube()) {
            Entity var7 = ItemMonsterPlacer.spawnCreature(var1, 57, (double)var6.getX() + 0.5D, (double)var6.getY() + 1.1D, (double)var6.getZ() + 0.5D);
            var7.timeUntilPortal = var7.getPortalCoolDown();
         }
      }

   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      EnumFacing$Axis var3 = (EnumFacing$Axis)var1.getBlockState(var2).getValue(AXIS);
      float var4 = 0.125F;
      float var5 = 0.125F;
      if(var3 == EnumFacing$Axis.X) {
         var4 = 0.5F;
      }

      if(var3 == EnumFacing$Axis.Z) {
         var5 = 0.5F;
      }

      this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var5, 0.5F + var4, 1.0F, 0.5F + var5);
   }

   public static int getMetaForAxis(EnumFacing$Axis var0) {
      return var0 == EnumFacing$Axis.X?1:(var0 == EnumFacing$Axis.Z?2:0);
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean func_176548_d(World var1, BlockPos var2) {
      BlockPortal$Size var3 = new BlockPortal$Size(var1, var2, EnumFacing$Axis.X);
      if(var3.func_150860_b() && BlockPortal$Size.access$000(var3) == 0) {
         var3.func_150859_c();
         return true;
      } else {
         BlockPortal$Size var4 = new BlockPortal$Size(var1, var2, EnumFacing$Axis.Z);
         if(var4.func_150860_b() && BlockPortal$Size.access$000(var4) == 0) {
            var4.func_150859_c();
            return true;
         } else {
            return false;
         }
      }
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing$Axis var5 = (EnumFacing$Axis)var3.getValue(AXIS);
      if(var5 == EnumFacing$Axis.X) {
         BlockPortal$Size var6 = new BlockPortal$Size(var1, var2, EnumFacing$Axis.X);
         if(!var6.func_150860_b() || BlockPortal$Size.access$000(var6) < BlockPortal$Size.access$100(var6) * BlockPortal$Size.access$200(var6)) {
            var1.setBlockState(var2, Blocks.air.getDefaultState());
         }
      } else if(var5 == EnumFacing$Axis.Z) {
         BlockPortal$Size var7 = new BlockPortal$Size(var1, var2, EnumFacing$Axis.Z);
         if(!var7.func_150860_b() || BlockPortal$Size.access$000(var7) < BlockPortal$Size.access$100(var7) * BlockPortal$Size.access$200(var7)) {
            var1.setBlockState(var2, Blocks.air.getDefaultState());
         }
      }

   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      EnumFacing$Axis var4 = null;
      IBlockState var5 = var1.getBlockState(var2);
      if(var1.getBlockState(var2).getBlock() == this) {
         var4 = (EnumFacing$Axis)var5.getValue(AXIS);
         return false;
      } else {
         if(var1.getBlockState(var2.west()).getBlock() == this && var1.getBlockState(var2.west(2)).getBlock() != this) {
            boolean var13 = true;
         } else {
            boolean var10000 = false;
         }

         if(var1.getBlockState(var2.east()).getBlock() == this && var1.getBlockState(var2.east(2)).getBlock() != this) {
            boolean var15 = true;
         } else {
            boolean var14 = false;
         }

         if(var1.getBlockState(var2.north()).getBlock() == this && var1.getBlockState(var2.north(2)).getBlock() != this) {
            boolean var17 = true;
         } else {
            boolean var16 = false;
         }

         if(var1.getBlockState(var2.south()).getBlock() == this && var1.getBlockState(var2.south(2)).getBlock() != this) {
            boolean var19 = true;
         } else {
            boolean var18 = false;
         }

         boolean var10 = var4 == EnumFacing$Axis.X;
         boolean var11 = var4 == EnumFacing$Axis.Z;
         return var3 == EnumFacing.WEST || var3 == EnumFacing.EAST || var3 == EnumFacing.NORTH || var3 == EnumFacing.SOUTH;
      }
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.TRANSLUCENT;
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if(var4.ridingEntity == null && var4.riddenByEntity == null) {
         var4.func_181015_d(var2);
      }

   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(var4.nextInt(100) == 0) {
         var1.playSound((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "portal.portal", 0.5F, var4.nextFloat() * 0.4F + 0.8F, false);
      }

      for(int var5 = 0; var5 < 4; ++var5) {
         double var6 = (double)((float)var2.getX() + var4.nextFloat());
         double var8 = (double)((float)var2.getY() + var4.nextFloat());
         double var10 = (double)((float)var2.getZ() + var4.nextFloat());
         double var12 = ((double)var4.nextFloat() - 0.5D) * 0.5D;
         double var14 = ((double)var4.nextFloat() - 0.5D) * 0.5D;
         double var16 = ((double)var4.nextFloat() - 0.5D) * 0.5D;
         int var18 = var4.nextInt(2) * 2 - 1;
         if(var1.getBlockState(var2.west()).getBlock() != this && var1.getBlockState(var2.east()).getBlock() != this) {
            var6 = (double)var2.getX() + 0.5D + 0.25D * (double)var18;
            var12 = (double)(var4.nextFloat() * 2.0F * (float)var18);
         } else {
            var10 = (double)var2.getZ() + 0.5D + 0.25D * (double)var18;
            var16 = (double)(var4.nextFloat() * 2.0F * (float)var18);
         }

         var1.spawnParticle(EnumParticleTypes.PORTAL, var6, var8, var10, var12, var14, var16, new int[0]);
      }

   }

   public Item getItem(World var1, BlockPos var2) {
      return null;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(AXIS, (var1 & 3) == 2?EnumFacing$Axis.Z:EnumFacing$Axis.X);
   }

   public int getMetaFromState(IBlockState var1) {
      return getMetaForAxis((EnumFacing$Axis)var1.getValue(AXIS));
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{AXIS});
   }

   public BlockPattern$PatternHelper func_181089_f(World var1, BlockPos var2) {
      EnumFacing$Axis var3 = EnumFacing$Axis.Z;
      BlockPortal$Size var4 = new BlockPortal$Size(var1, var2, EnumFacing$Axis.X);
      LoadingCache var5 = BlockPattern.func_181627_a(var1, true);
      if(!var4.func_150860_b()) {
         var3 = EnumFacing$Axis.X;
         var4 = new BlockPortal$Size(var1, var2, EnumFacing$Axis.Z);
      }

      if(!var4.func_150860_b()) {
         return new BlockPattern$PatternHelper(var2, EnumFacing.NORTH, EnumFacing.UP, var5, 1, 1, 1);
      } else {
         int[] var6 = new int[EnumFacing$AxisDirection.values().length];
         EnumFacing var7 = BlockPortal$Size.access$300(var4).rotateYCCW();
         BlockPos var8 = BlockPortal$Size.access$400(var4).up(var4.func_181100_a() - 1);

         for(EnumFacing$AxisDirection var12 : EnumFacing$AxisDirection.values()) {
            BlockPattern$PatternHelper var13 = new BlockPattern$PatternHelper(var7.getAxisDirection() == var12?var8:var8.offset(BlockPortal$Size.access$300(var4), var4.func_181101_b() - 1), EnumFacing.func_181076_a(var12, var3), EnumFacing.UP, var5, var4.func_181101_b(), var4.func_181100_a(), 1);

            for(int var14 = 0; var14 < var4.func_181101_b(); ++var14) {
               for(int var15 = 0; var15 < var4.func_181100_a(); ++var15) {
                  BlockWorldState var16 = var13.translateOffset(var14, var15, 1);
                  if(var16.getBlockState() != null && var16.getBlockState().getBlock().getMaterial() != Material.air) {
                     ++var6[var12.ordinal()];
                  }
               }
            }
         }

         EnumFacing$AxisDirection var17 = EnumFacing$AxisDirection.POSITIVE;

         for(EnumFacing$AxisDirection var21 : EnumFacing$AxisDirection.values()) {
            if(var6[var21.ordinal()] < var6[var17.ordinal()]) {
               var17 = var21;
            }
         }

         return new BlockPattern$PatternHelper(var7.getAxisDirection() == var17?var8:var8.offset(BlockPortal$Size.access$300(var4), var4.func_181101_b() - 1), EnumFacing.func_181076_a(var17, var3), EnumFacing.UP, var5, var4.func_181101_b(), var4.func_181100_a(), 1);
      }
   }
}
