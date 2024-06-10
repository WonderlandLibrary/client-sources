/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ 
/*  5:   */ public class ModelSlime
/*  6:   */   extends ModelBase
/*  7:   */ {
/*  8:   */   ModelRenderer slimeBodies;
/*  9:   */   ModelRenderer slimeRightEye;
/* 10:   */   ModelRenderer slimeLeftEye;
/* 11:   */   ModelRenderer slimeMouth;
/* 12:   */   private static final String __OBFID = "CL_00000858";
/* 13:   */   
/* 14:   */   public ModelSlime(int par1)
/* 15:   */   {
/* 16:22 */     this.slimeBodies = new ModelRenderer(this, 0, par1);
/* 17:23 */     this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
/* 18:25 */     if (par1 > 0)
/* 19:   */     {
/* 20:27 */       this.slimeBodies = new ModelRenderer(this, 0, par1);
/* 21:28 */       this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
/* 22:29 */       this.slimeRightEye = new ModelRenderer(this, 32, 0);
/* 23:30 */       this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
/* 24:31 */       this.slimeLeftEye = new ModelRenderer(this, 32, 4);
/* 25:32 */       this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
/* 26:33 */       this.slimeMouth = new ModelRenderer(this, 32, 8);
/* 27:34 */       this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 32:   */   {
/* 33:43 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 34:44 */     this.slimeBodies.render(par7);
/* 35:46 */     if (this.slimeRightEye != null)
/* 36:   */     {
/* 37:48 */       this.slimeRightEye.render(par7);
/* 38:49 */       this.slimeLeftEye.render(par7);
/* 39:50 */       this.slimeMouth.render(par7);
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSlime
 * JD-Core Version:    0.7.0.1
 */