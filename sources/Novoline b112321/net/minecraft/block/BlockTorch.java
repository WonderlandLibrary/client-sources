package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockTorch$1;
import net.minecraft.block.BlockTorch$2;
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
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockTorch extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)(new BlockTorch$1()));

   protected BlockTorch() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
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

   private boolean canPlaceOn(World var1, BlockPos var2) {
      if(World.doesBlockHaveSolidTopSurface(var1, var2)) {
         return true;
      } else {
         Block var3 = var1.getBlockState(var2).getBlock();
         return var3 instanceof BlockFence || var3 == Blocks.glass || var3 == Blocks.cobblestone_wall || var3 == Blocks.stained_glass;
      }
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      for(EnumFacing var4 : FACING.getAllowedValues()) {
         if(this.canPlaceAt(var1, var2, var4)) {
            return true;
         }
      }

      return false;
   }

   private boolean canPlaceAt(World var1, BlockPos var2, EnumFacing var3) {
      BlockPos var4 = var2.offset(var3.getOpposite());
      boolean var5 = var3.getAxis().isHorizontal();
      return var1.isBlockNormalCube(var4, true) || var3.equals(EnumFacing.UP) && this.canPlaceOn(var1, var4);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if(this.canPlaceAt(var1, var2, var3)) {
         return this.getDefaultState().withProperty(FACING, var3);
      } else {
         for(Object var10 : EnumFacing$Plane.HORIZONTAL) {
            if(var1.isBlockNormalCube(var2.offset(((EnumFacing)var10).getOpposite()), true)) {
               return this.getDefaultState().withProperty(FACING, (EnumFacing)var10);
            }
         }

         return this.getDefaultState();
      }
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.checkForDrop(var1, var2, var3);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.onNeighborChangeInternal(var1, var2, var3);
   }

   protected boolean onNeighborChangeInternal(World var1, BlockPos var2, IBlockState var3) {
      if(!this.checkForDrop(var1, var2, var3)) {
         return true;
      } else {
         EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
         EnumFacing$Axis var5 = var4.getAxis();
         EnumFacing var6 = var4.getOpposite();
         boolean var7 = false;
         if(var5.isHorizontal() && !var1.isBlockNormalCube(var2.offset(var6), true)) {
            var7 = true;
         } else if(var5.isVertical() && !this.canPlaceOn(var1, var2.offset(var6))) {
            var7 = true;
         }

         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
         return true;
      }
   }

   protected boolean checkForDrop(World var1, BlockPos var2, IBlockState var3) {
      if(var3.getBlock() == this && this.canPlaceAt(var1, var2, (EnumFacing)var3.getValue(FACING))) {
         return true;
      } else {
         if(var1.getBlockState(var2).getBlock() == this) {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         }

         return false;
      }
   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      EnumFacing var5 = (EnumFacing)var1.getBlockState(var2).getValue(FACING);
      float var6 = 0.15F;
      if(var5 == EnumFacing.EAST) {
         this.setBlockBounds(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
      } else if(var5 == EnumFacing.WEST) {
         this.setBlockBounds(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
      } else if(var5 == EnumFacing.SOUTH) {
         this.setBlockBounds(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
      } else if(var5 == EnumFacing.NORTH) {
         this.setBlockBounds(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
      } else {
         var6 = 0.1F;
         this.setBlockBounds(0.5F - var6, 0.0F, 0.5F - var6, 0.5F + var6, 0.6F, 0.5F + var6);
      }

      return super.collisionRayTrace(var1, var2, var3, var4);
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
      double var6 = (double)var2.getX() + 0.5D;
      double var8 = (double)var2.getY() + 0.7D;
      double var10 = (double)var2.getZ() + 0.5D;
      double var12 = 0.22D;
      double var14 = 0.27D;
      if(var5.getAxis().isHorizontal()) {
         EnumFacing var16 = var5.getOpposite();
         var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + 0.27D * (double)var16.getFrontOffsetX(), var8 + 0.22D, var10 + 0.27D * (double)var16.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
         var1.spawnParticle(EnumParticleTypes.FLAME, var6 + 0.27D * (double)var16.getFrontOffsetX(), var8 + 0.22D, var10 + 0.27D * (double)var16.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
      } else {
         var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6, var8, var10, 0.0D, 0.0D, 0.0D, new int[0]);
         var1.spawnParticle(EnumParticleTypes.FLAME, var6, var8, var10, 0.0D, 0.0D, 0.0D, new int[0]);
      }

   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState();
      switch(var1) {
      case 1:
         var2 = var2.withProperty(FACING, EnumFacing.EAST);
         break;
      case 2:
         var2 = var2.withProperty(FACING, EnumFacing.WEST);
         break;
      case 3:
         var2 = var2.withProperty(FACING, EnumFacing.SOUTH);
         break;
      case 4:
         var2 = var2.withProperty(FACING, EnumFacing.NORTH);
         break;
      case 5:
      default:
         var2 = var2.withProperty(FACING, EnumFacing.UP);
      }

      return var2;
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      switch(BlockTorch$2.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)var1.getValue(FACING)).ordinal()]) {
      case 1:
         var2 = var2 | 1;
         break;
      case 2:
         var2 = var2 | 2;
         break;
      case 3:
         var2 = var2 | 3;
         break;
      case 4:
         var2 = var2 | 4;
         break;
      case 5:
      case 6:
      default:
         var2 = var2 | 5;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING});
   }
}
