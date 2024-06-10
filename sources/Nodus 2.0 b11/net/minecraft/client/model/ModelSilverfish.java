/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ModelSilverfish
/*  7:   */   extends ModelBase
/*  8:   */ {
/*  9: 9 */   private ModelRenderer[] silverfishBodyParts = new ModelRenderer[7];
/* 10:   */   private ModelRenderer[] silverfishWings;
/* 11:13 */   private float[] field_78170_c = new float[7];
/* 12:16 */   private static final int[][] silverfishBoxLength = { { 3, 2, 2 }, { 4, 3, 2 }, { 6, 4, 3 }, { 3, 3, 3 }, { 2, 2, 3 }, { 2, 1, 2 }, { 1, 1, 2 } };
/* 13:19 */   private static final int[][] silverfishTexturePositions = { new int[2], { 0, 4 }, { 0, 9 }, { 0, 16 }, { 0, 22 }, { 11 }, { 13, 4 } };
/* 14:   */   private static final String __OBFID = "CL_00000855";
/* 15:   */   
/* 16:   */   public ModelSilverfish()
/* 17:   */   {
/* 18:24 */     float var1 = -3.5F;
/* 19:26 */     for (int var2 = 0; var2 < this.silverfishBodyParts.length; var2++)
/* 20:   */     {
/* 21:28 */       this.silverfishBodyParts[var2] = new ModelRenderer(this, silverfishTexturePositions[var2][0], silverfishTexturePositions[var2][1]);
/* 22:29 */       this.silverfishBodyParts[var2].addBox(silverfishBoxLength[var2][0] * -0.5F, 0.0F, silverfishBoxLength[var2][2] * -0.5F, silverfishBoxLength[var2][0], silverfishBoxLength[var2][1], silverfishBoxLength[var2][2]);
/* 23:30 */       this.silverfishBodyParts[var2].setRotationPoint(0.0F, 24 - silverfishBoxLength[var2][1], var1);
/* 24:31 */       this.field_78170_c[var2] = var1;
/* 25:33 */       if (var2 < this.silverfishBodyParts.length - 1) {
/* 26:35 */         var1 += (silverfishBoxLength[var2][2] + silverfishBoxLength[(var2 + 1)][2]) * 0.5F;
/* 27:   */       }
/* 28:   */     }
/* 29:39 */     this.silverfishWings = new ModelRenderer[3];
/* 30:40 */     this.silverfishWings[0] = new ModelRenderer(this, 20, 0);
/* 31:41 */     this.silverfishWings[0].addBox(-5.0F, 0.0F, silverfishBoxLength[2][2] * -0.5F, 10, 8, silverfishBoxLength[2][2]);
/* 32:42 */     this.silverfishWings[0].setRotationPoint(0.0F, 16.0F, this.field_78170_c[2]);
/* 33:43 */     this.silverfishWings[1] = new ModelRenderer(this, 20, 11);
/* 34:44 */     this.silverfishWings[1].addBox(-3.0F, 0.0F, silverfishBoxLength[4][2] * -0.5F, 6, 4, silverfishBoxLength[4][2]);
/* 35:45 */     this.silverfishWings[1].setRotationPoint(0.0F, 20.0F, this.field_78170_c[4]);
/* 36:46 */     this.silverfishWings[2] = new ModelRenderer(this, 20, 18);
/* 37:47 */     this.silverfishWings[2].addBox(-3.0F, 0.0F, silverfishBoxLength[4][2] * -0.5F, 6, 5, silverfishBoxLength[1][2]);
/* 38:48 */     this.silverfishWings[2].setRotationPoint(0.0F, 19.0F, this.field_78170_c[1]);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 42:   */   {
/* 43:56 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 44:59 */     for (int var8 = 0; var8 < this.silverfishBodyParts.length; var8++) {
/* 45:61 */       this.silverfishBodyParts[var8].render(par7);
/* 46:   */     }
/* 47:64 */     for (var8 = 0; var8 < this.silverfishWings.length; var8++) {
/* 48:66 */       this.silverfishWings[var8].render(par7);
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 53:   */   {
/* 54:77 */     for (int var8 = 0; var8 < this.silverfishBodyParts.length; var8++)
/* 55:   */     {
/* 56:79 */       this.silverfishBodyParts[var8].rotateAngleY = (MathHelper.cos(par3 * 0.9F + var8 * 0.15F * 3.141593F) * 3.141593F * 0.05F * (1 + Math.abs(var8 - 2)));
/* 57:80 */       this.silverfishBodyParts[var8].rotationPointX = (MathHelper.sin(par3 * 0.9F + var8 * 0.15F * 3.141593F) * 3.141593F * 0.2F * Math.abs(var8 - 2));
/* 58:   */     }
/* 59:83 */     this.silverfishWings[0].rotateAngleY = this.silverfishBodyParts[2].rotateAngleY;
/* 60:84 */     this.silverfishWings[1].rotateAngleY = this.silverfishBodyParts[4].rotateAngleY;
/* 61:85 */     this.silverfishWings[1].rotationPointX = this.silverfishBodyParts[4].rotationPointX;
/* 62:86 */     this.silverfishWings[2].rotateAngleY = this.silverfishBodyParts[1].rotateAngleY;
/* 63:87 */     this.silverfishWings[2].rotationPointX = this.silverfishBodyParts[1].rotationPointX;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSilverfish
 * JD-Core Version:    0.7.0.1
 */