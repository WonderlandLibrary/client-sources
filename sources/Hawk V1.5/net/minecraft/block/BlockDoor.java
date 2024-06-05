package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
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
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoor extends Block {
   public static final PropertyEnum HINGEPOSITION_PROP;
   public static final PropertyBool OPEN_PROP;
   public static final PropertyBool POWERED_PROP;
   public static final PropertyDirection FACING_PROP;
   private static final String __OBFID = "CL_00000230";
   public static final PropertyEnum HALF_PROP;

   public Item getItem(World var1, BlockPos var2) {
      return this.func_176509_j();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{HALF_PROP, FACING_PROP, OPEN_PROP, HINGEPOSITION_PROP, POWERED_PROP});
   }

   public static boolean func_176514_f(IBlockAccess var0, BlockPos var1) {
      return func_176516_g(func_176515_e(var0, var1));
   }

   protected BlockDoor(Material var1) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING_PROP, EnumFacing.NORTH).withProperty(OPEN_PROP, false).withProperty(HINGEPOSITION_PROP, BlockDoor.EnumHingePosition.LEFT).withProperty(POWERED_PROP, false).withProperty(HALF_PROP, BlockDoor.EnumDoorHalf.LOWER));
   }

   public static EnumFacing func_176511_f(int var0) {
      return EnumFacing.getHorizontal(var0 & 3).rotateYCCW();
   }

   public static int func_176515_e(IBlockAccess var0, BlockPos var1) {
      IBlockState var2 = var0.getBlockState(var1);
      int var3 = var2.getBlock().getMetaFromState(var2);
      boolean var4 = func_176518_i(var3);
      IBlockState var5 = var0.getBlockState(var1.offsetDown());
      int var6 = var5.getBlock().getMetaFromState(var5);
      int var7 = var4 ? var6 : var3;
      IBlockState var8 = var0.getBlockState(var1.offsetUp());
      int var9 = var8.getBlock().getMetaFromState(var8);
      int var10 = var4 ? var3 : var9;
      boolean var11 = (var10 & 1) != 0;
      boolean var12 = (var10 & 2) != 0;
      return func_176510_b(var7) | (var4 ? 8 : 0) | (var11 ? 16 : 0) | (var12 ? 32 : 0);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return var2.getY() >= 255 ? false : World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) && super.canPlaceBlockAt(var1, var2) && super.canPlaceBlockAt(var1, var2.offsetUp());
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (this.blockMaterial == Material.iron) {
         return true;
      } else {
         BlockPos var9 = var3.getValue(HALF_PROP) == BlockDoor.EnumDoorHalf.LOWER ? var2 : var2.offsetDown();
         IBlockState var10 = var2.equals(var9) ? var3 : var1.getBlockState(var9);
         if (var10.getBlock() != this) {
            return false;
         } else {
            var3 = var10.cycleProperty(OPEN_PROP);
            var1.setBlockState(var9, var3, 2);
            var1.markBlockRangeForRenderUpdate(var9, var2);
            var1.playAuxSFXAtEntity(var4, (Boolean)var3.getValue(OPEN_PROP) ? 1003 : 1006, var2, 0);
            return true;
         }
      }
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return var1.getValue(HALF_PROP) == BlockDoor.EnumDoorHalf.UPPER ? null : this.func_176509_j();
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getCollisionBoundingBox(var1, var2, var3);
   }

   public static EnumFacing func_176517_h(IBlockAccess var0, BlockPos var1) {
      return func_176511_f(func_176515_e(var0, var1));
   }

   public int getMobilityFlag() {
      return 1;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.func_150011_b(func_176515_e(var1, var2));
   }

   protected static int func_176510_b(int var0) {
      return var0 & 7;
   }

   protected static boolean func_176513_j(int var0) {
      return (var0 & 16) != 0;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (var3.getValue(HALF_PROP) == BlockDoor.EnumDoorHalf.UPPER) {
         BlockPos var5 = var2.offsetDown();
         IBlockState var6 = var1.getBlockState(var5);
         if (var6.getBlock() != this) {
            var1.setBlockToAir(var2);
         } else if (var4 != this) {
            this.onNeighborBlockChange(var1, var5, var6, var4);
         }
      } else {
         boolean var9 = false;
         BlockPos var10 = var2.offsetUp();
         IBlockState var7 = var1.getBlockState(var10);
         if (var7.getBlock() != this) {
            var1.setBlockToAir(var2);
            var9 = true;
         }

         if (!World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown())) {
            var1.setBlockToAir(var2);
            var9 = true;
            if (var7.getBlock() == this) {
               var1.setBlockToAir(var10);
            }
         }

         if (var9) {
            if (!var1.isRemote) {
               this.dropBlockAsItem(var1, var2, var3, 0);
            }
         } else {
            boolean var8 = var1.isBlockPowered(var2) || var1.isBlockPowered(var10);
            if ((var8 || var4.canProvidePower()) && var4 != this && var8 != (Boolean)var7.getValue(POWERED_PROP)) {
               var1.setBlockState(var10, var7.withProperty(POWERED_PROP, var8), 2);
               if (var8 != (Boolean)var3.getValue(OPEN_PROP)) {
                  var1.setBlockState(var2, var3.withProperty(OPEN_PROP, var8), 2);
                  var1.markBlockRangeForRenderUpdate(var2, var2);
                  var1.playAuxSFXAtEntity((EntityPlayer)null, var8 ? 1003 : 1006, var2, 0);
               }
            }
         }
      }

   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return func_176516_g(func_176515_e(var1, var2));
   }

   protected static boolean func_176518_i(int var0) {
      return (var0 & 8) != 0;
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getSelectedBoundingBox(var1, var2);
   }

   static {
      FACING_PROP = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
      OPEN_PROP = PropertyBool.create("open");
      HINGEPOSITION_PROP = PropertyEnum.create("hinge", BlockDoor.EnumHingePosition.class);
      POWERED_PROP = PropertyBool.create("powered");
      HALF_PROP = PropertyEnum.create("half", BlockDoor.EnumDoorHalf.class);
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      IBlockState var4;
      if (var1.getValue(HALF_PROP) == BlockDoor.EnumDoorHalf.LOWER) {
         var4 = var2.getBlockState(var3.offsetUp());
         if (var4.getBlock() == this) {
            var1 = var1.withProperty(HINGEPOSITION_PROP, var4.getValue(HINGEPOSITION_PROP)).withProperty(POWERED_PROP, var4.getValue(POWERED_PROP));
         }
      } else {
         var4 = var2.getBlockState(var3.offsetDown());
         if (var4.getBlock() == this) {
            var1 = var1.withProperty(FACING_PROP, var4.getValue(FACING_PROP)).withProperty(OPEN_PROP, var4.getValue(OPEN_PROP));
         }
      }

      return var1;
   }

   public boolean isFullCube() {
      return false;
   }

   public void func_176512_a(World var1, BlockPos var2, boolean var3) {
      IBlockState var4 = var1.getBlockState(var2);
      if (var4.getBlock() == this) {
         BlockPos var5 = var4.getValue(HALF_PROP) == BlockDoor.EnumDoorHalf.LOWER ? var2 : var2.offsetDown();
         IBlockState var6 = var2 == var5 ? var4 : var1.getBlockState(var5);
         if (var6.getBlock() == this && (Boolean)var6.getValue(OPEN_PROP) != var3) {
            var1.setBlockState(var5, var6.withProperty(OPEN_PROP, var3), 2);
            var1.markBlockRangeForRenderUpdate(var5, var2);
            var1.playAuxSFXAtEntity((EntityPlayer)null, var3 ? 1003 : 1006, var2, 0);
         }
      }

   }

   private Item func_176509_j() {
      return this == Blocks.iron_door ? Items.iron_door : (this == Blocks.spruce_door ? Items.spruce_door : (this == Blocks.birch_door ? Items.birch_door : (this == Blocks.jungle_door ? Items.jungle_door : (this == Blocks.acacia_door ? Items.acacia_door : (this == Blocks.dark_oak_door ? Items.dark_oak_door : Items.oak_door)))));
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int var1) {
      return (var1 & 8) > 0 ? this.getDefaultState().withProperty(HALF_PROP, BlockDoor.EnumDoorHalf.UPPER).withProperty(HINGEPOSITION_PROP, (var1 & 1) > 0 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).withProperty(POWERED_PROP, (var1 & 2) > 0) : this.getDefaultState().withProperty(HALF_PROP, BlockDoor.EnumDoorHalf.LOWER).withProperty(FACING_PROP, EnumFacing.getHorizontal(var1 & 3).rotateYCCW()).withProperty(OPEN_PROP, (var1 & 4) > 0);
   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.collisionRayTrace(var1, var2, var3, var4);
   }

   protected static boolean func_176516_g(int var0) {
      return (var0 & 4) != 0;
   }

   private void func_150011_b(int var1) {
      float var2 = 0.1875F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
      EnumFacing var3 = func_176511_f(var1);
      boolean var4 = func_176516_g(var1);
      boolean var5 = func_176513_j(var1);
      if (var4) {
         if (var3 == EnumFacing.EAST) {
            if (!var5) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
         } else if (var3 == EnumFacing.SOUTH) {
            if (!var5) {
               this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
         } else if (var3 == EnumFacing.WEST) {
            if (!var5) {
               this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
         } else if (var3 == EnumFacing.NORTH) {
            if (!var5) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            } else {
               this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      } else if (var3 == EnumFacing.EAST) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
      } else if (var3 == EnumFacing.SOUTH) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
      } else if (var3 == EnumFacing.WEST) {
         this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else if (var3 == EnumFacing.NORTH) {
         this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
      }

   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      BlockPos var5 = var2.offsetDown();
      if (var4.capabilities.isCreativeMode && var3.getValue(HALF_PROP) == BlockDoor.EnumDoorHalf.UPPER && var1.getBlockState(var5).getBlock() == this) {
         var1.setBlockToAir(var5);
      }

   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3;
      if (var1.getValue(HALF_PROP) == BlockDoor.EnumDoorHalf.UPPER) {
         var3 = var2 | 8;
         if (var1.getValue(HINGEPOSITION_PROP) == BlockDoor.EnumHingePosition.RIGHT) {
            var3 |= 1;
         }

         if ((Boolean)var1.getValue(POWERED_PROP)) {
            var3 |= 2;
         }
      } else {
         var3 = var2 | ((EnumFacing)var1.getValue(FACING_PROP)).rotateY().getHorizontalIndex();
         if ((Boolean)var1.getValue(OPEN_PROP)) {
            var3 |= 4;
         }
      }

      return var3;
   }

   public static enum EnumDoorHalf implements IStringSerializable {
      UPPER("UPPER", 0),
      LOWER("LOWER", 1);

      private static final String __OBFID = "CL_00002124";
      private static final BlockDoor.EnumDoorHalf[] ENUM$VALUES = new BlockDoor.EnumDoorHalf[]{UPPER, LOWER};
      private static final BlockDoor.EnumDoorHalf[] $VALUES = new BlockDoor.EnumDoorHalf[]{UPPER, LOWER};

      public String getName() {
         return this == UPPER ? "upper" : "lower";
      }

      public String toString() {
         return this.getName();
      }

      private EnumDoorHalf(String var3, int var4) {
      }
   }

   public static enum EnumHingePosition implements IStringSerializable {
      RIGHT("RIGHT", 1),
      LEFT("LEFT", 0);

      private static final String __OBFID = "CL_00002123";
      private static final BlockDoor.EnumHingePosition[] ENUM$VALUES = new BlockDoor.EnumHingePosition[]{LEFT, RIGHT};
      private static final BlockDoor.EnumHingePosition[] $VALUES = new BlockDoor.EnumHingePosition[]{LEFT, RIGHT};

      private EnumHingePosition(String var3, int var4) {
      }

      public String getName() {
         return this == LEFT ? "left" : "right";
      }

      public String toString() {
         return this.getName();
      }
   }
}
