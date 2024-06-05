package net;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

final class auY extends BehaviorDefaultDispenseItem {
   protected ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      World var3 = var1.getWorld();
      BlockPos var4 = var1.getBlockPos().offset(BlockDispenser.getFacing(var1.getBlockMetadata()));
      EntityTNTPrimed var5 = new EntityTNTPrimed(var3, (double)var4.getX() + 0.5D, (double)var4.getY(), (double)var4.getZ() + 0.5D, (EntityLivingBase)null);
      var3.spawnEntityInWorld(var5);
      var3.playSoundAtEntity(var5, "game.tnt.primed", 1.0F, 1.0F);
      --var2.stackSize;
      return var2;
   }
}
