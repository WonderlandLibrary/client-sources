/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ModelCreeper
/*  7:   */   extends ModelBase
/*  8:   */ {
/*  9:   */   public ModelRenderer head;
/* 10:   */   public ModelRenderer field_78133_b;
/* 11:   */   public ModelRenderer body;
/* 12:   */   public ModelRenderer leg1;
/* 13:   */   public ModelRenderer leg2;
/* 14:   */   public ModelRenderer leg3;
/* 15:   */   public ModelRenderer leg4;
/* 16:   */   private static final String __OBFID = "CL_00000837";
/* 17:   */   
/* 18:   */   public ModelCreeper()
/* 19:   */   {
/* 20:19 */     this(0.0F);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ModelCreeper(float par1)
/* 24:   */   {
/* 25:24 */     byte var2 = 4;
/* 26:25 */     this.head = new ModelRenderer(this, 0, 0);
/* 27:26 */     this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1);
/* 28:27 */     this.head.setRotationPoint(0.0F, var2, 0.0F);
/* 29:28 */     this.field_78133_b = new ModelRenderer(this, 32, 0);
/* 30:29 */     this.field_78133_b.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1 + 0.5F);
/* 31:30 */     this.field_78133_b.setRotationPoint(0.0F, var2, 0.0F);
/* 32:31 */     this.body = new ModelRenderer(this, 16, 16);
/* 33:32 */     this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, par1);
/* 34:33 */     this.body.setRotationPoint(0.0F, var2, 0.0F);
/* 35:34 */     this.leg1 = new ModelRenderer(this, 0, 16);
/* 36:35 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, par1);
/* 37:36 */     this.leg1.setRotationPoint(-2.0F, 12 + var2, 4.0F);
/* 38:37 */     this.leg2 = new ModelRenderer(this, 0, 16);
/* 39:38 */     this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, par1);
/* 40:39 */     this.leg2.setRotationPoint(2.0F, 12 + var2, 4.0F);
/* 41:40 */     this.leg3 = new ModelRenderer(this, 0, 16);
/* 42:41 */     this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, par1);
/* 43:42 */     this.leg3.setRotationPoint(-2.0F, 12 + var2, -4.0F);
/* 44:43 */     this.leg4 = new ModelRenderer(this, 0, 16);
/* 45:44 */     this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, par1);
/* 46:45 */     this.leg4.setRotationPoint(2.0F, 12 + var2, -4.0F);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 50:   */   {
/* 51:53 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 52:54 */     this.head.render(par7);
/* 53:55 */     this.body.render(par7);
/* 54:56 */     this.leg1.render(par7);
/* 55:57 */     this.leg2.render(par7);
/* 56:58 */     this.leg3.render(par7);
/* 57:59 */     this.leg4.render(par7);
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 61:   */   {
/* 62:69 */     this.head.rotateAngleY = (par4 / 57.295776F);
/* 63:70 */     this.head.rotateAngleX = (par5 / 57.295776F);
/* 64:71 */     this.leg1.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.4F * par2);
/* 65:72 */     this.leg2.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2);
/* 66:73 */     this.leg3.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2);
/* 67:74 */     this.leg4.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.4F * par2);
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelCreeper
 * JD-Core Version:    0.7.0.1
 */