package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor$1;
import net.minecraft.block.BlockTrapDoor$DoorHalf;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrapDoor extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing$Plane.HORIZONTAL);
   public static final PropertyBool OPEN = PropertyBool.create("open");
   public static final PropertyEnum HALF = PropertyEnum.create("half", BlockTrapDoor$DoorHalf.class);

   protected BlockTrapDoor(Material var1) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, Boolean.FALSE).withProperty(HALF, BlockTrapDoor$DoorHalf.BOTTOM));
      float var2 = 0.5F;
      float var3 = 1.0F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return !((Boolean)var1.getBlockState(var2).getValue(OPEN)).booleanValue();
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getSelectedBoundingBox(var1, var2);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getCollisionBoundingBox(var1, var2, var3);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.setBounds(var1.getBlockState(var2));
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.1875F;
      this.setBlockBounds(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
   }

   public void setBounds(IBlockState var1) {
      if(var1.getBlock() == this) {
         boolean var2 = var1.getValue(HALF) == BlockTrapDoor$DoorHalf.TOP;
         Boolean var3 = (Boolean)var1.getValue(OPEN);
         EnumFacing var4 = (EnumFacing)var1.getValue(FACING);
         float var5 = 0.1875F;
         this.setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
         if(var3.booleanValue()) {
            if(var4 == EnumFacing.NORTH) {
               this.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
            }

            if(var4 == EnumFacing.SOUTH) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
            }

            if(var4 == EnumFacing.WEST) {
               this.setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if(var4 == EnumFacing.EAST) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
            }
         }
      }

   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(this.blockMaterial != Material.iron) {
         var3 = var3.cycleProperty(OPEN);
         var1.setBlockState(var2, var3, 2);
         var1.playAuxSFXAtEntity(var4, ((Boolean)var3.getValue(OPEN)).booleanValue()?1003:1006, var2, 0);
      }

      return true;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!var1.isRemote) {
         BlockPos var5 = var2.offset(((EnumFacing)var3.getValue(FACING)).getOpposite());
         if(!isValidSupportBlock(var1.getBlockState(var5).getBlock())) {
            var1.setBlockToAir(var2);
            this.dropBlockAsItem(var1, var2, var3, 0);
         } else {
            boolean var6 = var1.isBlockPowered(var2);
            if(var4.canProvidePower()) {
               boolean var7 = ((Boolean)var3.getValue(OPEN)).booleanValue();
               if(var7 != var6) {
                  var1.setBlockState(var2, var3.withProperty(OPEN, Boolean.valueOf(var6)), 2);
                  var1.playAuxSFXAtEntity((EntityPlayer)null, 1003, var2, 0);
               }
            }
         }
      }

   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.collisionRayTrace(var1, var2, var3, var4);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.getDefaultState();
      if(var3.getAxis().isHorizontal()) {
         var9 = var9.withProperty(FACING, var3).withProperty(OPEN, Boolean.FALSE);
         var9 = var9.withProperty(HALF, var5 > 0.5F?BlockTrapDoor$DoorHalf.TOP:BlockTrapDoor$DoorHalf.BOTTOM);
      }

      return var9;
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return !var3.getAxis().isVertical() && isValidSupportBlock(var1.getBlockState(var2.offset(var3.getOpposite())).getBlock());
   }

   protected static EnumFacing getFacing(int var0) {
      switch(var0 & 3) {
      case 0:
         return EnumFacing.NORTH;
      case 1:
         return EnumFacing.SOUTH;
      case 2:
         return EnumFacing.WEST;
      case 3:
      default:
         return EnumFacing.EAST;
      }
   }

   protected static int getMetaForFacing(EnumFacing var0) {
      switch(BlockTrapDoor$1.$SwitchMap$net$minecraft$util$EnumFacing[var0.ordinal()]) {
      case 1:
         return 0;
      case 2:
         return 1;
      case 3:
         return 2;
      case 4:
      default:
         return 3;
      }
   }

   private static boolean isValidSupportBlock(Block var0) {
      return var0.blockMaterial.isOpaque() && var0.isFullCube() || var0 == Blocks.glowstone || var0 instanceof BlockSlab || var0 instanceof BlockStairs;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, getFacing(var1)).withProperty(OPEN, Boolean.valueOf((var1 & 4) != 0)).withProperty(HALF, (var1 & 8) == 0?BlockTrapDoor$DoorHalf.BOTTOM:BlockTrapDoor$DoorHalf.TOP);
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | getMetaForFacing((EnumFacing)var1.getValue(FACING));
      if(((Boolean)var1.getValue(OPEN)).booleanValue()) {
         var2 |= 4;
      }

      if(var1.getValue(HALF) == BlockTrapDoor$DoorHalf.TOP) {
         var2 |= 8;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, OPEN, HALF});
   }
}
