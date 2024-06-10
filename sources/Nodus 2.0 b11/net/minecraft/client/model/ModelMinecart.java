/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ 
/*  5:   */ public class ModelMinecart
/*  6:   */   extends ModelBase
/*  7:   */ {
/*  8: 7 */   public ModelRenderer[] sideModels = new ModelRenderer[7];
/*  9:   */   private static final String __OBFID = "CL_00000844";
/* 10:   */   
/* 11:   */   public ModelMinecart()
/* 12:   */   {
/* 13:12 */     this.sideModels[0] = new ModelRenderer(this, 0, 10);
/* 14:13 */     this.sideModels[1] = new ModelRenderer(this, 0, 0);
/* 15:14 */     this.sideModels[2] = new ModelRenderer(this, 0, 0);
/* 16:15 */     this.sideModels[3] = new ModelRenderer(this, 0, 0);
/* 17:16 */     this.sideModels[4] = new ModelRenderer(this, 0, 0);
/* 18:17 */     this.sideModels[5] = new ModelRenderer(this, 44, 10);
/* 19:18 */     byte var1 = 20;
/* 20:19 */     byte var2 = 8;
/* 21:20 */     byte var3 = 16;
/* 22:21 */     byte var4 = 4;
/* 23:22 */     this.sideModels[0].addBox(-var1 / 2, -var3 / 2, -1.0F, var1, var3, 2, 0.0F);
/* 24:23 */     this.sideModels[0].setRotationPoint(0.0F, var4, 0.0F);
/* 25:24 */     this.sideModels[5].addBox(-var1 / 2 + 1, -var3 / 2 + 1, -1.0F, var1 - 2, var3 - 2, 1, 0.0F);
/* 26:25 */     this.sideModels[5].setRotationPoint(0.0F, var4, 0.0F);
/* 27:26 */     this.sideModels[1].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
/* 28:27 */     this.sideModels[1].setRotationPoint(-var1 / 2 + 1, var4, 0.0F);
/* 29:28 */     this.sideModels[2].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
/* 30:29 */     this.sideModels[2].setRotationPoint(var1 / 2 - 1, var4, 0.0F);
/* 31:30 */     this.sideModels[3].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
/* 32:31 */     this.sideModels[3].setRotationPoint(0.0F, var4, -var3 / 2 + 1);
/* 33:32 */     this.sideModels[4].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
/* 34:33 */     this.sideModels[4].setRotationPoint(0.0F, var4, var3 / 2 - 1);
/* 35:34 */     this.sideModels[0].rotateAngleX = 1.570796F;
/* 36:35 */     this.sideModels[1].rotateAngleY = 4.712389F;
/* 37:36 */     this.sideModels[2].rotateAngleY = 1.570796F;
/* 38:37 */     this.sideModels[3].rotateAngleY = 3.141593F;
/* 39:38 */     this.sideModels[5].rotateAngleX = -1.570796F;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 43:   */   {
/* 44:46 */     this.sideModels[5].rotationPointY = (4.0F - par4);
/* 45:48 */     for (int var8 = 0; var8 < 6; var8++) {
/* 46:50 */       this.sideModels[var8].render(par7);
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelMinecart
 * JD-Core Version:    0.7.0.1
 */