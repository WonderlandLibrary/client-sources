/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.entity.passive.EntityOcelot;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import org.lwjgl.opengl.GL11;
/*   8:    */ 
/*   9:    */ public class ModelOcelot
/*  10:    */   extends ModelBase
/*  11:    */ {
/*  12:    */   ModelRenderer ocelotBackLeftLeg;
/*  13:    */   ModelRenderer ocelotBackRightLeg;
/*  14:    */   ModelRenderer ocelotFrontLeftLeg;
/*  15:    */   ModelRenderer ocelotFrontRightLeg;
/*  16:    */   ModelRenderer ocelotTail;
/*  17:    */   ModelRenderer ocelotTail2;
/*  18:    */   ModelRenderer ocelotHead;
/*  19:    */   ModelRenderer ocelotBody;
/*  20: 34 */   int field_78163_i = 1;
/*  21:    */   private static final String __OBFID = "CL_00000848";
/*  22:    */   
/*  23:    */   public ModelOcelot()
/*  24:    */   {
/*  25: 39 */     setTextureOffset("head.main", 0, 0);
/*  26: 40 */     setTextureOffset("head.nose", 0, 24);
/*  27: 41 */     setTextureOffset("head.ear1", 0, 10);
/*  28: 42 */     setTextureOffset("head.ear2", 6, 10);
/*  29: 43 */     this.ocelotHead = new ModelRenderer(this, "head");
/*  30: 44 */     this.ocelotHead.addBox("main", -2.5F, -2.0F, -3.0F, 5, 4, 5);
/*  31: 45 */     this.ocelotHead.addBox("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2);
/*  32: 46 */     this.ocelotHead.addBox("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2);
/*  33: 47 */     this.ocelotHead.addBox("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2);
/*  34: 48 */     this.ocelotHead.setRotationPoint(0.0F, 15.0F, -9.0F);
/*  35: 49 */     this.ocelotBody = new ModelRenderer(this, 20, 0);
/*  36: 50 */     this.ocelotBody.addBox(-2.0F, 3.0F, -8.0F, 4, 16, 6, 0.0F);
/*  37: 51 */     this.ocelotBody.setRotationPoint(0.0F, 12.0F, -10.0F);
/*  38: 52 */     this.ocelotTail = new ModelRenderer(this, 0, 15);
/*  39: 53 */     this.ocelotTail.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
/*  40: 54 */     this.ocelotTail.rotateAngleX = 0.9F;
/*  41: 55 */     this.ocelotTail.setRotationPoint(0.0F, 15.0F, 8.0F);
/*  42: 56 */     this.ocelotTail2 = new ModelRenderer(this, 4, 15);
/*  43: 57 */     this.ocelotTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
/*  44: 58 */     this.ocelotTail2.setRotationPoint(0.0F, 20.0F, 14.0F);
/*  45: 59 */     this.ocelotBackLeftLeg = new ModelRenderer(this, 8, 13);
/*  46: 60 */     this.ocelotBackLeftLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
/*  47: 61 */     this.ocelotBackLeftLeg.setRotationPoint(1.1F, 18.0F, 5.0F);
/*  48: 62 */     this.ocelotBackRightLeg = new ModelRenderer(this, 8, 13);
/*  49: 63 */     this.ocelotBackRightLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
/*  50: 64 */     this.ocelotBackRightLeg.setRotationPoint(-1.1F, 18.0F, 5.0F);
/*  51: 65 */     this.ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0);
/*  52: 66 */     this.ocelotFrontLeftLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
/*  53: 67 */     this.ocelotFrontLeftLeg.setRotationPoint(1.2F, 13.8F, -5.0F);
/*  54: 68 */     this.ocelotFrontRightLeg = new ModelRenderer(this, 40, 0);
/*  55: 69 */     this.ocelotFrontRightLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
/*  56: 70 */     this.ocelotFrontRightLeg.setRotationPoint(-1.2F, 13.8F, -5.0F);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*  60:    */   {
/*  61: 78 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/*  62: 80 */     if (this.isChild)
/*  63:    */     {
/*  64: 82 */       float var8 = 2.0F;
/*  65: 83 */       GL11.glPushMatrix();
/*  66: 84 */       GL11.glScalef(1.5F / var8, 1.5F / var8, 1.5F / var8);
/*  67: 85 */       GL11.glTranslatef(0.0F, 10.0F * par7, 4.0F * par7);
/*  68: 86 */       this.ocelotHead.render(par7);
/*  69: 87 */       GL11.glPopMatrix();
/*  70: 88 */       GL11.glPushMatrix();
/*  71: 89 */       GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
/*  72: 90 */       GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
/*  73: 91 */       this.ocelotBody.render(par7);
/*  74: 92 */       this.ocelotBackLeftLeg.render(par7);
/*  75: 93 */       this.ocelotBackRightLeg.render(par7);
/*  76: 94 */       this.ocelotFrontLeftLeg.render(par7);
/*  77: 95 */       this.ocelotFrontRightLeg.render(par7);
/*  78: 96 */       this.ocelotTail.render(par7);
/*  79: 97 */       this.ocelotTail2.render(par7);
/*  80: 98 */       GL11.glPopMatrix();
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:102 */       this.ocelotHead.render(par7);
/*  85:103 */       this.ocelotBody.render(par7);
/*  86:104 */       this.ocelotTail.render(par7);
/*  87:105 */       this.ocelotTail2.render(par7);
/*  88:106 */       this.ocelotBackLeftLeg.render(par7);
/*  89:107 */       this.ocelotBackRightLeg.render(par7);
/*  90:108 */       this.ocelotFrontLeftLeg.render(par7);
/*  91:109 */       this.ocelotFrontRightLeg.render(par7);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/*  96:    */   {
/*  97:120 */     this.ocelotHead.rotateAngleX = (par5 / 57.295776F);
/*  98:121 */     this.ocelotHead.rotateAngleY = (par4 / 57.295776F);
/*  99:123 */     if (this.field_78163_i != 3)
/* 100:    */     {
/* 101:125 */       this.ocelotBody.rotateAngleX = 1.570796F;
/* 102:127 */       if (this.field_78163_i == 2)
/* 103:    */       {
/* 104:129 */         this.ocelotBackLeftLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.0F * par2);
/* 105:130 */         this.ocelotBackRightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 0.3F) * 1.0F * par2);
/* 106:131 */         this.ocelotFrontLeftLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F + 0.3F) * 1.0F * par2);
/* 107:132 */         this.ocelotFrontRightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.0F * par2);
/* 108:133 */         this.ocelotTail2.rotateAngleX = (1.727876F + 0.3141593F * MathHelper.cos(par1) * par2);
/* 109:    */       }
/* 110:    */       else
/* 111:    */       {
/* 112:137 */         this.ocelotBackLeftLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.0F * par2);
/* 113:138 */         this.ocelotBackRightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.0F * par2);
/* 114:139 */         this.ocelotFrontLeftLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.0F * par2);
/* 115:140 */         this.ocelotFrontRightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.0F * par2);
/* 116:142 */         if (this.field_78163_i == 1) {
/* 117:144 */           this.ocelotTail2.rotateAngleX = (1.727876F + 0.7853982F * MathHelper.cos(par1) * par2);
/* 118:    */         } else {
/* 119:148 */           this.ocelotTail2.rotateAngleX = (1.727876F + 0.4712389F * MathHelper.cos(par1) * par2);
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 126:    */   {
/* 127:160 */     EntityOcelot var5 = (EntityOcelot)par1EntityLivingBase;
/* 128:161 */     this.ocelotBody.rotationPointY = 12.0F;
/* 129:162 */     this.ocelotBody.rotationPointZ = -10.0F;
/* 130:163 */     this.ocelotHead.rotationPointY = 15.0F;
/* 131:164 */     this.ocelotHead.rotationPointZ = -9.0F;
/* 132:165 */     this.ocelotTail.rotationPointY = 15.0F;
/* 133:166 */     this.ocelotTail.rotationPointZ = 8.0F;
/* 134:167 */     this.ocelotTail2.rotationPointY = 20.0F;
/* 135:168 */     this.ocelotTail2.rotationPointZ = 14.0F;
/* 136:169 */     this.ocelotFrontLeftLeg.rotationPointY = (this.ocelotFrontRightLeg.rotationPointY = 13.8F);
/* 137:170 */     this.ocelotFrontLeftLeg.rotationPointZ = (this.ocelotFrontRightLeg.rotationPointZ = -5.0F);
/* 138:171 */     this.ocelotBackLeftLeg.rotationPointY = (this.ocelotBackRightLeg.rotationPointY = 18.0F);
/* 139:172 */     this.ocelotBackLeftLeg.rotationPointZ = (this.ocelotBackRightLeg.rotationPointZ = 5.0F);
/* 140:173 */     this.ocelotTail.rotateAngleX = 0.9F;
/* 141:175 */     if (var5.isSneaking())
/* 142:    */     {
/* 143:177 */       this.ocelotBody.rotationPointY += 1.0F;
/* 144:178 */       this.ocelotHead.rotationPointY += 2.0F;
/* 145:179 */       this.ocelotTail.rotationPointY += 1.0F;
/* 146:180 */       this.ocelotTail2.rotationPointY += -4.0F;
/* 147:181 */       this.ocelotTail2.rotationPointZ += 2.0F;
/* 148:182 */       this.ocelotTail.rotateAngleX = 1.570796F;
/* 149:183 */       this.ocelotTail2.rotateAngleX = 1.570796F;
/* 150:184 */       this.field_78163_i = 0;
/* 151:    */     }
/* 152:186 */     else if (var5.isSprinting())
/* 153:    */     {
/* 154:188 */       this.ocelotTail2.rotationPointY = this.ocelotTail.rotationPointY;
/* 155:189 */       this.ocelotTail2.rotationPointZ += 2.0F;
/* 156:190 */       this.ocelotTail.rotateAngleX = 1.570796F;
/* 157:191 */       this.ocelotTail2.rotateAngleX = 1.570796F;
/* 158:192 */       this.field_78163_i = 2;
/* 159:    */     }
/* 160:194 */     else if (var5.isSitting())
/* 161:    */     {
/* 162:196 */       this.ocelotBody.rotateAngleX = 0.7853982F;
/* 163:197 */       this.ocelotBody.rotationPointY += -4.0F;
/* 164:198 */       this.ocelotBody.rotationPointZ += 5.0F;
/* 165:199 */       this.ocelotHead.rotationPointY += -3.3F;
/* 166:200 */       this.ocelotHead.rotationPointZ += 1.0F;
/* 167:201 */       this.ocelotTail.rotationPointY += 8.0F;
/* 168:202 */       this.ocelotTail.rotationPointZ += -2.0F;
/* 169:203 */       this.ocelotTail2.rotationPointY += 2.0F;
/* 170:204 */       this.ocelotTail2.rotationPointZ += -0.8F;
/* 171:205 */       this.ocelotTail.rotateAngleX = 1.727876F;
/* 172:206 */       this.ocelotTail2.rotateAngleX = 2.670354F;
/* 173:207 */       this.ocelotFrontLeftLeg.rotateAngleX = (this.ocelotFrontRightLeg.rotateAngleX = -0.1570796F);
/* 174:208 */       this.ocelotFrontLeftLeg.rotationPointY = (this.ocelotFrontRightLeg.rotationPointY = 15.8F);
/* 175:209 */       this.ocelotFrontLeftLeg.rotationPointZ = (this.ocelotFrontRightLeg.rotationPointZ = -7.0F);
/* 176:210 */       this.ocelotBackLeftLeg.rotateAngleX = (this.ocelotBackRightLeg.rotateAngleX = -1.570796F);
/* 177:211 */       this.ocelotBackLeftLeg.rotationPointY = (this.ocelotBackRightLeg.rotationPointY = 21.0F);
/* 178:212 */       this.ocelotBackLeftLeg.rotationPointZ = (this.ocelotBackRightLeg.rotationPointZ = 1.0F);
/* 179:213 */       this.field_78163_i = 3;
/* 180:    */     }
/* 181:    */     else
/* 182:    */     {
/* 183:217 */       this.field_78163_i = 1;
/* 184:    */     }
/* 185:    */   }
/* 186:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelOcelot
 * JD-Core Version:    0.7.0.1
 */