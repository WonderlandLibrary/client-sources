package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class EntityDamageSourceIndirect extends EntityDamageSource {
   private Entity indirectEntity;

   public EntityDamageSourceIndirect(String var1, Entity var2, Entity var3) {
      super(var1, var2);
      this.indirectEntity = var3;
   }

   public Entity getSourceOfDamage() {
      return this.damageSourceEntity;
   }

   public Entity getEntity() {
      return this.indirectEntity;
   }

   public IChatComponent getDeathMessage(EntityLivingBase var1) {
      IChatComponent var2 = this.indirectEntity == null?this.damageSourceEntity.getDisplayName():this.indirectEntity.getDisplayName();
      ItemStack var3 = this.indirectEntity instanceof EntityLivingBase?((EntityLivingBase)this.indirectEntity).getHeldItem():null;
      String var4 = "death.attack." + this.damageType;
      String var5 = var4 + ".item";
      return var3.hasDisplayName() && StatCollector.canTranslate(var5)?new ChatComponentTranslation(var5, new Object[]{var1.getDisplayName(), var2, var3.getChatComponent()}):new ChatComponentTranslation(var4, new Object[]{var1.getDisplayName(), var2});
   }
}
