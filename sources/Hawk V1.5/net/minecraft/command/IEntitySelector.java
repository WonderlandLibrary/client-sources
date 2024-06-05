package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public final class IEntitySelector {
   public static final Predicate field_180132_d = new Predicate() {
      private static final String __OBFID = "CL_00002256";

      public boolean apply(Object var1) {
         return this.func_180103_a((Entity)var1);
      }

      public boolean func_180103_a(Entity var1) {
         return !(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).func_175149_v();
      }
   };
   private static final String __OBFID = "CL_00002257";
   public static final Predicate selectInventories = new Predicate() {
      private static final String __OBFID = "CL_00001867";

      public boolean apply(Object var1) {
         return this.func_180102_a((Entity)var1);
      }

      public boolean func_180102_a(Entity var1) {
         return var1 instanceof IInventory && var1.isEntityAlive();
      }
   };
   public static final Predicate field_152785_b = new Predicate() {
      private static final String __OBFID = "CL_00001542";

      public boolean apply(Object var1) {
         return this.func_180130_a((Entity)var1);
      }

      public boolean func_180130_a(Entity var1) {
         return var1.isEntityAlive() && var1.riddenByEntity == null && var1.ridingEntity == null;
      }
   };
   public static final Predicate selectAnything = new Predicate() {
      private static final String __OBFID = "CL_00001541";

      public boolean func_180131_a(Entity var1) {
         return var1.isEntityAlive();
      }

      public boolean apply(Object var1) {
         return this.func_180131_a((Entity)var1);
      }
   };

   public static class ArmoredMob implements Predicate {
      private final ItemStack field_96567_c;
      private static final String __OBFID = "CL_00001543";

      public ArmoredMob(ItemStack var1) {
         this.field_96567_c = var1;
      }

      public boolean func_180100_a(Entity var1) {
         if (!var1.isEntityAlive()) {
            return false;
         } else if (!(var1 instanceof EntityLivingBase)) {
            return false;
         } else {
            EntityLivingBase var2 = (EntityLivingBase)var1;
            return var2.getEquipmentInSlot(EntityLiving.getArmorPosition(this.field_96567_c)) != null ? false : (var2 instanceof EntityLiving ? ((EntityLiving)var2).canPickUpLoot() : (var2 instanceof EntityArmorStand ? true : var2 instanceof EntityPlayer));
         }
      }

      public boolean apply(Object var1) {
         return this.func_180100_a((Entity)var1);
      }
   }
}
