/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ 
/*   5:    */ public class ModelEnderman
/*   6:    */   extends ModelBiped
/*   7:    */ {
/*   8:    */   public boolean isCarrying;
/*   9:    */   public boolean isAttacking;
/*  10:    */   private static final String __OBFID = "CL_00000838";
/*  11:    */   
/*  12:    */   public ModelEnderman()
/*  13:    */   {
/*  14: 16 */     super(0.0F, -14.0F, 64, 32);
/*  15: 17 */     float var1 = -14.0F;
/*  16: 18 */     float var2 = 0.0F;
/*  17: 19 */     this.bipedHeadwear = new ModelRenderer(this, 0, 16);
/*  18: 20 */     this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, var2 - 0.5F);
/*  19: 21 */     this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
/*  20: 22 */     this.bipedBody = new ModelRenderer(this, 32, 16);
/*  21: 23 */     this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, var2);
/*  22: 24 */     this.bipedBody.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
/*  23: 25 */     this.bipedRightArm = new ModelRenderer(this, 56, 0);
/*  24: 26 */     this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, var2);
/*  25: 27 */     this.bipedRightArm.setRotationPoint(-3.0F, 2.0F + var1, 0.0F);
/*  26: 28 */     this.bipedLeftArm = new ModelRenderer(this, 56, 0);
/*  27: 29 */     this.bipedLeftArm.mirror = true;
/*  28: 30 */     this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, var2);
/*  29: 31 */     this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + var1, 0.0F);
/*  30: 32 */     this.bipedRightLeg = new ModelRenderer(this, 56, 0);
/*  31: 33 */     this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, var2);
/*  32: 34 */     this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + var1, 0.0F);
/*  33: 35 */     this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
/*  34: 36 */     this.bipedLeftLeg.mirror = true;
/*  35: 37 */     this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, var2);
/*  36: 38 */     this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + var1, 0.0F);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/*  40:    */   {
/*  41: 48 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/*  42: 49 */     this.bipedHead.showModel = true;
/*  43: 50 */     float var8 = -14.0F;
/*  44: 51 */     this.bipedBody.rotateAngleX = 0.0F;
/*  45: 52 */     this.bipedBody.rotationPointY = var8;
/*  46: 53 */     this.bipedBody.rotationPointZ = -0.0F;
/*  47: 54 */     this.bipedRightLeg.rotateAngleX -= 0.0F;
/*  48: 55 */     this.bipedLeftLeg.rotateAngleX -= 0.0F;
/*  49: 56 */     this.bipedRightArm.rotateAngleX = ((float)(this.bipedRightArm.rotateAngleX * 0.5D));
/*  50: 57 */     this.bipedLeftArm.rotateAngleX = ((float)(this.bipedLeftArm.rotateAngleX * 0.5D));
/*  51: 58 */     this.bipedRightLeg.rotateAngleX = ((float)(this.bipedRightLeg.rotateAngleX * 0.5D));
/*  52: 59 */     this.bipedLeftLeg.rotateAngleX = ((float)(this.bipedLeftLeg.rotateAngleX * 0.5D));
/*  53: 60 */     float var9 = 0.4F;
/*  54: 62 */     if (this.bipedRightArm.rotateAngleX > var9) {
/*  55: 64 */       this.bipedRightArm.rotateAngleX = var9;
/*  56:    */     }
/*  57: 67 */     if (this.bipedLeftArm.rotateAngleX > var9) {
/*  58: 69 */       this.bipedLeftArm.rotateAngleX = var9;
/*  59:    */     }
/*  60: 72 */     if (this.bipedRightArm.rotateAngleX < -var9) {
/*  61: 74 */       this.bipedRightArm.rotateAngleX = (-var9);
/*  62:    */     }
/*  63: 77 */     if (this.bipedLeftArm.rotateAngleX < -var9) {
/*  64: 79 */       this.bipedLeftArm.rotateAngleX = (-var9);
/*  65:    */     }
/*  66: 82 */     if (this.bipedRightLeg.rotateAngleX > var9) {
/*  67: 84 */       this.bipedRightLeg.rotateAngleX = var9;
/*  68:    */     }
/*  69: 87 */     if (this.bipedLeftLeg.rotateAngleX > var9) {
/*  70: 89 */       this.bipedLeftLeg.rotateAngleX = var9;
/*  71:    */     }
/*  72: 92 */     if (this.bipedRightLeg.rotateAngleX < -var9) {
/*  73: 94 */       this.bipedRightLeg.rotateAngleX = (-var9);
/*  74:    */     }
/*  75: 97 */     if (this.bipedLeftLeg.rotateAngleX < -var9) {
/*  76: 99 */       this.bipedLeftLeg.rotateAngleX = (-var9);
/*  77:    */     }
/*  78:102 */     if (this.isCarrying)
/*  79:    */     {
/*  80:104 */       this.bipedRightArm.rotateAngleX = -0.5F;
/*  81:105 */       this.bipedLeftArm.rotateAngleX = -0.5F;
/*  82:106 */       this.bipedRightArm.rotateAngleZ = 0.05F;
/*  83:107 */       this.bipedLeftArm.rotateAngleZ = -0.05F;
/*  84:    */     }
/*  85:110 */     this.bipedRightArm.rotationPointZ = 0.0F;
/*  86:111 */     this.bipedLeftArm.rotationPointZ = 0.0F;
/*  87:112 */     this.bipedRightLeg.rotationPointZ = 0.0F;
/*  88:113 */     this.bipedLeftLeg.rotationPointZ = 0.0F;
/*  89:114 */     this.bipedRightLeg.rotationPointY = (9.0F + var8);
/*  90:115 */     this.bipedLeftLeg.rotationPointY = (9.0F + var8);
/*  91:116 */     this.bipedHead.rotationPointZ = -0.0F;
/*  92:117 */     this.bipedHead.rotationPointY = (var8 + 1.0F);
/*  93:118 */     this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
/*  94:119 */     this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
/*  95:120 */     this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
/*  96:121 */     this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
/*  97:122 */     this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
/*  98:123 */     this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
/*  99:125 */     if (this.isAttacking)
/* 100:    */     {
/* 101:127 */       float var10 = 1.0F;
/* 102:128 */       this.bipedHead.rotationPointY -= var10 * 5.0F;
/* 103:    */     }
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelEnderman
 * JD-Core Version:    0.7.0.1
 */