package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockButton extends Block {
   public static final PropertyDirection FACING_PROP = PropertyDirection.create("facing");
   private final boolean wooden;
   public static final PropertyBool POWERED_PROP = PropertyBool.create("powered");
   private static final String __OBFID = "CL_00000209";

   public int getMetaFromState(IBlockState var1) {
      int var2;
      switch((EnumFacing)var1.getValue(FACING_PROP)) {
      case EAST:
         var2 = 1;
         break;
      case WEST:
         var2 = 2;
         break;
      case SOUTH:
         var2 = 3;
         break;
      case NORTH:
         var2 = 4;
         break;
      case UP:
      default:
         var2 = 5;
         break;
      case DOWN:
         var2 = 0;
      }

      if ((Boolean)var1.getValue(POWERED_PROP)) {
         var2 |= 8;
      }

      return var2;
   }

   public int isProvidingWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return (Boolean)var3.getValue(POWERED_PROP) ? 15 : 0;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if ((Boolean)var3.getValue(POWERED_PROP)) {
         this.func_176582_b(var1, var2, (EnumFacing)var3.getValue(FACING_PROP));
      }

      super.breakBlock(var1, var2, var3);
   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing var2;
      switch(var1 & 7) {
      case 0:
         var2 = EnumFacing.DOWN;
         break;
      case 1:
         var2 = EnumFacing.EAST;
         break;
      case 2:
         var2 = EnumFacing.WEST;
         break;
      case 3:
         var2 = EnumFacing.SOUTH;
         break;
      case 4:
         var2 = EnumFacing.NORTH;
         break;
      case 5:
      default:
         var2 = EnumFacing.UP;
      }

      return this.getDefaultState().withProperty(FACING_PROP, var2).withProperty(POWERED_PROP, (var1 & 8) > 0);
   }

   private void func_180681_d(IBlockState var1) {
      EnumFacing var2 = (EnumFacing)var1.getValue(FACING_PROP);
      boolean var3 = (Boolean)var1.getValue(POWERED_PROP);
      float var4 = 0.25F;
      float var5 = 0.375F;
      float var6 = (float)(var3 ? 1 : 2) / 16.0F;
      float var7 = 0.125F;
      float var8 = 0.1875F;
      switch(var2) {
      case EAST:
         this.setBlockBounds(0.0F, 0.375F, 0.3125F, var6, 0.625F, 0.6875F);
         break;
      case WEST:
         this.setBlockBounds(1.0F - var6, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
         break;
      case SOUTH:
         this.setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, var6);
         break;
      case NORTH:
         this.setBlockBounds(0.3125F, 0.375F, 1.0F - var6, 0.6875F, 0.625F, 1.0F);
         break;
      case UP:
         this.setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + var6, 0.625F);
         break;
      case DOWN:
         this.setBlockBounds(0.3125F, 1.0F - var6, 0.375F, 0.6875F, 1.0F, 0.625F);
      }

   }

   private void func_176582_b(World var1, BlockPos var2, EnumFacing var3) {
      var1.notifyNeighborsOfStateChange(var2, this);
      var1.notifyNeighborsOfStateChange(var2.offset(var3.getOpposite()), this);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (this.func_176583_e(var1, var2, var3)) {
         EnumFacing var5 = (EnumFacing)var3.getValue(FACING_PROP);
         if (!var1.getBlockState(var2.offset(var5.getOpposite())).getBlock().isNormalCube()) {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         }
      }

   }

   private void func_180680_f(World var1, BlockPos var2, IBlockState var3) {
      this.func_180681_d(var3);
      List var4 = var1.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB((double)var2.getX() + this.minX, (double)var2.getY() + this.minY, (double)var2.getZ() + this.minZ, (double)var2.getX() + this.maxX, (double)var2.getY() + this.maxY, (double)var2.getZ() + this.maxZ));
      boolean var5 = !var4.isEmpty();
      boolean var6 = (Boolean)var3.getValue(POWERED_PROP);
      if (var5 && !var6) {
         var1.setBlockState(var2, var3.withProperty(POWERED_PROP, true));
         this.func_176582_b(var1, var2, (EnumFacing)var3.getValue(FACING_PROP));
         var1.markBlockRangeForRenderUpdate(var2, var2);
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
      }

      if (!var5 && var6) {
         var1.setBlockState(var2, var3.withProperty(POWERED_PROP, false));
         this.func_176582_b(var1, var2, (EnumFacing)var3.getValue(FACING_PROP));
         var1.markBlockRangeForRenderUpdate(var2, var2);
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
      }

      if (var5) {
         var1.scheduleUpdate(var2, this, this.tickRate(var1));
      }

   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.1875F;
      float var2 = 0.125F;
      float var3 = 0.125F;
      this.setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      EnumFacing[] var3 = EnumFacing.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnumFacing var6 = var3[var5];
         if (var1.getBlockState(var2.offset(var6)).getBlock().isNormalCube()) {
            return true;
         }
      }

      return false;
   }

   public boolean canProvidePower() {
      return true;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if ((Boolean)var3.getValue(POWERED_PROP)) {
         return true;
      } else {
         var1.setBlockState(var2, var3.withProperty(POWERED_PROP, true), 3);
         var1.markBlockRangeForRenderUpdate(var2, var2);
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
         this.func_176582_b(var1, var2, (EnumFacing)var3.getValue(FACING_PROP));
         var1.scheduleUpdate(var2, this, this.tickRate(var1));
         return true;
      }
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING_PROP, POWERED_PROP});
   }

   public int tickRate(World var1) {
      return this.wooden ? 30 : 20;
   }

   public int isProvidingStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !(Boolean)var3.getValue(POWERED_PROP) ? 0 : (var3.getValue(FACING_PROP) == var4 ? 15 : 0);
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (!var1.isRemote && this.wooden && !(Boolean)var3.getValue(POWERED_PROP)) {
         this.func_180680_f(var1, var2, var3);
      }

   }

   public boolean isFullCube() {
      return false;
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return var1.getBlockState(var2.offset(var3.getOpposite())).getBlock().isNormalCube() ? this.getDefaultState().withProperty(FACING_PROP, var3).withProperty(POWERED_PROP, false) : this.getDefaultState().withProperty(FACING_PROP, EnumFacing.DOWN).withProperty(POWERED_PROP, false);
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return var1.getBlockState(var2.offset(var3.getOpposite())).getBlock().isNormalCube();
   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.func_180681_d(var1.getBlockState(var2));
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.isRemote && (Boolean)var3.getValue(POWERED_PROP)) {
         if (this.wooden) {
            this.func_180680_f(var1, var2, var3);
         } else {
            var1.setBlockState(var2, var3.withProperty(POWERED_PROP, false));
            this.func_176582_b(var1, var2, (EnumFacing)var3.getValue(FACING_PROP));
            var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
            var1.markBlockRangeForRenderUpdate(var2, var2);
         }
      }

   }

   protected BlockButton(boolean var1) {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING_PROP, EnumFacing.NORTH).withProperty(POWERED_PROP, false));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.wooden = var1;
   }

   private boolean func_176583_e(World var1, BlockPos var2, IBlockState var3) {
      if (!this.canPlaceBlockAt(var1, var2)) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
         return false;
      } else {
         return true;
      }
   }

   public boolean isOpaqueCube() {
      return false;
   }

   static final class SwitchEnumFacing {
      private static final String __OBFID = "CL_00002131";
      static final int[] field_180420_a = new int[EnumFacing.values().length];

      static {
         try {
            field_180420_a[EnumFacing.EAST.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_180420_a[EnumFacing.WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_180420_a[EnumFacing.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_180420_a[EnumFacing.NORTH.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180420_a[EnumFacing.UP.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180420_a[EnumFacing.DOWN.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
