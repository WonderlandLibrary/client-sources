package net.minecraft.block;

import java.util.Iterator;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLever extends Block {
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   private static final String __OBFID = "CL_00000264";
   public static final PropertyEnum FACING = PropertyEnum.create("facing", BlockLever.EnumOrientation.class);

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, BlockLever.EnumOrientation.func_176853_a(var1 & 7)).withProperty(POWERED, (var1 & 8) > 0);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (this.func_176356_e(var1, var2) && !this.func_176358_d(var1, var2.offset(((BlockLever.EnumOrientation)var3.getValue(FACING)).func_176852_c().getOpposite()))) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.1875F;
      switch((BlockLever.EnumOrientation)var1.getBlockState(var2).getValue(FACING)) {
      case EAST:
         this.setBlockBounds(0.0F, 0.2F, 0.5F - var3, var3 * 2.0F, 0.8F, 0.5F + var3);
         break;
      case WEST:
         this.setBlockBounds(1.0F - var3 * 2.0F, 0.2F, 0.5F - var3, 1.0F, 0.8F, 0.5F + var3);
         break;
      case SOUTH:
         this.setBlockBounds(0.5F - var3, 0.2F, 0.0F, 0.5F + var3, 0.8F, var3 * 2.0F);
         break;
      case NORTH:
         this.setBlockBounds(0.5F - var3, 0.2F, 1.0F - var3 * 2.0F, 0.5F + var3, 0.8F, 1.0F);
         break;
      case UP_Z:
      case UP_X:
         var3 = 0.25F;
         this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.6F, 0.5F + var3);
         break;
      case DOWN_X:
      case DOWN_Z:
         var3 = 0.25F;
         this.setBlockBounds(0.5F - var3, 0.4F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
      }

   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return this.func_176358_d(var1, var2.offsetWest()) ? true : (this.func_176358_d(var1, var2.offsetEast()) ? true : (this.func_176358_d(var1, var2.offsetNorth()) ? true : (this.func_176358_d(var1, var2.offsetSouth()) ? true : (World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) ? true : this.func_176358_d(var1, var2.offsetUp())))));
   }

   protected boolean func_176358_d(World var1, BlockPos var2) {
      return var1.getBlockState(var2).getBlock().isNormalCube();
   }

   public int isProvidingStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !(Boolean)var3.getValue(POWERED) ? 0 : (((BlockLever.EnumOrientation)var3.getValue(FACING)).func_176852_c() == var4 ? 15 : 0);
   }

   public boolean isFullCube() {
      return false;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if ((Boolean)var3.getValue(POWERED)) {
         var1.notifyNeighborsOfStateChange(var2, this);
         EnumFacing var4 = ((BlockLever.EnumOrientation)var3.getValue(FACING)).func_176852_c();
         var1.notifyNeighborsOfStateChange(var2.offset(var4.getOpposite()), this);
      }

      super.breakBlock(var1, var2, var3);
   }

   public boolean canProvidePower() {
      return true;
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockLever.EnumOrientation)var1.getValue(FACING)).func_176855_a();
      if ((Boolean)var1.getValue(POWERED)) {
         var3 |= 8;
      }

      return var3;
   }

   private boolean func_176356_e(World var1, BlockPos var2) {
      if (this.canPlaceBlockAt(var1, var2)) {
         return true;
      } else {
         this.dropBlockAsItem(var1, var2, var1.getBlockState(var2), 0);
         var1.setBlockToAir(var2);
         return false;
      }
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.isRemote) {
         return true;
      } else {
         var3 = var3.cycleProperty(POWERED);
         var1.setBlockState(var2, var3, 3);
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, (Boolean)var3.getValue(POWERED) ? 0.6F : 0.5F);
         var1.notifyNeighborsOfStateChange(var2, this);
         EnumFacing var9 = ((BlockLever.EnumOrientation)var3.getValue(FACING)).func_176852_c();
         var1.notifyNeighborsOfStateChange(var2.offset(var9.getOpposite()), this);
         return true;
      }
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, POWERED});
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.getDefaultState().withProperty(POWERED, false);
      if (this.func_176358_d(var1, var2.offset(var3.getOpposite()))) {
         return var9.withProperty(FACING, BlockLever.EnumOrientation.func_176856_a(var3, var8.func_174811_aO()));
      } else {
         Iterator var10 = EnumFacing.Plane.HORIZONTAL.iterator();

         EnumFacing var11;
         do {
            if (!var10.hasNext()) {
               if (World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown())) {
                  return var9.withProperty(FACING, BlockLever.EnumOrientation.func_176856_a(EnumFacing.UP, var8.func_174811_aO()));
               }

               return var9;
            }

            var11 = (EnumFacing)var10.next();
         } while(var11 == var3 || !this.func_176358_d(var1, var2.offset(var11.getOpposite())));

         return var9.withProperty(FACING, BlockLever.EnumOrientation.func_176856_a(var11, var8.func_174811_aO()));
      }
   }

   public static int func_176357_a(EnumFacing var0) {
      switch(var0) {
      case DOWN:
         return 0;
      case UP:
         return 5;
      case NORTH:
         return 4;
      case SOUTH:
         return 3;
      case WEST:
         return 2;
      case EAST:
         return 1;
      default:
         return -1;
      }
   }

   public int isProvidingWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return (Boolean)var3.getValue(POWERED) ? 15 : 0;
   }

   protected BlockLever() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, BlockLever.EnumOrientation.NORTH).withProperty(POWERED, false));
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.UP && World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) ? true : this.func_176358_d(var1, var2.offset(var3.getOpposite()));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public static enum EnumOrientation implements IStringSerializable {
      SOUTH("SOUTH", 3, 3, "south", EnumFacing.SOUTH),
      NORTH("NORTH", 4, 4, "north", EnumFacing.NORTH);

      private final String field_176867_k;
      EAST("EAST", 1, 1, "east", EnumFacing.EAST);

      private static final BlockLever.EnumOrientation[] field_176869_i = new BlockLever.EnumOrientation[values().length];
      UP_Z("UP_Z", 5, 5, "up_z", EnumFacing.UP),
      UP_X("UP_X", 6, 6, "up_x", EnumFacing.UP);

      private final EnumFacing field_176864_l;
      private static final BlockLever.EnumOrientation[] ENUM$VALUES = new BlockLever.EnumOrientation[]{DOWN_X, EAST, WEST, SOUTH, NORTH, UP_Z, UP_X, DOWN_Z};
      WEST("WEST", 2, 2, "west", EnumFacing.WEST);

      private static final String __OBFID = "CL_00002102";
      DOWN_X("DOWN_X", 0, 0, "down_x", EnumFacing.DOWN);

      private static final BlockLever.EnumOrientation[] $VALUES = new BlockLever.EnumOrientation[]{DOWN_X, EAST, WEST, SOUTH, NORTH, UP_Z, UP_X, DOWN_Z};
      DOWN_Z("DOWN_Z", 7, 7, "down_z", EnumFacing.DOWN);

      private final int field_176866_j;

      public String getName() {
         return this.field_176867_k;
      }

      public EnumFacing func_176852_c() {
         return this.field_176864_l;
      }

      public static BlockLever.EnumOrientation func_176853_a(int var0) {
         if (var0 < 0 || var0 >= field_176869_i.length) {
            var0 = 0;
         }

         return field_176869_i[var0];
      }

      public static BlockLever.EnumOrientation func_176856_a(EnumFacing var0, EnumFacing var1) {
         switch(var0) {
         case DOWN:
            switch(var1.getAxis()) {
            case X:
               return DOWN_X;
            case Z:
               return DOWN_Z;
            default:
               throw new IllegalArgumentException(String.valueOf((new StringBuilder("Invalid entityFacing ")).append(var1).append(" for facing ").append(var0)));
            }
         case UP:
            switch(var1.getAxis()) {
            case X:
               return UP_X;
            case Z:
               return UP_Z;
            default:
               throw new IllegalArgumentException(String.valueOf((new StringBuilder("Invalid entityFacing ")).append(var1).append(" for facing ").append(var0)));
            }
         case NORTH:
            return NORTH;
         case SOUTH:
            return SOUTH;
         case WEST:
            return WEST;
         case EAST:
            return EAST;
         default:
            throw new IllegalArgumentException(String.valueOf((new StringBuilder("Invalid facing: ")).append(var0)));
         }
      }

      public int func_176855_a() {
         return this.field_176866_j;
      }

      private EnumOrientation(String var3, int var4, int var5, String var6, EnumFacing var7) {
         this.field_176866_j = var5;
         this.field_176867_k = var6;
         this.field_176864_l = var7;
      }

      public String toString() {
         return this.field_176867_k;
      }

      static {
         BlockLever.EnumOrientation[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockLever.EnumOrientation var3 = var0[var2];
            field_176869_i[var3.func_176855_a()] = var3;
         }

      }
   }

   static final class SwitchEnumFacing {
      static final int[] ORIENTATION_LOOKUP;
      static final int[] FACING_LOOKUP;
      private static final String __OBFID = "CL_00002103";
      static final int[] AXIS_LOOKUP = new int[EnumFacing.Axis.values().length];

      static {
         try {
            AXIS_LOOKUP[EnumFacing.Axis.X.ordinal()] = 1;
         } catch (NoSuchFieldError var16) {
         }

         try {
            AXIS_LOOKUP[EnumFacing.Axis.Z.ordinal()] = 2;
         } catch (NoSuchFieldError var15) {
         }

         ORIENTATION_LOOKUP = new int[BlockLever.EnumOrientation.values().length];

         try {
            ORIENTATION_LOOKUP[BlockLever.EnumOrientation.EAST.ordinal()] = 1;
         } catch (NoSuchFieldError var14) {
         }

         try {
            ORIENTATION_LOOKUP[BlockLever.EnumOrientation.WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var13) {
         }

         try {
            ORIENTATION_LOOKUP[BlockLever.EnumOrientation.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var12) {
         }

         try {
            ORIENTATION_LOOKUP[BlockLever.EnumOrientation.NORTH.ordinal()] = 4;
         } catch (NoSuchFieldError var11) {
         }

         try {
            ORIENTATION_LOOKUP[BlockLever.EnumOrientation.UP_Z.ordinal()] = 5;
         } catch (NoSuchFieldError var10) {
         }

         try {
            ORIENTATION_LOOKUP[BlockLever.EnumOrientation.UP_X.ordinal()] = 6;
         } catch (NoSuchFieldError var9) {
         }

         try {
            ORIENTATION_LOOKUP[BlockLever.EnumOrientation.DOWN_X.ordinal()] = 7;
         } catch (NoSuchFieldError var8) {
         }

         try {
            ORIENTATION_LOOKUP[BlockLever.EnumOrientation.DOWN_Z.ordinal()] = 8;
         } catch (NoSuchFieldError var7) {
         }

         FACING_LOOKUP = new int[EnumFacing.values().length];

         try {
            FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            FACING_LOOKUP[EnumFacing.UP.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
