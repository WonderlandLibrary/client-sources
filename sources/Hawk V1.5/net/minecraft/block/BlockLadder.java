package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Iterator;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLadder extends Block {
   public static final PropertyDirection field_176382_a;
   private static final String __OBFID = "CL_00000262";

   static {
      field_176382_a = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if (var3.getAxis().isHorizontal() && this.func_176381_b(var1, var2, var3)) {
         return this.getDefaultState().withProperty(field_176382_a, var3);
      } else {
         Iterator var9 = EnumFacing.Plane.HORIZONTAL.iterator();

         while(var9.hasNext()) {
            EnumFacing var10 = (EnumFacing)var9.next();
            if (this.func_176381_b(var1, var2, var10)) {
               return this.getDefaultState().withProperty(field_176382_a, var10);
            }
         }

         return this.getDefaultState();
      }
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return var1.getBlockState(var2.offsetWest()).getBlock().isNormalCube() ? true : (var1.getBlockState(var2.offsetEast()).getBlock().isNormalCube() ? true : (var1.getBlockState(var2.offsetNorth()).getBlock().isNormalCube() ? true : var1.getBlockState(var2.offsetSouth()).getBlock().isNormalCube()));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getSelectedBoundingBox(var1, var2);
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getCollisionBoundingBox(var1, var2, var3);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = (EnumFacing)var3.getValue(field_176382_a);
      if (!this.func_176381_b(var1, var2, var5)) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

      super.onNeighborBlockChange(var1, var2, var3, var4);
   }

   protected BlockLadder() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176382_a, EnumFacing.NORTH));
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      if (var3.getBlock() == this) {
         float var4 = 0.125F;
         switch((EnumFacing)var3.getValue(field_176382_a)) {
         case NORTH:
            this.setBlockBounds(0.0F, 0.0F, 1.0F - var4, 1.0F, 1.0F, 1.0F);
            break;
         case SOUTH:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var4);
            break;
         case WEST:
            this.setBlockBounds(1.0F - var4, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
         case EAST:
         default:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, var4, 1.0F, 1.0F);
         }
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing var2 = EnumFacing.getFront(var1);
      if (var2.getAxis() == EnumFacing.Axis.Y) {
         var2 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(field_176382_a, var2);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176382_a});
   }

   public boolean isFullCube() {
      return false;
   }

   protected boolean func_176381_b(World var1, BlockPos var2, EnumFacing var3) {
      return var1.getBlockState(var2.offset(var3.getOpposite())).getBlock().isNormalCube();
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumFacing)var1.getValue(field_176382_a)).getIndex();
   }

   static final class SwitchEnumFacing {
      static final int[] field_180190_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002104";

      static {
         try {
            field_180190_a[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_180190_a[EnumFacing.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180190_a[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180190_a[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
