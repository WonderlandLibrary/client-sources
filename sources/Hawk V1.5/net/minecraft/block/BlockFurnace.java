package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockFurnace extends BlockContainer {
   private static boolean field_149934_M;
   private static final String __OBFID = "CL_00000248";
   public static final PropertyDirection FACING;
   private final boolean isBurning;

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(FACING, var8.func_174811_aO().getOpposite());
   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing var2 = EnumFacing.getFront(var1);
      if (var2.getAxis() == EnumFacing.Axis.Y) {
         var2 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, var2);
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumFacing)var1.getValue(FACING)).getIndex();
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if (!field_149934_M) {
         TileEntity var4 = var1.getTileEntity(var2);
         if (var4 instanceof TileEntityFurnace) {
            InventoryHelper.dropInventoryItems(var1, var2, (TileEntityFurnace)var4);
            var1.updateComparatorOutputLevel(var2, this);
         }
      }

      super.breakBlock(var1, var2, var3);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING});
   }

   private void func_176445_e(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote) {
         Block var4 = var1.getBlockState(var2.offsetNorth()).getBlock();
         Block var5 = var1.getBlockState(var2.offsetSouth()).getBlock();
         Block var6 = var1.getBlockState(var2.offsetWest()).getBlock();
         Block var7 = var1.getBlockState(var2.offsetEast()).getBlock();
         EnumFacing var8 = (EnumFacing)var3.getValue(FACING);
         if (var8 == EnumFacing.NORTH && var4.isFullBlock() && !var5.isFullBlock()) {
            var8 = EnumFacing.SOUTH;
         } else if (var8 == EnumFacing.SOUTH && var5.isFullBlock() && !var4.isFullBlock()) {
            var8 = EnumFacing.NORTH;
         } else if (var8 == EnumFacing.WEST && var6.isFullBlock() && !var7.isFullBlock()) {
            var8 = EnumFacing.EAST;
         } else if (var8 == EnumFacing.EAST && var7.isFullBlock() && !var6.isFullBlock()) {
            var8 = EnumFacing.WEST;
         }

         var1.setBlockState(var2, var3.withProperty(FACING, var8), 2);
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.furnace);
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.isBurning) {
         EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
         double var6 = (double)var2.getX() + 0.5D;
         double var8 = (double)var2.getY() + var4.nextDouble() * 6.0D / 16.0D;
         double var10 = (double)var2.getZ() + 0.5D;
         double var12 = 0.52D;
         double var14 = var4.nextDouble() * 0.6D - 0.3D;
         switch(var5) {
         case WEST:
            var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 - var12, var8, var10 + var14, 0.0D, 0.0D, 0.0D);
            var1.spawnParticle(EnumParticleTypes.FLAME, var6 - var12, var8, var10 + var14, 0.0D, 0.0D, 0.0D);
            break;
         case EAST:
            var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var12, var8, var10 + var14, 0.0D, 0.0D, 0.0D);
            var1.spawnParticle(EnumParticleTypes.FLAME, var6 + var12, var8, var10 + var14, 0.0D, 0.0D, 0.0D);
            break;
         case NORTH:
            var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var14, var8, var10 - var12, 0.0D, 0.0D, 0.0D);
            var1.spawnParticle(EnumParticleTypes.FLAME, var6 + var14, var8, var10 - var12, 0.0D, 0.0D, 0.0D);
            break;
         case SOUTH:
            var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var14, var8, var10 + var12, 0.0D, 0.0D, 0.0D);
            var1.spawnParticle(EnumParticleTypes.FLAME, var6 + var14, var8, var10 + var12, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return Container.calcRedstoneFromInventory(var1.getTileEntity(var2));
   }

   public IBlockState getStateForEntityRender(IBlockState var1) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
   }

   protected BlockFurnace(boolean var1) {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.isBurning = var1;
   }

   static {
      FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.func_176445_e(var1, var2, var3);
   }

   public int getRenderType() {
      return 3;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.isRemote) {
         return true;
      } else {
         TileEntity var9 = var1.getTileEntity(var2);
         if (var9 instanceof TileEntityFurnace) {
            var4.displayGUIChest((TileEntityFurnace)var9);
         }

         return true;
      }
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityFurnace();
   }

   public static void func_176446_a(boolean var0, World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      TileEntity var4 = var1.getTileEntity(var2);
      field_149934_M = true;
      if (var0) {
         var1.setBlockState(var2, Blocks.lit_furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
         var1.setBlockState(var2, Blocks.lit_furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
      } else {
         var1.setBlockState(var2, Blocks.furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
         var1.setBlockState(var2, Blocks.furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
      }

      field_149934_M = false;
      if (var4 != null) {
         var4.validate();
         var1.setTileEntity(var2, var4);
      }

   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.setBlockState(var2, var3.withProperty(FACING, var4.func_174811_aO().getOpposite()), 2);
      if (var5.hasDisplayName()) {
         TileEntity var6 = var1.getTileEntity(var2);
         if (var6 instanceof TileEntityFurnace) {
            ((TileEntityFurnace)var6).setCustomInventoryName(var5.getDisplayName());
         }
      }

   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(Blocks.furnace);
   }

   static final class SwitchEnumFacing {
      static final int[] field_180356_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002111";

      static {
         try {
            field_180356_a[EnumFacing.WEST.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_180356_a[EnumFacing.EAST.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180356_a[EnumFacing.NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180356_a[EnumFacing.SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
