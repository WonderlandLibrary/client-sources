package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor$EnumDoorHalf;
import net.minecraft.block.BlockDoor$EnumHingePosition;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoor extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing$Plane.HORIZONTAL);
   public static final PropertyBool OPEN = PropertyBool.create("open");
   public static final PropertyEnum HINGE = PropertyEnum.create("hinge", BlockDoor$EnumHingePosition.class);
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyEnum HALF = PropertyEnum.create("half", BlockDoor$EnumDoorHalf.class);

   protected BlockDoor(Material var1) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, Boolean.FALSE).withProperty(HINGE, BlockDoor$EnumHingePosition.LEFT).withProperty(POWERED, Boolean.FALSE).withProperty(HALF, BlockDoor$EnumDoorHalf.LOWER));
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal((this.getUnlocalizedName() + ".name").replaceAll("tile", "item"));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return isOpen(combineMetadata(var1, var2));
   }

   public boolean isFullCube() {
      return false;
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
      this.setBoundBasedOnMeta(combineMetadata(var1, var2));
   }

   private void setBoundBasedOnMeta(int var1) {
      float var2 = 0.1875F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
      EnumFacing var3 = getFacing(var1);
      boolean var4 = isOpen(var1);
      boolean var5 = isHingeLeft(var1);
      if(var3 == EnumFacing.EAST) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
      } else if(var3 == EnumFacing.SOUTH) {
         this.setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else if(var3 == EnumFacing.WEST) {
         this.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
      } else if(var3 == EnumFacing.NORTH) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
      }

   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(this.blockMaterial == Material.iron) {
         return true;
      } else {
         BlockPos var9 = var3.getValue(HALF) == BlockDoor$EnumDoorHalf.LOWER?var2:var2.down();
         IBlockState var10 = var2.equals(var9)?var3:var1.getBlockState(var9);
         if(var10.getBlock() != this) {
            return false;
         } else {
            var3 = var10.cycleProperty(OPEN);
            var1.setBlockState(var9, var3, 2);
            var1.markBlockRangeForRenderUpdate(var9, var2);
            var1.playAuxSFXAtEntity(var4, ((Boolean)var3.getValue(OPEN)).booleanValue()?1003:1006, var2, 0);
            return true;
         }
      }
   }

   public void toggleDoor(World var1, BlockPos var2, boolean var3) {
      IBlockState var4 = var1.getBlockState(var2);
      if(var4.getBlock() == this) {
         BlockPos var5 = var4.getValue(HALF) == BlockDoor$EnumDoorHalf.LOWER?var2:var2.down();
         IBlockState var6 = var2 == var5?var4:var1.getBlockState(var5);
         if(var6.getBlock() == this && ((Boolean)var6.getValue(OPEN)).booleanValue() != var3) {
            var1.setBlockState(var5, var6.withProperty(OPEN, Boolean.valueOf(var3)), 2);
            var1.markBlockRangeForRenderUpdate(var5, var2);
            var1.playAuxSFXAtEntity((EntityPlayer)null, 1003, var2, 0);
         }
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(var3.getValue(HALF) == BlockDoor$EnumDoorHalf.UPPER) {
         BlockPos var5 = var2.down();
         IBlockState var6 = var1.getBlockState(var5);
         if(var6.getBlock() != this) {
            var1.setBlockToAir(var2);
         } else if(var4 != this) {
            this.onNeighborBlockChange(var1, var5, var6, var4);
         }
      } else {
         boolean var8 = false;
         BlockPos var11 = var2.up();
         IBlockState var7 = var1.getBlockState(var11);
         if(var7.getBlock() != this) {
            var1.setBlockToAir(var2);
            var8 = true;
         }

         if(!World.doesBlockHaveSolidTopSurface(var1, var2.down())) {
            var1.setBlockToAir(var2);
            var8 = true;
            if(var7.getBlock() == this) {
               var1.setBlockToAir(var11);
            }
         }

         if(!var1.isRemote) {
            this.dropBlockAsItem(var1, var2, var3, 0);
         }
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return var1.getValue(HALF) == BlockDoor$EnumDoorHalf.UPPER?null:this.getItem();
   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.collisionRayTrace(var1, var2, var3, var4);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return var2.getY() < 255 && World.doesBlockHaveSolidTopSurface(var1, var2.down()) && super.canPlaceBlockAt(var1, var2) && super.canPlaceBlockAt(var1, var2.up());
   }

   public int getMobilityFlag() {
      return 1;
   }

   public static int combineMetadata(IBlockAccess var0, BlockPos var1) {
      IBlockState var2 = var0.getBlockState(var1);
      int var3 = var2.getBlock().getMetaFromState(var2);
      boolean var4 = isTop(var3);
      IBlockState var5 = var0.getBlockState(var1.down());
      int var6 = var5.getBlock().getMetaFromState(var5);
      IBlockState var8 = var0.getBlockState(var1.up());
      int var9 = var8.getBlock().getMetaFromState(var8);
      boolean var11 = (var3 & 1) != 0;
      boolean var12 = (var3 & 2) != 0;
      return removeHalfBit(var6) | 8 | 16 | 32;
   }

   public Item getItem(World var1, BlockPos var2) {
      return this.getItem();
   }

   private Item getItem() {
      return this == Blocks.iron_door?Items.iron_door:(this == Blocks.spruce_door?Items.spruce_door:(this == Blocks.birch_door?Items.birch_door:(this == Blocks.jungle_door?Items.jungle_door:(this == Blocks.acacia_door?Items.acacia_door:(this == Blocks.dark_oak_door?Items.dark_oak_door:Items.oak_door)))));
   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      BlockPos var5 = var2.down();
      if(var4.abilities.isCreative() && var3.getValue(HALF) == BlockDoor$EnumDoorHalf.UPPER && var1.getBlockState(var5).getBlock() == this) {
         var1.setBlockToAir(var5);
      }

   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if(var1.getValue(HALF) == BlockDoor$EnumDoorHalf.LOWER) {
         IBlockState var4 = var2.getBlockState(var3.up());
         if(var4.getBlock() == this) {
            var1 = var1.withProperty(HINGE, var4.getValue(HINGE)).withProperty(POWERED, var4.getValue(POWERED));
         }
      } else {
         IBlockState var5 = var2.getBlockState(var3.down());
         if(var5.getBlock() == this) {
            var1 = var1.withProperty(FACING, var5.getValue(FACING)).withProperty(OPEN, var5.getValue(OPEN));
         }
      }

      return var1;
   }

   public IBlockState getStateFromMeta(int var1) {
      return (var1 & 8) > 0?this.getDefaultState().withProperty(HALF, BlockDoor$EnumDoorHalf.UPPER).withProperty(HINGE, (var1 & 1) > 0?BlockDoor$EnumHingePosition.RIGHT:BlockDoor$EnumHingePosition.LEFT).withProperty(POWERED, Boolean.valueOf((var1 & 2) > 0)):this.getDefaultState().withProperty(HALF, BlockDoor$EnumDoorHalf.LOWER).withProperty(FACING, EnumFacing.getHorizontal(var1 & 3).rotateYCCW()).withProperty(OPEN, Boolean.valueOf((var1 & 4) > 0));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      if(var1.getValue(HALF) == BlockDoor$EnumDoorHalf.UPPER) {
         var2 = var2 | 8;
         if(var1.getValue(HINGE) == BlockDoor$EnumHingePosition.RIGHT) {
            var2 |= 1;
         }

         if(((Boolean)var1.getValue(POWERED)).booleanValue()) {
            var2 |= 2;
         }
      } else {
         var2 = var2 | ((EnumFacing)var1.getValue(FACING)).rotateY().getHorizontalIndex();
         if(((Boolean)var1.getValue(OPEN)).booleanValue()) {
            var2 |= 4;
         }
      }

      return var2;
   }

   protected static int removeHalfBit(int var0) {
      return var0 & 7;
   }

   public static boolean isOpen(IBlockAccess var0, BlockPos var1) {
      return isOpen(combineMetadata(var0, var1));
   }

   public static EnumFacing getFacing(IBlockAccess var0, BlockPos var1) {
      return getFacing(combineMetadata(var0, var1));
   }

   public static EnumFacing getFacing(int var0) {
      return EnumFacing.getHorizontal(var0 & 3).rotateYCCW();
   }

   protected static boolean isOpen(int var0) {
      return (var0 & 4) != 0;
   }

   protected static boolean isTop(int var0) {
      return (var0 & 8) != 0;
   }

   protected static boolean isHingeLeft(int var0) {
      return (var0 & 16) != 0;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{HALF, FACING, OPEN, HINGE, POWERED});
   }
}
