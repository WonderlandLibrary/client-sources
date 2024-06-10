/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ModelSnowMan
/*  7:   */   extends ModelBase
/*  8:   */ {
/*  9:   */   public ModelRenderer body;
/* 10:   */   public ModelRenderer bottomBody;
/* 11:   */   public ModelRenderer head;
/* 12:   */   public ModelRenderer rightHand;
/* 13:   */   public ModelRenderer leftHand;
/* 14:   */   private static final String __OBFID = "CL_00000859";
/* 15:   */   
/* 16:   */   public ModelSnowMan()
/* 17:   */   {
/* 18:17 */     float var1 = 4.0F;
/* 19:18 */     float var2 = 0.0F;
/* 20:19 */     this.head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
/* 21:20 */     this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, var2 - 0.5F);
/* 22:21 */     this.head.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
/* 23:22 */     this.rightHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
/* 24:23 */     this.rightHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, var2 - 0.5F);
/* 25:24 */     this.rightHand.setRotationPoint(0.0F, 0.0F + var1 + 9.0F - 7.0F, 0.0F);
/* 26:25 */     this.leftHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
/* 27:26 */     this.leftHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, var2 - 0.5F);
/* 28:27 */     this.leftHand.setRotationPoint(0.0F, 0.0F + var1 + 9.0F - 7.0F, 0.0F);
/* 29:28 */     this.body = new ModelRenderer(this, 0, 16).setTextureSize(64, 64);
/* 30:29 */     this.body.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, var2 - 0.5F);
/* 31:30 */     this.body.setRotationPoint(0.0F, 0.0F + var1 + 9.0F, 0.0F);
/* 32:31 */     this.bottomBody = new ModelRenderer(this, 0, 36).setTextureSize(64, 64);
/* 33:32 */     this.bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, var2 - 0.5F);
/* 34:33 */     this.bottomBody.setRotationPoint(0.0F, 0.0F + var1 + 20.0F, 0.0F);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 38:   */   {
/* 39:43 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/* 40:44 */     this.head.rotateAngleY = (par4 / 57.295776F);
/* 41:45 */     this.head.rotateAngleX = (par5 / 57.295776F);
/* 42:46 */     this.body.rotateAngleY = (par4 / 57.295776F * 0.25F);
/* 43:47 */     float var8 = MathHelper.sin(this.body.rotateAngleY);
/* 44:48 */     float var9 = MathHelper.cos(this.body.rotateAngleY);
/* 45:49 */     this.rightHand.rotateAngleZ = 1.0F;
/* 46:50 */     this.leftHand.rotateAngleZ = -1.0F;
/* 47:51 */     this.rightHand.rotateAngleY = (0.0F + this.body.rotateAngleY);
/* 48:52 */     this.leftHand.rotateAngleY = (3.141593F + this.body.rotateAngleY);
/* 49:53 */     this.rightHand.rotationPointX = (var9 * 5.0F);
/* 50:54 */     this.rightHand.rotationPointZ = (-var8 * 5.0F);
/* 51:55 */     this.leftHand.rotationPointX = (-var9 * 5.0F);
/* 52:56 */     this.leftHand.rotationPointZ = (var8 * 5.0F);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 56:   */   {
/* 57:64 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 58:65 */     this.body.render(par7);
/* 59:66 */     this.bottomBody.render(par7);
/* 60:67 */     this.head.render(par7);
/* 61:68 */     this.rightHand.render(par7);
/* 62:69 */     this.leftHand.render(par7);
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSnowMan
 * JD-Core Version:    0.7.0.1
 */