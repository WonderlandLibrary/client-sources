/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.passive.EntitySheep;
/*  6:   */ 
/*  7:   */ public class ModelSheep2
/*  8:   */   extends ModelQuadruped
/*  9:   */ {
/* 10:   */   private float field_78153_i;
/* 11:   */   private static final String __OBFID = "CL_00000853";
/* 12:   */   
/* 13:   */   public ModelSheep2()
/* 14:   */   {
/* 15:14 */     super(12, 0.0F);
/* 16:15 */     this.head = new ModelRenderer(this, 0, 0);
/* 17:16 */     this.head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
/* 18:17 */     this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
/* 19:18 */     this.body = new ModelRenderer(this, 28, 8);
/* 20:19 */     this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
/* 21:20 */     this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 25:   */   {
/* 26:29 */     super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
/* 27:30 */     this.head.rotationPointY = (6.0F + ((EntitySheep)par1EntityLivingBase).func_70894_j(par4) * 9.0F);
/* 28:31 */     this.field_78153_i = ((EntitySheep)par1EntityLivingBase).func_70890_k(par4);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 32:   */   {
/* 33:41 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/* 34:42 */     this.head.rotateAngleX = this.field_78153_i;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSheep2
 * JD-Core Version:    0.7.0.1
 */