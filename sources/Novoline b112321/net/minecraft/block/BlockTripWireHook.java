package net.minecraft.block;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook$1;
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
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripWireHook extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing$Plane.HORIZONTAL);
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");

   public BlockTripWireHook() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.FALSE).withProperty(ATTACHED, Boolean.FALSE).withProperty(SUSPENDED, Boolean.FALSE));
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setTickRandomly(true);
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.withProperty(SUSPENDED, Boolean.valueOf(!World.doesBlockHaveSolidTopSurface(var2, var3.down())));
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

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return var3.getAxis().isHorizontal() && var1.getBlockState(var2.offset(var3.getOpposite())).getBlock().isNormalCube();
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      for(Object var4 : EnumFacing$Plane.HORIZONTAL) {
         if(var1.getBlockState(var2.offset((EnumFacing)var4)).getBlock().isNormalCube()) {
            return true;
         }
      }

      return false;
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.getDefaultState().withProperty(POWERED, Boolean.FALSE).withProperty(ATTACHED, Boolean.FALSE).withProperty(SUSPENDED, Boolean.FALSE);
      if(var3.getAxis().isHorizontal()) {
         var9 = var9.withProperty(FACING, var3);
      }

      return var9;
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      this.func_176260_a(var1, var2, var3, false, false, -1, (IBlockState)null);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(var4 != this && this.checkForDrop(var1, var2, var3)) {
         EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
         if(!var1.getBlockState(var2.offset(var5.getOpposite())).getBlock().isNormalCube()) {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         }
      }

   }

   public void func_176260_a(World var1, BlockPos var2, IBlockState var3, boolean var4, boolean var5, int var6, IBlockState var7) {
      EnumFacing var8 = (EnumFacing)var3.getValue(FACING);
      boolean var9 = ((Boolean)var3.getValue(ATTACHED)).booleanValue();
      boolean var10 = ((Boolean)var3.getValue(POWERED)).booleanValue();
      boolean var11 = !World.doesBlockHaveSolidTopSurface(var1, var2.down());
      boolean var12 = true;
      boolean var13 = false;
      int var14 = 0;
      IBlockState[] var15 = new IBlockState[42];

      for(int var16 = 1; var16 < 42; ++var16) {
         BlockPos var17 = var2.offset(var8, var16);
         IBlockState var18 = var1.getBlockState(var17);
         if(var18.getBlock() == Blocks.tripwire_hook) {
            if(var18.getValue(FACING) == var8.getOpposite()) {
               var14 = var16;
            }
            break;
         }

         if(var18.getBlock() != Blocks.tripwire && var16 != var6) {
            var15[var16] = null;
            var12 = false;
         } else {
            if(var16 == var6) {
               var18 = (IBlockState)Objects.firstNonNull(var7, var18);
            }

            boolean var19 = !((Boolean)var18.getValue(BlockTripWire.DISARMED)).booleanValue();
            boolean var20 = ((Boolean)var18.getValue(BlockTripWire.POWERED)).booleanValue();
            boolean var21 = ((Boolean)var18.getValue(BlockTripWire.SUSPENDED)).booleanValue();
            var12 &= var21 == var11;
            var13 |= true;
            var15[var16] = var18;
            if(var16 == var6) {
               var1.scheduleUpdate(var2, this, this.tickRate(var1));
               var12 &= var19;
            }
         }
      }

      var12 = var12 & var14 > 1;
      var13 = var13 & var12;
      IBlockState var24 = this.getDefaultState().withProperty(ATTACHED, Boolean.valueOf(var12)).withProperty(POWERED, Boolean.valueOf(var13));
      BlockPos var25 = var2.offset(var8, var14);
      EnumFacing var27 = var8.getOpposite();
      var1.setBlockState(var25, var24.withProperty(FACING, var27), 3);
      this.func_176262_b(var1, var25, var27);
      this.a(var1, var25, var12, var13, var9, var10);
      this.a(var1, var2, var12, var13, var9, var10);
      var1.setBlockState(var2, var24.withProperty(FACING, var8), 3);
      this.func_176262_b(var1, var2, var8);
      if(var9 != var12) {
         for(int var26 = 1; var26 < var14; ++var26) {
            BlockPos var28 = var2.offset(var8, var26);
            IBlockState var29 = var15[var26];
            if(var1.getBlockState(var28).getBlock() != Blocks.air) {
               var1.setBlockState(var28, var29.withProperty(ATTACHED, Boolean.valueOf(var12)), 3);
            }
         }
      }

   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.func_176260_a(var1, var2, var3, false, true, -1, (IBlockState)null);
   }

   private void a(World var1, BlockPos var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.1D, (double)var2.getZ() + 0.5D, "random.click", 0.4F, 0.6F);
   }

   private void func_176262_b(World var1, BlockPos var2, EnumFacing var3) {
      var1.notifyNeighborsOfStateChange(var2, this);
      var1.notifyNeighborsOfStateChange(var2.offset(var3.getOpposite()), this);
   }

   private boolean checkForDrop(World var1, BlockPos var2, IBlockState var3) {
      if(!this.canPlaceBlockAt(var1, var2)) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
         return false;
      } else {
         return true;
      }
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.1875F;
      switch(BlockTripWireHook$1.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)var1.getBlockState(var2).getValue(FACING)).ordinal()]) {
      case 1:
         this.setBlockBounds(0.0F, 0.2F, 0.3125F, 0.375F, 0.8F, 0.6875F);
         break;
      case 2:
         this.setBlockBounds(0.625F, 0.2F, 0.3125F, 1.0F, 0.8F, 0.6875F);
         break;
      case 3:
         this.setBlockBounds(0.3125F, 0.2F, 0.0F, 0.6875F, 0.8F, 0.375F);
         break;
      case 4:
         this.setBlockBounds(0.3125F, 0.2F, 0.625F, 0.6875F, 0.8F, 1.0F);
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = ((Boolean)var3.getValue(ATTACHED)).booleanValue();
      boolean var5 = ((Boolean)var3.getValue(POWERED)).booleanValue();
      this.func_176260_a(var1, var2, var3, true, false, -1, (IBlockState)null);
      var1.notifyNeighborsOfStateChange(var2, this);
      var1.notifyNeighborsOfStateChange(var2.offset(((EnumFacing)var3.getValue(FACING)).getOpposite()), this);
      super.breakBlock(var1, var2, var3);
   }

   public int getWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return ((Boolean)var3.getValue(POWERED)).booleanValue()?15:0;
   }

   public int getStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !((Boolean)var3.getValue(POWERED)).booleanValue()?0:(var3.getValue(FACING) == var4?15:0);
   }

   public boolean canProvidePower() {
      return true;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(var1 & 3)).withProperty(POWERED, Boolean.valueOf((var1 & 8) > 0)).withProperty(ATTACHED, Boolean.valueOf((var1 & 4) > 0));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | ((EnumFacing)var1.getValue(FACING)).getHorizontalIndex();
      if(((Boolean)var1.getValue(POWERED)).booleanValue()) {
         var2 |= 8;
      }

      if(((Boolean)var1.getValue(ATTACHED)).booleanValue()) {
         var2 |= 4;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, POWERED, ATTACHED, SUSPENDED});
   }
}
