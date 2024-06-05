package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire$EnumAttachPosition;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneWire extends Block {
   public static final PropertyEnum NORTH = PropertyEnum.create("north", BlockRedstoneWire$EnumAttachPosition.class);
   public static final PropertyEnum EAST = PropertyEnum.create("east", BlockRedstoneWire$EnumAttachPosition.class);
   public static final PropertyEnum SOUTH = PropertyEnum.create("south", BlockRedstoneWire$EnumAttachPosition.class);
   public static final PropertyEnum WEST = PropertyEnum.create("west", BlockRedstoneWire$EnumAttachPosition.class);
   public static final iV P = iV.a("power", 0, 15);
   private boolean canProvidePower = true;
   private final Set blocksNeedingUpdate = Sets.newHashSet();

   public BlockRedstoneWire() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, BlockRedstoneWire$EnumAttachPosition.NONE).withProperty(EAST, BlockRedstoneWire$EnumAttachPosition.NONE).withProperty(SOUTH, BlockRedstoneWire$EnumAttachPosition.NONE).withProperty(WEST, BlockRedstoneWire$EnumAttachPosition.NONE).withProperty(P, Integer.valueOf(0)));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      var1 = var1.withProperty(WEST, this.getAttachPosition(var2, var3, EnumFacing.WEST));
      var1 = var1.withProperty(EAST, this.getAttachPosition(var2, var3, EnumFacing.EAST));
      var1 = var1.withProperty(NORTH, this.getAttachPosition(var2, var3, EnumFacing.NORTH));
      var1 = var1.withProperty(SOUTH, this.getAttachPosition(var2, var3, EnumFacing.SOUTH));
      return var1;
   }

   private BlockRedstoneWire$EnumAttachPosition getAttachPosition(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      BlockPos var4 = var2.offset(var3);
      Block var5 = var1.getBlockState(var2.offset(var3)).getBlock();
      if(!canConnectTo(var1.getBlockState(var4), var3) && (var5.isBlockNormalCube() || !canConnectUpwardsTo(var1.getBlockState(var4.down())))) {
         Block var6 = var1.getBlockState(var2.up()).getBlock();
         return !var6.isBlockNormalCube() && var5.isBlockNormalCube() && canConnectUpwardsTo(var1.getBlockState(var4.up()))?BlockRedstoneWire$EnumAttachPosition.UP:BlockRedstoneWire$EnumAttachPosition.NONE;
      } else {
         return BlockRedstoneWire$EnumAttachPosition.SIDE;
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      IBlockState var4 = var1.getBlockState(var2);
      return var4.getBlock() != this?super.colorMultiplier(var1, var2, var3):this.colorMultiplier(((Integer)var4.getValue(P)).intValue());
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return World.doesBlockHaveSolidTopSurface(var1, var2.down()) || var1.getBlockState(var2.down()).getBlock() == Blocks.glowstone;
   }

   private IBlockState updateSurroundingRedstone(World var1, BlockPos var2, IBlockState var3) {
      var3 = this.calculateCurrentChanges(var1, var2, var2, var3);
      ArrayList var4 = Lists.newArrayList(this.blocksNeedingUpdate);
      this.blocksNeedingUpdate.clear();

      for(BlockPos var6 : var4) {
         var1.notifyNeighborsOfStateChange(var6, this);
      }

      return var3;
   }

   private IBlockState calculateCurrentChanges(World var1, BlockPos var2, BlockPos var3, IBlockState var4) {
      IBlockState var5 = var4;
      int var6 = ((Integer)var4.getValue(P)).intValue();
      int var7 = 0;
      var7 = this.getMaxCurrentStrength(var1, var3, var7);
      this.canProvidePower = false;
      int var8 = var1.isBlockIndirectlyGettingPowered(var2);
      this.canProvidePower = true;
      if(var8 > var7 - 1) {
         var7 = var8;
      }

      int var9 = 0;

      for(Object var11 : EnumFacing$Plane.HORIZONTAL) {
         BlockPos var12 = var2.offset((EnumFacing)var11);
         if(var12.getX() == var3.getX() && var12.getZ() == var3.getZ()) {
            boolean var19 = false;
         } else {
            boolean var10000 = true;
         }

         var9 = this.getMaxCurrentStrength(var1, var12, var9);
         if(var1.getBlockState(var12).getBlock().isNormalCube() && !var1.getBlockState(var2.up()).getBlock().isNormalCube()) {
            if(var2.getY() >= var3.getY()) {
               var9 = this.getMaxCurrentStrength(var1, var12.up(), var9);
            }
         } else if(!var1.getBlockState(var12).getBlock().isNormalCube() && var2.getY() <= var3.getY()) {
            var9 = this.getMaxCurrentStrength(var1, var12.down(), var9);
         }
      }

      if(var9 > var7) {
         var7 = var9 - 1;
      } else {
         --var7;
      }

      if(var8 > var7 - 1) {
         var7 = var8;
      }

      if(var6 != var7) {
         var4 = var4.withProperty(P, Integer.valueOf(var7));
         if(var1.getBlockState(var2) == var5) {
            var1.setBlockState(var2, var4, 2);
         }

         this.blocksNeedingUpdate.add(var2);

         for(EnumFacing var13 : EnumFacing.values()) {
            this.blocksNeedingUpdate.add(var2.offset(var13));
         }
      }

      return var4;
   }

   private void notifyWireNeighborsOfStateChange(World var1, BlockPos var2) {
      if(var1.getBlockState(var2).getBlock() == this) {
         var1.notifyNeighborsOfStateChange(var2, this);

         for(EnumFacing var6 : EnumFacing.values()) {
            var1.notifyNeighborsOfStateChange(var2.offset(var6), this);
         }
      }

   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if(!var1.isRemote) {
         this.updateSurroundingRedstone(var1, var2, var3);

         for(Object var5 : EnumFacing$Plane.VERTICAL) {
            var1.notifyNeighborsOfStateChange(var2.offset((EnumFacing)var5), this);
         }

         for(Object var10 : EnumFacing$Plane.HORIZONTAL) {
            EnumFacing var6 = (EnumFacing)var10;
            this.notifyWireNeighborsOfStateChange(var1, var2.offset(var6));
         }

         for(Object var11 : EnumFacing$Plane.HORIZONTAL) {
            EnumFacing var12 = (EnumFacing)var11;
            BlockPos var7 = var2.offset(var12);
            if(var1.getBlockState(var7).getBlock().isNormalCube()) {
               this.notifyWireNeighborsOfStateChange(var1, var7.up());
            } else {
               this.notifyWireNeighborsOfStateChange(var1, var7.down());
            }
         }
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      super.breakBlock(var1, var2, var3);
      if(!var1.isRemote) {
         for(EnumFacing var7 : EnumFacing.values()) {
            var1.notifyNeighborsOfStateChange(var2.offset(var7), this);
         }

         this.updateSurroundingRedstone(var1, var2, var3);

         for(Object var10 : EnumFacing$Plane.HORIZONTAL) {
            this.notifyWireNeighborsOfStateChange(var1, var2.offset((EnumFacing)var10));
         }

         for(Object var11 : EnumFacing$Plane.HORIZONTAL) {
            BlockPos var12 = var2.offset((EnumFacing)var11);
            if(var1.getBlockState(var12).getBlock().isNormalCube()) {
               this.notifyWireNeighborsOfStateChange(var1, var12.up());
            } else {
               this.notifyWireNeighborsOfStateChange(var1, var12.down());
            }
         }
      }

   }

   private int getMaxCurrentStrength(World var1, BlockPos var2, int var3) {
      if(var1.getBlockState(var2).getBlock() != this) {
         return var3;
      } else {
         int var4 = ((Integer)var1.getBlockState(var2).getValue(P)).intValue();
         return var4 > var3?var4:var3;
      }
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!var1.isRemote) {
         if(this.canPlaceBlockAt(var1, var2)) {
            this.updateSurroundingRedstone(var1, var2, var3);
         } else {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         }
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.redstone;
   }

   public int getStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !this.canProvidePower?0:this.getWeakPower(var1, var2, var3, var4);
   }

   public int getWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if(!this.canProvidePower) {
         return 0;
      } else {
         int var5 = ((Integer)var3.getValue(P)).intValue();
         return 0;
      }
   }

   private boolean func_176339_d(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      BlockPos var4 = var2.offset(var3);
      IBlockState var5 = var1.getBlockState(var4);
      Block var6 = var5.getBlock();
      boolean var7 = var6.isNormalCube();
      boolean var8 = var1.getBlockState(var2.up()).getBlock().isNormalCube();
      return canConnectUpwardsTo(var1, var4.up()) || canConnectTo(var5, var3) || var6 == Blocks.powered_repeater && var5.getValue(BlockRedstoneDiode.FACING) == var3 || canConnectUpwardsTo(var1, var4.down());
   }

   protected static boolean canConnectUpwardsTo(IBlockAccess var0, BlockPos var1) {
      return canConnectUpwardsTo(var0.getBlockState(var1));
   }

   protected static boolean canConnectUpwardsTo(IBlockState var0) {
      return canConnectTo(var0, (EnumFacing)null);
   }

   protected static boolean canConnectTo(IBlockState var0, EnumFacing var1) {
      Block var2 = var0.getBlock();
      if(var2 == Blocks.redstone_wire) {
         return true;
      } else if(!Blocks.unpowered_repeater.isAssociated(var2)) {
         return var2.canProvidePower();
      } else {
         EnumFacing var3 = (EnumFacing)var0.getValue(BlockRedstoneRepeater.FACING);
         return var3 == var1 || var3.getOpposite() == var1;
      }
   }

   public boolean canProvidePower() {
      return this.canProvidePower;
   }

   private int colorMultiplier(int var1) {
      float var2 = (float)var1 / 15.0F;
      float var3 = var2 * 0.6F + 0.4F;
      var3 = 0.3F;
      float var4 = var2 * var2 * 0.7F - 0.5F;
      float var5 = var2 * var2 * 0.6F - 0.7F;
      if(var4 < 0.0F) {
         var4 = 0.0F;
      }

      if(var5 < 0.0F) {
         var5 = 0.0F;
      }

      int var6 = MathHelper.clamp_int((int)(var3 * 255.0F), 0, 255);
      int var7 = MathHelper.clamp_int((int)(var4 * 255.0F), 0, 255);
      int var8 = MathHelper.clamp_int((int)(var5 * 255.0F), 0, 255);
      return -16777216 | var6 << 16 | var7 << 8 | var8;
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = ((Integer)var3.getValue(P)).intValue();
      double var6 = (double)var2.getX() + 0.5D + ((double)var4.nextFloat() - 0.5D) * 0.2D;
      double var8 = (double)((float)var2.getY() + 0.0625F);
      double var10 = (double)var2.getZ() + 0.5D + ((double)var4.nextFloat() - 0.5D) * 0.2D;
      float var12 = (float)var5 / 15.0F;
      float var13 = var12 * 0.6F + 0.4F;
      float var14 = Math.max(0.0F, var12 * var12 * 0.7F - 0.5F);
      float var15 = Math.max(0.0F, var12 * var12 * 0.6F - 0.7F);
      var1.spawnParticle(EnumParticleTypes.REDSTONE, var6, var8, var10, (double)var13, (double)var14, (double)var15, new int[0]);
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.redstone;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(P, Integer.valueOf(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Integer)var1.getValue(P)).intValue();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, P});
   }
}
