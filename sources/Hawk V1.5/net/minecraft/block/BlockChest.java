package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

public class BlockChest extends BlockContainer {
   private final Random rand = new Random();
   public static final PropertyDirection FACING_PROP;
   public final int chestType;
   private static final String __OBFID = "CL_00000214";

   public IBlockState checkForSurroundingChests(World var1, BlockPos var2, IBlockState var3) {
      if (var1.isRemote) {
         return var3;
      } else {
         IBlockState var4 = var1.getBlockState(var2.offsetNorth());
         IBlockState var5 = var1.getBlockState(var2.offsetSouth());
         IBlockState var6 = var1.getBlockState(var2.offsetWest());
         IBlockState var7 = var1.getBlockState(var2.offsetEast());
         EnumFacing var8 = (EnumFacing)var3.getValue(FACING_PROP);
         Block var9 = var4.getBlock();
         Block var10 = var5.getBlock();
         Block var11 = var6.getBlock();
         Block var12 = var7.getBlock();
         if (var9 != this && var10 != this) {
            boolean var21 = var9.isFullBlock();
            boolean var22 = var10.isFullBlock();
            if (var11 == this || var12 == this) {
               BlockPos var23 = var11 == this ? var2.offsetWest() : var2.offsetEast();
               IBlockState var24 = var1.getBlockState(var23.offsetNorth());
               IBlockState var25 = var1.getBlockState(var23.offsetSouth());
               var8 = EnumFacing.SOUTH;
               EnumFacing var26;
               if (var11 == this) {
                  var26 = (EnumFacing)var6.getValue(FACING_PROP);
               } else {
                  var26 = (EnumFacing)var7.getValue(FACING_PROP);
               }

               if (var26 == EnumFacing.NORTH) {
                  var8 = EnumFacing.NORTH;
               }

               Block var19 = var24.getBlock();
               Block var20 = var25.getBlock();
               if ((var21 || var19.isFullBlock()) && !var22 && !var20.isFullBlock()) {
                  var8 = EnumFacing.SOUTH;
               }

               if ((var22 || var20.isFullBlock()) && !var21 && !var19.isFullBlock()) {
                  var8 = EnumFacing.NORTH;
               }
            }
         } else {
            BlockPos var13 = var9 == this ? var2.offsetNorth() : var2.offsetSouth();
            IBlockState var14 = var1.getBlockState(var13.offsetWest());
            IBlockState var15 = var1.getBlockState(var13.offsetEast());
            var8 = EnumFacing.EAST;
            EnumFacing var16;
            if (var9 == this) {
               var16 = (EnumFacing)var4.getValue(FACING_PROP);
            } else {
               var16 = (EnumFacing)var5.getValue(FACING_PROP);
            }

            if (var16 == EnumFacing.WEST) {
               var8 = EnumFacing.WEST;
            }

            Block var17 = var14.getBlock();
            Block var18 = var15.getBlock();
            if ((var11.isFullBlock() || var17.isFullBlock()) && !var12.isFullBlock() && !var18.isFullBlock()) {
               var8 = EnumFacing.EAST;
            }

            if ((var12.isFullBlock() || var18.isFullBlock()) && !var11.isFullBlock() && !var17.isFullBlock()) {
               var8 = EnumFacing.WEST;
            }
         }

         var3 = var3.withProperty(FACING_PROP, var8);
         var1.setBlockState(var2, var3, 3);
         return var3;
      }
   }

