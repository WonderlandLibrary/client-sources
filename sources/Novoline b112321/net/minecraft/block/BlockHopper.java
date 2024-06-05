package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHopper$1;
import net.minecraft.block.material.MapColor;
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
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHopper extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)(new BlockHopper$1()));
   public static final PropertyBool ENABLED = PropertyBool.create("enabled");

   public BlockHopper() {
      super(Material.iron, MapColor.stoneColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN).withProperty(ENABLED, Boolean.TRUE));
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      float var7 = 0.125F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      EnumFacing var9 = var3.getOpposite();
      if(var9 == EnumFacing.UP) {
         var9 = EnumFacing.DOWN;
      }

      return this.getDefaultState().withProperty(FACING, var9).withProperty(ENABLED, Boolean.TRUE);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityHopper();
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      super.onBlockPlacedBy(var1, var2, var3, var4, var5);
      if(var5.hasDisplayName()) {
         TileEntity var6 = var1.getTileEntity(var2);
         if(var6 instanceof TileEntityHopper) {
            ((TileEntityHopper)var6).setCustomName(var5.getDisplayName());
         }
      }

   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.updateState(var1, var2, var3);
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(!var1.isRemote) {
         TileEntity var9 = var1.getTileEntity(var2);
         if(var9 instanceof TileEntityHopper) {
            var4.displayGUIChest((TileEntityHopper)var9);
            var4.triggerAchievement(StatList.field_181732_P);
         }
      }

      return true;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.updateState(var1, var2, var3);
   }

   private void updateState(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = !var1.isBlockPowered(var2);
      if(var4 != ((Boolean)var3.getValue(ENABLED)).booleanValue()) {
         var1.setBlockState(var2, var3.withProperty(ENABLED, Boolean.valueOf(var4)), 4);
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      if(var4 instanceof TileEntityHopper) {
         InventoryHelper.dropInventoryItems(var1, var2, (TileEntityHopper)var4);
         var1.updateComparatorOutputLevel(var2, this);
      }

      super.breakBlock(var1, var2, var3);
   }

   public int getRenderType() {
      return 3;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   public static EnumFacing getFacing(int var0) {
      return EnumFacing.getFront(var0 & 7);
   }

   public static boolean isEnabled(int var0) {
      return (var0 & 8) != 8;
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return Container.calcRedstone(var1.getTileEntity(var2));
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, getFacing(var1)).withProperty(ENABLED, Boolean.valueOf(isEnabled(var1)));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | ((EnumFacing)var1.getValue(FACING)).getIndex();
      if(!((Boolean)var1.getValue(ENABLED)).booleanValue()) {
         var2 |= 8;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, ENABLED});
   }
}
