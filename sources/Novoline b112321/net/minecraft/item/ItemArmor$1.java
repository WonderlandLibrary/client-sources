package net.minecraft.item;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EntitySelectors$ArmoredMob;

final class ItemArmor$1 extends BehaviorDefaultDispenseItem {
   protected ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      BlockPos var3 = var1.getBlockPos().offset(BlockDispenser.getFacing(var1.getBlockMetadata()));
      int var4 = var3.getX();
      int var5 = var3.getY();
      int var6 = var3.getZ();
      AxisAlignedBB var7 = new AxisAlignedBB((double)var4, (double)var5, (double)var6, (double)(var4 + 1), (double)(var5 + 1), (double)(var6 + 1));
      List var8 = var1.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, var7, Predicates.and(EntitySelectors.NOT_SPECTATING, new EntitySelectors$ArmoredMob(var2)));
      if(!var8.isEmpty()) {
         EntityLivingBase var9 = (EntityLivingBase)var8.get(0);
         int var10 = var9 instanceof EntityPlayer?1:0;
         int var11 = EntityLiving.getArmorPosition(var2);
         ItemStack var12 = var2.copy();
         var12.stackSize = 1;
         var9.setCurrentItemOrArmor(var11 - var10, var12);
         if(var9 instanceof EntityLiving) {
            ((EntityLiving)var9).setEquipmentDropChance(var11, 2.0F);
         }

         --var2.stackSize;
         return var2;
      } else {
         return super.dispenseStack(var1, var2);
      }
   }
}