   public ILockableContainer getLockableContainer(World var1, BlockPos var2) {
      TileEntity var3 = var1.getTileEntity(var2);
      if (!(var3 instanceof TileEntityChest)) {
         return null;
      } else {
         Object var4 = (TileEntityChest)var3;
         if (this.cannotOpenChest(var1, var2)) {
            return null;
         } else {
            Iterator var5 = EnumFacing.Plane.HORIZONTAL.iterator();

            while(true) {
               while(true) {
                  EnumFacing var6;
                  TileEntity var9;
                  do {
                     BlockPos var7;
                     Block var8;
                     do {
                        if (!var5.hasNext()) {
                           return (ILockableContainer)var4;
                        }

                        var6 = (EnumFacing)var5.next();
                        var7 = var2.offset(var6);
                        var8 = var1.getBlockState(var7).getBlock();
                     } while(var8 != this);

                     if (this.cannotOpenChest(var1, var7)) {
                        return null;
                     }

                     var9 = var1.getTileEntity(var7);
                  } while(!(var9 instanceof TileEntityChest));

                  if (var6 != EnumFacing.WEST && var6 != EnumFacing.NORTH) {
                     var4 = new InventoryLargeChest("container.chestDouble", (ILockableContainer)var4, (TileEntityChest)var9);
                  } else {
                     var4 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)var9, (ILockableContainer)var4);
                  }
               }
            }
         }
      }
   }

   private boolean cannotOpenChest(World var1, BlockPos var2) {
      return this.isBelowSolidBlock(var1, var2) || this.isOcelotSittingOnChest(var1, var2);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      int var3 = 0;
      BlockPos var4 = var2.offsetWest();
      BlockPos var5 = var2.offsetEast();
      BlockPos var6 = var2.offsetNorth();
      BlockPos var7 = var2.offsetSouth();
      if (var1.getBlockState(var4).getBlock() == this) {
         if (this.isSurroundingBlockChest(var1, var4)) {
            return false;
         }

         ++var3;
      }

      if (var1.getBlockState(var5).getBlock() == this) {
         if (this.isSurroundingBlockChest(var1, var5)) {
            return false;
         }

         ++var3;
      }

      if (var1.getBlockState(var6).getBlock() == this) {
         if (this.isSurroundingBlockChest(var1, var6)) {
            return false;
         }

         ++var3;
      }

      if (var1.getBlockState(var7).getBlock() == this) {
         if (this.isSurroundingBlockChest(var1, var7)) {
            return false;
         }

         ++var3;
      }

      return var3 <= 1;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.isRemote) {
         return true;
      } else {
         ILockableContainer var9 = this.getLockableContainer(var1, var2);
         if (var9 != null) {
            var4.displayGUIChest(var9);
         }

         return true;
      }
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public boolean isFullCube() {
      return false;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return Container.calcRedstoneFromInventory((IInventory)this.getLockableContainer(var1, var2));
   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing var2 = EnumFacing.getFront(var1);
      if (var2.getAxis() == EnumFacing.Axis.Y) {
         var2 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING_PROP, var2);
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      EnumFacing var6 = EnumFacing.getHorizontal(MathHelper.floor_double((double)(var4.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
      var3 = var3.withProperty(FACING_PROP, var6);
      BlockPos var7 = var2.offsetNorth();
      BlockPos var8 = var2.offsetSouth();
      BlockPos var9 = var2.offsetWest();
      BlockPos var10 = var2.offsetEast();
      boolean var11 = this == var1.getBlockState(var7).getBlock();
      boolean var12 = this == var1.getBlockState(var8).getBlock();
      boolean var13 = this == var1.getBlockState(var9).getBlock();
      boolean var14 = this == var1.getBlockState(var10).getBlock();
      if (!var11 && !var12 && !var13 && !var14) {
         var1.setBlockState(var2, var3, 3);
      } else if (var6.getAxis() == EnumFacing.Axis.X && (var11 || var12)) {
         if (var11) {
            var1.setBlockState(var7, var3, 3);
         } else {
            var1.setBlockState(var8, var3, 3);
         }

         var1.setBlockState(var2, var3, 3);
      } else if (var6.getAxis() == EnumFacing.Axis.Z && (var13 || var14)) {
         if (var13) {
            var1.setBlockState(var9, var3, 3);
         } else {
            var1.setBlockState(var10, var3, 3);
         }

         var1.setBlockState(var2, var3, 3);
      }

      if (var5.hasDisplayName()) {
         TileEntity var15 = var1.getTileEntity(var2);
         if (var15 instanceof TileEntityChest) {
            ((TileEntityChest)var15).setCustomName(var5.getDisplayName());
         }
      }

   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityChest();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING_PROP});
   }

   public int isProvidingStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var4 == EnumFacing.UP ? this.isProvidingWeakPower(var1, var2, var3, var4) : 0;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.checkForSurroundingChests(var1, var2, var3);
      Iterator var4 = EnumFacing.Plane.HORIZONTAL.iterator();

      while(var4.hasNext()) {
         EnumFacing var5 = (EnumFacing)var4.next();
         BlockPos var6 = var2.offset(var5);
         IBlockState var7 = var1.getBlockState(var6);
         if (var7.getBlock() == this) {
            this.checkForSurroundingChests(var1, var6, var7);
         }
      }

   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      if (var1.getBlockState(var2.offsetNorth()).getBlock() == this) {
         this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
      } else if (var1.getBlockState(var2.offsetSouth()).getBlock() == this) {
         this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
      } else if (var1.getBlockState(var2.offsetWest()).getBlock() == this) {
         this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
      } else if (var1.getBlockState(var2.offsetEast()).getBlock() == this) {
         this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
      } else {
         this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      if (var4 instanceof IInventory) {
         InventoryHelper.dropInventoryItems(var1, var2, (IInventory)var4);
         var1.updateComparatorOutputLevel(var2, this);
      }

      super.breakBlock(var1, var2, var3);
   }

   public int isProvidingWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if (!this.canProvidePower()) {
         return 0;
      } else {
         int var5 = 0;
         TileEntity var6 = var1.getTileEntity(var2);
         if (var6 instanceof TileEntityChest) {
            var5 = ((TileEntityChest)var6).numPlayersUsing;
         }

         return MathHelper.clamp_int(var5, 0, 15);
      }
   }

   public int getRenderType() {
      return 2;
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(FACING_PROP, var8.func_174811_aO());
   }

   public boolean canProvidePower() {
      return this.chestType == 1;
   }

   private boolean isBelowSolidBlock(World var1, BlockPos var2) {
      return var1.getBlockState(var2.offsetUp()).getBlock().isNormalCube();
   }

   protected BlockChest(int var1) {
      super(Material.wood);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING_PROP, EnumFacing.NORTH));
      this.chestType = var1;
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
   }

   static {
      FACING_PROP = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
   }

   public IBlockState func_176458_f(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = null;
      Iterator var5 = EnumFacing.Plane.HORIZONTAL.iterator();

      EnumFacing var6;
      while(var5.hasNext()) {
         var6 = (EnumFacing)var5.next();
         IBlockState var7 = var1.getBlockState(var2.offset(var6));
         if (var7.getBlock() == this) {
            return var3;
         }

         if (var7.getBlock().isFullBlock()) {
            if (var4 != null) {
               var4 = null;
               break;
            }

            var4 = var6;
         }
      }

      if (var4 != null) {
         return var3.withProperty(FACING_PROP, var4.getOpposite());
      } else {
         var6 = (EnumFacing)var3.getValue(FACING_PROP);
         if (var1.getBlockState(var2.offset(var6)).getBlock().isFullBlock()) {
            var6 = var6.getOpposite();
         }

         if (var1.getBlockState(var2.offset(var6)).getBlock().isFullBlock()) {
            var6 = var6.rotateY();
         }

         if (var1.getBlockState(var2.offset(var6)).getBlock().isFullBlock()) {
            var6 = var6.getOpposite();
         }

         return var3.withProperty(FACING_PROP, var6);
      }
   }

   private boolean isSurroundingBlockChest(World var1, BlockPos var2) {
      if (var1.getBlockState(var2).getBlock() != this) {
         return false;
      } else {
         Iterator var3 = EnumFacing.Plane.HORIZONTAL.iterator();

         while(var3.hasNext()) {
            EnumFacing var4 = (EnumFacing)var3.next();
            if (var1.getBlockState(var2.offset(var4)).getBlock() == this) {
               return true;
            }
         }

         return false;
      }
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumFacing)var1.getValue(FACING_PROP)).getIndex();
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      super.onNeighborBlockChange(var1, var2, var3, var4);
      TileEntity var5 = var1.getTileEntity(var2);
      if (var5 instanceof TileEntityChest) {
         var5.updateContainingBlockInfo();
      }

   }

   public boolean isOpaqueCube() {
      return false;
   }

   private boolean isOcelotSittingOnChest(World var1, BlockPos var2) {
      Iterator var3 = var1.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB((double)var2.getX(), (double)(var2.getY() + 1), (double)var2.getZ(), (double)(var2.getX() + 1), (double)(var2.getY() + 2), (double)(var2.getZ() + 1))).iterator();

      while(var3.hasNext()) {
         Entity var5 = (Entity)var3.next();
         EntityOcelot var4 = (EntityOcelot)var5;
         if (var4.isSitting()) {
            return true;
         }
      }

      return false;
   }
}
