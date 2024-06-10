/*  1:   */ package net.minecraft.potion;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  4:   */ 
/*  5:   */ public class PotionAttackDamage
/*  6:   */   extends Potion
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00001525";
/*  9:   */   
/* 10:   */   protected PotionAttackDamage(int par1, boolean par2, int par3)
/* 11:   */   {
/* 12:11 */     super(par1, par2, par3);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public double func_111183_a(int par1, AttributeModifier par2AttributeModifier)
/* 16:   */   {
/* 17:16 */     return this.id == Potion.weakness.id ? -0.5F * (par1 + 1) : 1.3D * (par1 + 1);
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.potion.PotionAttackDamage
 * JD-Core Version:    0.7.0.1
 */