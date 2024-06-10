/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.entity.passive.EntityHorse;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import org.lwjgl.opengl.GL11;
/*   8:    */ 
/*   9:    */ public class ModelHorse
/*  10:    */   extends ModelBase
/*  11:    */ {
/*  12:    */   private ModelRenderer head;
/*  13:    */   private ModelRenderer mouthTop;
/*  14:    */   private ModelRenderer mouthBottom;
/*  15:    */   private ModelRenderer horseLeftEar;
/*  16:    */   private ModelRenderer horseRightEar;
/*  17:    */   private ModelRenderer muleLeftEar;
/*  18:    */   private ModelRenderer muleRightEar;
/*  19:    */   private ModelRenderer neck;
/*  20:    */   private ModelRenderer horseFaceRopes;
/*  21:    */   private ModelRenderer mane;
/*  22:    */   private ModelRenderer body;
/*  23:    */   private ModelRenderer tailBase;
/*  24:    */   private ModelRenderer tailMiddle;
/*  25:    */   private ModelRenderer tailTip;
/*  26:    */   private ModelRenderer backLeftLeg;
/*  27:    */   private ModelRenderer backLeftShin;
/*  28:    */   private ModelRenderer backLeftHoof;
/*  29:    */   private ModelRenderer backRightLeg;
/*  30:    */   private ModelRenderer backRightShin;
/*  31:    */   private ModelRenderer backRightHoof;
/*  32:    */   private ModelRenderer frontLeftLeg;
/*  33:    */   private ModelRenderer frontLeftShin;
/*  34:    */   private ModelRenderer frontLeftHoof;
/*  35:    */   private ModelRenderer frontRightLeg;
/*  36:    */   private ModelRenderer frontRightShin;
/*  37:    */   private ModelRenderer frontRightHoof;
/*  38:    */   private ModelRenderer muleLeftChest;
/*  39:    */   private ModelRenderer muleRightChest;
/*  40:    */   private ModelRenderer horseSaddleBottom;
/*  41:    */   private ModelRenderer horseSaddleFront;
/*  42:    */   private ModelRenderer horseSaddleBack;
/*  43:    */   private ModelRenderer horseLeftSaddleRope;
/*  44:    */   private ModelRenderer horseLeftSaddleMetal;
/*  45:    */   private ModelRenderer horseRightSaddleRope;
/*  46:    */   private ModelRenderer horseRightSaddleMetal;
/*  47:    */   private ModelRenderer horseLeftFaceMetal;
/*  48:    */   private ModelRenderer horseRightFaceMetal;
/*  49:    */   private ModelRenderer horseLeftRein;
/*  50:    */   private ModelRenderer horseRightRein;
/*  51:    */   private static final String __OBFID = "CL_00000846";
/*  52:    */   
/*  53:    */   public ModelHorse()
/*  54:    */   {
/*  55: 68 */     this.textureWidth = 128;
/*  56: 69 */     this.textureHeight = 128;
/*  57: 70 */     this.body = new ModelRenderer(this, 0, 34);
/*  58: 71 */     this.body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24);
/*  59: 72 */     this.body.setRotationPoint(0.0F, 11.0F, 9.0F);
/*  60: 73 */     this.tailBase = new ModelRenderer(this, 44, 0);
/*  61: 74 */     this.tailBase.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3);
/*  62: 75 */     this.tailBase.setRotationPoint(0.0F, 3.0F, 14.0F);
/*  63: 76 */     setBoxRotation(this.tailBase, -1.134464F, 0.0F, 0.0F);
/*  64: 77 */     this.tailMiddle = new ModelRenderer(this, 38, 7);
/*  65: 78 */     this.tailMiddle.addBox(-1.5F, -2.0F, 3.0F, 3, 4, 7);
/*  66: 79 */     this.tailMiddle.setRotationPoint(0.0F, 3.0F, 14.0F);
/*  67: 80 */     setBoxRotation(this.tailMiddle, -1.134464F, 0.0F, 0.0F);
/*  68: 81 */     this.tailTip = new ModelRenderer(this, 24, 3);
/*  69: 82 */     this.tailTip.addBox(-1.5F, -4.5F, 9.0F, 3, 4, 7);
/*  70: 83 */     this.tailTip.setRotationPoint(0.0F, 3.0F, 14.0F);
/*  71: 84 */     setBoxRotation(this.tailTip, -1.40215F, 0.0F, 0.0F);
/*  72: 85 */     this.backLeftLeg = new ModelRenderer(this, 78, 29);
/*  73: 86 */     this.backLeftLeg.addBox(-2.5F, -2.0F, -2.5F, 4, 9, 5);
/*  74: 87 */     this.backLeftLeg.setRotationPoint(4.0F, 9.0F, 11.0F);
/*  75: 88 */     this.backLeftShin = new ModelRenderer(this, 78, 43);
/*  76: 89 */     this.backLeftShin.addBox(-2.0F, 0.0F, -1.5F, 3, 5, 3);
/*  77: 90 */     this.backLeftShin.setRotationPoint(4.0F, 16.0F, 11.0F);
/*  78: 91 */     this.backLeftHoof = new ModelRenderer(this, 78, 51);
/*  79: 92 */     this.backLeftHoof.addBox(-2.5F, 5.1F, -2.0F, 4, 3, 4);
/*  80: 93 */     this.backLeftHoof.setRotationPoint(4.0F, 16.0F, 11.0F);
/*  81: 94 */     this.backRightLeg = new ModelRenderer(this, 96, 29);
/*  82: 95 */     this.backRightLeg.addBox(-1.5F, -2.0F, -2.5F, 4, 9, 5);
/*  83: 96 */     this.backRightLeg.setRotationPoint(-4.0F, 9.0F, 11.0F);
/*  84: 97 */     this.backRightShin = new ModelRenderer(this, 96, 43);
/*  85: 98 */     this.backRightShin.addBox(-1.0F, 0.0F, -1.5F, 3, 5, 3);
/*  86: 99 */     this.backRightShin.setRotationPoint(-4.0F, 16.0F, 11.0F);
/*  87:100 */     this.backRightHoof = new ModelRenderer(this, 96, 51);
/*  88:101 */     this.backRightHoof.addBox(-1.5F, 5.1F, -2.0F, 4, 3, 4);
/*  89:102 */     this.backRightHoof.setRotationPoint(-4.0F, 16.0F, 11.0F);
/*  90:103 */     this.frontLeftLeg = new ModelRenderer(this, 44, 29);
/*  91:104 */     this.frontLeftLeg.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4);
/*  92:105 */     this.frontLeftLeg.setRotationPoint(4.0F, 9.0F, -8.0F);
/*  93:106 */     this.frontLeftShin = new ModelRenderer(this, 44, 41);
/*  94:107 */     this.frontLeftShin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3);
/*  95:108 */     this.frontLeftShin.setRotationPoint(4.0F, 16.0F, -8.0F);
/*  96:109 */     this.frontLeftHoof = new ModelRenderer(this, 44, 51);
/*  97:110 */     this.frontLeftHoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
/*  98:111 */     this.frontLeftHoof.setRotationPoint(4.0F, 16.0F, -8.0F);
/*  99:112 */     this.frontRightLeg = new ModelRenderer(this, 60, 29);
/* 100:113 */     this.frontRightLeg.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4);
/* 101:114 */     this.frontRightLeg.setRotationPoint(-4.0F, 9.0F, -8.0F);
/* 102:115 */     this.frontRightShin = new ModelRenderer(this, 60, 41);
/* 103:116 */     this.frontRightShin.addBox(-1.1F, 0.0F, -1.6F, 3, 5, 3);
/* 104:117 */     this.frontRightShin.setRotationPoint(-4.0F, 16.0F, -8.0F);
/* 105:118 */     this.frontRightHoof = new ModelRenderer(this, 60, 51);
/* 106:119 */     this.frontRightHoof.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
/* 107:120 */     this.frontRightHoof.setRotationPoint(-4.0F, 16.0F, -8.0F);
/* 108:121 */     this.head = new ModelRenderer(this, 0, 0);
/* 109:122 */     this.head.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7);
/* 110:123 */     this.head.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 111:124 */     setBoxRotation(this.head, 0.5235988F, 0.0F, 0.0F);
/* 112:125 */     this.mouthTop = new ModelRenderer(this, 24, 18);
/* 113:126 */     this.mouthTop.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6);
/* 114:127 */     this.mouthTop.setRotationPoint(0.0F, 3.95F, -10.0F);
/* 115:128 */     setBoxRotation(this.mouthTop, 0.5235988F, 0.0F, 0.0F);
/* 116:129 */     this.mouthBottom = new ModelRenderer(this, 24, 27);
/* 117:130 */     this.mouthBottom.addBox(-2.0F, -7.0F, -6.5F, 4, 2, 5);
/* 118:131 */     this.mouthBottom.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 119:132 */     setBoxRotation(this.mouthBottom, 0.5235988F, 0.0F, 0.0F);
/* 120:133 */     this.head.addChild(this.mouthTop);
/* 121:134 */     this.head.addChild(this.mouthBottom);
/* 122:135 */     this.horseLeftEar = new ModelRenderer(this, 0, 0);
/* 123:136 */     this.horseLeftEar.addBox(0.45F, -12.0F, 4.0F, 2, 3, 1);
/* 124:137 */     this.horseLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 125:138 */     setBoxRotation(this.horseLeftEar, 0.5235988F, 0.0F, 0.0F);
/* 126:139 */     this.horseRightEar = new ModelRenderer(this, 0, 0);
/* 127:140 */     this.horseRightEar.addBox(-2.45F, -12.0F, 4.0F, 2, 3, 1);
/* 128:141 */     this.horseRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 129:142 */     setBoxRotation(this.horseRightEar, 0.5235988F, 0.0F, 0.0F);
/* 130:143 */     this.muleLeftEar = new ModelRenderer(this, 0, 12);
/* 131:144 */     this.muleLeftEar.addBox(-2.0F, -16.0F, 4.0F, 2, 7, 1);
/* 132:145 */     this.muleLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 133:146 */     setBoxRotation(this.muleLeftEar, 0.5235988F, 0.0F, 0.261799F);
/* 134:147 */     this.muleRightEar = new ModelRenderer(this, 0, 12);
/* 135:148 */     this.muleRightEar.addBox(0.0F, -16.0F, 4.0F, 2, 7, 1);
/* 136:149 */     this.muleRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 137:150 */     setBoxRotation(this.muleRightEar, 0.5235988F, 0.0F, -0.261799F);
/* 138:151 */     this.neck = new ModelRenderer(this, 0, 12);
/* 139:152 */     this.neck.addBox(-2.05F, -9.8F, -2.0F, 4, 14, 8);
/* 140:153 */     this.neck.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 141:154 */     setBoxRotation(this.neck, 0.5235988F, 0.0F, 0.0F);
/* 142:155 */     this.muleLeftChest = new ModelRenderer(this, 0, 34);
/* 143:156 */     this.muleLeftChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
/* 144:157 */     this.muleLeftChest.setRotationPoint(-7.5F, 3.0F, 10.0F);
/* 145:158 */     setBoxRotation(this.muleLeftChest, 0.0F, 1.570796F, 0.0F);
/* 146:159 */     this.muleRightChest = new ModelRenderer(this, 0, 47);
/* 147:160 */     this.muleRightChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
/* 148:161 */     this.muleRightChest.setRotationPoint(4.5F, 3.0F, 10.0F);
/* 149:162 */     setBoxRotation(this.muleRightChest, 0.0F, 1.570796F, 0.0F);
/* 150:163 */     this.horseSaddleBottom = new ModelRenderer(this, 80, 0);
/* 151:164 */     this.horseSaddleBottom.addBox(-5.0F, 0.0F, -3.0F, 10, 1, 8);
/* 152:165 */     this.horseSaddleBottom.setRotationPoint(0.0F, 2.0F, 2.0F);
/* 153:166 */     this.horseSaddleFront = new ModelRenderer(this, 106, 9);
/* 154:167 */     this.horseSaddleFront.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 2);
/* 155:168 */     this.horseSaddleFront.setRotationPoint(0.0F, 2.0F, 2.0F);
/* 156:169 */     this.horseSaddleBack = new ModelRenderer(this, 80, 9);
/* 157:170 */     this.horseSaddleBack.addBox(-4.0F, -1.0F, 3.0F, 8, 1, 2);
/* 158:171 */     this.horseSaddleBack.setRotationPoint(0.0F, 2.0F, 2.0F);
/* 159:172 */     this.horseLeftSaddleMetal = new ModelRenderer(this, 74, 0);
/* 160:173 */     this.horseLeftSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
/* 161:174 */     this.horseLeftSaddleMetal.setRotationPoint(5.0F, 3.0F, 2.0F);
/* 162:175 */     this.horseLeftSaddleRope = new ModelRenderer(this, 70, 0);
/* 163:176 */     this.horseLeftSaddleRope.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
/* 164:177 */     this.horseLeftSaddleRope.setRotationPoint(5.0F, 3.0F, 2.0F);
/* 165:178 */     this.horseRightSaddleMetal = new ModelRenderer(this, 74, 4);
/* 166:179 */     this.horseRightSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
/* 167:180 */     this.horseRightSaddleMetal.setRotationPoint(-5.0F, 3.0F, 2.0F);
/* 168:181 */     this.horseRightSaddleRope = new ModelRenderer(this, 80, 0);
/* 169:182 */     this.horseRightSaddleRope.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
/* 170:183 */     this.horseRightSaddleRope.setRotationPoint(-5.0F, 3.0F, 2.0F);
/* 171:184 */     this.horseLeftFaceMetal = new ModelRenderer(this, 74, 13);
/* 172:185 */     this.horseLeftFaceMetal.addBox(1.5F, -8.0F, -4.0F, 1, 2, 2);
/* 173:186 */     this.horseLeftFaceMetal.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 174:187 */     setBoxRotation(this.horseLeftFaceMetal, 0.5235988F, 0.0F, 0.0F);
/* 175:188 */     this.horseRightFaceMetal = new ModelRenderer(this, 74, 13);
/* 176:189 */     this.horseRightFaceMetal.addBox(-2.5F, -8.0F, -4.0F, 1, 2, 2);
/* 177:190 */     this.horseRightFaceMetal.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 178:191 */     setBoxRotation(this.horseRightFaceMetal, 0.5235988F, 0.0F, 0.0F);
/* 179:192 */     this.horseLeftRein = new ModelRenderer(this, 44, 10);
/* 180:193 */     this.horseLeftRein.addBox(2.6F, -6.0F, -6.0F, 0, 3, 16);
/* 181:194 */     this.horseLeftRein.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 182:195 */     this.horseRightRein = new ModelRenderer(this, 44, 5);
/* 183:196 */     this.horseRightRein.addBox(-2.6F, -6.0F, -6.0F, 0, 3, 16);
/* 184:197 */     this.horseRightRein.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 185:198 */     this.mane = new ModelRenderer(this, 58, 0);
/* 186:199 */     this.mane.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4);
/* 187:200 */     this.mane.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 188:201 */     setBoxRotation(this.mane, 0.5235988F, 0.0F, 0.0F);
/* 189:202 */     this.horseFaceRopes = new ModelRenderer(this, 80, 12);
/* 190:203 */     this.horseFaceRopes.addBox(-2.5F, -10.1F, -7.0F, 5, 5, 12, 0.2F);
/* 191:204 */     this.horseFaceRopes.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 192:205 */     setBoxRotation(this.horseFaceRopes, 0.5235988F, 0.0F, 0.0F);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 196:    */   {
/* 197:213 */     EntityHorse var8 = (EntityHorse)par1Entity;
/* 198:214 */     int var9 = var8.getHorseType();
/* 199:215 */     float var10 = var8.getGrassEatingAmount(0.0F);
/* 200:216 */     boolean var11 = var8.isAdultHorse();
/* 201:217 */     boolean var12 = (var11) && (var8.isHorseSaddled());
/* 202:218 */     boolean var13 = (var11) && (var8.isChested());
/* 203:219 */     boolean var14 = (var9 == 1) || (var9 == 2);
/* 204:220 */     float var15 = var8.getHorseSize();
/* 205:221 */     boolean var16 = var8.riddenByEntity != null;
/* 206:223 */     if (var12)
/* 207:    */     {
/* 208:225 */       this.horseFaceRopes.render(par7);
/* 209:226 */       this.horseSaddleBottom.render(par7);
/* 210:227 */       this.horseSaddleFront.render(par7);
/* 211:228 */       this.horseSaddleBack.render(par7);
/* 212:229 */       this.horseLeftSaddleRope.render(par7);
/* 213:230 */       this.horseLeftSaddleMetal.render(par7);
/* 214:231 */       this.horseRightSaddleRope.render(par7);
/* 215:232 */       this.horseRightSaddleMetal.render(par7);
/* 216:233 */       this.horseLeftFaceMetal.render(par7);
/* 217:234 */       this.horseRightFaceMetal.render(par7);
/* 218:236 */       if (var16)
/* 219:    */       {
/* 220:238 */         this.horseLeftRein.render(par7);
/* 221:239 */         this.horseRightRein.render(par7);
/* 222:    */       }
/* 223:    */     }
/* 224:243 */     if (!var11)
/* 225:    */     {
/* 226:245 */       GL11.glPushMatrix();
/* 227:246 */       GL11.glScalef(var15, 0.5F + var15 * 0.5F, var15);
/* 228:247 */       GL11.glTranslatef(0.0F, 0.95F * (1.0F - var15), 0.0F);
/* 229:    */     }
/* 230:250 */     this.backLeftLeg.render(par7);
/* 231:251 */     this.backLeftShin.render(par7);
/* 232:252 */     this.backLeftHoof.render(par7);
/* 233:253 */     this.backRightLeg.render(par7);
/* 234:254 */     this.backRightShin.render(par7);
/* 235:255 */     this.backRightHoof.render(par7);
/* 236:256 */     this.frontLeftLeg.render(par7);
/* 237:257 */     this.frontLeftShin.render(par7);
/* 238:258 */     this.frontLeftHoof.render(par7);
/* 239:259 */     this.frontRightLeg.render(par7);
/* 240:260 */     this.frontRightShin.render(par7);
/* 241:261 */     this.frontRightHoof.render(par7);
/* 242:263 */     if (!var11)
/* 243:    */     {
/* 244:265 */       GL11.glPopMatrix();
/* 245:266 */       GL11.glPushMatrix();
/* 246:267 */       GL11.glScalef(var15, var15, var15);
/* 247:268 */       GL11.glTranslatef(0.0F, 1.35F * (1.0F - var15), 0.0F);
/* 248:    */     }
/* 249:271 */     this.body.render(par7);
/* 250:272 */     this.tailBase.render(par7);
/* 251:273 */     this.tailMiddle.render(par7);
/* 252:274 */     this.tailTip.render(par7);
/* 253:275 */     this.neck.render(par7);
/* 254:276 */     this.mane.render(par7);
/* 255:278 */     if (!var11)
/* 256:    */     {
/* 257:280 */       GL11.glPopMatrix();
/* 258:281 */       GL11.glPushMatrix();
/* 259:282 */       float var17 = 0.5F + var15 * var15 * 0.5F;
/* 260:283 */       GL11.glScalef(var17, var17, var17);
/* 261:285 */       if (var10 <= 0.0F) {
/* 262:287 */         GL11.glTranslatef(0.0F, 1.35F * (1.0F - var15), 0.0F);
/* 263:    */       } else {
/* 264:291 */         GL11.glTranslatef(0.0F, 0.9F * (1.0F - var15) * var10 + 1.35F * (1.0F - var15) * (1.0F - var10), 0.15F * (1.0F - var15) * var10);
/* 265:    */       }
/* 266:    */     }
/* 267:295 */     if (var14)
/* 268:    */     {
/* 269:297 */       this.muleLeftEar.render(par7);
/* 270:298 */       this.muleRightEar.render(par7);
/* 271:    */     }
/* 272:    */     else
/* 273:    */     {
/* 274:302 */       this.horseLeftEar.render(par7);
/* 275:303 */       this.horseRightEar.render(par7);
/* 276:    */     }
/* 277:306 */     this.head.render(par7);
/* 278:308 */     if (!var11) {
/* 279:310 */       GL11.glPopMatrix();
/* 280:    */     }
/* 281:313 */     if (var13)
/* 282:    */     {
/* 283:315 */       this.muleLeftChest.render(par7);
/* 284:316 */       this.muleRightChest.render(par7);
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   private void setBoxRotation(ModelRenderer par1ModelRenderer, float par2, float par3, float par4)
/* 289:    */   {
/* 290:325 */     par1ModelRenderer.rotateAngleX = par2;
/* 291:326 */     par1ModelRenderer.rotateAngleY = par3;
/* 292:327 */     par1ModelRenderer.rotateAngleZ = par4;
/* 293:    */   }
/* 294:    */   
/* 295:    */   private float updateHorseRotation(float par1, float par2, float par3)
/* 296:    */   {
/* 297:337 */     for (float var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F) {}
/* 298:342 */     while (var4 >= 180.0F) {
/* 299:344 */       var4 -= 360.0F;
/* 300:    */     }
/* 301:347 */     return par1 + par3 * var4;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 305:    */   {
/* 306:356 */     super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
/* 307:357 */     float var5 = updateHorseRotation(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, par4);
/* 308:358 */     float var6 = updateHorseRotation(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, par4);
/* 309:359 */     float var7 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par4;
/* 310:360 */     float var8 = var6 - var5;
/* 311:361 */     float var9 = var7 / 57.295776F;
/* 312:363 */     if (var8 > 20.0F) {
/* 313:365 */       var8 = 20.0F;
/* 314:    */     }
/* 315:368 */     if (var8 < -20.0F) {
/* 316:370 */       var8 = -20.0F;
/* 317:    */     }
/* 318:373 */     if (par3 > 0.2F) {
/* 319:375 */       var9 += MathHelper.cos(par2 * 0.4F) * 0.15F * par3;
/* 320:    */     }
/* 321:378 */     EntityHorse var10 = (EntityHorse)par1EntityLivingBase;
/* 322:379 */     float var11 = var10.getGrassEatingAmount(par4);
/* 323:380 */     float var12 = var10.getRearingAmount(par4);
/* 324:381 */     float var13 = 1.0F - var12;
/* 325:382 */     float var14 = var10.func_110201_q(par4);
/* 326:383 */     boolean var15 = var10.field_110278_bp != 0;
/* 327:384 */     boolean var16 = var10.isHorseSaddled();
/* 328:385 */     boolean var17 = var10.riddenByEntity != null;
/* 329:386 */     float var18 = par1EntityLivingBase.ticksExisted + par4;
/* 330:387 */     float var19 = MathHelper.cos(par2 * 0.6662F + 3.141593F);
/* 331:388 */     float var20 = var19 * 0.8F * par3;
/* 332:389 */     this.head.rotationPointY = 4.0F;
/* 333:390 */     this.head.rotationPointZ = -10.0F;
/* 334:391 */     this.tailBase.rotationPointY = 3.0F;
/* 335:392 */     this.tailMiddle.rotationPointZ = 14.0F;
/* 336:393 */     this.muleRightChest.rotationPointY = 3.0F;
/* 337:394 */     this.muleRightChest.rotationPointZ = 10.0F;
/* 338:395 */     this.body.rotateAngleX = 0.0F;
/* 339:396 */     this.head.rotateAngleX = (0.5235988F + var9);
/* 340:397 */     this.head.rotateAngleY = (var8 / 57.295776F);
/* 341:398 */     this.head.rotateAngleX = (var12 * (0.261799F + var9) + var11 * 2.18166F + (1.0F - Math.max(var12, var11)) * this.head.rotateAngleX);
/* 342:399 */     this.head.rotateAngleY = (var12 * (var8 / 57.295776F) + (1.0F - Math.max(var12, var11)) * this.head.rotateAngleY);
/* 343:400 */     this.head.rotationPointY = (var12 * -6.0F + var11 * 11.0F + (1.0F - Math.max(var12, var11)) * this.head.rotationPointY);
/* 344:401 */     this.head.rotationPointZ = (var12 * -1.0F + var11 * -10.0F + (1.0F - Math.max(var12, var11)) * this.head.rotationPointZ);
/* 345:402 */     this.tailBase.rotationPointY = (var12 * 9.0F + var13 * this.tailBase.rotationPointY);
/* 346:403 */     this.tailMiddle.rotationPointZ = (var12 * 18.0F + var13 * this.tailMiddle.rotationPointZ);
/* 347:404 */     this.muleRightChest.rotationPointY = (var12 * 5.5F + var13 * this.muleRightChest.rotationPointY);
/* 348:405 */     this.muleRightChest.rotationPointZ = (var12 * 15.0F + var13 * this.muleRightChest.rotationPointZ);
/* 349:406 */     this.body.rotateAngleX = (var12 * -0.7853982F + var13 * this.body.rotateAngleX);
/* 350:407 */     this.horseLeftEar.rotationPointY = this.head.rotationPointY;
/* 351:408 */     this.horseRightEar.rotationPointY = this.head.rotationPointY;
/* 352:409 */     this.muleLeftEar.rotationPointY = this.head.rotationPointY;
/* 353:410 */     this.muleRightEar.rotationPointY = this.head.rotationPointY;
/* 354:411 */     this.neck.rotationPointY = this.head.rotationPointY;
/* 355:412 */     this.mouthTop.rotationPointY = 0.02F;
/* 356:413 */     this.mouthBottom.rotationPointY = 0.0F;
/* 357:414 */     this.mane.rotationPointY = this.head.rotationPointY;
/* 358:415 */     this.horseLeftEar.rotationPointZ = this.head.rotationPointZ;
/* 359:416 */     this.horseRightEar.rotationPointZ = this.head.rotationPointZ;
/* 360:417 */     this.muleLeftEar.rotationPointZ = this.head.rotationPointZ;
/* 361:418 */     this.muleRightEar.rotationPointZ = this.head.rotationPointZ;
/* 362:419 */     this.neck.rotationPointZ = this.head.rotationPointZ;
/* 363:420 */     this.mouthTop.rotationPointZ = (0.02F - var14 * 1.0F);
/* 364:421 */     this.mouthBottom.rotationPointZ = (0.0F + var14 * 1.0F);
/* 365:422 */     this.mane.rotationPointZ = this.head.rotationPointZ;
/* 366:423 */     this.horseLeftEar.rotateAngleX = this.head.rotateAngleX;
/* 367:424 */     this.horseRightEar.rotateAngleX = this.head.rotateAngleX;
/* 368:425 */     this.muleLeftEar.rotateAngleX = this.head.rotateAngleX;
/* 369:426 */     this.muleRightEar.rotateAngleX = this.head.rotateAngleX;
/* 370:427 */     this.neck.rotateAngleX = this.head.rotateAngleX;
/* 371:428 */     this.mouthTop.rotateAngleX = (0.0F - 0.09424778F * var14);
/* 372:429 */     this.mouthBottom.rotateAngleX = (0.0F + 0.1570796F * var14);
/* 373:430 */     this.mane.rotateAngleX = this.head.rotateAngleX;
/* 374:431 */     this.horseLeftEar.rotateAngleY = this.head.rotateAngleY;
/* 375:432 */     this.horseRightEar.rotateAngleY = this.head.rotateAngleY;
/* 376:433 */     this.muleLeftEar.rotateAngleY = this.head.rotateAngleY;
/* 377:434 */     this.muleRightEar.rotateAngleY = this.head.rotateAngleY;
/* 378:435 */     this.neck.rotateAngleY = this.head.rotateAngleY;
/* 379:436 */     this.mouthTop.rotateAngleY = 0.0F;
/* 380:437 */     this.mouthBottom.rotateAngleY = 0.0F;
/* 381:438 */     this.mane.rotateAngleY = this.head.rotateAngleY;
/* 382:439 */     this.muleLeftChest.rotateAngleX = (var20 / 5.0F);
/* 383:440 */     this.muleRightChest.rotateAngleX = (-var20 / 5.0F);
/* 384:441 */     float var21 = 1.570796F;
/* 385:442 */     float var22 = 4.712389F;
/* 386:443 */     float var23 = -1.047198F;
/* 387:444 */     float var24 = 0.261799F * var12;
/* 388:445 */     float var25 = MathHelper.cos(var18 * 0.6F + 3.141593F);
/* 389:446 */     this.frontLeftLeg.rotationPointY = (-2.0F * var12 + 9.0F * var13);
/* 390:447 */     this.frontLeftLeg.rotationPointZ = (-2.0F * var12 + -8.0F * var13);
/* 391:448 */     this.frontRightLeg.rotationPointY = this.frontLeftLeg.rotationPointY;
/* 392:449 */     this.frontRightLeg.rotationPointZ = this.frontLeftLeg.rotationPointZ;
/* 393:450 */     this.backLeftShin.rotationPointY = (this.backLeftLeg.rotationPointY + MathHelper.sin(1.570796F + var24 + var13 * -var19 * 0.5F * par3) * 7.0F);
/* 394:451 */     this.backLeftShin.rotationPointZ = (this.backLeftLeg.rotationPointZ + MathHelper.cos(4.712389F + var24 + var13 * -var19 * 0.5F * par3) * 7.0F);
/* 395:452 */     this.backRightShin.rotationPointY = (this.backRightLeg.rotationPointY + MathHelper.sin(1.570796F + var24 + var13 * var19 * 0.5F * par3) * 7.0F);
/* 396:453 */     this.backRightShin.rotationPointZ = (this.backRightLeg.rotationPointZ + MathHelper.cos(4.712389F + var24 + var13 * var19 * 0.5F * par3) * 7.0F);
/* 397:454 */     float var26 = (-1.047198F + var25) * var12 + var20 * var13;
/* 398:455 */     float var27 = (-1.047198F + -var25) * var12 + -var20 * var13;
/* 399:456 */     this.frontLeftShin.rotationPointY = (this.frontLeftLeg.rotationPointY + MathHelper.sin(1.570796F + var26) * 7.0F);
/* 400:457 */     this.frontLeftShin.rotationPointZ = (this.frontLeftLeg.rotationPointZ + MathHelper.cos(4.712389F + var26) * 7.0F);
/* 401:458 */     this.frontRightShin.rotationPointY = (this.frontRightLeg.rotationPointY + MathHelper.sin(1.570796F + var27) * 7.0F);
/* 402:459 */     this.frontRightShin.rotationPointZ = (this.frontRightLeg.rotationPointZ + MathHelper.cos(4.712389F + var27) * 7.0F);
/* 403:460 */     this.backLeftLeg.rotateAngleX = (var24 + -var19 * 0.5F * par3 * var13);
/* 404:461 */     this.backLeftShin.rotateAngleX = (-0.0872665F * var12 + (-var19 * 0.5F * par3 - Math.max(0.0F, var19 * 0.5F * par3)) * var13);
/* 405:462 */     this.backLeftHoof.rotateAngleX = this.backLeftShin.rotateAngleX;
/* 406:463 */     this.backRightLeg.rotateAngleX = (var24 + var19 * 0.5F * par3 * var13);
/* 407:464 */     this.backRightShin.rotateAngleX = (-0.0872665F * var12 + (var19 * 0.5F * par3 - Math.max(0.0F, -var19 * 0.5F * par3)) * var13);
/* 408:465 */     this.backRightHoof.rotateAngleX = this.backRightShin.rotateAngleX;
/* 409:466 */     this.frontLeftLeg.rotateAngleX = var26;
/* 410:467 */     this.frontLeftShin.rotateAngleX = ((this.frontLeftLeg.rotateAngleX + 3.141593F * Math.max(0.0F, 0.2F + var25 * 0.2F)) * var12 + (var20 + Math.max(0.0F, var19 * 0.5F * par3)) * var13);
/* 411:468 */     this.frontLeftHoof.rotateAngleX = this.frontLeftShin.rotateAngleX;
/* 412:469 */     this.frontRightLeg.rotateAngleX = var27;
/* 413:470 */     this.frontRightShin.rotateAngleX = ((this.frontRightLeg.rotateAngleX + 3.141593F * Math.max(0.0F, 0.2F - var25 * 0.2F)) * var12 + (-var20 + Math.max(0.0F, -var19 * 0.5F * par3)) * var13);
/* 414:471 */     this.frontRightHoof.rotateAngleX = this.frontRightShin.rotateAngleX;
/* 415:472 */     this.backLeftHoof.rotationPointY = this.backLeftShin.rotationPointY;
/* 416:473 */     this.backLeftHoof.rotationPointZ = this.backLeftShin.rotationPointZ;
/* 417:474 */     this.backRightHoof.rotationPointY = this.backRightShin.rotationPointY;
/* 418:475 */     this.backRightHoof.rotationPointZ = this.backRightShin.rotationPointZ;
/* 419:476 */     this.frontLeftHoof.rotationPointY = this.frontLeftShin.rotationPointY;
/* 420:477 */     this.frontLeftHoof.rotationPointZ = this.frontLeftShin.rotationPointZ;
/* 421:478 */     this.frontRightHoof.rotationPointY = this.frontRightShin.rotationPointY;
/* 422:479 */     this.frontRightHoof.rotationPointZ = this.frontRightShin.rotationPointZ;
/* 423:481 */     if (var16)
/* 424:    */     {
/* 425:483 */       this.horseSaddleBottom.rotationPointY = (var12 * 0.5F + var13 * 2.0F);
/* 426:484 */       this.horseSaddleBottom.rotationPointZ = (var12 * 11.0F + var13 * 2.0F);
/* 427:485 */       this.horseSaddleFront.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 428:486 */       this.horseSaddleBack.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 429:487 */       this.horseLeftSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 430:488 */       this.horseRightSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 431:489 */       this.horseLeftSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 432:490 */       this.horseRightSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 433:491 */       this.muleLeftChest.rotationPointY = this.muleRightChest.rotationPointY;
/* 434:492 */       this.horseSaddleFront.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 435:493 */       this.horseSaddleBack.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 436:494 */       this.horseLeftSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 437:495 */       this.horseRightSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 438:496 */       this.horseLeftSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 439:497 */       this.horseRightSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 440:498 */       this.muleLeftChest.rotationPointZ = this.muleRightChest.rotationPointZ;
/* 441:499 */       this.horseSaddleBottom.rotateAngleX = this.body.rotateAngleX;
/* 442:500 */       this.horseSaddleFront.rotateAngleX = this.body.rotateAngleX;
/* 443:501 */       this.horseSaddleBack.rotateAngleX = this.body.rotateAngleX;
/* 444:502 */       this.horseLeftRein.rotationPointY = this.head.rotationPointY;
/* 445:503 */       this.horseRightRein.rotationPointY = this.head.rotationPointY;
/* 446:504 */       this.horseFaceRopes.rotationPointY = this.head.rotationPointY;
/* 447:505 */       this.horseLeftFaceMetal.rotationPointY = this.head.rotationPointY;
/* 448:506 */       this.horseRightFaceMetal.rotationPointY = this.head.rotationPointY;
/* 449:507 */       this.horseLeftRein.rotationPointZ = this.head.rotationPointZ;
/* 450:508 */       this.horseRightRein.rotationPointZ = this.head.rotationPointZ;
/* 451:509 */       this.horseFaceRopes.rotationPointZ = this.head.rotationPointZ;
/* 452:510 */       this.horseLeftFaceMetal.rotationPointZ = this.head.rotationPointZ;
/* 453:511 */       this.horseRightFaceMetal.rotationPointZ = this.head.rotationPointZ;
/* 454:512 */       this.horseLeftRein.rotateAngleX = var9;
/* 455:513 */       this.horseRightRein.rotateAngleX = var9;
/* 456:514 */       this.horseFaceRopes.rotateAngleX = this.head.rotateAngleX;
/* 457:515 */       this.horseLeftFaceMetal.rotateAngleX = this.head.rotateAngleX;
/* 458:516 */       this.horseRightFaceMetal.rotateAngleX = this.head.rotateAngleX;
/* 459:517 */       this.horseFaceRopes.rotateAngleY = this.head.rotateAngleY;
/* 460:518 */       this.horseLeftFaceMetal.rotateAngleY = this.head.rotateAngleY;
/* 461:519 */       this.horseLeftRein.rotateAngleY = this.head.rotateAngleY;
/* 462:520 */       this.horseRightFaceMetal.rotateAngleY = this.head.rotateAngleY;
/* 463:521 */       this.horseRightRein.rotateAngleY = this.head.rotateAngleY;
/* 464:523 */       if (var17)
/* 465:    */       {
/* 466:525 */         this.horseLeftSaddleRope.rotateAngleX = -1.047198F;
/* 467:526 */         this.horseLeftSaddleMetal.rotateAngleX = -1.047198F;
/* 468:527 */         this.horseRightSaddleRope.rotateAngleX = -1.047198F;
/* 469:528 */         this.horseRightSaddleMetal.rotateAngleX = -1.047198F;
/* 470:529 */         this.horseLeftSaddleRope.rotateAngleZ = 0.0F;
/* 471:530 */         this.horseLeftSaddleMetal.rotateAngleZ = 0.0F;
/* 472:531 */         this.horseRightSaddleRope.rotateAngleZ = 0.0F;
/* 473:532 */         this.horseRightSaddleMetal.rotateAngleZ = 0.0F;
/* 474:    */       }
/* 475:    */       else
/* 476:    */       {
/* 477:536 */         this.horseLeftSaddleRope.rotateAngleX = (var20 / 3.0F);
/* 478:537 */         this.horseLeftSaddleMetal.rotateAngleX = (var20 / 3.0F);
/* 479:538 */         this.horseRightSaddleRope.rotateAngleX = (var20 / 3.0F);
/* 480:539 */         this.horseRightSaddleMetal.rotateAngleX = (var20 / 3.0F);
/* 481:540 */         this.horseLeftSaddleRope.rotateAngleZ = (var20 / 5.0F);
/* 482:541 */         this.horseLeftSaddleMetal.rotateAngleZ = (var20 / 5.0F);
/* 483:542 */         this.horseRightSaddleRope.rotateAngleZ = (-var20 / 5.0F);
/* 484:543 */         this.horseRightSaddleMetal.rotateAngleZ = (-var20 / 5.0F);
/* 485:    */       }
/* 486:    */     }
/* 487:547 */     var21 = -1.3089F + par3 * 1.5F;
/* 488:549 */     if (var21 > 0.0F) {
/* 489:551 */       var21 = 0.0F;
/* 490:    */     }
/* 491:554 */     if (var15)
/* 492:    */     {
/* 493:556 */       this.tailBase.rotateAngleY = MathHelper.cos(var18 * 0.7F);
/* 494:557 */       var21 = 0.0F;
/* 495:    */     }
/* 496:    */     else
/* 497:    */     {
/* 498:561 */       this.tailBase.rotateAngleY = 0.0F;
/* 499:    */     }
/* 500:564 */     this.tailMiddle.rotateAngleY = this.tailBase.rotateAngleY;
/* 501:565 */     this.tailTip.rotateAngleY = this.tailBase.rotateAngleY;
/* 502:566 */     this.tailMiddle.rotationPointY = this.tailBase.rotationPointY;
/* 503:567 */     this.tailTip.rotationPointY = this.tailBase.rotationPointY;
/* 504:568 */     this.tailMiddle.rotationPointZ = this.tailBase.rotationPointZ;
/* 505:569 */     this.tailTip.rotationPointZ = this.tailBase.rotationPointZ;
/* 506:570 */     this.tailBase.rotateAngleX = var21;
/* 507:571 */     this.tailMiddle.rotateAngleX = var21;
/* 508:572 */     this.tailTip.rotateAngleX = (-0.2618F + var21);
/* 509:    */   }
/* 510:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelHorse
 * JD-Core Version:    0.7.0.1
 */