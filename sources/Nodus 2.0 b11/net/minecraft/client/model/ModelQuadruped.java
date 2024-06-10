/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ import org.lwjgl.opengl.GL11;
/*  6:   */ 
/*  7:   */ public class ModelQuadruped
/*  8:   */   extends ModelBase
/*  9:   */ {
/* 10: 9 */   public ModelRenderer head = new ModelRenderer(this, 0, 0);
/* 11:   */   public ModelRenderer body;
/* 12:   */   public ModelRenderer leg1;
/* 13:   */   public ModelRenderer leg2;
/* 14:   */   public ModelRenderer leg3;
/* 15:   */   public ModelRenderer leg4;
/* 16:15 */   protected float field_78145_g = 8.0F;
/* 17:16 */   protected float field_78151_h = 4.0F;
/* 18:   */   private static final String __OBFID = "CL_00000851";
/* 19:   */   
/* 20:   */   public ModelQuadruped(int par1, float par2)
/* 21:   */   {
/* 22:21 */     this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, par2);
/* 23:22 */     this.head.setRotationPoint(0.0F, 18 - par1, -6.0F);
/* 24:23 */     this.body = new ModelRenderer(this, 28, 8);
/* 25:24 */     this.body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, par2);
/* 26:25 */     this.body.setRotationPoint(0.0F, 17 - par1, 2.0F);
/* 27:26 */     this.leg1 = new ModelRenderer(this, 0, 16);
/* 28:27 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, par1, 4, par2);
/* 29:28 */     this.leg1.setRotationPoint(-3.0F, 24 - par1, 7.0F);
/* 30:29 */     this.leg2 = new ModelRenderer(this, 0, 16);
/* 31:30 */     this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, par1, 4, par2);
/* 32:31 */     this.leg2.setRotationPoint(3.0F, 24 - par1, 7.0F);
/* 33:32 */     this.leg3 = new ModelRenderer(this, 0, 16);
/* 34:33 */     this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, par1, 4, par2);
/* 35:34 */     this.leg3.setRotationPoint(-3.0F, 24 - par1, -5.0F);
/* 36:35 */     this.leg4 = new ModelRenderer(this, 0, 16);
/* 37:36 */     this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, par1, 4, par2);
/* 38:37 */     this.leg4.setRotationPoint(3.0F, 24 - par1, -5.0F);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 42:   */   {
/* 43:45 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 44:47 */     if (this.isChild)
/* 45:   */     {
/* 46:49 */       float var8 = 2.0F;
/* 47:50 */       GL11.glPushMatrix();
/* 48:51 */       GL11.glTranslatef(0.0F, this.field_78145_g * par7, this.field_78151_h * par7);
/* 49:52 */       this.head.render(par7);
/* 50:53 */       GL11.glPopMatrix();
/* 51:54 */       GL11.glPushMatrix();
/* 52:55 */       GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
/* 53:56 */       GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
/* 54:57 */       this.body.render(par7);
/* 55:58 */       this.leg1.render(par7);
/* 56:59 */       this.leg2.render(par7);
/* 57:60 */       this.leg3.render(par7);
/* 58:61 */       this.leg4.render(par7);
/* 59:62 */       GL11.glPopMatrix();
/* 60:   */     }
/* 61:   */     else
/* 62:   */     {
/* 63:66 */       this.head.render(par7);
/* 64:67 */       this.body.render(par7);
/* 65:68 */       this.leg1.render(par7);
/* 66:69 */       this.leg2.render(par7);
/* 67:70 */       this.leg3.render(par7);
/* 68:71 */       this.leg4.render(par7);
/* 69:   */     }
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 73:   */   {
/* 74:82 */     float var8 = 57.295776F;
/* 75:83 */     this.head.rotateAngleX = (par5 / 57.295776F);
/* 76:84 */     this.head.rotateAngleY = (par4 / 57.295776F);
/* 77:85 */     this.body.rotateAngleX = 1.570796F;
/* 78:86 */     this.leg1.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.4F * par2);
/* 79:87 */     this.leg2.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2);
/* 80:88 */     this.leg3.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2);
/* 81:89 */     this.leg4.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.4F * par2);
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelQuadruped
 * JD-Core Version:    0.7.0.1
 */