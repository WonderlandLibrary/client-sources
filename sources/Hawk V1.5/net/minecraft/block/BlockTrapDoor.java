package net.minecraft.block;

import com.google.common.base.Predicate;
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
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrapDoor extends Block {
   public static final PropertyBool field_176283_b;
   public static final PropertyEnum field_176285_M;
   public static final PropertyDirection field_176284_a;
   private static final String __OBFID = "CL_00000327";

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getSelectedBoundingBox(var1, var2);
   }

   protected static EnumFacing func_176281_b(int var0) {
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

   static {
      field_176284_a = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
      field_176283_b = PropertyBool.create("open");
      field_176285_M = PropertyEnum.create("half", BlockTrapDoor.DoorHalf.class);
   }

   public void func_180693_d(IBlockState var1) {
      if (var1.getBlock() == this) {
         boolean var2 = var1.getValue(field_176285_M) == BlockTrapDoor.DoorHalf.TOP;
         Boolean var3 = (Boolean)var1.getValue(field_176283_b);
         EnumFacing var4 = (EnumFacing)var1.getValue(field_176284_a);
         float var5 = 0.1875F;
         if (var2) {
            this.setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
         } else {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
         }

         if (var3) {
            if (var4 == EnumFacing.NORTH) {
               this.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
            }

            if (var4 == EnumFacing.SOUTH) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
            }

            if (var4 == EnumFacing.WEST) {
               this.setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if (var4 == EnumFacing.EAST) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
            }
         }
      }

   }

   public boolean isFullCube() {
      return false;
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.1875F;
      this.setBlockBounds(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
   }

   protected static int func_176282_a(EnumFacing var0) {
      switch(var0) {
      case NORTH:
         return 0;
      case SOUTH:
         return 1;
      case WEST:
         return 2;
      case EAST:
      default:
         return 3;
      }
   }

   private static boolean isValidSupportBlock(Block var0) {
      return var0.blockMaterial.isOpaque() && var0.isFullCube() || var0 == Blocks.glowstone || var0 instanceof BlockSlab || var0 instanceof BlockStairs;
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return !var3.getAxis().isVertical() && isValidSupportBlock(var1.getBlockState(var2.offset(var3.getOpposite())).getBlock());
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176284_a, func_176281_b(var1)).withProperty(field_176283_b, (var1 & 4) != 0).withProperty(field_176285_M, (var1 & 8) == 0 ? BlockTrapDoor.DoorHalf.BOTTOM : BlockTrapDoor.DoorHalf.TOP);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.func_180693_d(var1.getBlockState(var2));
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (this.blockMaterial == Material.iron) {
         return true;
      } else {
         var3 = var3.cycleProperty(field_176283_b);
         var1.setBlockState(var2, var3, 2);
         var1.playAuxSFXAtEntity(var4, (Boolean)var3.getValue(field_176283_b) ? 1003 : 1006, var2, 0);
         return true;
      }
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | func_176282_a((EnumFacing)var1.getValue(field_176284_a));
      if ((Boolean)var1.getValue(field_176283_b)) {
         var3 |= 4;
      }

      if (var1.getValue(field_176285_M) == BlockTrapDoor.DoorHalf.TOP) {
         var3 |= 8;
      }

      return var3;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.getDefaultState();
      if (var3.getAxis().isHorizontal()) {
         var9 = var9.withProperty(field_176284_a, var3).withProperty(field_176283_b, false);
         var9 = var9.withProperty(field_176285_M, var5 > 0.5F ? BlockTrapDoor.DoorHalf.TOP : BlockTrapDoor.DoorHalf.BOTTOM);
      }

      return var9;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getCollisionBoundingBox(var1, var2, var3);
   }

   protected BlockTrapDoor(Material var1) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176284_a, EnumFacing.NORTH).withProperty(field_176283_b, false).withProperty(field_176285_M, BlockTrapDoor.DoorHalf.BOTTOM));
      float var2 = 0.5F;
      float var3 = 1.0F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return !(Boolean)var1.getBlockState(var2).getValue(field_176283_b);
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176284_a, field_176283_b, field_176285_M});
   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.collisionRayTrace(var1, var2, var3, var4);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.isRemote) {
         BlockPos var5 = var2.offset(((EnumFacing)var3.getValue(field_176284_a)).getOpposite());
         if (!isValidSupportBlock(var1.getBlockState(var5).getBlock())) {
            var1.setBlockToAir(var2);
            this.dropBlockAsItem(var1, var2, var3, 0);
         } else {
            boolean var6 = var1.isBlockPowered(var2);
            if (var6 || var4.canProvidePower()) {
               boolean var7 = (Boolean)var3.getValue(field_176283_b);
               if (var7 != var6) {
                  var1.setBlockState(var2, var3.withProperty(field_176283_b, var6), 2);
                  var1.playAuxSFXAtEntity((EntityPlayer)null, var6 ? 1003 : 1006, var2, 0);
               }
            }
         }
      }

   }

   static final class SwitchEnumFacing {
      static final int[] field_177058_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002052";

      static {
         try {
            field_177058_a[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_177058_a[EnumFacing.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177058_a[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177058_a[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   public static enum DoorHalf implements IStringSerializable {
      private static final String __OBFID = "CL_00002051";
      private static final BlockTrapDoor.DoorHalf[] $VALUES = new BlockTrapDoor.DoorHalf[]{TOP, BOTTOM};
      TOP("TOP", 0, "top"),
      BOTTOM("BOTTOM", 1, "bottom");

      private static final BlockTrapDoor.DoorHalf[] ENUM$VALUES = new BlockTrapDoor.DoorHalf[]{TOP, BOTTOM};
      private final String field_176671_c;

      public String toString() {
         return this.field_176671_c;
      }

      public String getName() {
         return this.field_176671_c;
      }

      private DoorHalf(String var3, int var4, String var5) {
         this.field_176671_c = var5;
      }
   }
}
