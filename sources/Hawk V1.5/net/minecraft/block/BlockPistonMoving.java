package net.minecraft.block;

import java.util.Random;
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
   private static final String __OBFID = "CL_00000368";
   public static final PropertyEnum field_176425_b;
   public static final PropertyDirection field_176426_a;

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      return null;
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.isRemote) {
         TileEntityPiston var6 = this.func_176422_e(var1, var2);
         if (var6 != null) {
            IBlockState var7 = var6.func_174927_b();
            var7.getBlock().dropBlockAsItem(var1, var2, var7, 0);
         }
      }

   }

   public Item getItem(World var1, BlockPos var2) {
      return null;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var1.isRemote && var1.getTileEntity(var2) == null) {
         var1.setBlockToAir(var2);
         return true;
      } else {
         return false;
      }
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return false;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      if (var4 instanceof TileEntityPiston) {
         ((TileEntityPiston)var4).clearPistonTileEntity();
      } else {
         super.breakBlock(var1, var2, var3);
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176426_a, BlockPistonExtension.func_176322_b(var1)).withProperty(field_176425_b, (var1 & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
   }

   public void onBlockDestroyedByPlayer(World var1, BlockPos var2, IBlockState var3) {
      BlockPos var4 = var2.offset(((EnumFacing)var3.getValue(field_176426_a)).getOpposite());
      IBlockState var5 = var1.getBlockState(var4);
      if (var5.getBlock() instanceof BlockPistonBase && (Boolean)var5.getValue(BlockPistonBase.EXTENDED)) {
         var1.setBlockToAir(var4);
      }

   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      TileEntityPiston var3 = this.func_176422_e(var1, var2);
      if (var3 != null) {
         IBlockState var4 = var3.func_174927_b();
         Block var5 = var4.getBlock();
         if (var5 == this || var5.getMaterial() == Material.air) {
            return;
         }

         float var6 = var3.func_145860_a(0.0F);
         if (var3.isExtending()) {
            var6 = 1.0F - var6;
         }

         var5.setBlockBoundsBasedOnState(var1, var2);
         if (var5 == Blocks.piston || var5 == Blocks.sticky_piston) {
            var6 = 0.0F;
         }

         EnumFacing var7 = var3.func_174930_e();
         this.minX = var5.getBlockBoundsMinX() - (double)((float)var7.getFrontOffsetX() * var6);
         this.minY = var5.getBlockBoundsMinY() - (double)((float)var7.getFrontOffsetY() * var6);
         this.minZ = var5.getBlockBoundsMinZ() - (double)((float)var7.getFrontOffsetZ() * var6);
         this.maxX = var5.getBlockBoundsMaxX() - (double)((float)var7.getFrontOffsetX() * var6);
         this.maxY = var5.getBlockBoundsMaxY() - (double)((float)var7.getFrontOffsetY() * var6);
         this.maxZ = var5.getBlockBoundsMaxZ() - (double)((float)var7.getFrontOffsetZ() * var6);
      }

   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(field_176426_a)).getIndex();
      if (var1.getValue(field_176425_b) == BlockPistonExtension.EnumPistonType.STICKY) {
         var3 |= 8;
      }

      return var3;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return null;
   }

   static {
      field_176426_a = BlockPistonExtension.field_176326_a;
      field_176425_b = BlockPistonExtension.field_176325_b;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176426_a, field_176425_b});
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public BlockPistonMoving() {
      super(Material.piston);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176426_a, EnumFacing.NORTH).withProperty(field_176425_b, BlockPistonExtension.EnumPistonType.DEFAULT));
      this.setHardness(-1.0F);
   }

   public static TileEntity func_176423_a(IBlockState var0, EnumFacing var1, boolean var2, boolean var3) {
      return new TileEntityPiston(var0, var1, var2, var3);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      TileEntityPiston var4 = this.func_176422_e(var1, var2);
      if (var4 == null) {
         return null;
      } else {
         float var5 = var4.func_145860_a(0.0F);
         if (var4.isExtending()) {
            var5 = 1.0F - var5;
         }

         return this.func_176424_a(var1, var2, var4.func_174927_b(), var5, var4.func_174930_e());
      }
   }

   private TileEntityPiston func_176422_e(IBlockAccess var1, BlockPos var2) {
      TileEntity var3 = var1.getTileEntity(var2);
      return var3 instanceof TileEntityPiston ? (TileEntityPiston)var3 : null;
   }

   public boolean isFullCube() {
      return false;
   }

   public AxisAlignedBB func_176424_a(World var1, BlockPos var2, IBlockState var3, float var4, EnumFacing var5) {
      if (var3.getBlock() != this && var3.getBlock().getMaterial() != Material.air) {
         AxisAlignedBB var6 = var3.getBlock().getCollisionBoundingBox(var1, var2, var3);
         if (var6 == null) {
            return null;
         } else {
            double var7 = var6.minX;
            double var9 = var6.minY;
            double var11 = var6.minZ;
            double var13 = var6.maxX;
            double var15 = var6.maxY;
            double var17 = var6.maxZ;
            if (var5.getFrontOffsetX() < 0) {
               var7 -= (double)((float)var5.getFrontOffsetX() * var4);
            } else {
               var13 -= (double)((float)var5.getFrontOffsetX() * var4);
            }

            if (var5.getFrontOffsetY() < 0) {
               var9 -= (double)((float)var5.getFrontOffsetY() * var4);
            } else {
               var15 -= (double)((float)var5.getFrontOffsetY() * var4);
            }

            if (var5.getFrontOffsetZ() < 0) {
               var11 -= (double)((float)var5.getFrontOffsetZ() * var4);
            } else {
               var17 -= (double)((float)var5.getFrontOffsetZ() * var4);
            }

            return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
         }
      } else {
         return null;
      }
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.isRemote) {
         var1.getTileEntity(var2);
      }

   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return false;
   }
}
