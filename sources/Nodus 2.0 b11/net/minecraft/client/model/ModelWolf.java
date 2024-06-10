/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.entity.passive.EntityWolf;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import org.lwjgl.opengl.GL11;
/*   8:    */ 
/*   9:    */ public class ModelWolf
/*  10:    */   extends ModelBase
/*  11:    */ {
/*  12:    */   public ModelRenderer wolfHeadMain;
/*  13:    */   public ModelRenderer wolfBody;
/*  14:    */   public ModelRenderer wolfLeg1;
/*  15:    */   public ModelRenderer wolfLeg2;
/*  16:    */   public ModelRenderer wolfLeg3;
/*  17:    */   public ModelRenderer wolfLeg4;
/*  18:    */   ModelRenderer wolfTail;
/*  19:    */   ModelRenderer wolfMane;
/*  20:    */   private static final String __OBFID = "CL_00000868";
/*  21:    */   
/*  22:    */   public ModelWolf()
/*  23:    */   {
/*  24: 38 */     float var1 = 0.0F;
/*  25: 39 */     float var2 = 13.5F;
/*  26: 40 */     this.wolfHeadMain = new ModelRenderer(this, 0, 0);
/*  27: 41 */     this.wolfHeadMain.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4, var1);
/*  28: 42 */     this.wolfHeadMain.setRotationPoint(-1.0F, var2, -7.0F);
/*  29: 43 */     this.wolfBody = new ModelRenderer(this, 18, 14);
/*  30: 44 */     this.wolfBody.addBox(-4.0F, -2.0F, -3.0F, 6, 9, 6, var1);
/*  31: 45 */     this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
/*  32: 46 */     this.wolfMane = new ModelRenderer(this, 21, 0);
/*  33: 47 */     this.wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, var1);
/*  34: 48 */     this.wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
/*  35: 49 */     this.wolfLeg1 = new ModelRenderer(this, 0, 18);
/*  36: 50 */     this.wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
/*  37: 51 */     this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
/*  38: 52 */     this.wolfLeg2 = new ModelRenderer(this, 0, 18);
/*  39: 53 */     this.wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
/*  40: 54 */     this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
/*  41: 55 */     this.wolfLeg3 = new ModelRenderer(this, 0, 18);
/*  42: 56 */     this.wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
/*  43: 57 */     this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
/*  44: 58 */     this.wolfLeg4 = new ModelRenderer(this, 0, 18);
/*  45: 59 */     this.wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
/*  46: 60 */     this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
/*  47: 61 */     this.wolfTail = new ModelRenderer(this, 9, 18);
/*  48: 62 */     this.wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
/*  49: 63 */     this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
/*  50: 64 */     this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2, 2, 1, var1);
/*  51: 65 */     this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5.0F, 0.0F, 2, 2, 1, var1);
/*  52: 66 */     this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -5.0F, 3, 3, 4, var1);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*  56:    */   {
/*  57: 74 */     super.render(par1Entity, par2, par3, par4, par5, par6, par7);
/*  58: 75 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/*  59: 77 */     if (this.isChild)
/*  60:    */     {
/*  61: 79 */       float var8 = 2.0F;
/*  62: 80 */       GL11.glPushMatrix();
/*  63: 81 */       GL11.glTranslatef(0.0F, 5.0F * par7, 2.0F * par7);
/*  64: 82 */       this.wolfHeadMain.renderWithRotation(par7);
/*  65: 83 */       GL11.glPopMatrix();
/*  66: 84 */       GL11.glPushMatrix();
/*  67: 85 */       GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
/*  68: 86 */       GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
/*  69: 87 */       this.wolfBody.render(par7);
/*  70: 88 */       this.wolfLeg1.render(par7);
/*  71: 89 */       this.wolfLeg2.render(par7);
/*  72: 90 */       this.wolfLeg3.render(par7);
/*  73: 91 */       this.wolfLeg4.render(par7);
/*  74: 92 */       this.wolfTail.renderWithRotation(par7);
/*  75: 93 */       this.wolfMane.render(par7);
/*  76: 94 */       GL11.glPopMatrix();
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80: 98 */       this.wolfHeadMain.renderWithRotation(par7);
/*  81: 99 */       this.wolfBody.render(par7);
/*  82:100 */       this.wolfLeg1.render(par7);
/*  83:101 */       this.wolfLeg2.render(par7);
/*  84:102 */       this.wolfLeg3.render(par7);
/*  85:103 */       this.wolfLeg4.render(par7);
/*  86:104 */       this.wolfTail.renderWithRotation(par7);
/*  87:105 */       this.wolfMane.render(par7);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/*  92:    */   {
/*  93:115 */     EntityWolf var5 = (EntityWolf)par1EntityLivingBase;
/*  94:117 */     if (var5.isAngry()) {
/*  95:119 */       this.wolfTail.rotateAngleY = 0.0F;
/*  96:    */     } else {
/*  97:123 */       this.wolfTail.rotateAngleY = (MathHelper.cos(par2 * 0.6662F) * 1.4F * par3);
/*  98:    */     }
/*  99:126 */     if (var5.isSitting())
/* 100:    */     {
/* 101:128 */       this.wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
/* 102:129 */       this.wolfMane.rotateAngleX = 1.256637F;
/* 103:130 */       this.wolfMane.rotateAngleY = 0.0F;
/* 104:131 */       this.wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
/* 105:132 */       this.wolfBody.rotateAngleX = 0.7853982F;
/* 106:133 */       this.wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
/* 107:134 */       this.wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
/* 108:135 */       this.wolfLeg1.rotateAngleX = 4.712389F;
/* 109:136 */       this.wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
/* 110:137 */       this.wolfLeg2.rotateAngleX = 4.712389F;
/* 111:138 */       this.wolfLeg3.rotateAngleX = 5.811947F;
/* 112:139 */       this.wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
/* 113:140 */       this.wolfLeg4.rotateAngleX = 5.811947F;
/* 114:141 */       this.wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
/* 115:    */     }
/* 116:    */     else
/* 117:    */     {
/* 118:145 */       this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
/* 119:146 */       this.wolfBody.rotateAngleX = 1.570796F;
/* 120:147 */       this.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
/* 121:148 */       this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
/* 122:149 */       this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
/* 123:150 */       this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
/* 124:151 */       this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
/* 125:152 */       this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
/* 126:153 */       this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
/* 127:154 */       this.wolfLeg1.rotateAngleX = (MathHelper.cos(par2 * 0.6662F) * 1.4F * par3);
/* 128:155 */       this.wolfLeg2.rotateAngleX = (MathHelper.cos(par2 * 0.6662F + 3.141593F) * 1.4F * par3);
/* 129:156 */       this.wolfLeg3.rotateAngleX = (MathHelper.cos(par2 * 0.6662F + 3.141593F) * 1.4F * par3);
/* 130:157 */       this.wolfLeg4.rotateAngleX = (MathHelper.cos(par2 * 0.6662F) * 1.4F * par3);
/* 131:    */     }
/* 132:160 */     this.wolfHeadMain.rotateAngleZ = (var5.getInterestedAngle(par4) + var5.getShakeAngle(par4, 0.0F));
/* 133:161 */     this.wolfMane.rotateAngleZ = var5.getShakeAngle(par4, -0.08F);
/* 134:162 */     this.wolfBody.rotateAngleZ = var5.getShakeAngle(par4, -0.16F);
/* 135:163 */     this.wolfTail.rotateAngleZ = var5.getShakeAngle(par4, -0.2F);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 139:    */   {
/* 140:173 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/* 141:174 */     this.wolfHeadMain.rotateAngleX = (par5 / 57.295776F);
/* 142:175 */     this.wolfHeadMain.rotateAngleY = (par4 / 57.295776F);
/* 143:176 */     this.wolfTail.rotateAngleX = par3;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelWolf
 * JD-Core Version:    0.7.0.1
 */