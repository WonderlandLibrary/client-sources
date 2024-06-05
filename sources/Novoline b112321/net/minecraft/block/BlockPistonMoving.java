package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonExtension$EnumPistonType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonMoving extends BlockContainer {
   public static final PropertyDirection FACING = BlockPistonExtension.FACING;
   public static final PropertyEnum TYPE = BlockPistonExtension.TYPE;

   public BlockPistonMoving() {
      super(Material.piston);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, BlockPistonExtension$EnumPistonType.DEFAULT));
      this.setHardness(-1.0F);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return null;
   }

   public static TileEntity newTileEntity(IBlockState var0, EnumFacing var1, boolean var2, boolean var3) {
      return new TileEntityPiston(var0, var1, var2, var3);
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      if(var4 instanceof TileEntityPiston) {
         ((TileEntityPiston)var4).clearPistonTileEntity();
      } else {
         super.breakBlock(var1, var2, var3);
      }

   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return false;
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return false;
   }

   public void onBlockDestroyedByPlayer(World var1, BlockPos var2, IBlockState var3) {
      BlockPos var4 = var2.offset(((EnumFacing)var3.getValue(FACING)).getOpposite());
      IBlockState var5 = var1.getBlockState(var4);
      if(var5.getBlock() instanceof BlockPistonBase && ((Boolean)var5.getValue(BlockPistonBase.EXTENDED)).booleanValue()) {
         var1.setBlockToAir(var4);
      }

   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(!var1.isRemote && var1.getTileEntity(var2) == null) {
         var1.setBlockToAir(var2);
         return true;
      } else {
         return false;
      }
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if(!var1.isRemote) {
         TileEntityPiston var6 = this.getTileEntity(var1, var2);
         IBlockState var7 = var6.getPistonState();
         var7.getBlock().dropBlockAsItem(var1, var2, var7, 0);
      }

   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      return null;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!var1.isRemote) {
         var1.getTileEntity(var2);
      }

   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      TileEntityPiston var4 = this.getTileEntity(var1, var2);
      return null;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      TileEntityPiston var3 = this.getTileEntity(var1, var2);
      IBlockState var4 = var3.getPistonState();
      Block var5 = var4.getBlock();
      if(var5 != this && var5.getMaterial() != Material.air) {
         float var6 = var3.getProgress(0.0F);
         if(var3.isExtending()) {
            var6 = 1.0F - var6;
         }

         var5.setBlockBoundsBasedOnState(var1, var2);
         if(var5 == Blocks.piston || var5 == Blocks.sticky_piston) {
            var6 = 0.0F;
         }

         EnumFacing var7 = var3.getFacing();
         this.minX = var5.getBlockBoundsMinX() - (double)((float)var7.getFrontOffsetX() * var6);
         this.minY = var5.getBlockBoundsMinY() - (double)((float)var7.getFrontOffsetY() * var6);
         this.minZ = var5.getBlockBoundsMinZ() - (double)((float)var7.getFrontOffsetZ() * var6);
         this.maxX = var5.getBlockBoundsMaxX() - (double)((float)var7.getFrontOffsetX() * var6);
         this.maxY = var5.getBlockBoundsMaxY() - (double)((float)var7.getFrontOffsetY() * var6);
         this.maxZ = var5.getBlockBoundsMaxZ() - (double)((float)var7.getFrontOffsetZ() * var6);
      }
   }

   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3, float var4, EnumFacing var5) {
      if(var3.getBlock() != this && var3.getBlock().getMaterial() != Material.air) {
         AxisAlignedBB var6 = var3.getBlock().getCollisionBoundingBox(var1, var2, var3);
         return null;
      } else {
         return null;
      }
   }

   private TileEntityPiston getTileEntity(IBlockAccess var1, BlockPos var2) {
      TileEntity var3 = var1.getTileEntity(var2);
      return var3 instanceof TileEntityPiston?(TileEntityPiston)var3:null;
   }

   public Item getItem(World var1, BlockPos var2) {
      return null;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, BlockPistonExtension.getFacing(var1)).withProperty(TYPE, (var1 & 8) > 0?BlockPistonExtension$EnumPistonType.STICKY:BlockPistonExtension$EnumPistonType.DEFAULT);
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | ((EnumFacing)var1.getValue(FACING)).getIndex();
      if(var1.getValue(TYPE) == BlockPistonExtension$EnumPistonType.STICKY) {
         var2 |= 8;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, TYPE});
   }
}
