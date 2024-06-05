package net.minecraft.block;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripWireHook extends Block {
   public static final PropertyBool field_176263_b;
   public static final PropertyDirection field_176264_a;
   public static final PropertyBool field_176266_N;
   private static final String __OBFID = "CL_00000329";
   public static final PropertyBool field_176265_M;

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.withProperty(field_176266_N, !World.doesBlockHaveSolidTopSurface(var2, var3.offsetDown()));
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.func_176260_a(var1, var2, var3, false, true, -1, (IBlockState)null);
   }

   public boolean canProvidePower() {
      return true;
   }

   public BlockTripWireHook() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176264_a, EnumFacing.NORTH).withProperty(field_176263_b, false).withProperty(field_176265_M, false).withProperty(field_176266_N, false));
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setTickRandomly(true);
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(field_176264_a)).getHorizontalIndex();
      if ((Boolean)var1.getValue(field_176263_b)) {
         var3 |= 8;
      }

      if ((Boolean)var1.getValue(field_176265_M)) {
         var3 |= 4;
      }

      return var3;
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.getDefaultState().withProperty(field_176263_b, false).withProperty(field_176265_M, false).withProperty(field_176266_N, false);
      if (var3.getAxis().isHorizontal()) {
         var9 = var9.withProperty(field_176264_a, var3);
      }

      return var9;
   }

   static {
      field_176264_a = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
      field_176263_b = PropertyBool.create("powered");
      field_176265_M = PropertyBool.create("attached");
      field_176266_N = PropertyBool.create("suspended");
   }

   private void func_176262_b(World var1, BlockPos var2, EnumFacing var3) {
      var1.notifyNeighborsOfStateChange(var2, this);
      var1.notifyNeighborsOfStateChange(var2.offset(var3.getOpposite()), this);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      Iterator var3 = EnumFacing.Plane.HORIZONTAL.iterator();

      while(var3.hasNext()) {
         EnumFacing var4 = (EnumFacing)var3.next();
         if (var1.getBlockState(var2.offset(var4)).getBlock().isNormalCube()) {
            return true;
         }
      }

      return false;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public int isProvidingStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !(Boolean)var3.getValue(field_176263_b) ? 0 : (var3.getValue(field_176264_a) == var4 ? 15 : 0);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176264_a, EnumFacing.getHorizontal(var1 & 3)).withProperty(field_176263_b, (var1 & 8) > 0).withProperty(field_176265_M, (var1 & 4) > 0);
   }

   private boolean func_176261_e(World var1, BlockPos var2, IBlockState var3) {
      if (!this.canPlaceBlockAt(var1, var2)) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
         return false;
      } else {
         return true;
      }
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = (Boolean)var3.getValue(field_176265_M);
      boolean var5 = (Boolean)var3.getValue(field_176263_b);
      if (var4 || var5) {
         this.func_176260_a(var1, var2, var3, true, false, -1, (IBlockState)null);
      }

      if (var5) {
         var1.notifyNeighborsOfStateChange(var2, this);
         var1.notifyNeighborsOfStateChange(var2.offset(((EnumFacing)var3.getValue(field_176264_a)).getOpposite()), this);
      }

      super.breakBlock(var1, var2, var3);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.1875F;
      switch((EnumFacing)var1.getBlockState(var2).getValue(field_176264_a)) {
      case EAST:
         this.setBlockBounds(0.0F, 0.2F, 0.5F - var3, var3 * 2.0F, 0.8F, 0.5F + var3);
         break;
      case WEST:
         this.setBlockBounds(1.0F - var3 * 2.0F, 0.2F, 0.5F - var3, 1.0F, 0.8F, 0.5F + var3);
         break;
      case SOUTH:
         this.setBlockBounds(0.5F - var3, 0.2F, 0.0F, 0.5F + var3, 0.8F, var3 * 2.0F);
         break;
      case NORTH:
         this.setBlockBounds(0.5F - var3, 0.2F, 1.0F - var3 * 2.0F, 0.5F + var3, 0.8F, 1.0F);
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176264_a, field_176263_b, field_176265_M, field_176266_N});
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (var4 != this && this.func_176261_e(var1, var2, var3)) {
         EnumFacing var5 = (EnumFacing)var3.getValue(field_176264_a);
         if (!var1.getBlockState(var2.offset(var5.getOpposite())).getBlock().isNormalCube()) {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         }
      }

   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public int isProvidingWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return (Boolean)var3.getValue(field_176263_b) ? 15 : 0;
   }

   public boolean isFullCube() {
      return false;
   }

   private void func_180694_a(World var1, BlockPos var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      if (var4 && !var6) {
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.1D, (double)var2.getZ() + 0.5D, "random.click", 0.4F, 0.6F);
      } else if (!var4 && var6) {
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.1D, (double)var2.getZ() + 0.5D, "random.click", 0.4F, 0.5F);
      } else if (var3 && !var5) {
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.1D, (double)var2.getZ() + 0.5D, "random.click", 0.4F, 0.7F);
      } else if (!var3 && var5) {
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.1D, (double)var2.getZ() + 0.5D, "random.bowhit", 0.4F, 1.2F / (var1.rand.nextFloat() * 0.2F + 0.9F));
      }

   }

   public void func_176260_a(World var1, BlockPos var2, IBlockState var3, boolean var4, boolean var5, int var6, IBlockState var7) {
      EnumFacing var8 = (EnumFacing)var3.getValue(field_176264_a);
      boolean var9 = (Boolean)var3.getValue(field_176265_M);
      boolean var10 = (Boolean)var3.getValue(field_176263_b);
      boolean var11 = !World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown());
      boolean var12 = !var4;
      boolean var13 = false;
      int var14 = 0;
      IBlockState[] var15 = new IBlockState[42];

      BlockPos var16;
      for(int var17 = 1; var17 < 42; ++var17) {
         var16 = var2.offset(var8, var17);
         IBlockState var18 = var1.getBlockState(var16);
         if (var18.getBlock() == Blocks.tripwire_hook) {
            if (var18.getValue(field_176264_a) == var8.getOpposite()) {
               var14 = var17;
            }
            break;
         }

         if (var18.getBlock() != Blocks.tripwire && var17 != var6) {
            var15[var17] = null;
            var12 = false;
         } else {
            if (var17 == var6) {
               var18 = (IBlockState)Objects.firstNonNull(var7, var18);
            }

            boolean var19 = !(Boolean)var18.getValue(BlockTripWire.field_176295_N);
            boolean var20 = (Boolean)var18.getValue(BlockTripWire.field_176293_a);
            boolean var21 = (Boolean)var18.getValue(BlockTripWire.field_176290_b);
            var12 &= var21 == var11;
            var13 |= var19 && var20;
            var15[var17] = var18;
            if (var17 == var6) {
               var1.scheduleUpdate(var2, this, this.tickRate(var1));
               var12 &= var19;
            }
         }
      }

      var12 &= var14 > 1;
      var13 &= var12;
      IBlockState var22 = this.getDefaultState().withProperty(field_176265_M, var12).withProperty(field_176263_b, var13);
      if (var14 > 0) {
         var16 = var2.offset(var8, var14);
         EnumFacing var23 = var8.getOpposite();
         var1.setBlockState(var16, var22.withProperty(field_176264_a, var23), 3);
         this.func_176262_b(var1, var16, var23);
         this.func_180694_a(var1, var16, var12, var13, var9, var10);
      }

      this.func_180694_a(var1, var2, var12, var13, var9, var10);
      if (!var4) {
         var1.setBlockState(var2, var22.withProperty(field_176264_a, var8), 3);
         if (var5) {
            this.func_176262_b(var1, var2, var8);
         }
      }

      if (var9 != var12) {
         for(int var24 = 1; var24 < var14; ++var24) {
            BlockPos var25 = var2.offset(var8, var24);
            IBlockState var26 = var15[var24];
            if (var26 != null && var1.getBlockState(var25).getBlock() != Blocks.air) {
               var1.setBlockState(var25, var26.withProperty(field_176265_M, var12), 3);
            }
         }
      }

   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return var3.getAxis().isHorizontal() && var1.getBlockState(var2.offset(var3.getOpposite())).getBlock().isNormalCube();
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      this.func_176260_a(var1, var2, var3, false, false, -1, (IBlockState)null);
   }

   static final class SwitchEnumFacing {
      static final int[] field_177056_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002050";

      static {
         try {
            field_177056_a[EnumFacing.EAST.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_177056_a[EnumFacing.WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177056_a[EnumFacing.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177056_a[EnumFacing.NORTH.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
