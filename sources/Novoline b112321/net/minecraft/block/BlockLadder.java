package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder$1;
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
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLadder extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing$Plane.HORIZONTAL);

   protected BlockLadder() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getCollisionBoundingBox(var1, var2, var3);
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getSelectedBoundingBox(var1, var2);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      if(var3.getBlock() == this) {
         float var4 = 0.125F;
         switch(BlockLadder$1.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)var3.getValue(FACING)).ordinal()]) {
         case 1:
            this.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
            break;
         case 2:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
            break;
         case 3:
            this.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
         case 4:
         default:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
         }
      }

   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return var1.getBlockState(var2.west()).getBlock().isNormalCube() || var1.getBlockState(var2.east()).getBlock().isNormalCube() || var1.getBlockState(var2.north()).getBlock().isNormalCube() || var1.getBlockState(var2.south()).getBlock().isNormalCube();
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if(var3.getAxis().isHorizontal() && this.canBlockStay(var1, var2, var3)) {
         return this.getDefaultState().withProperty(FACING, var3);
      } else {
         for(Object var10 : EnumFacing$Plane.HORIZONTAL) {
            if(this.canBlockStay(var1, var2, (EnumFacing)var10)) {
               return this.getDefaultState().withProperty(FACING, (EnumFacing)var10);
            }
         }

         return this.getDefaultState();
      }
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
      if(!this.canBlockStay(var1, var2, var5)) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

      super.onNeighborBlockChange(var1, var2, var3, var4);
   }

   protected boolean canBlockStay(World var1, BlockPos var2, EnumFacing var3) {
      return var1.getBlockState(var2.offset(var3.getOpposite())).getBlock().isNormalCube();
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing var2 = EnumFacing.getFront(var1);
      if(var2.getAxis() == EnumFacing$Axis.Y) {
         var2 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, var2);
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumFacing)var1.getValue(FACING)).getIndex();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING});
   }
}
