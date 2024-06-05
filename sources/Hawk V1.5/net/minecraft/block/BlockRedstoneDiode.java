package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRedstoneDiode extends BlockDirectional {
   protected final boolean isRepeaterPowered;
   private static final String __OBFID = "CL_00000226";

   public boolean isFullCube() {
      return false;
   }

   public boolean func_176402_i(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = ((EnumFacing)var3.getValue(AGE)).getOpposite();
      BlockPos var5 = var2.offset(var4);
      return isRedstoneRepeaterBlockID(var1.getBlockState(var5).getBlock()) ? var1.getBlockState(var5).getValue(AGE) != var4 : false;
   }

   protected BlockRedstoneDiode(boolean var1) {
      super(Material.circuits);
      this.isRepeaterPowered = var1;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (this.func_176409_d(var1, var2)) {
         this.func_176398_g(var1, var2, var3);
      } else {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
         EnumFacing[] var5 = EnumFacing.values();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            EnumFacing var8 = var5[var7];
            var1.notifyNeighborsOfStateChange(var2.offset(var8), this);
         }
      }

   }

   protected int func_176407_c(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = (EnumFacing)var3.getValue(AGE);
      EnumFacing var5 = var4.rotateY();
      EnumFacing var6 = var4.rotateYCCW();
      return Math.max(this.func_176401_c(var1, var2.offset(var5), var5), this.func_176401_c(var1, var2.offset(var6), var6));
   }

   protected void func_176398_g(World var1, BlockPos var2, IBlockState var3) {
      if (!this.func_176405_b(var1, var2, var3)) {
         boolean var4 = this.func_176404_e(var1, var2, var3);
         if ((this.isRepeaterPowered && !var4 || !this.isRepeaterPowered && var4) && !var1.isBlockTickPending(var2, this)) {
            byte var5 = -1;
            if (this.func_176402_i(var1, var2, var3)) {
               var5 = -3;
            } else if (this.isRepeaterPowered) {
               var5 = -2;
            }

            var1.func_175654_a(var2, this, this.func_176403_d(var3), var5);
         }
      }

   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (this.func_176404_e(var1, var2, var3)) {
         var1.scheduleUpdate(var2, this, 1);
      }

   }

   public static boolean isRedstoneRepeaterBlockID(Block var0) {
      return Blocks.unpowered_repeater.func_149907_e(var0) || Blocks.unpowered_comparator.func_149907_e(var0);
   }

   protected abstract IBlockState func_180674_e(IBlockState var1);

   protected void func_176400_h(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = (EnumFacing)var3.getValue(AGE);
      BlockPos var5 = var2.offset(var4.getOpposite());
      var1.notifyBlockOfStateChange(var5, this);
      var1.notifyNeighborsOfStateExcept(var5, this, var4);
   }

   protected int func_176408_a(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      return 15;
   }

   protected int func_176399_m(IBlockState var1) {
      return this.func_176403_d(var1);
   }

   protected int func_176397_f(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = (EnumFacing)var3.getValue(AGE);
      BlockPos var5 = var2.offset(var4);
      int var6 = var1.getRedstonePower(var5, var4);
      if (var6 >= 15) {
         return var6;
      } else {
         IBlockState var7 = var1.getBlockState(var5);
         return Math.max(var6, var7.getBlock() == Blocks.redstone_wire ? (Integer)var7.getValue(BlockRedstoneWire.POWER) : 0);
      }
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) ? super.canPlaceBlockAt(var1, var2) : false;
   }

   public int isProvidingStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return this.isProvidingWeakPower(var1, var2, var3, var4);
   }

   public boolean func_176409_d(World var1, BlockPos var2) {
      return World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown());
   }

   protected abstract IBlockState func_180675_k(IBlockState var1);

   protected boolean func_176404_e(World var1, BlockPos var2, IBlockState var3) {
      return this.func_176397_f(var1, var2, var3) > 0;
   }

   public boolean isAssociatedBlock(Block var1) {
      return this.func_149907_e(var1);
   }

   public int isProvidingWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !this.func_176406_l(var3) ? 0 : (var3.getValue(AGE) == var4 ? this.func_176408_a(var1, var2, var3) : 0);
   }

   protected int func_176401_c(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      IBlockState var4 = var1.getBlockState(var2);
      Block var5 = var4.getBlock();
      return this.func_149908_a(var5) ? (var5 == Blocks.redstone_wire ? (Integer)var4.getValue(BlockRedstoneWire.POWER) : var1.getStrongPower(var2, var3)) : 0;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   protected boolean func_176406_l(IBlockState var1) {
      return this.isRepeaterPowered;
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3.getAxis() != EnumFacing.Axis.Y;
   }

   protected boolean func_149908_a(Block var1) {
      return var1.canProvidePower();
   }

   public boolean canProvidePower() {
      return true;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.func_176400_h(var1, var2, var3);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public void onBlockDestroyedByPlayer(World var1, BlockPos var2, IBlockState var3) {
      if (this.isRepeaterPowered) {
         EnumFacing[] var4 = EnumFacing.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            EnumFacing var7 = var4[var6];
            var1.notifyNeighborsOfStateChange(var2.offset(var7), this);
         }
      }

      super.onBlockDestroyedByPlayer(var1, var2, var3);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(AGE, var8.func_174811_aO().getOpposite());
   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public boolean func_149907_e(Block var1) {
      return var1 == this.func_180674_e(this.getDefaultState()).getBlock() || var1 == this.func_180675_k(this.getDefaultState()).getBlock();
   }

   public boolean func_176405_b(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      return false;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!this.func_176405_b(var1, var2, var3)) {
         boolean var5 = this.func_176404_e(var1, var2, var3);
         if (this.isRepeaterPowered && !var5) {
            var1.setBlockState(var2, this.func_180675_k(var3), 2);
         } else if (!this.isRepeaterPowered) {
            var1.setBlockState(var2, this.func_180674_e(var3), 2);
            if (!var5) {
               var1.func_175654_a(var2, this.func_180674_e(var3).getBlock(), this.func_176399_m(var3), -1);
            }
         }
      }

   }

   protected abstract int func_176403_d(IBlockState var1);
}
