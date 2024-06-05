package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

final class Bootstrap$9 extends BehaviorDefaultDispenseItem {
   private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();

   public ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      EnumFacing var3 = BlockDispenser.getFacing(var1.getBlockMetadata());
      World var4 = var1.getWorld();
      double var5 = var1.getX() + (double)((float)var3.getFrontOffsetX() * 1.125F);
      double var7 = var1.getY() + (double)((float)var3.getFrontOffsetY() * 1.125F);
      double var9 = var1.getZ() + (double)((float)var3.getFrontOffsetZ() * 1.125F);
      BlockPos var11 = var1.getBlockPos().offset(var3);
      Material var12 = var4.getBlockState(var11).getBlock().getMaterial();
      double var13;
      if(Material.water.equals(var12)) {
         var13 = 1.0D;
      } else {
         if(!Material.air.equals(var12) || !Material.water.equals(var4.getBlockState(var11.down()).getBlock().getMaterial())) {
            return this.field_150842_b.dispense(var1, var2);
         }

         var13 = 0.0D;
      }

      EntityBoat var15 = new EntityBoat(var4, var5, var7 + var13, var9);
      var4.spawnEntityInWorld(var15);
      var2.splitStack(1);
      return var2;
   }

   protected void playDispenseSound(IBlockSource var1) {
      var1.getWorld().playAuxSFX(1000, var1.getBlockPos(), 0);
   }
}
