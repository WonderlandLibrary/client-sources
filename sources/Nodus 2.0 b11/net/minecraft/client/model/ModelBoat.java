/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ 
/*  5:   */ public class ModelBoat
/*  6:   */   extends ModelBase
/*  7:   */ {
/*  8: 7 */   public ModelRenderer[] boatSides = new ModelRenderer[5];
/*  9:   */   private static final String __OBFID = "CL_00000832";
/* 10:   */   
/* 11:   */   public ModelBoat()
/* 12:   */   {
/* 13:12 */     this.boatSides[0] = new ModelRenderer(this, 0, 8);
/* 14:13 */     this.boatSides[1] = new ModelRenderer(this, 0, 0);
/* 15:14 */     this.boatSides[2] = new ModelRenderer(this, 0, 0);
/* 16:15 */     this.boatSides[3] = new ModelRenderer(this, 0, 0);
/* 17:16 */     this.boatSides[4] = new ModelRenderer(this, 0, 0);
/* 18:17 */     byte var1 = 24;
/* 19:18 */     byte var2 = 6;
/* 20:19 */     byte var3 = 20;
/* 21:20 */     byte var4 = 4;
/* 22:21 */     this.boatSides[0].addBox(-var1 / 2, -var3 / 2 + 2, -3.0F, var1, var3 - 4, 4, 0.0F);
/* 23:22 */     this.boatSides[0].setRotationPoint(0.0F, var4, 0.0F);
/* 24:23 */     this.boatSides[1].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
/* 25:24 */     this.boatSides[1].setRotationPoint(-var1 / 2 + 1, var4, 0.0F);
/* 26:25 */     this.boatSides[2].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
/* 27:26 */     this.boatSides[2].setRotationPoint(var1 / 2 - 1, var4, 0.0F);
/* 28:27 */     this.boatSides[3].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
/* 29:28 */     this.boatSides[3].setRotationPoint(0.0F, var4, -var3 / 2 + 1);
/* 30:29 */     this.boatSides[4].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
/* 31:30 */     this.boatSides[4].setRotationPoint(0.0F, var4, var3 / 2 - 1);
/* 32:31 */     this.boatSides[0].rotateAngleX = 1.570796F;
/* 33:32 */     this.boatSides[1].rotateAngleY = 4.712389F;
/* 34:33 */     this.boatSides[2].rotateAngleY = 1.570796F;
/* 35:34 */     this.boatSides[3].rotateAngleY = 3.141593F;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 39:   */   {
/* 40:42 */     for (int var8 = 0; var8 < 5; var8++) {
/* 41:44 */       this.boatSides[var8].render(par7);
/* 42:   */     }
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelBoat
 * JD-Core Version:    0.7.0.1
 */