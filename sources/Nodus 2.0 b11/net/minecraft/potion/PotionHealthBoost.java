/*  1:   */ package net.minecraft.potion;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLivingBase;
/*  4:   */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*  5:   */ 
/*  6:   */ public class PotionHealthBoost
/*  7:   */   extends Potion
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00001526";
/* 10:   */   
/* 11:   */   public PotionHealthBoost(int par1, boolean par2, int par3)
/* 12:   */   {
/* 13:12 */     super(par1, par2, par3);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void removeAttributesModifiersFromEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3)
/* 17:   */   {
/* 18:17 */     super.removeAttributesModifiersFromEntity(par1EntityLivingBase, par2BaseAttributeMap, par3);
/* 19:19 */     if (par1EntityLivingBase.getHealth() > par1EntityLivingBase.getMaxHealth()) {
/* 20:21 */       par1EntityLivingBase.setHealth(par1EntityLivingBase.getMaxHealth());
/* 21:   */     }
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.potion.PotionHealthBoost
 * JD-Core Version:    0.7.0.1
 */