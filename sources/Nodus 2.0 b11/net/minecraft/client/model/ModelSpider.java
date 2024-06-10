/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.util.MathHelper;
/*   5:    */ 
/*   6:    */ public class ModelSpider
/*   7:    */   extends ModelBase
/*   8:    */ {
/*   9:    */   public ModelRenderer spiderHead;
/*  10:    */   public ModelRenderer spiderNeck;
/*  11:    */   public ModelRenderer spiderBody;
/*  12:    */   public ModelRenderer spiderLeg1;
/*  13:    */   public ModelRenderer spiderLeg2;
/*  14:    */   public ModelRenderer spiderLeg3;
/*  15:    */   public ModelRenderer spiderLeg4;
/*  16:    */   public ModelRenderer spiderLeg5;
/*  17:    */   public ModelRenderer spiderLeg6;
/*  18:    */   public ModelRenderer spiderLeg7;
/*  19:    */   public ModelRenderer spiderLeg8;
/*  20:    */   private static final String __OBFID = "CL_00000860";
/*  21:    */   
/*  22:    */   public ModelSpider()
/*  23:    */   {
/*  24: 44 */     float var1 = 0.0F;
/*  25: 45 */     byte var2 = 15;
/*  26: 46 */     this.spiderHead = new ModelRenderer(this, 32, 4);
/*  27: 47 */     this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, var1);
/*  28: 48 */     this.spiderHead.setRotationPoint(0.0F, var2, -3.0F);
/*  29: 49 */     this.spiderNeck = new ModelRenderer(this, 0, 0);
/*  30: 50 */     this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, var1);
/*  31: 51 */     this.spiderNeck.setRotationPoint(0.0F, var2, 0.0F);
/*  32: 52 */     this.spiderBody = new ModelRenderer(this, 0, 12);
/*  33: 53 */     this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, var1);
/*  34: 54 */     this.spiderBody.setRotationPoint(0.0F, var2, 9.0F);
/*  35: 55 */     this.spiderLeg1 = new ModelRenderer(this, 18, 0);
/*  36: 56 */     this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
/*  37: 57 */     this.spiderLeg1.setRotationPoint(-4.0F, var2, 2.0F);
/*  38: 58 */     this.spiderLeg2 = new ModelRenderer(this, 18, 0);
/*  39: 59 */     this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
/*  40: 60 */     this.spiderLeg2.setRotationPoint(4.0F, var2, 2.0F);
/*  41: 61 */     this.spiderLeg3 = new ModelRenderer(this, 18, 0);
/*  42: 62 */     this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
/*  43: 63 */     this.spiderLeg3.setRotationPoint(-4.0F, var2, 1.0F);
/*  44: 64 */     this.spiderLeg4 = new ModelRenderer(this, 18, 0);
/*  45: 65 */     this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
/*  46: 66 */     this.spiderLeg4.setRotationPoint(4.0F, var2, 1.0F);
/*  47: 67 */     this.spiderLeg5 = new ModelRenderer(this, 18, 0);
/*  48: 68 */     this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
/*  49: 69 */     this.spiderLeg5.setRotationPoint(-4.0F, var2, 0.0F);
/*  50: 70 */     this.spiderLeg6 = new ModelRenderer(this, 18, 0);
/*  51: 71 */     this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
/*  52: 72 */     this.spiderLeg6.setRotationPoint(4.0F, var2, 0.0F);
/*  53: 73 */     this.spiderLeg7 = new ModelRenderer(this, 18, 0);
/*  54: 74 */     this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
/*  55: 75 */     this.spiderLeg7.setRotationPoint(-4.0F, var2, -1.0F);
/*  56: 76 */     this.spiderLeg8 = new ModelRenderer(this, 18, 0);
/*  57: 77 */     this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
/*  58: 78 */     this.spiderLeg8.setRotationPoint(4.0F, var2, -1.0F);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*  62:    */   {
/*  63: 86 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/*  64: 87 */     this.spiderHead.render(par7);
/*  65: 88 */     this.spiderNeck.render(par7);
/*  66: 89 */     this.spiderBody.render(par7);
/*  67: 90 */     this.spiderLeg1.render(par7);
/*  68: 91 */     this.spiderLeg2.render(par7);
/*  69: 92 */     this.spiderLeg3.render(par7);
/*  70: 93 */     this.spiderLeg4.render(par7);
/*  71: 94 */     this.spiderLeg5.render(par7);
/*  72: 95 */     this.spiderLeg6.render(par7);
/*  73: 96 */     this.spiderLeg7.render(par7);
/*  74: 97 */     this.spiderLeg8.render(par7);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/*  78:    */   {
/*  79:107 */     this.spiderHead.rotateAngleY = (par4 / 57.295776F);
/*  80:108 */     this.spiderHead.rotateAngleX = (par5 / 57.295776F);
/*  81:109 */     float var8 = 0.7853982F;
/*  82:110 */     this.spiderLeg1.rotateAngleZ = (-var8);
/*  83:111 */     this.spiderLeg2.rotateAngleZ = var8;
/*  84:112 */     this.spiderLeg3.rotateAngleZ = (-var8 * 0.74F);
/*  85:113 */     this.spiderLeg4.rotateAngleZ = (var8 * 0.74F);
/*  86:114 */     this.spiderLeg5.rotateAngleZ = (-var8 * 0.74F);
/*  87:115 */     this.spiderLeg6.rotateAngleZ = (var8 * 0.74F);
/*  88:116 */     this.spiderLeg7.rotateAngleZ = (-var8);
/*  89:117 */     this.spiderLeg8.rotateAngleZ = var8;
/*  90:118 */     float var9 = -0.0F;
/*  91:119 */     float var10 = 0.3926991F;
/*  92:120 */     this.spiderLeg1.rotateAngleY = (var10 * 2.0F + var9);
/*  93:121 */     this.spiderLeg2.rotateAngleY = (-var10 * 2.0F - var9);
/*  94:122 */     this.spiderLeg3.rotateAngleY = (var10 * 1.0F + var9);
/*  95:123 */     this.spiderLeg4.rotateAngleY = (-var10 * 1.0F - var9);
/*  96:124 */     this.spiderLeg5.rotateAngleY = (-var10 * 1.0F + var9);
/*  97:125 */     this.spiderLeg6.rotateAngleY = (var10 * 1.0F - var9);
/*  98:126 */     this.spiderLeg7.rotateAngleY = (-var10 * 2.0F + var9);
/*  99:127 */     this.spiderLeg8.rotateAngleY = (var10 * 2.0F - var9);
/* 100:128 */     float var11 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
/* 101:129 */     float var12 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 3.141593F) * 0.4F) * par2;
/* 102:130 */     float var13 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 1.570796F) * 0.4F) * par2;
/* 103:131 */     float var14 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 4.712389F) * 0.4F) * par2;
/* 104:132 */     float var15 = Math.abs(MathHelper.sin(par1 * 0.6662F + 0.0F) * 0.4F) * par2;
/* 105:133 */     float var16 = Math.abs(MathHelper.sin(par1 * 0.6662F + 3.141593F) * 0.4F) * par2;
/* 106:134 */     float var17 = Math.abs(MathHelper.sin(par1 * 0.6662F + 1.570796F) * 0.4F) * par2;
/* 107:135 */     float var18 = Math.abs(MathHelper.sin(par1 * 0.6662F + 4.712389F) * 0.4F) * par2;
/* 108:136 */     this.spiderLeg1.rotateAngleY += var11;
/* 109:137 */     this.spiderLeg2.rotateAngleY += -var11;
/* 110:138 */     this.spiderLeg3.rotateAngleY += var12;
/* 111:139 */     this.spiderLeg4.rotateAngleY += -var12;
/* 112:140 */     this.spiderLeg5.rotateAngleY += var13;
/* 113:141 */     this.spiderLeg6.rotateAngleY += -var13;
/* 114:142 */     this.spiderLeg7.rotateAngleY += var14;
/* 115:143 */     this.spiderLeg8.rotateAngleY += -var14;
/* 116:144 */     this.spiderLeg1.rotateAngleZ += var15;
/* 117:145 */     this.spiderLeg2.rotateAngleZ += -var15;
/* 118:146 */     this.spiderLeg3.rotateAngleZ += var16;
/* 119:147 */     this.spiderLeg4.rotateAngleZ += -var16;
/* 120:148 */     this.spiderLeg5.rotateAngleZ += var17;
/* 121:149 */     this.spiderLeg6.rotateAngleZ += -var17;
/* 122:150 */     this.spiderLeg7.rotateAngleZ += var18;
/* 123:151 */     this.spiderLeg8.rotateAngleZ += -var18;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSpider
 * JD-Core Version:    0.7.0.1
 */