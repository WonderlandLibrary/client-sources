/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ 
/*  5:   */ public class ModelSquid
/*  6:   */   extends ModelBase
/*  7:   */ {
/*  8:   */   ModelRenderer squidBody;
/*  9:11 */   ModelRenderer[] squidTentacles = new ModelRenderer[8];
/* 10:   */   private static final String __OBFID = "CL_00000861";
/* 11:   */   
/* 12:   */   public ModelSquid()
/* 13:   */   {
/* 14:16 */     byte var1 = -16;
/* 15:17 */     this.squidBody = new ModelRenderer(this, 0, 0);
/* 16:18 */     this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
/* 17:19 */     this.squidBody.rotationPointY += 24 + var1;
/* 18:21 */     for (int var2 = 0; var2 < this.squidTentacles.length; var2++)
/* 19:   */     {
/* 20:23 */       this.squidTentacles[var2] = new ModelRenderer(this, 48, 0);
/* 21:24 */       double var3 = var2 * 3.141592653589793D * 2.0D / this.squidTentacles.length;
/* 22:25 */       float var5 = (float)Math.cos(var3) * 5.0F;
/* 23:26 */       float var6 = (float)Math.sin(var3) * 5.0F;
/* 24:27 */       this.squidTentacles[var2].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
/* 25:28 */       this.squidTentacles[var2].rotationPointX = var5;
/* 26:29 */       this.squidTentacles[var2].rotationPointZ = var6;
/* 27:30 */       this.squidTentacles[var2].rotationPointY = (31 + var1);
/* 28:31 */       var3 = var2 * 3.141592653589793D * -2.0D / this.squidTentacles.length + 1.570796326794897D;
/* 29:32 */       this.squidTentacles[var2].rotateAngleY = ((float)var3);
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 34:   */   {
/* 35:43 */     ModelRenderer[] var8 = this.squidTentacles;
/* 36:44 */     int var9 = var8.length;
/* 37:46 */     for (int var10 = 0; var10 < var9; var10++)
/* 38:   */     {
/* 39:48 */       ModelRenderer var11 = var8[var10];
/* 40:49 */       var11.rotateAngleX = par3;
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 45:   */   {
/* 46:58 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 47:59 */     this.squidBody.render(par7);
/* 48:61 */     for (int var8 = 0; var8 < this.squidTentacles.length; var8++) {
/* 49:63 */       this.squidTentacles[var8].render(par7);
/* 50:   */     }
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSquid
 * JD-Core Version:    0.7.0.1
 */