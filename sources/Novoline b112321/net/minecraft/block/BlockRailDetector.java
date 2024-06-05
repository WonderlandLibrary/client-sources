package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase$EnumRailDirection;
import net.minecraft.block.BlockRailDetector$1;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRailDetector extends BlockRailBase {
   public static final PropertyEnum SHAPE = PropertyEnum.create("shape", BlockRailBase$EnumRailDirection.class, (Predicate)(new BlockRailDetector$1()));
   public static final PropertyBool POWERED = PropertyBool.create("powered");

   public BlockRailDetector() {
      super(true);
      this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.FALSE).withProperty(SHAPE, BlockRailBase$EnumRailDirection.NORTH_SOUTH));
      this.setTickRandomly(true);
   }

   public int tickRate(World var1) {
      return 20;
   }

   public boolean canProvidePower() {
      return true;
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if(!var1.isRemote && !((Boolean)var3.getValue(POWERED)).booleanValue()) {
         this.updatePoweredState(var1, var2, var3);
      }

   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(!var1.isRemote && ((Boolean)var3.getValue(POWERED)).booleanValue()) {
         this.updatePoweredState(var1, var2, var3);
      }

   }

   public int getWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return ((Boolean)var3.getValue(POWERED)).booleanValue()?15:0;
   }

   public int getStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !((Boolean)var3.getValue(POWERED)).booleanValue()?0:(var4 == EnumFacing.UP?15:0);
   }

   private void updatePoweredState(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = ((Boolean)var3.getValue(POWERED)).booleanValue();
      boolean var5 = false;
      List var6 = this.findMinecarts(var1, var2, EntityMinecart.class, new Predicate[0]);
      if(!var6.isEmpty()) {
         var5 = true;
      }

      var1.setBlockState(var2, var3.withProperty(POWERED, Boolean.TRUE), 3);
      var1.notifyNeighborsOfStateChange(var2, this);
      var1.notifyNeighborsOfStateChange(var2.down(), this);
      var1.markBlockRangeForRenderUpdate(var2, var2);
      var1.setBlockState(var2, var3.withProperty(POWERED, Boolean.FALSE), 3);
      var1.notifyNeighborsOfStateChange(var2, this);
      var1.notifyNeighborsOfStateChange(var2.down(), this);
      var1.markBlockRangeForRenderUpdate(var2, var2);
      var1.scheduleUpdate(var2, this, this.tickRate(var1));
      var1.updateComparatorOutputLevel(var2, this);
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      super.onBlockAdded(var1, var2, var3);
      this.updatePoweredState(var1, var2, var3);
   }

   public IProperty getShapeProperty() {
      return SHAPE;
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      if(((Boolean)var1.getBlockState(var2).getValue(POWERED)).booleanValue()) {
         List var3 = this.findMinecarts(var1, var2, EntityMinecartCommandBlock.class, new Predicate[0]);
         if(!var3.isEmpty()) {
            return ((EntityMinecartCommandBlock)var3.get(0)).getCommandBlockLogic().getSuccessCount();
         }

         List var4 = this.findMinecarts(var1, var2, EntityMinecart.class, new Predicate[]{EntitySelectors.selectInventories});
         if(!var4.isEmpty()) {
            return Container.a((IInventory)var4.get(0));
         }
      }

      return 0;
   }

   protected List findMinecarts(World var1, BlockPos var2, Class var3, Predicate... var4) {
      AxisAlignedBB var5 = this.getDectectionBox(var2);
      return var4.length != 1?var1.getEntitiesWithinAABB(var3, var5):var1.getEntitiesWithinAABB(var3, var5, var4[0]);
   }

   private AxisAlignedBB getDectectionBox(BlockPos var1) {
      float var2 = 0.2F;
      return new AxisAlignedBB((double)((float)var1.getX() + 0.2F), (double)var1.getY(), (double)((float)var1.getZ() + 0.2F), (double)((float)(var1.getX() + 1) - 0.2F), (double)((float)(var1.getY() + 1) - 0.2F), (double)((float)(var1.getZ() + 1) - 0.2F));
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(SHAPE, BlockRailBase$EnumRailDirection.byMetadata(var1 & 7)).withProperty(POWERED, Boolean.valueOf((var1 & 8) > 0));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | ((BlockRailBase$EnumRailDirection)var1.getValue(SHAPE)).getMetadata();
      if(((Boolean)var1.getValue(POWERED)).booleanValue()) {
         var2 |= 8;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{SHAPE, POWERED});
   }
}
