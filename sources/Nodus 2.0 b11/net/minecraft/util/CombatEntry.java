/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ 
/*  6:   */ public class CombatEntry
/*  7:   */ {
/*  8:   */   private final DamageSource damageSrc;
/*  9:   */   private final int field_94567_b;
/* 10:   */   private final float field_94568_c;
/* 11:   */   private final float field_94565_d;
/* 12:   */   private final String field_94566_e;
/* 13:   */   private final float field_94564_f;
/* 14:   */   private static final String __OBFID = "CL_00001519";
/* 15:   */   
/* 16:   */   public CombatEntry(DamageSource par1DamageSource, int par2, float par3, float par4, String par5Str, float par6)
/* 17:   */   {
/* 18:17 */     this.damageSrc = par1DamageSource;
/* 19:18 */     this.field_94567_b = par2;
/* 20:19 */     this.field_94568_c = par4;
/* 21:20 */     this.field_94565_d = par3;
/* 22:21 */     this.field_94566_e = par5Str;
/* 23:22 */     this.field_94564_f = par6;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public DamageSource getDamageSrc()
/* 27:   */   {
/* 28:30 */     return this.damageSrc;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public float func_94563_c()
/* 32:   */   {
/* 33:35 */     return this.field_94568_c;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean func_94559_f()
/* 37:   */   {
/* 38:40 */     return this.damageSrc.getEntity() instanceof EntityLivingBase;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String func_94562_g()
/* 42:   */   {
/* 43:45 */     return this.field_94566_e;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public IChatComponent func_151522_h()
/* 47:   */   {
/* 48:50 */     return getDamageSrc().getEntity() == null ? null : getDamageSrc().getEntity().func_145748_c_();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public float func_94561_i()
/* 52:   */   {
/* 53:55 */     return this.damageSrc == DamageSource.outOfWorld ? 3.4028235E+38F : this.field_94564_f;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.CombatEntry
 * JD-Core Version:    0.7.0.1
 */