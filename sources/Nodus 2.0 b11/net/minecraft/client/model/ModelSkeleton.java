/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.monster.EntitySkeleton;
/*  6:   */ 
/*  7:   */ public class ModelSkeleton
/*  8:   */   extends ModelZombie
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000857";
/* 11:   */   
/* 12:   */   public ModelSkeleton()
/* 13:   */   {
/* 14:13 */     this(0.0F);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ModelSkeleton(float par1)
/* 18:   */   {
/* 19:18 */     super(par1, 0.0F, 64, 32);
/* 20:19 */     this.bipedRightArm = new ModelRenderer(this, 40, 16);
/* 21:20 */     this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, par1);
/* 22:21 */     this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
/* 23:22 */     this.bipedLeftArm = new ModelRenderer(this, 40, 16);
/* 24:23 */     this.bipedLeftArm.mirror = true;
/* 25:24 */     this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, par1);
/* 26:25 */     this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
/* 27:26 */     this.bipedRightLeg = new ModelRenderer(this, 0, 16);
/* 28:27 */     this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, par1);
/* 29:28 */     this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
/* 30:29 */     this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
/* 31:30 */     this.bipedLeftLeg.mirror = true;
/* 32:31 */     this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, par1);
/* 33:32 */     this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 37:   */   {
/* 38:41 */     this.aimedBow = (((EntitySkeleton)par1EntityLivingBase).getSkeletonType() == 1);
/* 39:42 */     super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 43:   */   {
/* 44:52 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSkeleton
 * JD-Core Version:    0.7.0.1
 */