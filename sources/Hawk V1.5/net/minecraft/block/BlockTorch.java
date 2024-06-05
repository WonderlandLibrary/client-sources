package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockTorch extends Block {
   private static final String __OBFID = "CL_00000325";
   public static final PropertyDirection FACING_PROP = PropertyDirection.create("facing", new Predicate() {
      private static final String __OBFID = "CL_00002054";

      public boolean apply(Object var1) {
         return this.func_176601_a((EnumFacing)var1);
      }

      public boolean func_176601_a(EnumFacing var1) {
         return var1 != EnumFacing.DOWN;
      }
   });

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.func_176593_f(var1, var2, var3);
   }

   protected boolean func_176592_e(World var1, BlockPos var2, IBlockState var3) {
      if (!this.func_176593_f(var1, var2, var3)) {
         return true;
      } else {
         EnumFacing var4 = (EnumFacing)var3.getValue(FACING_PROP);
         EnumFacing.Axis var5 = var4.getAxis();
         EnumFacing var6 = var4.getOpposite();
         boolean var7 = false;
         if (var5.isHorizontal() && !var1.func_175677_d(var2.offset(var6), true)) {
            var7 = true;
         } else if (var5.isVertical() && !this.func_176594_d(var1, var2.offset(var6))) {
            var7 = true;
         }

         if (var7) {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean func_176594_d(World var1, BlockPos var2) {
      if (World.doesBlockHaveSolidTopSurface(var1, var2)) {
         return true;
      } else {
         Block var3 = var1.getBlockState(var2).getBlock();
         return var3 instanceof BlockFence || var3 == Blocks.glass || var3 == Blocks.cobblestone_wall || var3 == Blocks.stained_glass;
      }
   }

   public boolean isFullCube() {
      return false;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.func_176592_e(var1, var2, var3);
   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      EnumFacing var5 = (EnumFacing)var1.getBlockState(var2).getValue(FACING_PROP);
      float var6 = 0.15F;
      if (var5 == EnumFacing.EAST) {
         this.setBlockBounds(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
      } else if (var5 == EnumFacing.WEST) {
         this.setBlockBounds(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
      } else if (var5 == EnumFacing.SOUTH) {
         this.setBlockBounds(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
      } else if (var5 == EnumFacing.NORTH) {
         this.setBlockBounds(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
      } else {
         var6 = 0.1F;
         this.setBlockBounds(0.5F - var6, 0.0F, 0.5F - var6, 0.5F + var6, 0.6F, 0.5F + var6);
      }

      return super.collisionRayTrace(var1, var2, var3, var4);
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3;
      switch((EnumFacing)var1.getValue(FACING_PROP)) {
      case EAST:
         var3 = var2 | 1;
         break;
      case WEST:
         var3 = var2 | 2;
         break;
      case SOUTH:
         var3 = var2 | 3;
         break;
      case NORTH:
         var3 = var2 | 4;
         break;
      case DOWN:
      case UP:
      default:
         var3 = var2 | 5;
      }

      return var3;
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      Iterator var3 = FACING_PROP.getAllowedValues().iterator();

      while(var3.hasNext()) {
         EnumFacing var4 = (EnumFacing)var3.next();
         if (this.func_176595_b(var1, var2, var4)) {
            return true;
         }
      }

      return false;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      EnumFacing var5 = (EnumFacing)var3.getValue(FACING_PROP);
      double var6 = (double)var2.getX() + 0.5D;
      double var8 = (double)var2.getY() + 0.7D;
      double var10 = (double)var2.getZ() + 0.5D;
      double var12 = 0.22D;
      double var14 = 0.27D;
      if (var5.getAxis().isHorizontal()) {
         EnumFacing var16 = var5.getOpposite();
         var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var14 * (double)var16.getFrontOffsetX(), var8 + var12, var10 + var14 * (double)var16.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
         var1.spawnParticle(EnumParticleTypes.FLAME, var6 + var14 * (double)var16.getFrontOffsetX(), var8 + var12, var10 + var14 * (double)var16.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
      } else {
         var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6, var8, var10, 0.0D, 0.0D, 0.0D);
         var1.spawnParticle(EnumParticleTypes.FLAME, var6, var8, var10, 0.0D, 0.0D, 0.0D);
      }

   }

   protected boolean func_176593_f(World var1, BlockPos var2, IBlockState var3) {
      if (var3.getBlock() == this && this.func_176595_b(var1, var2, (EnumFacing)var3.getValue(FACING_PROP))) {
         return true;
      } else {
         if (var1.getBlockState(var2).getBlock() == this) {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         }

         return false;
      }
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if (this.func_176595_b(var1, var2, var3)) {
         return this.getDefaultState().withProperty(FACING_PROP, var3);
      } else {
         Iterator var9 = EnumFacing.Plane.HORIZONTAL.iterator();

         while(var9.hasNext()) {
            EnumFacing var10 = (EnumFacing)var9.next();
            if (var1.func_175677_d(var2.offset(var10.getOpposite()), true)) {
               return this.getDefaultState().withProperty(FACING_PROP, var10);
            }
         }

         return this.getDefaultState();
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   protected BlockTorch() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING_PROP, EnumFacing.UP));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING_PROP});
   }

   private boolean func_176595_b(World var1, BlockPos var2, EnumFacing var3) {
      BlockPos var4 = var2.offset(var3.getOpposite());
      boolean var5 = var3.getAxis().isHorizontal();
      return var5 && var1.func_175677_d(var4, true) || var3.equals(EnumFacing.UP) && this.func_176594_d(var1, var4);
   }

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState();
      switch(var1) {
      case 1:
         var2 = var2.withProperty(FACING_PROP, EnumFacing.EAST);
         break;
      case 2:
         var2 = var2.withProperty(FACING_PROP, EnumFacing.WEST);
         break;
      case 3:
         var2 = var2.withProperty(FACING_PROP, EnumFacing.SOUTH);
         break;
      case 4:
         var2 = var2.withProperty(FACING_PROP, EnumFacing.NORTH);
         break;
      case 5:
      default:
         var2 = var2.withProperty(FACING_PROP, EnumFacing.UP);
      }

      return var2;
   }

   static final class SwitchEnumFacing {
      static final int[] field_176609_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002053";

      static {
         try {
            field_176609_a[EnumFacing.EAST.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_176609_a[EnumFacing.WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_176609_a[EnumFacing.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_176609_a[EnumFacing.NORTH.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_176609_a[EnumFacing.DOWN.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_176609_a[EnumFacing.UP.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
