/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import org.lwjgl.opengl.GL11;
/*  5:   */ 
/*  6:   */ public class ModelEnderCrystal
/*  7:   */   extends ModelBase
/*  8:   */ {
/*  9:   */   private ModelRenderer cube;
/* 10:12 */   private ModelRenderer glass = new ModelRenderer(this, "glass");
/* 11:   */   private ModelRenderer base;
/* 12:   */   private static final String __OBFID = "CL_00000871";
/* 13:   */   
/* 14:   */   public ModelEnderCrystal(float par1, boolean par2)
/* 15:   */   {
/* 16:20 */     this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/* 17:21 */     this.cube = new ModelRenderer(this, "cube");
/* 18:22 */     this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/* 19:24 */     if (par2)
/* 20:   */     {
/* 21:26 */       this.base = new ModelRenderer(this, "base");
/* 22:27 */       this.base.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 27:   */   {
/* 28:36 */     GL11.glPushMatrix();
/* 29:37 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 30:38 */     GL11.glTranslatef(0.0F, -0.5F, 0.0F);
/* 31:40 */     if (this.base != null) {
/* 32:42 */       this.base.render(par7);
/* 33:   */     }
/* 34:45 */     GL11.glRotatef(par3, 0.0F, 1.0F, 0.0F);
/* 35:46 */     GL11.glTranslatef(0.0F, 0.8F + par4, 0.0F);
/* 36:47 */     GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 37:48 */     this.glass.render(par7);
/* 38:49 */     float var8 = 0.875F;
/* 39:50 */     GL11.glScalef(var8, var8, var8);
/* 40:51 */     GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 41:52 */     GL11.glRotatef(par3, 0.0F, 1.0F, 0.0F);
/* 42:53 */     this.glass.render(par7);
/* 43:54 */     GL11.glScalef(var8, var8, var8);
/* 44:55 */     GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 45:56 */     GL11.glRotatef(par3, 0.0F, 1.0F, 0.0F);
/* 46:57 */     this.cube.render(par7);
/* 47:58 */     GL11.glPopMatrix();
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelEnderCrystal
 * JD-Core Version:    0.7.0.1
 */