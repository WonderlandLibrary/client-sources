package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityDamageSource extends DamageSource {
   private static final String __OBFID = "CL_00001522";
   protected Entity damageSourceEntity;
   private boolean field_180140_r = false;

   public EntityDamageSource(String var1, Entity var2) {
      super(var1);
      this.damageSourceEntity = var2;
   }

   public boolean isDifficultyScaled() {
      return this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof EntityPlayer);
   }

   public boolean func_180139_w() {
      return this.field_180140_r;
   }

   public EntityDamageSource func_180138_v() {
      this.field_180140_r = true;
      return this;
   }

   public IChatComponent getDeathMessage(EntityLivingBase var1) {
      ItemStack var2 = this.damageSourceEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
      String var3 = String.valueOf((new StringBuilder("death.attack.")).append(this.damageType));
      String var4 = String.valueOf((new StringBuilder(String.valueOf(var3))).append(".item"));
      return var2 != null && var2.hasDisplayName() && StatCollector.canTranslate(var4) ? new ChatComponentTranslation(var4, new Object[]{var1.getDisplayName(), this.damageSourceEntity.getDisplayName(), var2.getChatComponent()}) : new ChatComponentTranslation(var3, new Object[]{var1.getDisplayName(), this.damageSourceEntity.getDisplayName()});
   }

   public Entity getEntity() {
      return this.damageSourceEntity;
   }
}
