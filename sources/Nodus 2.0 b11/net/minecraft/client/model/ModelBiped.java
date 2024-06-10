/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.util.MathHelper;
/*   5:    */ import org.lwjgl.opengl.GL11;
/*   6:    */ 
/*   7:    */ public class ModelBiped
/*   8:    */   extends ModelBase
/*   9:    */ {
/*  10:    */   public ModelRenderer bipedHead;
/*  11:    */   public ModelRenderer bipedHeadwear;
/*  12:    */   public ModelRenderer bipedBody;
/*  13:    */   public ModelRenderer bipedRightArm;
/*  14:    */   public ModelRenderer bipedLeftArm;
/*  15:    */   public ModelRenderer bipedRightLeg;
/*  16:    */   public ModelRenderer bipedLeftLeg;
/*  17:    */   public ModelRenderer bipedEars;
/*  18:    */   public ModelRenderer bipedCloak;
/*  19:    */   public int heldItemLeft;
/*  20:    */   public int heldItemRight;
/*  21:    */   public boolean isSneak;
/*  22:    */   public boolean aimedBow;
/*  23:    */   private static final String __OBFID = "CL_00000840";
/*  24:    */   
/*  25:    */   public ModelBiped()
/*  26:    */   {
/*  27: 36 */     this(0.0F);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ModelBiped(float par1)
/*  31:    */   {
/*  32: 41 */     this(par1, 0.0F, 64, 32);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ModelBiped(float par1, float par2, int par3, int par4)
/*  36:    */   {
/*  37: 46 */     this.textureWidth = par3;
/*  38: 47 */     this.textureHeight = par4;
/*  39: 48 */     this.bipedCloak = new ModelRenderer(this, 0, 0);
/*  40: 49 */     this.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, par1);
/*  41: 50 */     this.bipedEars = new ModelRenderer(this, 24, 0);
/*  42: 51 */     this.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, par1);
/*  43: 52 */     this.bipedHead = new ModelRenderer(this, 0, 0);
/*  44: 53 */     this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1);
/*  45: 54 */     this.bipedHead.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
/*  46: 55 */     this.bipedHeadwear = new ModelRenderer(this, 32, 0);
/*  47: 56 */     this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1 + 0.5F);
/*  48: 57 */     this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
/*  49: 58 */     this.bipedBody = new ModelRenderer(this, 16, 16);
/*  50: 59 */     this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, par1);
/*  51: 60 */     this.bipedBody.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
/*  52: 61 */     this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  53: 62 */     this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, par1);
/*  54: 63 */     this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + par2, 0.0F);
/*  55: 64 */     this.bipedLeftArm = new ModelRenderer(this, 40, 16);
/*  56: 65 */     this.bipedLeftArm.mirror = true;
/*  57: 66 */     this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, par1);
/*  58: 67 */     this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + par2, 0.0F);
/*  59: 68 */     this.bipedRightLeg = new ModelRenderer(this, 0, 16);
/*  60: 69 */     this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
/*  61: 70 */     this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + par2, 0.0F);
/*  62: 71 */     this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
/*  63: 72 */     this.bipedLeftLeg.mirror = true;
/*  64: 73 */     this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
/*  65: 74 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + par2, 0.0F);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*  69:    */   {
/*  70: 82 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/*  71: 84 */     if (this.isChild)
/*  72:    */     {
/*  73: 86 */       float var8 = 2.0F;
/*  74: 87 */       GL11.glPushMatrix();
/*  75: 88 */       GL11.glScalef(1.5F / var8, 1.5F / var8, 1.5F / var8);
/*  76: 89 */       GL11.glTranslatef(0.0F, 16.0F * par7, 0.0F);
/*  77: 90 */       this.bipedHead.render(par7);
/*  78: 91 */       GL11.glPopMatrix();
/*  79: 92 */       GL11.glPushMatrix();
/*  80: 93 */       GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
/*  81: 94 */       GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
/*  82: 95 */       this.bipedBody.render(par7);
/*  83: 96 */       this.bipedRightArm.render(par7);
/*  84: 97 */       this.bipedLeftArm.render(par7);
/*  85: 98 */       this.bipedRightLeg.render(par7);
/*  86: 99 */       this.bipedLeftLeg.render(par7);
/*  87:100 */       this.bipedHeadwear.render(par7);
/*  88:101 */       GL11.glPopMatrix();
/*  89:    */     }
/*  90:    */     else
/*  91:    */     {
/*  92:105 */       this.bipedHead.render(par7);
/*  93:106 */       this.bipedBody.render(par7);
/*  94:107 */       this.bipedRightArm.render(par7);
/*  95:108 */       this.bipedLeftArm.render(par7);
/*  96:109 */       this.bipedRightLeg.render(par7);
/*  97:110 */       this.bipedLeftLeg.render(par7);
/*  98:111 */       this.bipedHeadwear.render(par7);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 103:    */   {
/* 104:122 */     this.bipedHead.rotateAngleY = (par4 / 57.295776F);
/* 105:123 */     this.bipedHead.rotateAngleX = (par5 / 57.295776F);
/* 106:124 */     this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
/* 107:125 */     this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
/* 108:126 */     this.bipedRightArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 2.0F * par2 * 0.5F);
/* 109:127 */     this.bipedLeftArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F);
/* 110:128 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 111:129 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 112:130 */     this.bipedRightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.4F * par2);
/* 113:131 */     this.bipedLeftLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2);
/* 114:132 */     this.bipedRightLeg.rotateAngleY = 0.0F;
/* 115:133 */     this.bipedLeftLeg.rotateAngleY = 0.0F;
/* 116:135 */     if (this.isRiding)
/* 117:    */     {
/* 118:137 */       this.bipedRightArm.rotateAngleX += -0.6283186F;
/* 119:138 */       this.bipedLeftArm.rotateAngleX += -0.6283186F;
/* 120:139 */       this.bipedRightLeg.rotateAngleX = -1.256637F;
/* 121:140 */       this.bipedLeftLeg.rotateAngleX = -1.256637F;
/* 122:141 */       this.bipedRightLeg.rotateAngleY = 0.3141593F;
/* 123:142 */       this.bipedLeftLeg.rotateAngleY = -0.3141593F;
/* 124:    */     }
/* 125:145 */     if (this.heldItemLeft != 0) {
/* 126:147 */       this.bipedLeftArm.rotateAngleX = (this.bipedLeftArm.rotateAngleX * 0.5F - 0.3141593F * this.heldItemLeft);
/* 127:    */     }
/* 128:150 */     if (this.heldItemRight != 0) {
/* 129:152 */       this.bipedRightArm.rotateAngleX = (this.bipedRightArm.rotateAngleX * 0.5F - 0.3141593F * this.heldItemRight);
/* 130:    */     }
/* 131:155 */     this.bipedRightArm.rotateAngleY = 0.0F;
/* 132:156 */     this.bipedLeftArm.rotateAngleY = 0.0F;
/* 133:160 */     if (this.onGround > -9990.0F)
/* 134:    */     {
/* 135:162 */       float var8 = this.onGround;
/* 136:163 */       this.bipedBody.rotateAngleY = (MathHelper.sin(MathHelper.sqrt_float(var8) * 3.141593F * 2.0F) * 0.2F);
/* 137:164 */       this.bipedRightArm.rotationPointZ = (MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F);
/* 138:165 */       this.bipedRightArm.rotationPointX = (-MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F);
/* 139:166 */       this.bipedLeftArm.rotationPointZ = (-MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F);
/* 140:167 */       this.bipedLeftArm.rotationPointX = (MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F);
/* 141:168 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 142:169 */       this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 143:170 */       this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
/* 144:171 */       var8 = 1.0F - this.onGround;
/* 145:172 */       var8 *= var8;
/* 146:173 */       var8 *= var8;
/* 147:174 */       var8 = 1.0F - var8;
/* 148:175 */       float var9 = MathHelper.sin(var8 * 3.141593F);
/* 149:176 */       float var10 = MathHelper.sin(this.onGround * 3.141593F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
/* 150:177 */       this.bipedRightArm.rotateAngleX = ((float)(this.bipedRightArm.rotateAngleX - (var9 * 1.2D + var10)));
/* 151:178 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
/* 152:179 */       this.bipedRightArm.rotateAngleZ = (MathHelper.sin(this.onGround * 3.141593F) * -0.4F);
/* 153:    */     }
/* 154:182 */     if (this.isSneak)
/* 155:    */     {
/* 156:184 */       this.bipedBody.rotateAngleX = 0.5F;
/* 157:185 */       this.bipedRightArm.rotateAngleX += 0.4F;
/* 158:186 */       this.bipedLeftArm.rotateAngleX += 0.4F;
/* 159:187 */       this.bipedRightLeg.rotationPointZ = 4.0F;
/* 160:188 */       this.bipedLeftLeg.rotationPointZ = 4.0F;
/* 161:189 */       this.bipedRightLeg.rotationPointY = 9.0F;
/* 162:190 */       this.bipedLeftLeg.rotationPointY = 9.0F;
/* 163:191 */       this.bipedHead.rotationPointY = 1.0F;
/* 164:192 */       this.bipedHeadwear.rotationPointY = 1.0F;
/* 165:    */     }
/* 166:    */     else
/* 167:    */     {
/* 168:196 */       this.bipedBody.rotateAngleX = 0.0F;
/* 169:197 */       this.bipedRightLeg.rotationPointZ = 0.1F;
/* 170:198 */       this.bipedLeftLeg.rotationPointZ = 0.1F;
/* 171:199 */       this.bipedRightLeg.rotationPointY = 12.0F;
/* 172:200 */       this.bipedLeftLeg.rotationPointY = 12.0F;
/* 173:201 */       this.bipedHead.rotationPointY = 0.0F;
/* 174:202 */       this.bipedHeadwear.rotationPointY = 0.0F;
/* 175:    */     }
/* 176:205 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 177:206 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 178:207 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
/* 179:208 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
/* 180:210 */     if (this.aimedBow)
/* 181:    */     {
/* 182:212 */       float var8 = 0.0F;
/* 183:213 */       float var9 = 0.0F;
/* 184:214 */       this.bipedRightArm.rotateAngleZ = 0.0F;
/* 185:215 */       this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 186:216 */       this.bipedRightArm.rotateAngleY = (-(0.1F - var8 * 0.6F) + this.bipedHead.rotateAngleY);
/* 187:217 */       this.bipedLeftArm.rotateAngleY = (0.1F - var8 * 0.6F + this.bipedHead.rotateAngleY + 0.4F);
/* 188:218 */       this.bipedRightArm.rotateAngleX = (-1.570796F + this.bipedHead.rotateAngleX);
/* 189:219 */       this.bipedLeftArm.rotateAngleX = (-1.570796F + this.bipedHead.rotateAngleX);
/* 190:220 */       this.bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
/* 191:221 */       this.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
/* 192:222 */       this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 193:223 */       this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 194:224 */       this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
/* 195:225 */       this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void renderEars(float par1)
/* 200:    */   {
/* 201:234 */     this.bipedEars.rotateAngleY = this.bipedHead.rotateAngleY;
/* 202:235 */     this.bipedEars.rotateAngleX = this.bipedHead.rotateAngleX;
/* 203:236 */     this.bipedEars.rotationPointX = 0.0F;
/* 204:237 */     this.bipedEars.rotationPointY = 0.0F;
/* 205:238 */     this.bipedEars.render(par1);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void renderCloak(float par1)
/* 209:    */   {
/* 210:246 */     this.bipedCloak.render(par1);
/* 211:    */   }
/* 212:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelBiped
 * JD-Core Version:    0.7.0.1
 */