/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import java.util.concurrent.Callable;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.gui.FontRenderer;
/*   8:    */ import net.minecraft.client.renderer.ItemRenderer;
/*   9:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  10:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  11:    */ import net.minecraft.client.renderer.Tessellator;
/*  12:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  13:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  14:    */ import net.minecraft.client.settings.GameSettings;
/*  15:    */ import net.minecraft.crash.CrashReport;
/*  16:    */ import net.minecraft.crash.CrashReportCategory;
/*  17:    */ import net.minecraft.entity.Entity;
/*  18:    */ import net.minecraft.entity.item.EntityItem;
/*  19:    */ import net.minecraft.item.Item;
/*  20:    */ import net.minecraft.item.ItemBlock;
/*  21:    */ import net.minecraft.item.ItemCloth;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraft.util.IIcon;
/*  24:    */ import net.minecraft.util.MathHelper;
/*  25:    */ import net.minecraft.util.ReportedException;
/*  26:    */ import net.minecraft.util.ResourceLocation;
/*  27:    */ import org.lwjgl.opengl.GL11;
/*  28:    */ 
/*  29:    */ public class RenderItem
/*  30:    */   extends Render
/*  31:    */ {
/*  32: 30 */   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*  33: 31 */   private RenderBlocks field_147913_i = new RenderBlocks();
/*  34: 34 */   private Random random = new Random();
/*  35: 35 */   public boolean renderWithColor = true;
/*  36:    */   public float zLevel;
/*  37:    */   public static boolean renderInFrame;
/*  38:    */   private static final String __OBFID = "CL_00001003";
/*  39:    */   
/*  40:    */   public RenderItem()
/*  41:    */   {
/*  42: 44 */     this.shadowSize = 0.15F;
/*  43: 45 */     this.shadowOpaque = 0.75F;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void doRender(EntityItem par1EntityItem, double par2, double par4, double par6, float par8, float par9)
/*  47:    */   {
/*  48: 56 */     ItemStack var10 = par1EntityItem.getEntityItem();
/*  49: 58 */     if (var10.getItem() != null)
/*  50:    */     {
/*  51: 60 */       bindEntityTexture(par1EntityItem);
/*  52: 61 */       this.random.setSeed(187L);
/*  53: 62 */       GL11.glPushMatrix();
/*  54: 63 */       float var11 = MathHelper.sin((par1EntityItem.age + par9) / 10.0F + par1EntityItem.hoverStart) * 0.1F + 0.1F;
/*  55: 64 */       float var12 = ((par1EntityItem.age + par9) / 20.0F + par1EntityItem.hoverStart) * 57.295776F;
/*  56: 65 */       byte var13 = 1;
/*  57: 67 */       if (par1EntityItem.getEntityItem().stackSize > 1) {
/*  58: 69 */         var13 = 2;
/*  59:    */       }
/*  60: 72 */       if (par1EntityItem.getEntityItem().stackSize > 5) {
/*  61: 74 */         var13 = 3;
/*  62:    */       }
/*  63: 77 */       if (par1EntityItem.getEntityItem().stackSize > 20) {
/*  64: 79 */         var13 = 4;
/*  65:    */       }
/*  66: 82 */       if (par1EntityItem.getEntityItem().stackSize > 40) {
/*  67: 84 */         var13 = 5;
/*  68:    */       }
/*  69: 87 */       GL11.glTranslatef((float)par2, (float)par4 + var11, (float)par6);
/*  70: 88 */       GL11.glEnable(32826);
/*  71: 93 */       if ((var10.getItemSpriteNumber() == 0) && ((var10.getItem() instanceof ItemBlock)) && (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var10.getItem()).getRenderType())))
/*  72:    */       {
/*  73: 95 */         Block var21 = Block.getBlockFromItem(var10.getItem());
/*  74: 96 */         GL11.glRotatef(var12, 0.0F, 1.0F, 0.0F);
/*  75: 98 */         if (renderInFrame)
/*  76:    */         {
/*  77:100 */           GL11.glScalef(1.25F, 1.25F, 1.25F);
/*  78:101 */           GL11.glTranslatef(0.0F, 0.05F, 0.0F);
/*  79:102 */           GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*  80:    */         }
/*  81:105 */         float var25 = 0.25F;
/*  82:106 */         int var24 = var21.getRenderType();
/*  83:108 */         if ((var24 == 1) || (var24 == 19) || (var24 == 12) || (var24 == 2)) {
/*  84:110 */           var25 = 0.5F;
/*  85:    */         }
/*  86:113 */         if (var21.getRenderBlockPass() > 0)
/*  87:    */         {
/*  88:115 */           GL11.glAlphaFunc(516, 0.1F);
/*  89:116 */           GL11.glEnable(3042);
/*  90:117 */           OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  91:    */         }
/*  92:120 */         GL11.glScalef(var25, var25, var25);
/*  93:122 */         for (int var26 = 0; var26 < var13; var26++)
/*  94:    */         {
/*  95:124 */           GL11.glPushMatrix();
/*  96:126 */           if (var26 > 0)
/*  97:    */           {
/*  98:128 */             float var18 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
/*  99:129 */             float var19 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
/* 100:130 */             float var20 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
/* 101:131 */             GL11.glTranslatef(var18, var19, var20);
/* 102:    */           }
/* 103:134 */           this.field_147913_i.renderBlockAsItem(var21, var10.getItemDamage(), 1.0F);
/* 104:135 */           GL11.glPopMatrix();
/* 105:    */         }
/* 106:138 */         if (var21.getRenderBlockPass() > 0) {
/* 107:140 */           GL11.glDisable(3042);
/* 108:    */         }
/* 109:    */       }
/* 110:147 */       else if ((var10.getItemSpriteNumber() == 1) && (var10.getItem().requiresMultipleRenderPasses()))
/* 111:    */       {
/* 112:149 */         if (renderInFrame)
/* 113:    */         {
/* 114:151 */           GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
/* 115:152 */           GL11.glTranslatef(0.0F, -0.05F, 0.0F);
/* 116:    */         }
/* 117:    */         else
/* 118:    */         {
/* 119:156 */           GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 120:    */         }
/* 121:159 */         for (int var23 = 0; var23 <= 1; var23++)
/* 122:    */         {
/* 123:161 */           this.random.setSeed(187L);
/* 124:162 */           IIcon var22 = var10.getItem().getIconFromDamageForRenderPass(var10.getItemDamage(), var23);
/* 125:164 */           if (this.renderWithColor)
/* 126:    */           {
/* 127:166 */             int var24 = var10.getItem().getColorFromItemStack(var10, var23);
/* 128:167 */             float var17 = (var24 >> 16 & 0xFF) / 255.0F;
/* 129:168 */             float var18 = (var24 >> 8 & 0xFF) / 255.0F;
/* 130:169 */             float var19 = (var24 & 0xFF) / 255.0F;
/* 131:170 */             GL11.glColor4f(var17, var18, var19, 1.0F);
/* 132:171 */             renderDroppedItem(par1EntityItem, var22, var13, par9, var17, var18, var19);
/* 133:    */           }
/* 134:    */           else
/* 135:    */           {
/* 136:175 */             renderDroppedItem(par1EntityItem, var22, var13, par9, 1.0F, 1.0F, 1.0F);
/* 137:    */           }
/* 138:    */         }
/* 139:    */       }
/* 140:    */       else
/* 141:    */       {
/* 142:181 */         if ((var10 != null) && ((var10.getItem() instanceof ItemCloth)))
/* 143:    */         {
/* 144:183 */           GL11.glAlphaFunc(516, 0.1F);
/* 145:184 */           GL11.glEnable(3042);
/* 146:185 */           OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 147:    */         }
/* 148:188 */         if (renderInFrame)
/* 149:    */         {
/* 150:190 */           GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
/* 151:191 */           GL11.glTranslatef(0.0F, -0.05F, 0.0F);
/* 152:    */         }
/* 153:    */         else
/* 154:    */         {
/* 155:195 */           GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 156:    */         }
/* 157:198 */         IIcon var14 = var10.getIconIndex();
/* 158:200 */         if (this.renderWithColor)
/* 159:    */         {
/* 160:202 */           int var15 = var10.getItem().getColorFromItemStack(var10, 0);
/* 161:203 */           float var16 = (var15 >> 16 & 0xFF) / 255.0F;
/* 162:204 */           float var17 = (var15 >> 8 & 0xFF) / 255.0F;
/* 163:205 */           float var18 = (var15 & 0xFF) / 255.0F;
/* 164:206 */           renderDroppedItem(par1EntityItem, var14, var13, par9, var16, var17, var18);
/* 165:    */         }
/* 166:    */         else
/* 167:    */         {
/* 168:210 */           renderDroppedItem(par1EntityItem, var14, var13, par9, 1.0F, 1.0F, 1.0F);
/* 169:    */         }
/* 170:213 */         if ((var10 != null) && ((var10.getItem() instanceof ItemCloth))) {
/* 171:215 */           GL11.glDisable(3042);
/* 172:    */         }
/* 173:    */       }
/* 174:220 */       GL11.glDisable(32826);
/* 175:221 */       GL11.glPopMatrix();
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   protected ResourceLocation getEntityTexture(EntityItem par1EntityItem)
/* 180:    */   {
/* 181:230 */     return this.renderManager.renderEngine.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
/* 182:    */   }
/* 183:    */   
/* 184:    */   private void renderDroppedItem(EntityItem par1EntityItem, IIcon par2Icon, int par3, float par4, float par5, float par6, float par7)
/* 185:    */   {
/* 186:238 */     Tessellator var8 = Tessellator.instance;
/* 187:240 */     if (par2Icon == null)
/* 188:    */     {
/* 189:242 */       TextureManager var9 = Minecraft.getMinecraft().getTextureManager();
/* 190:243 */       ResourceLocation var10 = var9.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
/* 191:244 */       par2Icon = ((TextureMap)var9.getTexture(var10)).getAtlasSprite("missingno");
/* 192:    */     }
/* 193:247 */     float var25 = par2Icon.getMinU();
/* 194:248 */     float var26 = par2Icon.getMaxU();
/* 195:249 */     float var11 = par2Icon.getMinV();
/* 196:250 */     float var12 = par2Icon.getMaxV();
/* 197:251 */     float var13 = 1.0F;
/* 198:252 */     float var14 = 0.5F;
/* 199:253 */     float var15 = 0.25F;
/* 200:256 */     if (this.renderManager.options.fancyGraphics)
/* 201:    */     {
/* 202:258 */       GL11.glPushMatrix();
/* 203:260 */       if (renderInFrame) {
/* 204:262 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 205:    */       } else {
/* 206:266 */         GL11.glRotatef(((par1EntityItem.age + par4) / 20.0F + par1EntityItem.hoverStart) * 57.295776F, 0.0F, 1.0F, 0.0F);
/* 207:    */       }
/* 208:269 */       float var16 = 0.0625F;
/* 209:270 */       float var17 = 0.021875F;
/* 210:271 */       ItemStack var18 = par1EntityItem.getEntityItem();
/* 211:272 */       int var19 = var18.stackSize;
/* 212:    */       byte var24;
/* 213:    */       byte var24;
/* 214:275 */       if (var19 < 2)
/* 215:    */       {
/* 216:277 */         var24 = 1;
/* 217:    */       }
/* 218:    */       else
/* 219:    */       {
/* 220:    */         byte var24;
/* 221:279 */         if (var19 < 16)
/* 222:    */         {
/* 223:281 */           var24 = 2;
/* 224:    */         }
/* 225:    */         else
/* 226:    */         {
/* 227:    */           byte var24;
/* 228:283 */           if (var19 < 32) {
/* 229:285 */             var24 = 3;
/* 230:    */           } else {
/* 231:289 */             var24 = 4;
/* 232:    */           }
/* 233:    */         }
/* 234:    */       }
/* 235:292 */       GL11.glTranslatef(-var14, -var15, -((var16 + var17) * var24 / 2.0F));
/* 236:294 */       for (int var20 = 0; var20 < var24; var20++)
/* 237:    */       {
/* 238:296 */         GL11.glTranslatef(0.0F, 0.0F, var16 + var17);
/* 239:298 */         if (var18.getItemSpriteNumber() == 0) {
/* 240:300 */           bindTexture(TextureMap.locationBlocksTexture);
/* 241:    */         } else {
/* 242:304 */           bindTexture(TextureMap.locationItemsTexture);
/* 243:    */         }
/* 244:307 */         GL11.glColor4f(par5, par6, par7, 1.0F);
/* 245:308 */         ItemRenderer.renderItemIn2D(var8, var26, var11, var25, var12, par2Icon.getIconWidth(), par2Icon.getIconHeight(), var16);
/* 246:310 */         if (var18.hasEffect())
/* 247:    */         {
/* 248:312 */           GL11.glDepthFunc(514);
/* 249:313 */           GL11.glDisable(2896);
/* 250:314 */           this.renderManager.renderEngine.bindTexture(RES_ITEM_GLINT);
/* 251:315 */           GL11.glEnable(3042);
/* 252:316 */           GL11.glBlendFunc(768, 1);
/* 253:317 */           float var21 = 0.76F;
/* 254:318 */           GL11.glColor4f(0.5F * var21, 0.25F * var21, 0.8F * var21, 1.0F);
/* 255:319 */           GL11.glMatrixMode(5890);
/* 256:320 */           GL11.glPushMatrix();
/* 257:321 */           float var22 = 0.125F;
/* 258:322 */           GL11.glScalef(var22, var22, var22);
/* 259:323 */           float var23 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
/* 260:324 */           GL11.glTranslatef(var23, 0.0F, 0.0F);
/* 261:325 */           GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
/* 262:326 */           ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, var16);
/* 263:327 */           GL11.glPopMatrix();
/* 264:328 */           GL11.glPushMatrix();
/* 265:329 */           GL11.glScalef(var22, var22, var22);
/* 266:330 */           var23 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
/* 267:331 */           GL11.glTranslatef(-var23, 0.0F, 0.0F);
/* 268:332 */           GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/* 269:333 */           ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, var16);
/* 270:334 */           GL11.glPopMatrix();
/* 271:335 */           GL11.glMatrixMode(5888);
/* 272:336 */           GL11.glDisable(3042);
/* 273:337 */           GL11.glEnable(2896);
/* 274:338 */           GL11.glDepthFunc(515);
/* 275:    */         }
/* 276:    */       }
/* 277:342 */       GL11.glPopMatrix();
/* 278:    */     }
/* 279:    */     else
/* 280:    */     {
/* 281:346 */       for (int var27 = 0; var27 < par3; var27++)
/* 282:    */       {
/* 283:348 */         GL11.glPushMatrix();
/* 284:350 */         if (var27 > 0)
/* 285:    */         {
/* 286:352 */           float var17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
/* 287:353 */           float var29 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
/* 288:354 */           float var28 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
/* 289:355 */           GL11.glTranslatef(var17, var29, var28);
/* 290:    */         }
/* 291:358 */         if (!renderInFrame) {
/* 292:360 */           GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 293:    */         }
/* 294:363 */         GL11.glColor4f(par5, par6, par7, 1.0F);
/* 295:364 */         var8.startDrawingQuads();
/* 296:365 */         var8.setNormal(0.0F, 1.0F, 0.0F);
/* 297:366 */         var8.addVertexWithUV(0.0F - var14, 0.0F - var15, 0.0D, var25, var12);
/* 298:367 */         var8.addVertexWithUV(var13 - var14, 0.0F - var15, 0.0D, var26, var12);
/* 299:368 */         var8.addVertexWithUV(var13 - var14, 1.0F - var15, 0.0D, var26, var11);
/* 300:369 */         var8.addVertexWithUV(0.0F - var14, 1.0F - var15, 0.0D, var25, var11);
/* 301:370 */         var8.draw();
/* 302:371 */         GL11.glPopMatrix();
/* 303:    */       }
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void renderItemIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5)
/* 308:    */   {
/* 309:381 */     int var6 = par3ItemStack.getItemDamage();
/* 310:382 */     Object var7 = par3ItemStack.getIconIndex();
/* 311:383 */     GL11.glEnable(3042);
/* 312:384 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 313:390 */     if ((par3ItemStack.getItemSpriteNumber() == 0) && (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(par3ItemStack.getItem()).getRenderType())))
/* 314:    */     {
/* 315:392 */       par2TextureManager.bindTexture(TextureMap.locationBlocksTexture);
/* 316:393 */       Block var15 = Block.getBlockFromItem(par3ItemStack.getItem());
/* 317:394 */       GL11.glPushMatrix();
/* 318:395 */       GL11.glTranslatef(par4 - 2, par5 + 3, -3.0F + this.zLevel);
/* 319:396 */       GL11.glScalef(10.0F, 10.0F, 10.0F);
/* 320:397 */       GL11.glTranslatef(1.0F, 0.5F, 1.0F);
/* 321:398 */       GL11.glScalef(1.0F, 1.0F, -1.0F);
/* 322:399 */       GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
/* 323:400 */       GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 324:401 */       int var9 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, 0);
/* 325:402 */       float var18 = (var9 >> 16 & 0xFF) / 255.0F;
/* 326:403 */       float var17 = (var9 >> 8 & 0xFF) / 255.0F;
/* 327:404 */       float var12 = (var9 & 0xFF) / 255.0F;
/* 328:406 */       if (this.renderWithColor) {
/* 329:408 */         GL11.glColor4f(var18, var17, var12, 1.0F);
/* 330:    */       }
/* 331:411 */       GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/* 332:412 */       this.field_147913_i.useInventoryTint = this.renderWithColor;
/* 333:413 */       this.field_147913_i.renderBlockAsItem(var15, var6, 1.0F);
/* 334:414 */       this.field_147913_i.useInventoryTint = true;
/* 335:415 */       GL11.glPopMatrix();
/* 336:    */     }
/* 337:417 */     else if (par3ItemStack.getItem().requiresMultipleRenderPasses())
/* 338:    */     {
/* 339:419 */       GL11.glDisable(2896);
/* 340:420 */       GL11.glEnable(3008);
/* 341:421 */       par2TextureManager.bindTexture(TextureMap.locationItemsTexture);
/* 342:422 */       GL11.glDisable(3008);
/* 343:423 */       GL11.glDisable(3553);
/* 344:424 */       GL11.glEnable(3042);
/* 345:425 */       OpenGlHelper.glBlendFunc(0, 0, 0, 0);
/* 346:426 */       GL11.glColorMask(false, false, false, true);
/* 347:427 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 348:428 */       Tessellator var8 = Tessellator.instance;
/* 349:429 */       var8.startDrawingQuads();
/* 350:430 */       var8.setColorOpaque_I(-1);
/* 351:431 */       var8.addVertex(par4 - 2, par5 + 18, this.zLevel);
/* 352:432 */       var8.addVertex(par4 + 18, par5 + 18, this.zLevel);
/* 353:433 */       var8.addVertex(par4 + 18, par5 - 2, this.zLevel);
/* 354:434 */       var8.addVertex(par4 - 2, par5 - 2, this.zLevel);
/* 355:435 */       var8.draw();
/* 356:436 */       GL11.glColorMask(true, true, true, true);
/* 357:437 */       GL11.glEnable(3553);
/* 358:438 */       GL11.glEnable(3008);
/* 359:439 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 360:441 */       for (int var9 = 0; var9 <= 1; var9++)
/* 361:    */       {
/* 362:443 */         IIcon var10 = par3ItemStack.getItem().getIconFromDamageForRenderPass(var6, var9);
/* 363:444 */         int var11 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, var9);
/* 364:445 */         float var12 = (var11 >> 16 & 0xFF) / 255.0F;
/* 365:446 */         float var13 = (var11 >> 8 & 0xFF) / 255.0F;
/* 366:447 */         float var14 = (var11 & 0xFF) / 255.0F;
/* 367:449 */         if (this.renderWithColor) {
/* 368:451 */           GL11.glColor4f(var12, var13, var14, 1.0F);
/* 369:    */         }
/* 370:454 */         renderIcon(par4, par5, var10, 16, 16);
/* 371:    */       }
/* 372:457 */       GL11.glDisable(3008);
/* 373:458 */       GL11.glEnable(2896);
/* 374:    */     }
/* 375:    */     else
/* 376:    */     {
/* 377:462 */       GL11.glDisable(2896);
/* 378:463 */       ResourceLocation var16 = par2TextureManager.getResourceLocation(par3ItemStack.getItemSpriteNumber());
/* 379:464 */       par2TextureManager.bindTexture(var16);
/* 380:466 */       if (var7 == null) {
/* 381:468 */         var7 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(var16)).getAtlasSprite("missingno");
/* 382:    */       }
/* 383:471 */       int var9 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, 0);
/* 384:472 */       float var18 = (var9 >> 16 & 0xFF) / 255.0F;
/* 385:473 */       float var17 = (var9 >> 8 & 0xFF) / 255.0F;
/* 386:474 */       float var12 = (var9 & 0xFF) / 255.0F;
/* 387:476 */       if (this.renderWithColor) {
/* 388:478 */         GL11.glColor4f(var18, var17, var12, 1.0F);
/* 389:    */       }
/* 390:481 */       renderIcon(par4, par5, (IIcon)var7, 16, 16);
/* 391:482 */       GL11.glEnable(2896);
/* 392:    */     }
/* 393:485 */     GL11.glEnable(2884);
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void renderItemAndEffectIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, final ItemStack par3ItemStack, int par4, int par5)
/* 397:    */   {
/* 398:493 */     if (par3ItemStack != null)
/* 399:    */     {
/* 400:495 */       this.zLevel += 50.0F;
/* 401:    */       try
/* 402:    */       {
/* 403:499 */         renderItemIntoGUI(par1FontRenderer, par2TextureManager, par3ItemStack, par4, par5);
/* 404:    */       }
/* 405:    */       catch (Throwable var9)
/* 406:    */       {
/* 407:503 */         CrashReport var7 = CrashReport.makeCrashReport(var9, "Rendering item");
/* 408:504 */         CrashReportCategory var8 = var7.makeCategory("Item being rendered");
/* 409:505 */         var8.addCrashSectionCallable("Item Type", new Callable()
/* 410:    */         {
/* 411:    */           private static final String __OBFID = "CL_00001004";
/* 412:    */           
/* 413:    */           public String call()
/* 414:    */           {
/* 415:510 */             return String.valueOf(par3ItemStack.getItem());
/* 416:    */           }
/* 417:512 */         });
/* 418:513 */         var8.addCrashSectionCallable("Item Aux", new Callable()
/* 419:    */         {
/* 420:    */           private static final String __OBFID = "CL_00001005";
/* 421:    */           
/* 422:    */           public String call()
/* 423:    */           {
/* 424:518 */             return String.valueOf(par3ItemStack.getItemDamage());
/* 425:    */           }
/* 426:520 */         });
/* 427:521 */         var8.addCrashSectionCallable("Item NBT", new Callable()
/* 428:    */         {
/* 429:    */           private static final String __OBFID = "CL_00001006";
/* 430:    */           
/* 431:    */           public String call()
/* 432:    */           {
/* 433:526 */             return String.valueOf(par3ItemStack.getTagCompound());
/* 434:    */           }
/* 435:528 */         });
/* 436:529 */         var8.addCrashSectionCallable("Item Foil", new Callable()
/* 437:    */         {
/* 438:    */           private static final String __OBFID = "CL_00001007";
/* 439:    */           
/* 440:    */           public String call()
/* 441:    */           {
/* 442:534 */             return String.valueOf(par3ItemStack.hasEffect());
/* 443:    */           }
/* 444:536 */         });
/* 445:537 */         throw new ReportedException(var7);
/* 446:    */       }
/* 447:540 */       if (par3ItemStack.hasEffect())
/* 448:    */       {
/* 449:542 */         GL11.glDepthFunc(514);
/* 450:543 */         GL11.glDisable(2896);
/* 451:544 */         GL11.glDepthMask(false);
/* 452:545 */         par2TextureManager.bindTexture(RES_ITEM_GLINT);
/* 453:546 */         GL11.glEnable(3008);
/* 454:547 */         GL11.glEnable(3042);
/* 455:548 */         GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
/* 456:549 */         renderGlint(par4 * 431278612 + par5 * 32178161, par4 - 2, par5 - 2, 20, 20);
/* 457:550 */         GL11.glDepthMask(true);
/* 458:551 */         GL11.glDisable(3008);
/* 459:552 */         GL11.glEnable(2896);
/* 460:553 */         GL11.glDepthFunc(515);
/* 461:    */       }
/* 462:556 */       this.zLevel -= 50.0F;
/* 463:    */     }
/* 464:    */   }
/* 465:    */   
/* 466:    */   private void renderGlint(int par1, int par2, int par3, int par4, int par5)
/* 467:    */   {
/* 468:562 */     for (int var6 = 0; var6 < 2; var6++)
/* 469:    */     {
/* 470:564 */       OpenGlHelper.glBlendFunc(772, 1, 0, 0);
/* 471:565 */       float var7 = 0.0039063F;
/* 472:566 */       float var8 = 0.0039063F;
/* 473:567 */       float var9 = (float)(Minecraft.getSystemTime() % (3000 + var6 * 1873)) / (3000.0F + var6 * 1873) * 256.0F;
/* 474:568 */       float var10 = 0.0F;
/* 475:569 */       Tessellator var11 = Tessellator.instance;
/* 476:570 */       float var12 = 4.0F;
/* 477:572 */       if (var6 == 1) {
/* 478:574 */         var12 = -1.0F;
/* 479:    */       }
/* 480:577 */       var11.startDrawingQuads();
/* 481:578 */       var11.addVertexWithUV(par2 + 0, par3 + par5, this.zLevel, (var9 + par5 * var12) * var7, (var10 + par5) * var8);
/* 482:579 */       var11.addVertexWithUV(par2 + par4, par3 + par5, this.zLevel, (var9 + par4 + par5 * var12) * var7, (var10 + par5) * var8);
/* 483:580 */       var11.addVertexWithUV(par2 + par4, par3 + 0, this.zLevel, (var9 + par4) * var7, (var10 + 0.0F) * var8);
/* 484:581 */       var11.addVertexWithUV(par2 + 0, par3 + 0, this.zLevel, (var9 + 0.0F) * var7, (var10 + 0.0F) * var8);
/* 485:582 */       var11.draw();
/* 486:    */     }
/* 487:    */   }
/* 488:    */   
/* 489:    */   public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5)
/* 490:    */   {
/* 491:592 */     renderItemOverlayIntoGUI(par1FontRenderer, par2TextureManager, par3ItemStack, par4, par5, null);
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5, String par6Str)
/* 495:    */   {
/* 496:597 */     if (par3ItemStack != null)
/* 497:    */     {
/* 498:599 */       if ((par3ItemStack.stackSize > 1) || (par6Str != null))
/* 499:    */       {
/* 500:601 */         String var7 = par6Str == null ? String.valueOf(par3ItemStack.stackSize) : par6Str;
/* 501:602 */         GL11.glDisable(2896);
/* 502:603 */         GL11.glDisable(2929);
/* 503:604 */         GL11.glDisable(3042);
/* 504:605 */         par1FontRenderer.drawStringWithShadow(var7, par4 + 19 - 2 - par1FontRenderer.getStringWidth(var7), par5 + 6 + 3, 16777215);
/* 505:606 */         GL11.glEnable(2896);
/* 506:607 */         GL11.glEnable(2929);
/* 507:    */       }
/* 508:610 */       if (par3ItemStack.isItemDamaged())
/* 509:    */       {
/* 510:612 */         int var12 = (int)Math.round(13.0D - par3ItemStack.getItemDamageForDisplay() * 13.0D / par3ItemStack.getMaxDamage());
/* 511:613 */         int var8 = (int)Math.round(255.0D - par3ItemStack.getItemDamageForDisplay() * 255.0D / par3ItemStack.getMaxDamage());
/* 512:614 */         GL11.glDisable(2896);
/* 513:615 */         GL11.glDisable(2929);
/* 514:616 */         GL11.glDisable(3553);
/* 515:617 */         GL11.glDisable(3008);
/* 516:618 */         GL11.glDisable(3042);
/* 517:619 */         Tessellator var9 = Tessellator.instance;
/* 518:620 */         int var10 = 255 - var8 << 16 | var8 << 8;
/* 519:621 */         int var11 = (255 - var8) / 4 << 16 | 0x3F00;
/* 520:622 */         renderQuad(var9, par4 + 2, par5 + 13, 13, 2, 0);
/* 521:623 */         renderQuad(var9, par4 + 2, par5 + 13, 12, 1, var11);
/* 522:624 */         renderQuad(var9, par4 + 2, par5 + 13, var12, 1, var10);
/* 523:625 */         GL11.glEnable(3553);
/* 524:626 */         GL11.glEnable(2896);
/* 525:627 */         GL11.glEnable(2929);
/* 526:628 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 527:    */       }
/* 528:    */     }
/* 529:    */   }
/* 530:    */   
/* 531:    */   private void renderQuad(Tessellator par1Tessellator, int par2, int par3, int par4, int par5, int par6)
/* 532:    */   {
/* 533:639 */     par1Tessellator.startDrawingQuads();
/* 534:640 */     par1Tessellator.setColorOpaque_I(par6);
/* 535:641 */     par1Tessellator.addVertex(par2 + 0, par3 + 0, 0.0D);
/* 536:642 */     par1Tessellator.addVertex(par2 + 0, par3 + par5, 0.0D);
/* 537:643 */     par1Tessellator.addVertex(par2 + par4, par3 + par5, 0.0D);
/* 538:644 */     par1Tessellator.addVertex(par2 + par4, par3 + 0, 0.0D);
/* 539:645 */     par1Tessellator.draw();
/* 540:    */   }
/* 541:    */   
/* 542:    */   public void renderIcon(int par1, int par2, IIcon par3Icon, int par4, int par5)
/* 543:    */   {
/* 544:650 */     Tessellator var6 = Tessellator.instance;
/* 545:651 */     var6.startDrawingQuads();
/* 546:652 */     var6.addVertexWithUV(par1 + 0, par2 + par5, this.zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
/* 547:653 */     var6.addVertexWithUV(par1 + par4, par2 + par5, this.zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
/* 548:654 */     var6.addVertexWithUV(par1 + par4, par2 + 0, this.zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
/* 549:655 */     var6.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, par3Icon.getMinU(), par3Icon.getMinV());
/* 550:656 */     var6.draw();
/* 551:    */   }
/* 552:    */   
/* 553:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 554:    */   {
/* 555:664 */     return getEntityTexture((EntityItem)par1Entity);
/* 556:    */   }
/* 557:    */   
/* 558:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 559:    */   {
/* 560:675 */     doRender((EntityItem)par1Entity, par2, par4, par6, par8, par9);
/* 561:    */   }
/* 562:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderItem
 * JD-Core Version:    0.7.0.1
 */