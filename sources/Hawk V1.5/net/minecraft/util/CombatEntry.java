package net.minecraft.util;

import net.minecraft.entity.EntityLivingBase;

public class CombatEntry {
   private final DamageSource damageSrc;
   private final float field_94564_f;
   private final int field_94567_b;
   private static final String __OBFID = "CL_00001519";
   private final float field_94565_d;
   private final String field_94566_e;
   private final float field_94568_c;

   public CombatEntry(DamageSource var1, int var2, float var3, float var4, String var5, float var6) {
      this.damageSrc = var1;
      this.field_94567_b = var2;
      this.field_94568_c = var4;
      this.field_94565_d = var3;
      this.field_94566_e = var5;
      this.field_94564_f = var6;
   }

   public IChatComponent func_151522_h() {
      return this.getDamageSrc().getEntity() == null ? null : this.getDamageSrc().getEntity().getDisplayName();
   }

   public float func_94563_c() {
      return this.field_94568_c;
   }

   public boolean func_94559_f() {
      return this.damageSrc.getEntity() instanceof EntityLivingBase;
   }

   public DamageSource getDamageSrc() {
      return this.damageSrc;
   }

   public String func_94562_g() {
      return this.field_94566_e;
   }

   public float func_94561_i() {
      return this.damageSrc == DamageSource.outOfWorld ? Float.MAX_VALUE : this.field_94564_f;
   }
}
