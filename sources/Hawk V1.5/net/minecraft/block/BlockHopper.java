package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHopper extends BlockContainer {
   private static final String __OBFID = "CL_00000257";
   public static final PropertyDirection field_176430_a = PropertyDirection.create("facing", new Predicate() {
      private static final String __OBFID = "CL_00002106";

      public boolean apply(Object var1) {
         return this.func_180180_a((EnumFacing)var1);
      }

      public boolean func_180180_a(EnumFacing var1) {
         return var1 != EnumFacing.UP;
      }
   });
   public static final PropertyBool field_176429_b = PropertyBool.create("enabled");

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      super.onBlockPlacedBy(var1, var2, var3, var4, var5);
      if (var5.hasDisplayName()) {
         TileEntity var6 = var1.getTileEntity(var2);
         if (var6 instanceof TileEntityHopper) {
            ((TileEntityHopper)var6).setCustomName(var5.getDisplayName());
         }
      }

   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityHopper();
   }

   public BlockHopper() {
      super(Material.iron);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176430_a, EnumFacing.DOWN).withProperty(field_176429_b, true));
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.isRemote) {
         return true;
      } else {
         TileEntity var9 = var1.getTileEntity(var2);
         if (var9 instanceof TileEntityHopper) {
            var4.displayGUIChest((TileEntityHopper)var9);
         }

         return true;
      }
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      if (var4 instanceof TileEntityHopper) {
         InventoryHelper.dropInventoryItems(var1, var2, (TileEntityHopper)var4);
         var1.updateComparatorOutputLevel(var2, this);
      }

      super.breakBlock(var1, var2, var3);
   }

   public static boolean getActiveStateFromMetadata(int var0) {
      return (var0 & 8) != 8;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(field_176430_a)).getIndex();
      if (!(Boolean)var1.getValue(field_176429_b)) {
         var3 |= 8;
      }

      return var3;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176430_a, field_176429_b});
   }

   private void func_176427_e(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = !var1.isBlockPowered(var2);
      if (var4 != (Boolean)var3.getValue(field_176429_b)) {
         var1.setBlockState(var2, var3.withProperty(field_176429_b, var4), 4);
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176430_a, func_176428_b(var1)).withProperty(field_176429_b, getActiveStateFromMetadata(var1));
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public int getRenderType() {
      return 3;
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public static EnumFacing func_176428_b(int var0) {
      return EnumFacing.getFront(var0 & 7);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.func_176427_e(var1, var2, var3);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      EnumFacing var9 = var3.getOpposite();
      if (var9 == EnumFacing.UP) {
         var9 = EnumFacing.DOWN;
      }

      return this.getDefaultState().withProperty(field_176430_a, var9).withProperty(field_176429_b, true);
   }

   public boolean isFullCube() {
      return false;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return Container.calcRedstoneFromInventory(var1.getTileEntity(var2));
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.func_176427_e(var1, var2, var3);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      float var7 = 0.125F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, var7, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var7);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(1.0F - var7, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 1.0F - var7, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }
}
