/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.gui.FontRenderer;
/*   7:    */ import net.minecraft.client.model.ModelBase;
/*   8:    */ import net.minecraft.client.model.ModelBox;
/*   9:    */ import net.minecraft.client.model.ModelRenderer;
/*  10:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  11:    */ import net.minecraft.client.renderer.RenderHelper;
/*  12:    */ import net.minecraft.client.renderer.Tessellator;
/*  13:    */ import net.minecraft.entity.Entity;
/*  14:    */ import net.minecraft.entity.EntityLivingBase;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  17:    */ import net.minecraft.util.EnumChatFormatting;
/*  18:    */ import net.minecraft.util.IChatComponent;
/*  19:    */ import net.minecraft.util.MathHelper;
/*  20:    */ import net.minecraft.util.ResourceLocation;
/*  21:    */ import org.apache.logging.log4j.LogManager;
/*  22:    */ import org.apache.logging.log4j.Logger;
/*  23:    */ import org.lwjgl.opengl.GL11;
/*  24:    */ 
/*  25:    */ public abstract class RendererLivingEntity
/*  26:    */   extends Render
/*  27:    */ {
/*  28: 26 */   private static final Logger logger = ;
/*  29: 27 */   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*  30:    */   protected ModelBase mainModel;
/*  31:    */   protected ModelBase renderPassModel;
/*  32:    */   private static final String __OBFID = "CL_00001012";
/*  33:    */   
/*  34:    */   public RendererLivingEntity(ModelBase par1ModelBase, float par2)
/*  35:    */   {
/*  36: 36 */     this.mainModel = par1ModelBase;
/*  37: 37 */     this.shadowSize = par2;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setRenderPassModel(ModelBase par1ModelBase)
/*  41:    */   {
/*  42: 46 */     this.renderPassModel = par1ModelBase;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private float interpolateRotation(float par1, float par2, float par3)
/*  46:    */   {
/*  47: 58 */     for (float var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F) {}
/*  48: 63 */     while (var4 >= 180.0F) {
/*  49: 65 */       var4 -= 360.0F;
/*  50:    */     }
/*  51: 68 */     return par1 + par3 * var4;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
/*  55:    */   {
/*  56: 79 */     GL11.glPushMatrix();
/*  57: 80 */     GL11.glDisable(2884);
/*  58: 81 */     this.mainModel.onGround = renderSwingProgress(par1EntityLivingBase, par9);
/*  59: 83 */     if (this.renderPassModel != null) {
/*  60: 85 */       this.renderPassModel.onGround = this.mainModel.onGround;
/*  61:    */     }
/*  62: 88 */     this.mainModel.isRiding = par1EntityLivingBase.isRiding();
/*  63: 90 */     if (this.renderPassModel != null) {
/*  64: 92 */       this.renderPassModel.isRiding = this.mainModel.isRiding;
/*  65:    */     }
/*  66: 95 */     this.mainModel.isChild = par1EntityLivingBase.isChild();
/*  67: 97 */     if (this.renderPassModel != null) {
/*  68: 99 */       this.renderPassModel.isChild = this.mainModel.isChild;
/*  69:    */     }
/*  70:    */     try
/*  71:    */     {
/*  72:104 */       float var10 = interpolateRotation(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, par9);
/*  73:105 */       float var11 = interpolateRotation(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, par9);
/*  74:108 */       if ((par1EntityLivingBase.isRiding()) && ((par1EntityLivingBase.ridingEntity instanceof EntityLivingBase)))
/*  75:    */       {
/*  76:110 */         EntityLivingBase var12 = (EntityLivingBase)par1EntityLivingBase.ridingEntity;
/*  77:111 */         var10 = interpolateRotation(var12.prevRenderYawOffset, var12.renderYawOffset, par9);
/*  78:112 */         float var13 = MathHelper.wrapAngleTo180_float(var11 - var10);
/*  79:114 */         if (var13 < -85.0F) {
/*  80:116 */           var13 = -85.0F;
/*  81:    */         }
/*  82:119 */         if (var13 >= 85.0F) {
/*  83:121 */           var13 = 85.0F;
/*  84:    */         }
/*  85:124 */         var10 = var11 - var13;
/*  86:126 */         if (var13 * var13 > 2500.0F) {
/*  87:128 */           var10 += var13 * 0.2F;
/*  88:    */         }
/*  89:    */       }
/*  90:132 */       float var26 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par9;
/*  91:133 */       renderLivingAt(par1EntityLivingBase, par2, par4, par6);
/*  92:134 */       float var13 = handleRotationFloat(par1EntityLivingBase, par9);
/*  93:135 */       rotateCorpse(par1EntityLivingBase, var13, var10, par9);
/*  94:136 */       float var14 = 0.0625F;
/*  95:137 */       GL11.glEnable(32826);
/*  96:138 */       GL11.glScalef(-1.0F, -1.0F, 1.0F);
/*  97:139 */       preRenderCallback(par1EntityLivingBase, par9);
/*  98:140 */       GL11.glTranslatef(0.0F, -24.0F * var14 - 0.007813F, 0.0F);
/*  99:141 */       float var15 = par1EntityLivingBase.prevLimbSwingAmount + (par1EntityLivingBase.limbSwingAmount - par1EntityLivingBase.prevLimbSwingAmount) * par9;
/* 100:142 */       float var16 = par1EntityLivingBase.limbSwing - par1EntityLivingBase.limbSwingAmount * (1.0F - par9);
/* 101:144 */       if (par1EntityLivingBase.isChild()) {
/* 102:146 */         var16 *= 3.0F;
/* 103:    */       }
/* 104:149 */       if (var15 > 1.0F) {
/* 105:151 */         var15 = 1.0F;
/* 106:    */       }
/* 107:154 */       GL11.glEnable(3008);
/* 108:155 */       this.mainModel.setLivingAnimations(par1EntityLivingBase, var16, var15, par9);
/* 109:156 */       renderModel(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
/* 110:162 */       for (int var17 = 0; var17 < 4; var17++)
/* 111:    */       {
/* 112:164 */         int var18 = shouldRenderPass(par1EntityLivingBase, var17, par9);
/* 113:166 */         if (var18 > 0)
/* 114:    */         {
/* 115:168 */           this.renderPassModel.setLivingAnimations(par1EntityLivingBase, var16, var15, par9);
/* 116:169 */           this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
/* 117:171 */           if ((var18 & 0xF0) == 16)
/* 118:    */           {
/* 119:173 */             func_82408_c(par1EntityLivingBase, var17, par9);
/* 120:174 */             this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
/* 121:    */           }
/* 122:177 */           if ((var18 & 0xF) == 15)
/* 123:    */           {
/* 124:179 */             float var19 = par1EntityLivingBase.ticksExisted + par9;
/* 125:180 */             bindTexture(RES_ITEM_GLINT);
/* 126:181 */             GL11.glEnable(3042);
/* 127:182 */             float var20 = 0.5F;
/* 128:183 */             GL11.glColor4f(var20, var20, var20, 1.0F);
/* 129:184 */             GL11.glDepthFunc(514);
/* 130:185 */             GL11.glDepthMask(false);
/* 131:187 */             for (int var21 = 0; var21 < 2; var21++)
/* 132:    */             {
/* 133:189 */               GL11.glDisable(2896);
/* 134:190 */               float var22 = 0.76F;
/* 135:191 */               GL11.glColor4f(0.5F * var22, 0.25F * var22, 0.8F * var22, 1.0F);
/* 136:192 */               GL11.glBlendFunc(768, 1);
/* 137:193 */               GL11.glMatrixMode(5890);
/* 138:194 */               GL11.glLoadIdentity();
/* 139:195 */               float var23 = var19 * (0.001F + var21 * 0.003F) * 20.0F;
/* 140:196 */               float var24 = 0.3333333F;
/* 141:197 */               GL11.glScalef(var24, var24, var24);
/* 142:198 */               GL11.glRotatef(30.0F - var21 * 60.0F, 0.0F, 0.0F, 1.0F);
/* 143:199 */               GL11.glTranslatef(0.0F, var23, 0.0F);
/* 144:200 */               GL11.glMatrixMode(5888);
/* 145:201 */               this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
/* 146:    */             }
/* 147:204 */             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 148:205 */             GL11.glMatrixMode(5890);
/* 149:206 */             GL11.glDepthMask(true);
/* 150:207 */             GL11.glLoadIdentity();
/* 151:208 */             GL11.glMatrixMode(5888);
/* 152:209 */             GL11.glEnable(2896);
/* 153:210 */             GL11.glDisable(3042);
/* 154:211 */             GL11.glDepthFunc(515);
/* 155:    */           }
/* 156:214 */           GL11.glDisable(3042);
/* 157:215 */           GL11.glEnable(3008);
/* 158:    */         }
/* 159:    */       }
/* 160:219 */       GL11.glDepthMask(true);
/* 161:220 */       renderEquippedItems(par1EntityLivingBase, par9);
/* 162:221 */       float var27 = par1EntityLivingBase.getBrightness(par9);
/* 163:222 */       int var18 = getColorMultiplier(par1EntityLivingBase, var27, par9);
/* 164:223 */       OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 165:224 */       GL11.glDisable(3553);
/* 166:225 */       OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 167:227 */       if (((var18 >> 24 & 0xFF) > 0) || (par1EntityLivingBase.hurtTime > 0) || (par1EntityLivingBase.deathTime > 0))
/* 168:    */       {
/* 169:229 */         GL11.glDisable(3553);
/* 170:230 */         GL11.glDisable(3008);
/* 171:231 */         GL11.glEnable(3042);
/* 172:232 */         GL11.glBlendFunc(770, 771);
/* 173:233 */         GL11.glDepthFunc(514);
/* 174:235 */         if ((par1EntityLivingBase.hurtTime > 0) || (par1EntityLivingBase.deathTime > 0))
/* 175:    */         {
/* 176:237 */           GL11.glColor4f(var27, 0.0F, 0.0F, 0.4F);
/* 177:238 */           this.mainModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
/* 178:240 */           for (int var28 = 0; var28 < 4; var28++) {
/* 179:242 */             if (inheritRenderPass(par1EntityLivingBase, var28, par9) >= 0)
/* 180:    */             {
/* 181:244 */               GL11.glColor4f(var27, 0.0F, 0.0F, 0.4F);
/* 182:245 */               this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
/* 183:    */             }
/* 184:    */           }
/* 185:    */         }
/* 186:250 */         if ((var18 >> 24 & 0xFF) > 0)
/* 187:    */         {
/* 188:252 */           float var19 = (var18 >> 16 & 0xFF) / 255.0F;
/* 189:253 */           float var20 = (var18 >> 8 & 0xFF) / 255.0F;
/* 190:254 */           float var30 = (var18 & 0xFF) / 255.0F;
/* 191:255 */           float var22 = (var18 >> 24 & 0xFF) / 255.0F;
/* 192:256 */           GL11.glColor4f(var19, var20, var30, var22);
/* 193:257 */           this.mainModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
/* 194:259 */           for (int var29 = 0; var29 < 4; var29++) {
/* 195:261 */             if (inheritRenderPass(par1EntityLivingBase, var29, par9) >= 0)
/* 196:    */             {
/* 197:263 */               GL11.glColor4f(var19, var20, var30, var22);
/* 198:264 */               this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
/* 199:    */             }
/* 200:    */           }
/* 201:    */         }
/* 202:269 */         GL11.glDepthFunc(515);
/* 203:270 */         GL11.glDisable(3042);
/* 204:271 */         GL11.glEnable(3008);
/* 205:272 */         GL11.glEnable(3553);
/* 206:    */       }
/* 207:275 */       GL11.glDisable(32826);
/* 208:    */     }
/* 209:    */     catch (Exception var25)
/* 210:    */     {
/* 211:279 */       logger.error("Couldn't render entity", var25);
/* 212:    */     }
/* 213:282 */     OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 214:283 */     GL11.glEnable(3553);
/* 215:284 */     OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 216:285 */     GL11.glEnable(2884);
/* 217:286 */     GL11.glPopMatrix();
/* 218:287 */     passSpecialRender(par1EntityLivingBase, par2, par4, par6);
/* 219:    */   }
/* 220:    */   
/* 221:    */   protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7)
/* 222:    */   {
/* 223:295 */     bindEntityTexture(par1EntityLivingBase);
/* 224:297 */     if (!par1EntityLivingBase.isInvisible())
/* 225:    */     {
/* 226:299 */       this.mainModel.render(par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
/* 227:    */     }
/* 228:301 */     else if (!par1EntityLivingBase.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))
/* 229:    */     {
/* 230:303 */       GL11.glPushMatrix();
/* 231:304 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
/* 232:305 */       GL11.glDepthMask(false);
/* 233:306 */       GL11.glEnable(3042);
/* 234:307 */       GL11.glBlendFunc(770, 771);
/* 235:308 */       GL11.glAlphaFunc(516, 0.003921569F);
/* 236:309 */       this.mainModel.render(par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
/* 237:310 */       GL11.glDisable(3042);
/* 238:311 */       GL11.glAlphaFunc(516, 0.1F);
/* 239:312 */       GL11.glPopMatrix();
/* 240:313 */       GL11.glDepthMask(true);
/* 241:    */     }
/* 242:    */     else
/* 243:    */     {
/* 244:317 */       this.mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityLivingBase);
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6)
/* 249:    */   {
/* 250:326 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/* 251:    */   }
/* 252:    */   
/* 253:    */   protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 254:    */   {
/* 255:331 */     GL11.glRotatef(180.0F - par3, 0.0F, 1.0F, 0.0F);
/* 256:333 */     if (par1EntityLivingBase.deathTime > 0)
/* 257:    */     {
/* 258:335 */       float var5 = (par1EntityLivingBase.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
/* 259:336 */       var5 = MathHelper.sqrt_float(var5);
/* 260:338 */       if (var5 > 1.0F) {
/* 261:340 */         var5 = 1.0F;
/* 262:    */       }
/* 263:343 */       GL11.glRotatef(var5 * getDeathMaxRotation(par1EntityLivingBase), 0.0F, 0.0F, 1.0F);
/* 264:    */     }
/* 265:    */     else
/* 266:    */     {
/* 267:347 */       String var6 = EnumChatFormatting.getTextWithoutFormattingCodes(par1EntityLivingBase.getCommandSenderName());
/* 268:349 */       if (((var6.equals("Dinnerbone")) || (var6.equals("Grumm"))) && ((!(par1EntityLivingBase instanceof EntityPlayer)) || (!((EntityPlayer)par1EntityLivingBase).getHideCape())))
/* 269:    */       {
/* 270:351 */         GL11.glTranslatef(0.0F, par1EntityLivingBase.height + 0.1F, 0.0F);
/* 271:352 */         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 272:    */       }
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   protected float renderSwingProgress(EntityLivingBase par1EntityLivingBase, float par2)
/* 277:    */   {
/* 278:359 */     return par1EntityLivingBase.getSwingProgress(par2);
/* 279:    */   }
/* 280:    */   
/* 281:    */   protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
/* 282:    */   {
/* 283:367 */     return par1EntityLivingBase.ticksExisted + par2;
/* 284:    */   }
/* 285:    */   
/* 286:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {}
/* 287:    */   
/* 288:    */   protected void renderArrowsStuckInEntity(EntityLivingBase par1EntityLivingBase, float par2)
/* 289:    */   {
/* 290:377 */     int var3 = par1EntityLivingBase.getArrowCountInEntity();
/* 291:379 */     if (var3 > 0)
/* 292:    */     {
/* 293:381 */       EntityArrow var4 = new EntityArrow(par1EntityLivingBase.worldObj, par1EntityLivingBase.posX, par1EntityLivingBase.posY, par1EntityLivingBase.posZ);
/* 294:382 */       Random var5 = new Random(par1EntityLivingBase.getEntityId());
/* 295:383 */       RenderHelper.disableStandardItemLighting();
/* 296:385 */       for (int var6 = 0; var6 < var3; var6++)
/* 297:    */       {
/* 298:387 */         GL11.glPushMatrix();
/* 299:388 */         ModelRenderer var7 = this.mainModel.getRandomModelBox(var5);
/* 300:389 */         ModelBox var8 = (ModelBox)var7.cubeList.get(var5.nextInt(var7.cubeList.size()));
/* 301:390 */         var7.postRender(0.0625F);
/* 302:391 */         float var9 = var5.nextFloat();
/* 303:392 */         float var10 = var5.nextFloat();
/* 304:393 */         float var11 = var5.nextFloat();
/* 305:394 */         float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0F;
/* 306:395 */         float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0F;
/* 307:396 */         float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0F;
/* 308:397 */         GL11.glTranslatef(var12, var13, var14);
/* 309:398 */         var9 = var9 * 2.0F - 1.0F;
/* 310:399 */         var10 = var10 * 2.0F - 1.0F;
/* 311:400 */         var11 = var11 * 2.0F - 1.0F;
/* 312:401 */         var9 *= -1.0F;
/* 313:402 */         var10 *= -1.0F;
/* 314:403 */         var11 *= -1.0F;
/* 315:404 */         float var15 = MathHelper.sqrt_float(var9 * var9 + var11 * var11);
/* 316:405 */         var4.prevRotationYaw = (var4.rotationYaw = (float)(Math.atan2(var9, var11) * 180.0D / 3.141592653589793D));
/* 317:406 */         var4.prevRotationPitch = (var4.rotationPitch = (float)(Math.atan2(var10, var15) * 180.0D / 3.141592653589793D));
/* 318:407 */         double var16 = 0.0D;
/* 319:408 */         double var18 = 0.0D;
/* 320:409 */         double var20 = 0.0D;
/* 321:410 */         float var22 = 0.0F;
/* 322:411 */         this.renderManager.func_147940_a(var4, var16, var18, var20, var22, par2);
/* 323:412 */         GL11.glPopMatrix();
/* 324:    */       }
/* 325:415 */       RenderHelper.enableStandardItemLighting();
/* 326:    */     }
/* 327:    */   }
/* 328:    */   
/* 329:    */   protected int inheritRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 330:    */   {
/* 331:421 */     return shouldRenderPass(par1EntityLivingBase, par2, par3);
/* 332:    */   }
/* 333:    */   
/* 334:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 335:    */   {
/* 336:429 */     return -1;
/* 337:    */   }
/* 338:    */   
/* 339:    */   protected void func_82408_c(EntityLivingBase par1EntityLivingBase, int par2, float par3) {}
/* 340:    */   
/* 341:    */   protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
/* 342:    */   {
/* 343:436 */     return 90.0F;
/* 344:    */   }
/* 345:    */   
/* 346:    */   protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase, float par2, float par3)
/* 347:    */   {
/* 348:444 */     return 0;
/* 349:    */   }
/* 350:    */   
/* 351:    */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {}
/* 352:    */   
/* 353:    */   protected void passSpecialRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6)
/* 354:    */   {
/* 355:458 */     GL11.glAlphaFunc(516, 0.1F);
/* 356:460 */     if (func_110813_b(par1EntityLivingBase))
/* 357:    */     {
/* 358:462 */       float var8 = 1.6F;
/* 359:463 */       float var9 = 0.01666667F * var8;
/* 360:464 */       double var10 = par1EntityLivingBase.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 361:465 */       float var12 = par1EntityLivingBase.isSneaking() ? 32.0F : 64.0F;
/* 362:467 */       if (var10 < var12 * var12)
/* 363:    */       {
/* 364:469 */         String var13 = par1EntityLivingBase.func_145748_c_().getFormattedText();
/* 365:471 */         if (par1EntityLivingBase.isSneaking())
/* 366:    */         {
/* 367:473 */           FontRenderer var14 = getFontRendererFromRenderManager();
/* 368:474 */           GL11.glPushMatrix();
/* 369:475 */           GL11.glTranslatef((float)par2 + 0.0F, (float)par4 + par1EntityLivingBase.height + 0.5F, (float)par6);
/* 370:476 */           GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 371:477 */           GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 372:478 */           GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 373:479 */           GL11.glScalef(-var9, -var9, var9);
/* 374:480 */           GL11.glDisable(2896);
/* 375:481 */           GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
/* 376:482 */           GL11.glDepthMask(false);
/* 377:483 */           GL11.glEnable(3042);
/* 378:484 */           OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 379:485 */           Tessellator var15 = Tessellator.instance;
/* 380:486 */           GL11.glDisable(3553);
/* 381:487 */           var15.startDrawingQuads();
/* 382:488 */           int var16 = var14.getStringWidth(var13) / 2;
/* 383:489 */           var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
/* 384:490 */           var15.addVertex(-var16 - 1, -1.0D, 0.0D);
/* 385:491 */           var15.addVertex(-var16 - 1, 8.0D, 0.0D);
/* 386:492 */           var15.addVertex(var16 + 1, 8.0D, 0.0D);
/* 387:493 */           var15.addVertex(var16 + 1, -1.0D, 0.0D);
/* 388:494 */           var15.draw();
/* 389:495 */           GL11.glEnable(3553);
/* 390:496 */           GL11.glDepthMask(true);
/* 391:497 */           var14.drawString(var13, -var14.getStringWidth(var13) / 2, 0, 553648127);
/* 392:498 */           GL11.glEnable(2896);
/* 393:499 */           GL11.glDisable(3042);
/* 394:500 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 395:501 */           GL11.glPopMatrix();
/* 396:    */         }
/* 397:    */         else
/* 398:    */         {
/* 399:505 */           func_96449_a(par1EntityLivingBase, par2, par4, par6, var13, var9, var10);
/* 400:    */         }
/* 401:    */       }
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   protected boolean func_110813_b(EntityLivingBase par1EntityLivingBase)
/* 406:    */   {
/* 407:513 */     return (Minecraft.isGuiEnabled()) && (par1EntityLivingBase != this.renderManager.livingPlayer) && (!par1EntityLivingBase.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) && (par1EntityLivingBase.riddenByEntity == null);
/* 408:    */   }
/* 409:    */   
/* 410:    */   protected void func_96449_a(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, String par8Str, float par9, double par10)
/* 411:    */   {
/* 412:518 */     if (par1EntityLivingBase.isPlayerSleeping()) {
/* 413:520 */       func_147906_a(par1EntityLivingBase, par8Str, par2, par4 - 1.5D, par6, 64);
/* 414:    */     } else {
/* 415:524 */       func_147906_a(par1EntityLivingBase, par8Str, par2, par4, par6, 64);
/* 416:    */     }
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 420:    */   {
/* 421:536 */     doRender((EntityLivingBase)par1Entity, par2, par4, par6, par8, par9);
/* 422:    */   }
/* 423:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RendererLivingEntity
 * JD-Core Version:    0.7.0.1
 */