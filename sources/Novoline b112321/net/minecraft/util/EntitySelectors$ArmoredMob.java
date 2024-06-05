package net.minecraft.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntitySelectors$ArmoredMob implements Predicate {
   private final ItemStack armor;

   public EntitySelectors$ArmoredMob(ItemStack var1) {
      this.armor = var1;
   }

   public boolean apply(Entity var1) {
      if(!var1.isEntityAlive()) {
         return false;
      } else if(!(var1 instanceof EntityLivingBase)) {
         return false;
      } else {
         boolean var10000;
         label0: {
            EntityLivingBase var2 = (EntityLivingBase)var1;
            if(var2.getEquipmentInSlot(EntityLiving.getArmorPosition(this.armor)) == null) {
               if(var2 instanceof EntityLiving) {
                  if(((EntityLiving)var2).canPickUpLoot()) {
                     break label0;
                  }
               } else if(var2 instanceof EntityArmorStand || var2 instanceof EntityPlayer) {
                  break label0;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }
}
