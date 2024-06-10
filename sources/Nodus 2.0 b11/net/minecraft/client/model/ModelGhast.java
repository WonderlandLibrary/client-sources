/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.util.MathHelper;
/*  6:   */ import org.lwjgl.opengl.GL11;
/*  7:   */ 
/*  8:   */ public class ModelGhast
/*  9:   */   extends ModelBase
/* 10:   */ {
/* 11:   */   ModelRenderer body;
/* 12:11 */   ModelRenderer[] tentacles = new ModelRenderer[9];
/* 13:   */   private static final String __OBFID = "CL_00000839";
/* 14:   */   
/* 15:   */   public ModelGhast()
/* 16:   */   {
/* 17:16 */     byte var1 = -16;
/* 18:17 */     this.body = new ModelRenderer(this, 0, 0);
/* 19:18 */     this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
/* 20:19 */     this.body.rotationPointY += 24 + var1;
/* 21:20 */     Random var2 = new Random(1660L);
/* 22:22 */     for (int var3 = 0; var3 < this.tentacles.length; var3++)
/* 23:   */     {
/* 24:24 */       this.tentacles[var3] = new ModelRenderer(this, 0, 0);
/* 25:25 */       float var4 = ((var3 % 3 - var3 / 3 % 2 * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 26:26 */       float var5 = (var3 / 3 / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 27:27 */       int var6 = var2.nextInt(7) + 8;
/* 28:28 */       this.tentacles[var3].addBox(-1.0F, 0.0F, -1.0F, 2, var6, 2);
/* 29:29 */       this.tentacles[var3].rotationPointX = var4;
/* 30:30 */       this.tentacles[var3].rotationPointZ = var5;
/* 31:31 */       this.tentacles[var3].rotationPointY = (31 + var1);
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 36:   */   {
/* 37:42 */     for (int var8 = 0; var8 < this.tentacles.length; var8++) {
/* 38:44 */       this.tentacles[var8].rotateAngleX = (0.2F * MathHelper.sin(par3 * 0.3F + var8) + 0.4F);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 43:   */   {
/* 44:53 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 45:54 */     GL11.glPushMatrix();
/* 46:55 */     GL11.glTranslatef(0.0F, 0.6F, 0.0F);
/* 47:56 */     this.body.render(par7);
/* 48:57 */     ModelRenderer[] var8 = this.tentacles;
/* 49:58 */     int var9 = var8.length;
/* 50:60 */     for (int var10 = 0; var10 < var9; var10++)
/* 51:   */     {
/* 52:62 */       ModelRenderer var11 = var8[var10];
/* 53:63 */       var11.render(par7);
/* 54:   */     }
/* 55:66 */     GL11.glPopMatrix();
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelGhast
 * JD-Core Version:    0.7.0.1
 */