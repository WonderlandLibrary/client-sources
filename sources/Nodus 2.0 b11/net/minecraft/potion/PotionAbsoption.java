/*  1:   */ package net.minecraft.potion;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLivingBase;
/*  4:   */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*  5:   */ 
/*  6:   */ public class PotionAbsoption
/*  7:   */   extends Potion
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00001524";
/* 10:   */   
/* 11:   */   protected PotionAbsoption(int par1, boolean par2, int par3)
/* 12:   */   {
/* 13:12 */     super(par1, par2, par3);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void removeAttributesModifiersFromEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3)
/* 17:   */   {
/* 18:17 */     par1EntityLivingBase.setAbsorptionAmount(par1EntityLivingBase.getAbsorptionAmount() - 4 * (par3 + 1));
/* 19:18 */     super.removeAttributesModifiersFromEntity(par1EntityLivingBase, par2BaseAttributeMap, par3);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void applyAttributesModifiersToEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3)
/* 23:   */   {
/* 24:23 */     par1EntityLivingBase.setAbsorptionAmount(par1EntityLivingBase.getAbsorptionAmount() + 4 * (par3 + 1));
/* 25:24 */     super.applyAttributesModifiersToEntity(par1EntityLivingBase, par2BaseAttributeMap, par3);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.potion.PotionAbsoption
 * JD-Core Version:    0.7.0.1
 */