package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRailDetector extends BlockRailBase {
   public static final PropertyBool field_176574_M = PropertyBool.create("powered");
   public static final PropertyEnum field_176573_b = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, new Predicate() {
      private static final String __OBFID = "CL_00002126";

      public boolean func_180344_a(BlockRailBase.EnumRailDirection var1) {
         return var1 != BlockRailBase.EnumRailDirection.NORTH_EAST && var1 != BlockRailBase.EnumRailDirection.NORTH_WEST && var1 != BlockRailBase.EnumRailDirection.SOUTH_EAST && var1 != BlockRailBase.EnumRailDirection.SOUTH_WEST;
      }

      public boolean apply(Object var1) {
         return this.func_180344_a((BlockRailBase.EnumRailDirection)var1);
      }
   });
   private static final String __OBFID = "CL_00000225";

   private AxisAlignedBB func_176572_a(BlockPos var1) {
      float var2 = 0.2F;
      return new AxisAlignedBB((double)((float)var1.getX() + 0.2F), (double)var1.getY(), (double)((float)var1.getZ() + 0.2F), (double)((float)(var1.getX() + 1) - 0.2F), (double)((float)(var1.getY() + 1) - 0.2F), (double)((float)(var1.getZ() + 1) - 0.2F));
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176573_b, BlockRailBase.EnumRailDirection.func_177016_a(var1 & 7)).withProperty(field_176574_M, (var1 & 8) > 0);
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      if ((Boolean)var1.getBlockState(var2).getValue(field_176574_M)) {
         List var3 = this.func_176571_a(var1, var2, EntityMinecartCommandBlock.class);
         if (!var3.isEmpty()) {
            return ((EntityMinecartCommandBlock)var3.get(0)).func_145822_e().getSuccessCount();
         }

         List var4 = this.func_176571_a(var1, var2, EntityMinecart.class, IEntitySelector.selectInventories);
         if (!var4.isEmpty()) {
            return Container.calcRedstoneFromInventory((IInventory)var4.get(0));
         }
      }

      return 0;
   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public BlockRailDetector() {
      super(true);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176574_M, false).withProperty(field_176573_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
      this.setTickRandomly(true);
   }

   private void func_176570_e(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = (Boolean)var3.getValue(field_176574_M);
      boolean var5 = false;
      List var6 = this.func_176571_a(var1, var2, EntityMinecart.class);
      if (!var6.isEmpty()) {
         var5 = true;
      }

      if (var5 && !var4) {
         var1.setBlockState(var2, var3.withProperty(field_176574_M, true), 3);
         var1.notifyNeighborsOfStateChange(var2, this);
         var1.notifyNeighborsOfStateChange(var2.offsetDown(), this);
         var1.markBlockRangeForRenderUpdate(var2, var2);
      }

      if (!var5 && var4) {
         var1.setBlockState(var2, var3.withProperty(field_176574_M, false), 3);
         var1.notifyNeighborsOfStateChange(var2, this);
         var1.notifyNeighborsOfStateChange(var2.offsetDown(), this);
         var1.markBlockRangeForRenderUpdate(var2, var2);
      }

      if (var5) {
         var1.scheduleUpdate(var2, this, this.tickRate(var1));
      }

      var1.updateComparatorOutputLevel(var2, this);
   }

   public int tickRate(World var1) {
      return 20;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.isRemote && (Boolean)var3.getValue(field_176574_M)) {
         this.func_176570_e(var1, var2, var3);
      }

   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      super.onBlockAdded(var1, var2, var3);
      this.func_176570_e(var1, var2, var3);
   }

   public boolean canProvidePower() {
      return true;
   }

   public int isProvidingWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return (Boolean)var3.getValue(field_176574_M) ? 15 : 0;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176573_b, field_176574_M});
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (!var1.isRemote && !(Boolean)var3.getValue(field_176574_M)) {
         this.func_176570_e(var1, var2, var3);
      }

   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockRailBase.EnumRailDirection)var1.getValue(field_176573_b)).func_177015_a();
      if ((Boolean)var1.getValue(field_176574_M)) {
         var3 |= 8;
      }

      return var3;
   }

   public int isProvidingStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !(Boolean)var3.getValue(field_176574_M) ? 0 : (var4 == EnumFacing.UP ? 15 : 0);
   }

   public IProperty func_176560_l() {
      return field_176573_b;
   }

   protected List func_176571_a(World var1, BlockPos var2, Class var3, Predicate... var4) {
      AxisAlignedBB var5 = this.func_176572_a(var2);
      return var4.length != 1 ? var1.getEntitiesWithinAABB(var3, var5) : var1.func_175647_a(var3, var5, var4[0]);
   }
}
