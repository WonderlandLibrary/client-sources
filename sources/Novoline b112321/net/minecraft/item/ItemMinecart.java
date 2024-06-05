package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase$EnumRailDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecart$EnumMinecartType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMinecart$1;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemMinecart extends Item {
   private static final IBehaviorDispenseItem dispenserMinecartBehavior = new ItemMinecart$1();
   private final EntityMinecart$EnumMinecartType minecartType;

   public ItemMinecart(EntityMinecart$EnumMinecartType var1) {
      this.maxStackSize = 1;
      this.minecartType = var1;
      this.setCreativeTab(CreativeTabs.tabTransport);
      BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
   }

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      IBlockState var9 = var3.getBlockState(var4);
      if(BlockRailBase.isRailBlock(var9)) {
         if(!var3.isRemote) {
            BlockRailBase$EnumRailDirection var10 = var9.getBlock() instanceof BlockRailBase?(BlockRailBase$EnumRailDirection)var9.getValue(((BlockRailBase)var9.getBlock()).getShapeProperty()):BlockRailBase$EnumRailDirection.NORTH_SOUTH;
            double var11 = 0.0D;
            if(var10.isAscending()) {
               var11 = 0.5D;
            }

            EntityMinecart var13 = EntityMinecart.func_180458_a(var3, (double)var4.getX() + 0.5D, (double)var4.getY() + 0.0625D + var11, (double)var4.getZ() + 0.5D, this.minecartType);
            if(var1.hasDisplayName()) {
               var13.setCustomNameTag(var1.getDisplayName());
            }

            var3.spawnEntityInWorld(var13);
         }

         --var1.stackSize;
         return true;
      } else {
         return false;
      }
   }

   static EntityMinecart$EnumMinecartType access$000(ItemMinecart var0) {
      return var0.minecartType;
   }
}
