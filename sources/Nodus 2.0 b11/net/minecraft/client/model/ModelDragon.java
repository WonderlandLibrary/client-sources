/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.entity.boss.EntityDragon;
/*   6:    */ import org.lwjgl.opengl.GL11;
/*   7:    */ 
/*   8:    */ public class ModelDragon
/*   9:    */   extends ModelBase
/*  10:    */ {
/*  11:    */   private ModelRenderer head;
/*  12:    */   private ModelRenderer spine;
/*  13:    */   private ModelRenderer jaw;
/*  14:    */   private ModelRenderer body;
/*  15:    */   private ModelRenderer rearLeg;
/*  16:    */   private ModelRenderer frontLeg;
/*  17:    */   private ModelRenderer rearLegTip;
/*  18:    */   private ModelRenderer frontLegTip;
/*  19:    */   private ModelRenderer rearFoot;
/*  20:    */   private ModelRenderer frontFoot;
/*  21:    */   private ModelRenderer wing;
/*  22:    */   private ModelRenderer wingTip;
/*  23:    */   private float partialTicks;
/*  24:    */   private static final String __OBFID = "CL_00000870";
/*  25:    */   
/*  26:    */   public ModelDragon(float par1)
/*  27:    */   {
/*  28: 50 */     this.textureWidth = 256;
/*  29: 51 */     this.textureHeight = 256;
/*  30: 52 */     setTextureOffset("body.body", 0, 0);
/*  31: 53 */     setTextureOffset("wing.skin", -56, 88);
/*  32: 54 */     setTextureOffset("wingtip.skin", -56, 144);
/*  33: 55 */     setTextureOffset("rearleg.main", 0, 0);
/*  34: 56 */     setTextureOffset("rearfoot.main", 112, 0);
/*  35: 57 */     setTextureOffset("rearlegtip.main", 196, 0);
/*  36: 58 */     setTextureOffset("head.upperhead", 112, 30);
/*  37: 59 */     setTextureOffset("wing.bone", 112, 88);
/*  38: 60 */     setTextureOffset("head.upperlip", 176, 44);
/*  39: 61 */     setTextureOffset("jaw.jaw", 176, 65);
/*  40: 62 */     setTextureOffset("frontleg.main", 112, 104);
/*  41: 63 */     setTextureOffset("wingtip.bone", 112, 136);
/*  42: 64 */     setTextureOffset("frontfoot.main", 144, 104);
/*  43: 65 */     setTextureOffset("neck.box", 192, 104);
/*  44: 66 */     setTextureOffset("frontlegtip.main", 226, 138);
/*  45: 67 */     setTextureOffset("body.scale", 220, 53);
/*  46: 68 */     setTextureOffset("head.scale", 0, 0);
/*  47: 69 */     setTextureOffset("neck.scale", 48, 0);
/*  48: 70 */     setTextureOffset("head.nostril", 112, 0);
/*  49: 71 */     float var2 = -16.0F;
/*  50: 72 */     this.head = new ModelRenderer(this, "head");
/*  51: 73 */     this.head.addBox("upperlip", -6.0F, -1.0F, -8.0F + var2, 12, 5, 16);
/*  52: 74 */     this.head.addBox("upperhead", -8.0F, -8.0F, 6.0F + var2, 16, 16, 16);
/*  53: 75 */     this.head.mirror = true;
/*  54: 76 */     this.head.addBox("scale", -5.0F, -12.0F, 12.0F + var2, 2, 4, 6);
/*  55: 77 */     this.head.addBox("nostril", -5.0F, -3.0F, -6.0F + var2, 2, 2, 4);
/*  56: 78 */     this.head.mirror = false;
/*  57: 79 */     this.head.addBox("scale", 3.0F, -12.0F, 12.0F + var2, 2, 4, 6);
/*  58: 80 */     this.head.addBox("nostril", 3.0F, -3.0F, -6.0F + var2, 2, 2, 4);
/*  59: 81 */     this.jaw = new ModelRenderer(this, "jaw");
/*  60: 82 */     this.jaw.setRotationPoint(0.0F, 4.0F, 8.0F + var2);
/*  61: 83 */     this.jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
/*  62: 84 */     this.head.addChild(this.jaw);
/*  63: 85 */     this.spine = new ModelRenderer(this, "neck");
/*  64: 86 */     this.spine.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
/*  65: 87 */     this.spine.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
/*  66: 88 */     this.body = new ModelRenderer(this, "body");
/*  67: 89 */     this.body.setRotationPoint(0.0F, 4.0F, 8.0F);
/*  68: 90 */     this.body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
/*  69: 91 */     this.body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
/*  70: 92 */     this.body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
/*  71: 93 */     this.body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
/*  72: 94 */     this.wing = new ModelRenderer(this, "wing");
/*  73: 95 */     this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
/*  74: 96 */     this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
/*  75: 97 */     this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
/*  76: 98 */     this.wingTip = new ModelRenderer(this, "wingtip");
/*  77: 99 */     this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
/*  78:100 */     this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
/*  79:101 */     this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
/*  80:102 */     this.wing.addChild(this.wingTip);
/*  81:103 */     this.frontLeg = new ModelRenderer(this, "frontleg");
/*  82:104 */     this.frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
/*  83:105 */     this.frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
/*  84:106 */     this.frontLegTip = new ModelRenderer(this, "frontlegtip");
/*  85:107 */     this.frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
/*  86:108 */     this.frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
/*  87:109 */     this.frontLeg.addChild(this.frontLegTip);
/*  88:110 */     this.frontFoot = new ModelRenderer(this, "frontfoot");
/*  89:111 */     this.frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
/*  90:112 */     this.frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
/*  91:113 */     this.frontLegTip.addChild(this.frontFoot);
/*  92:114 */     this.rearLeg = new ModelRenderer(this, "rearleg");
/*  93:115 */     this.rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
/*  94:116 */     this.rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
/*  95:117 */     this.rearLegTip = new ModelRenderer(this, "rearlegtip");
/*  96:118 */     this.rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
/*  97:119 */     this.rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
/*  98:120 */     this.rearLeg.addChild(this.rearLegTip);
/*  99:121 */     this.rearFoot = new ModelRenderer(this, "rearfoot");
/* 100:122 */     this.rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
/* 101:123 */     this.rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
/* 102:124 */     this.rearLegTip.addChild(this.rearFoot);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 106:    */   {
/* 107:133 */     this.partialTicks = par4;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 111:    */   {
/* 112:141 */     GL11.glPushMatrix();
/* 113:142 */     EntityDragon var8 = (EntityDragon)par1Entity;
/* 114:143 */     float var9 = var8.prevAnimTime + (var8.animTime - var8.prevAnimTime) * this.partialTicks;
/* 115:144 */     this.jaw.rotateAngleX = ((float)(Math.sin(var9 * 3.141593F * 2.0F) + 1.0D) * 0.2F);
/* 116:145 */     float var10 = (float)(Math.sin(var9 * 3.141593F * 2.0F - 1.0F) + 1.0D);
/* 117:146 */     var10 = (var10 * var10 * 1.0F + var10 * 2.0F) * 0.05F;
/* 118:147 */     GL11.glTranslatef(0.0F, var10 - 2.0F, -3.0F);
/* 119:148 */     GL11.glRotatef(var10 * 2.0F, 1.0F, 0.0F, 0.0F);
/* 120:149 */     float var11 = -30.0F;
/* 121:150 */     float var13 = 0.0F;
/* 122:151 */     float var14 = 1.5F;
/* 123:152 */     double[] var15 = var8.getMovementOffsets(6, this.partialTicks);
/* 124:153 */     float var16 = updateRotations(var8.getMovementOffsets(5, this.partialTicks)[0] - var8.getMovementOffsets(10, this.partialTicks)[0]);
/* 125:154 */     float var17 = updateRotations(var8.getMovementOffsets(5, this.partialTicks)[0] + var16 / 2.0F);
/* 126:155 */     var11 += 2.0F;
/* 127:156 */     float var18 = var9 * 3.141593F * 2.0F;
/* 128:157 */     var11 = 20.0F;
/* 129:158 */     float var12 = -12.0F;
/* 130:161 */     for (int var19 = 0; var19 < 5; var19++)
/* 131:    */     {
/* 132:163 */       double[] var20 = var8.getMovementOffsets(5 - var19, this.partialTicks);
/* 133:164 */       float var21 = (float)Math.cos(var19 * 0.45F + var18) * 0.15F;
/* 134:165 */       this.spine.rotateAngleY = (updateRotations(var20[0] - var15[0]) * 3.141593F / 180.0F * var14);
/* 135:166 */       this.spine.rotateAngleX = (var21 + (float)(var20[1] - var15[1]) * 3.141593F / 180.0F * var14 * 5.0F);
/* 136:167 */       this.spine.rotateAngleZ = (-updateRotations(var20[0] - var17) * 3.141593F / 180.0F * var14);
/* 137:168 */       this.spine.rotationPointY = var11;
/* 138:169 */       this.spine.rotationPointZ = var12;
/* 139:170 */       this.spine.rotationPointX = var13;
/* 140:171 */       var11 = (float)(var11 + Math.sin(this.spine.rotateAngleX) * 10.0D);
/* 141:172 */       var12 = (float)(var12 - Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0D);
/* 142:173 */       var13 = (float)(var13 - Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0D);
/* 143:174 */       this.spine.render(par7);
/* 144:    */     }
/* 145:177 */     this.head.rotationPointY = var11;
/* 146:178 */     this.head.rotationPointZ = var12;
/* 147:179 */     this.head.rotationPointX = var13;
/* 148:180 */     double[] var23 = var8.getMovementOffsets(0, this.partialTicks);
/* 149:181 */     this.head.rotateAngleY = (updateRotations(var23[0] - var15[0]) * 3.141593F / 180.0F * 1.0F);
/* 150:182 */     this.head.rotateAngleZ = (-updateRotations(var23[0] - var17) * 3.141593F / 180.0F * 1.0F);
/* 151:183 */     this.head.render(par7);
/* 152:184 */     GL11.glPushMatrix();
/* 153:185 */     GL11.glTranslatef(0.0F, 1.0F, 0.0F);
/* 154:186 */     GL11.glRotatef(-var16 * var14 * 1.0F, 0.0F, 0.0F, 1.0F);
/* 155:187 */     GL11.glTranslatef(0.0F, -1.0F, 0.0F);
/* 156:188 */     this.body.rotateAngleZ = 0.0F;
/* 157:189 */     this.body.render(par7);
/* 158:191 */     for (int var22 = 0; var22 < 2; var22++)
/* 159:    */     {
/* 160:193 */       GL11.glEnable(2884);
/* 161:194 */       float var21 = var9 * 3.141593F * 2.0F;
/* 162:195 */       this.wing.rotateAngleX = (0.125F - (float)Math.cos(var21) * 0.2F);
/* 163:196 */       this.wing.rotateAngleY = 0.25F;
/* 164:197 */       this.wing.rotateAngleZ = ((float)(Math.sin(var21) + 0.125D) * 0.8F);
/* 165:198 */       this.wingTip.rotateAngleZ = (-(float)(Math.sin(var21 + 2.0F) + 0.5D) * 0.75F);
/* 166:199 */       this.rearLeg.rotateAngleX = (1.0F + var10 * 0.1F);
/* 167:200 */       this.rearLegTip.rotateAngleX = (0.5F + var10 * 0.1F);
/* 168:201 */       this.rearFoot.rotateAngleX = (0.75F + var10 * 0.1F);
/* 169:202 */       this.frontLeg.rotateAngleX = (1.3F + var10 * 0.1F);
/* 170:203 */       this.frontLegTip.rotateAngleX = (-0.5F - var10 * 0.1F);
/* 171:204 */       this.frontFoot.rotateAngleX = (0.75F + var10 * 0.1F);
/* 172:205 */       this.wing.render(par7);
/* 173:206 */       this.frontLeg.render(par7);
/* 174:207 */       this.rearLeg.render(par7);
/* 175:208 */       GL11.glScalef(-1.0F, 1.0F, 1.0F);
/* 176:210 */       if (var22 == 0) {
/* 177:212 */         GL11.glCullFace(1028);
/* 178:    */       }
/* 179:    */     }
/* 180:216 */     GL11.glPopMatrix();
/* 181:217 */     GL11.glCullFace(1029);
/* 182:218 */     GL11.glDisable(2884);
/* 183:219 */     float var24 = -(float)Math.sin(var9 * 3.141593F * 2.0F) * 0.0F;
/* 184:220 */     var18 = var9 * 3.141593F * 2.0F;
/* 185:221 */     var11 = 10.0F;
/* 186:222 */     var12 = 60.0F;
/* 187:223 */     var13 = 0.0F;
/* 188:224 */     var15 = var8.getMovementOffsets(11, this.partialTicks);
/* 189:226 */     for (int var25 = 0; var25 < 12; var25++)
/* 190:    */     {
/* 191:228 */       var23 = var8.getMovementOffsets(12 + var25, this.partialTicks);
/* 192:229 */       var24 = (float)(var24 + Math.sin(var25 * 0.45F + var18) * 0.0500000007450581D);
/* 193:230 */       this.spine.rotateAngleY = ((updateRotations(var23[0] - var15[0]) * var14 + 180.0F) * 3.141593F / 180.0F);
/* 194:231 */       this.spine.rotateAngleX = (var24 + (float)(var23[1] - var15[1]) * 3.141593F / 180.0F * var14 * 5.0F);
/* 195:232 */       this.spine.rotateAngleZ = (updateRotations(var23[0] - var17) * 3.141593F / 180.0F * var14);
/* 196:233 */       this.spine.rotationPointY = var11;
/* 197:234 */       this.spine.rotationPointZ = var12;
/* 198:235 */       this.spine.rotationPointX = var13;
/* 199:236 */       var11 = (float)(var11 + Math.sin(this.spine.rotateAngleX) * 10.0D);
/* 200:237 */       var12 = (float)(var12 - Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0D);
/* 201:238 */       var13 = (float)(var13 - Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0D);
/* 202:239 */       this.spine.render(par7);
/* 203:    */     }
/* 204:242 */     GL11.glPopMatrix();
/* 205:    */   }
/* 206:    */   
/* 207:    */   private float updateRotations(double par1)
/* 208:    */   {
/* 209:252 */     while (par1 >= 180.0D) {
/* 210:254 */       par1 -= 360.0D;
/* 211:    */     }
/* 212:257 */     while (par1 < -180.0D) {
/* 213:259 */       par1 += 360.0D;
/* 214:    */     }
/* 215:262 */     return (float)par1;
/* 216:    */   }
/* 217:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelDragon
 * JD-Core Version:    0.7.0.1
 */