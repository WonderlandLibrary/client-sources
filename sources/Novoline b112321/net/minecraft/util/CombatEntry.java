package net.minecraft.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class CombatEntry {
   private final DamageSource damageSrc;
   private final int field_94567_b;
   private final float damage;
   private final float health;
   private final String field_94566_e;
   private final float fallDistance;

   public CombatEntry(DamageSource var1, int var2, float var3, float var4, String var5, float var6) {
      this.damageSrc = var1;
      this.field_94567_b = var2;
      this.damage = var4;
      this.health = var3;
      this.field_94566_e = var5;
      this.fallDistance = var6;
   }

   public DamageSource getDamageSrc() {
      return this.damageSrc;
   }

   public float func_94563_c() {
      return this.damage;
   }

   public boolean isLivingDamageSrc() {
      return this.damageSrc.getEntity() instanceof EntityLivingBase;
   }

   public String func_94562_g() {
      return this.field_94566_e;
   }

   public IChatComponent getDamageSrcDisplayName() {
      return this.getDamageSrc().getEntity() == null?null:this.getDamageSrc().getEntity().getDisplayName();
   }

   public float getDamageAmount() {
      return this.damageSrc == DamageSource.outOfWorld?Float.MAX_VALUE:this.fallDistance;
   }
}
