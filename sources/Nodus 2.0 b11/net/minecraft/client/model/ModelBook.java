/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ModelBook
/*  7:   */   extends ModelBase
/*  8:   */ {
/*  9: 9 */   public ModelRenderer coverRight = new ModelRenderer(this).setTextureOffset(0, 0).addBox(-6.0F, -5.0F, 0.0F, 6, 10, 0);
/* 10:12 */   public ModelRenderer coverLeft = new ModelRenderer(this).setTextureOffset(16, 0).addBox(0.0F, -5.0F, 0.0F, 6, 10, 0);
/* 11:15 */   public ModelRenderer pagesRight = new ModelRenderer(this).setTextureOffset(0, 10).addBox(0.0F, -4.0F, -0.99F, 5, 8, 1);
/* 12:18 */   public ModelRenderer pagesLeft = new ModelRenderer(this).setTextureOffset(12, 10).addBox(0.0F, -4.0F, -0.01F, 5, 8, 1);
/* 13:21 */   public ModelRenderer flippingPageRight = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
/* 14:24 */   public ModelRenderer flippingPageLeft = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
/* 15:27 */   public ModelRenderer bookSpine = new ModelRenderer(this).setTextureOffset(12, 0).addBox(-1.0F, -5.0F, 0.0F, 2, 10, 0);
/* 16:   */   private static final String __OBFID = "CL_00000833";
/* 17:   */   
/* 18:   */   public ModelBook()
/* 19:   */   {
/* 20:32 */     this.coverRight.setRotationPoint(0.0F, 0.0F, -1.0F);
/* 21:33 */     this.coverLeft.setRotationPoint(0.0F, 0.0F, 1.0F);
/* 22:34 */     this.bookSpine.rotateAngleY = 1.570796F;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 26:   */   {
/* 27:42 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 28:43 */     this.coverRight.render(par7);
/* 29:44 */     this.coverLeft.render(par7);
/* 30:45 */     this.bookSpine.render(par7);
/* 31:46 */     this.pagesRight.render(par7);
/* 32:47 */     this.pagesLeft.render(par7);
/* 33:48 */     this.flippingPageRight.render(par7);
/* 34:49 */     this.flippingPageLeft.render(par7);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 38:   */   {
/* 39:59 */     float var8 = (MathHelper.sin(par1 * 0.02F) * 0.1F + 1.25F) * par4;
/* 40:60 */     this.coverRight.rotateAngleY = (3.141593F + var8);
/* 41:61 */     this.coverLeft.rotateAngleY = (-var8);
/* 42:62 */     this.pagesRight.rotateAngleY = var8;
/* 43:63 */     this.pagesLeft.rotateAngleY = (-var8);
/* 44:64 */     this.flippingPageRight.rotateAngleY = (var8 - var8 * 2.0F * par2);
/* 45:65 */     this.flippingPageLeft.rotateAngleY = (var8 - var8 * 2.0F * par3);
/* 46:66 */     this.pagesRight.rotationPointX = MathHelper.sin(var8);
/* 47:67 */     this.pagesLeft.rotationPointX = MathHelper.sin(var8);
/* 48:68 */     this.flippingPageRight.rotationPointX = MathHelper.sin(var8);
/* 49:69 */     this.flippingPageLeft.rotationPointX = MathHelper.sin(var8);
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelBook
 * JD-Core Version:    0.7.0.1
 */