/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.passive.EntitySheep;
/*  6:   */ 
/*  7:   */ public class ModelSheep1
/*  8:   */   extends ModelQuadruped
/*  9:   */ {
/* 10:   */   private float field_78152_i;
/* 11:   */   private static final String __OBFID = "CL_00000852";
/* 12:   */   
/* 13:   */   public ModelSheep1()
/* 14:   */   {
/* 15:14 */     super(12, 0.0F);
/* 16:15 */     this.head = new ModelRenderer(this, 0, 0);
/* 17:16 */     this.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
/* 18:17 */     this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
/* 19:18 */     this.body = new ModelRenderer(this, 28, 8);
/* 20:19 */     this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
/* 21:20 */     this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
/* 22:21 */     float var1 = 0.5F;
/* 23:22 */     this.leg1 = new ModelRenderer(this, 0, 16);
/* 24:23 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
/* 25:24 */     this.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
/* 26:25 */     this.leg2 = new ModelRenderer(this, 0, 16);
/* 27:26 */     this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
/* 28:27 */     this.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
/* 29:28 */     this.leg3 = new ModelRenderer(this, 0, 16);
/* 30:29 */     this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
/* 31:30 */     this.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
/* 32:31 */     this.leg4 = new ModelRenderer(this, 0, 16);
/* 33:32 */     this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
/* 34:33 */     this.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 38:   */   {
/* 39:42 */     super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
/* 40:43 */     this.head.rotationPointY = (6.0F + ((EntitySheep)par1EntityLivingBase).func_70894_j(par4) * 9.0F);
/* 41:44 */     this.field_78152_i = ((EntitySheep)par1EntityLivingBase).func_70890_k(par4);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 45:   */   {
/* 46:54 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/* 47:55 */     this.head.rotateAngleX = this.field_78152_i;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSheep1
 * JD-Core Version:    0.7.0.1
 */