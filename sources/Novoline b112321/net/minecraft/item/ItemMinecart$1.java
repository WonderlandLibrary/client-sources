package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase$EnumRailDirection;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

final class ItemMinecart$1 extends BehaviorDefaultDispenseItem {
   private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();

   public ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      EnumFacing var3 = BlockDispenser.getFacing(var1.getBlockMetadata());
      World var4 = var1.getWorld();
      double var5 = var1.getX() + (double)var3.getFrontOffsetX() * 1.125D;
      double var7 = Math.floor(var1.getY()) + (double)var3.getFrontOffsetY();
      double var9 = var1.getZ() + (double)var3.getFrontOffsetZ() * 1.125D;
      BlockPos var11 = var1.getBlockPos().offset(var3);
      IBlockState var12 = var4.getBlockState(var11);
      BlockRailBase$EnumRailDirection var13 = var12.getBlock() instanceof BlockRailBase?(BlockRailBase$EnumRailDirection)var12.getValue(((BlockRailBase)var12.getBlock()).getShapeProperty()):BlockRailBase$EnumRailDirection.NORTH_SOUTH;
      double var14;
      if(BlockRailBase.isRailBlock(var12)) {
         if(var13.isAscending()) {
            var14 = 0.6D;
         } else {
            var14 = 0.1D;
         }
      } else {
         if(var12.getBlock().getMaterial() != Material.air || !BlockRailBase.isRailBlock(var4.getBlockState(var11.down()))) {
            return this.behaviourDefaultDispenseItem.dispense(var1, var2);
         }

         IBlockState var16 = var4.getBlockState(var11.down());
         BlockRailBase$EnumRailDirection var17 = var16.getBlock() instanceof BlockRailBase?(BlockRailBase$EnumRailDirection)var16.getValue(((BlockRailBase)var16.getBlock()).getShapeProperty()):BlockRailBase$EnumRailDirection.NORTH_SOUTH;
         if(var3 != EnumFacing.DOWN && var17.isAscending()) {
            var14 = -0.4D;
         } else {
            var14 = -0.9D;
         }
      }

      EntityMinecart var18 = EntityMinecart.func_180458_a(var4, var5, var7 + var14, var9, ItemMinecart.access$000((ItemMinecart)var2.getItem()));
      if(var2.hasDisplayName()) {
         var18.setCustomNameTag(var2.getDisplayName());
      }

      var4.spawnEntityInWorld(var18);
      var2.splitStack(1);
      return var2;
   }

   protected void playDispenseSound(IBlockSource var1) {
      var1.getWorld().playAuxSFX(1000, var1.getBlockPos(), 0);
   }
}
