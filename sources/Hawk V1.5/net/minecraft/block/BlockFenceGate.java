package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFenceGate extends BlockDirectional {
   public static final PropertyBool field_176466_a = PropertyBool.create("open");
   public static final PropertyBool field_176467_M = PropertyBool.create("in_wall");
   private static final String __OBFID = "CL_00000243";
   public static final PropertyBool field_176465_b = PropertyBool.create("powered");

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return (Boolean)var1.getBlockState(var2).getValue(field_176466_a);
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if ((Boolean)var3.getValue(field_176466_a)) {
         var3 = var3.withProperty(field_176466_a, false);
         var1.setBlockState(var2, var3, 2);
      } else {
         EnumFacing var9 = EnumFacing.fromAngle((double)var4.rotationYaw);
         if (var3.getValue(AGE) == var9.getOpposite()) {
            var3 = var3.withProperty(AGE, var9);
         }

         var3 = var3.withProperty(field_176466_a, true);
         var1.setBlockState(var2, var3, 2);
      }

      var1.playAuxSFXAtEntity(var4, (Boolean)var3.getValue(field_176466_a) ? 1003 : 1006, var2, 0);
      return true;
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(AGE)).getHorizontalIndex();
      if ((Boolean)var1.getValue(field_176465_b)) {
         var3 |= 8;
      }

      if ((Boolean)var1.getValue(field_176466_a)) {
         var3 |= 4;
      }

      return var3;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.isRemote) {
         boolean var5 = var1.isBlockPowered(var2);
         if (var5 || var4.canProvidePower()) {
            if (var5 && !(Boolean)var3.getValue(field_176466_a) && !(Boolean)var3.getValue(field_176465_b)) {
               var1.setBlockState(var2, var3.withProperty(field_176466_a, true).withProperty(field_176465_b, true), 2);
               var1.playAuxSFXAtEntity((EntityPlayer)null, 1003, var2, 0);
            } else if (!var5 && (Boolean)var3.getValue(field_176466_a) && (Boolean)var3.getValue(field_176465_b)) {
               var1.setBlockState(var2, var3.withProperty(field_176466_a, false).withProperty(field_176465_b, false), 2);
               var1.playAuxSFXAtEntity((EntityPlayer)null, 1006, var2, 0);
            } else if (var5 != (Boolean)var3.getValue(field_176465_b)) {
               var1.setBlockState(var2, var3.withProperty(field_176465_b, var5), 2);
            }
         }
      }

   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      EnumFacing.Axis var4 = ((EnumFacing)var1.getValue(AGE)).getAxis();
      if (var4 == EnumFacing.Axis.Z && (var2.getBlockState(var3.offsetWest()).getBlock() == Blocks.cobblestone_wall || var2.getBlockState(var3.offsetEast()).getBlock() == Blocks.cobblestone_wall) || var4 == EnumFacing.Axis.X && (var2.getBlockState(var3.offsetNorth()).getBlock() == Blocks.cobblestone_wall || var2.getBlockState(var3.offsetSouth()).getBlock() == Blocks.cobblestone_wall)) {
         var1 = var1.withProperty(field_176467_M, true);
      }

      return var1;
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(AGE, var8.func_174811_aO()).withProperty(field_176466_a, false).withProperty(field_176465_b, false).withProperty(field_176467_M, false);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(AGE, EnumFacing.getHorizontal(var1)).withProperty(field_176466_a, (var1 & 4) != 0).withProperty(field_176465_b, (var1 & 8) != 0);
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{AGE, field_176466_a, field_176465_b, field_176467_M});
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return var1.getBlockState(var2.offsetDown()).getBlock().getMaterial().isSolid() ? super.canPlaceBlockAt(var1, var2) : false;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      EnumFacing.Axis var3 = ((EnumFacing)var1.getBlockState(var2).getValue(AGE)).getAxis();
      if (var3 == EnumFacing.Axis.Z) {
         this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
      } else {
         this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
      }

   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      if ((Boolean)var3.getValue(field_176466_a)) {
         return null;
      } else {
         EnumFacing.Axis var4 = ((EnumFacing)var3.getValue(AGE)).getAxis();
         return var4 == EnumFacing.Axis.Z ? new AxisAlignedBB((double)var2.getX(), (double)var2.getY(), (double)((float)var2.getZ() + 0.375F), (double)(var2.getX() + 1), (double)((float)var2.getY() + 1.5F), (double)((float)var2.getZ() + 0.625F)) : new AxisAlignedBB((double)((float)var2.getX() + 0.375F), (double)var2.getY(), (double)var2.getZ(), (double)((float)var2.getX() + 0.625F), (double)((float)var2.getY() + 1.5F), (double)(var2.getZ() + 1));
      }
   }

   public BlockFenceGate() {
      super(Material.wood);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176466_a, false).withProperty(field_176465_b, false).withProperty(field_176467_M, false));
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }
}
