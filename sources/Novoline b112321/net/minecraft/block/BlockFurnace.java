package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace$1;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockFurnace extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing$Plane.HORIZONTAL);
   private final boolean isBurning;
   private static boolean keepInventory;

   protected BlockFurnace(boolean var1) {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.isBurning = var1;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.furnace);
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.setDefaultFacing(var1, var2, var3);
   }

   private void setDefaultFacing(World var1, BlockPos var2, IBlockState var3) {
      if(!var1.isRemote) {
         Block var4 = var1.getBlockState(var2.north()).getBlock();
         Block var5 = var1.getBlockState(var2.south()).getBlock();
         Block var6 = var1.getBlockState(var2.west()).getBlock();
         Block var7 = var1.getBlockState(var2.east()).getBlock();
         EnumFacing var8 = (EnumFacing)var3.getValue(FACING);
         if(var8 == EnumFacing.NORTH && var4.isFullBlock() && !var5.isFullBlock()) {
            var8 = EnumFacing.SOUTH;
         } else if(var8 == EnumFacing.SOUTH && var5.isFullBlock() && !var4.isFullBlock()) {
            var8 = EnumFacing.NORTH;
         } else if(var8 == EnumFacing.WEST && var6.isFullBlock() && !var7.isFullBlock()) {
            var8 = EnumFacing.EAST;
         } else if(var8 == EnumFacing.EAST && var7.isFullBlock() && !var6.isFullBlock()) {
            var8 = EnumFacing.WEST;
         }

         var1.setBlockState(var2, var3.withProperty(FACING, var8), 2);
      }

   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(this.isBurning) {
         EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
         double var6 = (double)var2.getX() + 0.5D;
         double var8 = (double)var2.getY() + var4.nextDouble() * 6.0D / 16.0D;
         double var10 = (double)var2.getZ() + 0.5D;
         double var12 = 0.52D;
         double var14 = var4.nextDouble() * 0.6D - 0.3D;
         switch(BlockFurnace$1.$SwitchMap$net$minecraft$util$EnumFacing[var5.ordinal()]) {
         case 1:
            var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 - 0.52D, var8, var10 + var14, 0.0D, 0.0D, 0.0D, new int[0]);
            var1.spawnParticle(EnumParticleTypes.FLAME, var6 - 0.52D, var8, var10 + var14, 0.0D, 0.0D, 0.0D, new int[0]);
            break;
         case 2:
            var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + 0.52D, var8, var10 + var14, 0.0D, 0.0D, 0.0D, new int[0]);
            var1.spawnParticle(EnumParticleTypes.FLAME, var6 + 0.52D, var8, var10 + var14, 0.0D, 0.0D, 0.0D, new int[0]);
            break;
         case 3:
            var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var14, var8, var10 - 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
            var1.spawnParticle(EnumParticleTypes.FLAME, var6 + var14, var8, var10 - 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
            break;
         case 4:
            var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var14, var8, var10 + 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
            var1.spawnParticle(EnumParticleTypes.FLAME, var6 + var14, var8, var10 + 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
         }
      }

   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(!var1.isRemote) {
         TileEntity var9 = var1.getTileEntity(var2);
         if(var9 instanceof TileEntityFurnace) {
            var4.displayGUIChest((TileEntityFurnace)var9);
            var4.triggerAchievement(StatList.field_181741_Y);
         }
      }

      return true;
   }

   public static void setState(boolean var0, World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      TileEntity var4 = var1.getTileEntity(var2);
      keepInventory = true;
      var1.setBlockState(var2, Blocks.lit_furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
      var1.setBlockState(var2, Blocks.lit_furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
      keepInventory = false;
      var4.validate();
      var1.setTileEntity(var2, var4);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityFurnace();
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(FACING, var8.getHorizontalFacing().getOpposite());
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.setBlockState(var2, var3.withProperty(FACING, var4.getHorizontalFacing().getOpposite()), 2);
      if(var5.hasDisplayName()) {
         TileEntity var6 = var1.getTileEntity(var2);
         if(var6 instanceof TileEntityFurnace) {
            ((TileEntityFurnace)var6).setCustomInventoryName(var5.getDisplayName());
         }
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if(!keepInventory) {
         TileEntity var4 = var1.getTileEntity(var2);
         if(var4 instanceof TileEntityFurnace) {
            InventoryHelper.dropInventoryItems(var1, var2, (TileEntityFurnace)var4);
            var1.updateComparatorOutputLevel(var2, this);
         }
      }

      super.breakBlock(var1, var2, var3);
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return Container.calcRedstone(var1.getTileEntity(var2));
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(Blocks.furnace);
   }

   public int getRenderType() {
      return 3;
   }

   public IBlockState getStateForEntityRender(IBlockState var1) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
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
