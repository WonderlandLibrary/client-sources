/*   1:    */ package me.connorm.Nodus.ui;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.net.URI;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Calendar;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Random;
/*  14:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  15:    */ import me.connorm.Nodus.Nodus;
/*  16:    */ import me.connorm.Nodus.file.NodusFileManager;
/*  17:    */ import me.connorm.Nodus.font.Fonts;
/*  18:    */ import me.connorm.Nodus.manager.GuiNodusManager;
/*  19:    */ import net.minecraft.client.Minecraft;
/*  20:    */ import net.minecraft.client.gui.FontRenderer;
/*  21:    */ import net.minecraft.client.gui.GuiButtonLanguage;
/*  22:    */ import net.minecraft.client.gui.GuiConfirmOpenLink;
/*  23:    */ import net.minecraft.client.gui.GuiLanguage;
/*  24:    */ import net.minecraft.client.gui.GuiMultiplayer;
/*  25:    */ import net.minecraft.client.gui.GuiOptions;
/*  26:    */ import net.minecraft.client.gui.GuiScreen;
/*  27:    */ import net.minecraft.client.gui.GuiScreenOnlineServers;
/*  28:    */ import net.minecraft.client.gui.GuiSelectWorld;
/*  29:    */ import net.minecraft.client.gui.GuiYesNo;
/*  30:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  31:    */ import net.minecraft.client.mco.ExceptionRetryCall;
/*  32:    */ import net.minecraft.client.mco.GuiScreenClientOutdated;
/*  33:    */ import net.minecraft.client.mco.McoClient;
/*  34:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  35:    */ import net.minecraft.client.renderer.Tessellator;
/*  36:    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*  37:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  38:    */ import net.minecraft.client.resources.I18n;
/*  39:    */ import net.minecraft.client.resources.IResource;
/*  40:    */ import net.minecraft.client.resources.IResourceManager;
/*  41:    */ import net.minecraft.client.shader.Framebuffer;
/*  42:    */ import net.minecraft.util.EnumChatFormatting;
/*  43:    */ import net.minecraft.util.MathHelper;
/*  44:    */ import net.minecraft.util.ResourceLocation;
/*  45:    */ import net.minecraft.util.Session;
/*  46:    */ import net.minecraft.world.demo.DemoWorldServer;
/*  47:    */ import net.minecraft.world.storage.ISaveFormat;
/*  48:    */ import net.minecraft.world.storage.WorldInfo;
/*  49:    */ import org.apache.commons.io.Charsets;
/*  50:    */ import org.apache.logging.log4j.LogManager;
/*  51:    */ import org.apache.logging.log4j.Logger;
/*  52:    */ import org.lwjgl.opengl.GL11;
/*  53:    */ import org.lwjgl.util.glu.Project;
/*  54:    */ import org.newdawn.slick.Image;
/*  55:    */ import org.newdawn.slick.SlickException;
/*  56:    */ 
/*  57:    */ public class NodusGuiMainMenu
/*  58:    */   extends GuiScreen
/*  59:    */ {
/*  60: 53 */   private static final AtomicInteger field_146973_f = new AtomicInteger(0);
/*  61: 54 */   private static final Logger logger = LogManager.getLogger();
/*  62: 57 */   private static final Random rand = new Random();
/*  63:    */   private float updateCounter;
/*  64:    */   private String splashText;
/*  65:    */   private NodusGuiButton buttonResetDemo;
/*  66:    */   private int panoramaTimer;
/*  67:    */   private DynamicTexture viewportTexture;
/*  68: 73 */   private boolean field_96141_q = true;
/*  69:    */   private static boolean field_96140_r;
/*  70:    */   private static boolean field_96139_s;
/*  71: 76 */   private final Object field_104025_t = new Object();
/*  72:    */   private String field_92025_p;
/*  73:    */   private String field_146972_A;
/*  74:    */   private String field_104024_v;
/*  75: 80 */   private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
/*  76: 81 */   private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
/*  77: 84 */   private static final ResourceLocation[] titlePanoramaPaths = { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
/*  78: 85 */   public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
/*  79:    */   private int field_92024_r;
/*  80:    */   private int field_92023_s;
/*  81:    */   private int field_92022_t;
/*  82:    */   private int field_92021_u;
/*  83:    */   private int field_92020_v;
/*  84:    */   private int field_92019_w;
/*  85:    */   private ResourceLocation field_110351_G;
/*  86:    */   private NodusGuiButton minecraftRealmsButton;
/*  87:    */   private static final String __OBFID = "CL_00001154";
/*  88:    */   
/*  89:    */   public NodusGuiMainMenu()
/*  90:    */   {
/*  91: 98 */     this.field_146972_A = field_96138_a;
/*  92: 99 */     this.splashText = "missingno";
/*  93:100 */     BufferedReader var1 = null;
/*  94:    */     try
/*  95:    */     {
/*  96:104 */       ArrayList var2 = new ArrayList();
/*  97:105 */       var1 = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
/*  98:    */       String var3;
/*  99:108 */       while ((var3 = var1.readLine()) != null)
/* 100:    */       {
/* 101:110 */         String var3 = var3.trim();
/* 102:112 */         if (!var3.isEmpty()) {
/* 103:114 */           var2.add(var3);
/* 104:    */         }
/* 105:    */       }
/* 106:118 */       if (!var2.isEmpty()) {
/* 107:    */         do
/* 108:    */         {
/* 109:122 */           this.splashText = ((String)var2.get(rand.nextInt(var2.size())));
/* 110:124 */         } while (this.splashText.hashCode() == 125780783);
/* 111:    */       }
/* 112:    */     }
/* 113:    */     catch (IOException localIOException)
/* 114:    */     {
/* 115:133 */       if (var1 != null) {
/* 116:    */         try
/* 117:    */         {
/* 118:137 */           var1.close();
/* 119:    */         }
/* 120:    */         catch (IOException localIOException1) {}
/* 121:    */       }
/* 122:    */     }
/* 123:    */     finally
/* 124:    */     {
/* 125:133 */       if (var1 != null) {
/* 126:    */         try
/* 127:    */         {
/* 128:137 */           var1.close();
/* 129:    */         }
/* 130:    */         catch (IOException localIOException2) {}
/* 131:    */       }
/* 132:    */     }
/* 133:146 */     this.updateCounter = rand.nextFloat();
/* 134:147 */     this.field_92025_p = "";
/* 135:149 */     if (!OpenGlHelper.openGL21)
/* 136:    */     {
/* 137:151 */       this.field_92025_p = "Old graphics card detected; this may prevent you from";
/* 138:152 */       this.field_146972_A = "playing in the far future as OpenGL 2.1 will be required.";
/* 139:153 */       this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void updateScreen()
/* 144:    */   {
/* 145:162 */     this.panoramaTimer += 1;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean doesGuiPauseGame()
/* 149:    */   {
/* 150:170 */     return false;
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected void keyTyped(char par1, int par2) {}
/* 154:    */   
/* 155:    */   public void initGui()
/* 156:    */   {
/* 157:183 */     this.viewportTexture = new DynamicTexture(256, 256);
/* 158:184 */     this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
/* 159:185 */     Calendar var1 = Calendar.getInstance();
/* 160:186 */     var1.setTime(new Date());
/* 161:188 */     if ((var1.get(2) + 1 == 11) && (var1.get(5) == 9)) {
/* 162:190 */       this.splashText = "Happy birthday, ez!";
/* 163:192 */     } else if ((var1.get(2) + 1 == 6) && (var1.get(5) == 1)) {
/* 164:194 */       this.splashText = "Happy birthday, Notch!";
/* 165:196 */     } else if ((var1.get(2) + 1 == 12) && (var1.get(5) == 24)) {
/* 166:198 */       this.splashText = "Merry X-mas!";
/* 167:200 */     } else if ((var1.get(2) + 1 == 1) && (var1.get(5) == 1)) {
/* 168:202 */       this.splashText = "Happy new year!";
/* 169:204 */     } else if ((var1.get(2) + 1 == 10) && (var1.get(5) == 31)) {
/* 170:206 */       this.splashText = "OOoooOOOoooo! Spooky!";
/* 171:    */     }
/* 172:209 */     boolean var2 = true;
/* 173:210 */     int var3 = height / 4 + 48;
/* 174:212 */     if (this.mc.isDemo()) {
/* 175:214 */       addDemoButtons(var3, 24);
/* 176:    */     } else {
/* 177:218 */       addSingleplayerMultiplayerButtons(var3, 24);
/* 178:    */     }
/* 179:221 */     func_130020_g();
/* 180:    */     
/* 181:223 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, var3 + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
/* 182:224 */     this.buttonList.add(new GuiButtonLanguage(5, width / 2 - 124, var3 + 72 + 12));
/* 183:225 */     this.buttonList.add(new NodusGuiButton(4, width / 2 + 2, var3 + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
/* 184:    */     
/* 185:227 */     Object var4 = this.field_104025_t;
/* 186:229 */     synchronized (this.field_104025_t)
/* 187:    */     {
/* 188:231 */       this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
/* 189:232 */       this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
/* 190:233 */       int var5 = Math.max(this.field_92023_s, this.field_92024_r);
/* 191:234 */       this.field_92022_t = ((width - var5) / 2);
/* 192:235 */       this.field_92021_u = (((NodusGuiButton)this.buttonList.get(0)).yPosition - 24);
/* 193:236 */       this.field_92020_v = (this.field_92022_t + var5);
/* 194:237 */       this.field_92019_w = (this.field_92021_u + 24);
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   private void func_130020_g()
/* 199:    */   {
/* 200:243 */     if (this.field_96141_q) {
/* 201:245 */       if (!field_96140_r)
/* 202:    */       {
/* 203:247 */         field_96140_r = true;
/* 204:248 */         new Thread("MCO Availability Checker #" + field_146973_f.incrementAndGet())
/* 205:    */         {
/* 206:    */           private static final String __OBFID = "CL_00001155";
/* 207:    */           
/* 208:    */           public void run()
/* 209:    */           {
/* 210:253 */             Session var1 = NodusGuiMainMenu.this.mc.getSession();
/* 211:254 */             McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 212:255 */             boolean var3 = false;
/* 213:257 */             for (int var4 = 0; var4 < 3; var4++)
/* 214:    */             {
/* 215:    */               try
/* 216:    */               {
/* 217:261 */                 Boolean var5 = var2.func_148687_b();
/* 218:263 */                 if (var5.booleanValue()) {
/* 219:265 */                   NodusGuiMainMenu.this.func_130022_h();
/* 220:    */                 }
/* 221:268 */                 NodusGuiMainMenu.field_96139_s = var5.booleanValue();
/* 222:    */               }
/* 223:    */               catch (ExceptionRetryCall var7)
/* 224:    */               {
/* 225:272 */                 var3 = true;
/* 226:    */               }
/* 227:    */               catch (ExceptionMcoService var8)
/* 228:    */               {
/* 229:276 */                 NodusGuiMainMenu.logger.error("Couldn't connect to Realms");
/* 230:    */               }
/* 231:    */               catch (IOException var9)
/* 232:    */               {
/* 233:280 */                 NodusGuiMainMenu.logger.error("Couldn't parse response connecting to Realms");
/* 234:    */               }
/* 235:283 */               if (!var3) {
/* 236:    */                 break;
/* 237:    */               }
/* 238:    */               try
/* 239:    */               {
/* 240:290 */                 Thread.sleep(10000L);
/* 241:    */               }
/* 242:    */               catch (InterruptedException var6)
/* 243:    */               {
/* 244:294 */                 Thread.currentThread().interrupt();
/* 245:    */               }
/* 246:    */             }
/* 247:    */           }
/* 248:    */         }.start();
/* 249:    */       }
/* 250:300 */       else if (field_96139_s)
/* 251:    */       {
/* 252:302 */         func_130022_h();
/* 253:    */       }
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   private void func_130022_h()
/* 258:    */   {
/* 259:309 */     this.minecraftRealmsButton.field_146125_m = true;
/* 260:    */   }
/* 261:    */   
/* 262:    */   private void addSingleplayerMultiplayerButtons(int par1, int par2)
/* 263:    */   {
/* 264:317 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, par1, I18n.format("menu.singleplayer", new Object[0])));
/* 265:318 */     this.buttonList.add(new NodusGuiButton(2, width / 2 - 100, par1 + par2 * 1, I18n.format("menu.multiplayer", new Object[0])));
/* 266:319 */     this.buttonList.add(new NodusGuiButton(100, width / 2 - 100, par1 + par2 * 2, I18n.format("Nodus", new Object[0])));
/* 267:    */   }
/* 268:    */   
/* 269:    */   private void addDemoButtons(int par1, int par2)
/* 270:    */   {
/* 271:327 */     this.buttonList.add(new NodusGuiButton(11, width / 2 - 100, par1, I18n.format("menu.playdemo", new Object[0])));
/* 272:328 */     this.buttonList.add(this.buttonResetDemo = new NodusGuiButton(12, width / 2 - 100, par1 + par2 * 1, I18n.format("menu.resetdemo", new Object[0])));
/* 273:329 */     ISaveFormat var3 = this.mc.getSaveLoader();
/* 274:330 */     WorldInfo var4 = var3.getWorldInfo("Demo_World");
/* 275:332 */     if (var4 == null) {
/* 276:334 */       this.buttonResetDemo.enabled = false;
/* 277:    */     }
/* 278:    */   }
/* 279:    */   
/* 280:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 281:    */   {
/* 282:340 */     if (p_146284_1_.id == 0) {
/* 283:342 */       this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/* 284:    */     }
/* 285:345 */     if (p_146284_1_.id == 5) {
/* 286:347 */       this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
/* 287:    */     }
/* 288:350 */     if (p_146284_1_.id == 100) {
/* 289:352 */       this.mc.displayGuiScreen(new GuiNodusManager(this));
/* 290:    */     }
/* 291:355 */     if (p_146284_1_.id == 1) {
/* 292:357 */       this.mc.displayGuiScreen(new GuiSelectWorld(this));
/* 293:    */     }
/* 294:360 */     if (p_146284_1_.id == 2) {
/* 295:362 */       this.mc.displayGuiScreen(new GuiMultiplayer(this));
/* 296:    */     }
/* 297:365 */     if ((p_146284_1_.id == 14) && (this.minecraftRealmsButton.field_146125_m)) {
/* 298:367 */       func_140005_i();
/* 299:    */     }
/* 300:370 */     if (p_146284_1_.id == 4) {
/* 301:372 */       this.mc.shutdown();
/* 302:    */     }
/* 303:375 */     if (p_146284_1_.id == 11) {
/* 304:377 */       this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
/* 305:    */     }
/* 306:380 */     if (p_146284_1_.id == 12)
/* 307:    */     {
/* 308:382 */       ISaveFormat var2 = this.mc.getSaveLoader();
/* 309:383 */       WorldInfo var3 = var2.getWorldInfo("Demo_World");
/* 310:385 */       if (var3 != null)
/* 311:    */       {
/* 312:387 */         GuiYesNo var4 = GuiSelectWorld.func_146623_a(this, var3.getWorldName(), 12);
/* 313:388 */         this.mc.displayGuiScreen(var4);
/* 314:    */       }
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   private void func_140005_i()
/* 319:    */   {
/* 320:395 */     Session var1 = this.mc.getSession();
/* 321:396 */     McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 322:    */     try
/* 323:    */     {
/* 324:400 */       if (var2.func_148695_c().booleanValue()) {
/* 325:402 */         this.mc.displayGuiScreen(new GuiScreenClientOutdated(this));
/* 326:    */       } else {
/* 327:406 */         this.mc.displayGuiScreen(new GuiScreenOnlineServers(this));
/* 328:    */       }
/* 329:    */     }
/* 330:    */     catch (ExceptionMcoService var4)
/* 331:    */     {
/* 332:411 */       logger.error("Couldn't connect to realms");
/* 333:    */     }
/* 334:    */     catch (IOException var5)
/* 335:    */     {
/* 336:415 */       logger.error("Couldn't connect to realms");
/* 337:    */     }
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void confirmClicked(boolean par1, int par2)
/* 341:    */   {
/* 342:421 */     if ((par1) && (par2 == 12))
/* 343:    */     {
/* 344:423 */       ISaveFormat var6 = this.mc.getSaveLoader();
/* 345:424 */       var6.flushCache();
/* 346:425 */       var6.deleteWorldDirectory("Demo_World");
/* 347:426 */       this.mc.displayGuiScreen(this);
/* 348:    */     }
/* 349:428 */     else if (par2 == 13)
/* 350:    */     {
/* 351:430 */       if (par1) {
/* 352:    */         try
/* 353:    */         {
/* 354:434 */           Class var3 = Class.forName("java.awt.Desktop");
/* 355:435 */           Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 356:436 */           var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { new URI(this.field_104024_v) });
/* 357:    */         }
/* 358:    */         catch (Throwable var5)
/* 359:    */         {
/* 360:440 */           logger.error("Couldn't open link", var5);
/* 361:    */         }
/* 362:    */       }
/* 363:444 */       this.mc.displayGuiScreen(this);
/* 364:    */     }
/* 365:    */   }
/* 366:    */   
/* 367:    */   private void drawPanorama(int par1, int par2, float par3)
/* 368:    */   {
/* 369:453 */     Tessellator var4 = Tessellator.instance;
/* 370:454 */     GL11.glMatrixMode(5889);
/* 371:455 */     GL11.glPushMatrix();
/* 372:456 */     GL11.glLoadIdentity();
/* 373:457 */     Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
/* 374:458 */     GL11.glMatrixMode(5888);
/* 375:459 */     GL11.glPushMatrix();
/* 376:460 */     GL11.glLoadIdentity();
/* 377:461 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 378:462 */     GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/* 379:463 */     GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 380:464 */     GL11.glEnable(3042);
/* 381:465 */     GL11.glDisable(3008);
/* 382:466 */     GL11.glDisable(2884);
/* 383:467 */     GL11.glDepthMask(false);
/* 384:468 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 385:469 */     byte var5 = 8;
/* 386:471 */     for (int var6 = 0; var6 < var5 * var5; var6++)
/* 387:    */     {
/* 388:473 */       GL11.glPushMatrix();
/* 389:474 */       float var7 = (var6 % var5 / var5 - 0.5F) / 64.0F;
/* 390:475 */       float var8 = (var6 / var5 / var5 - 0.5F) / 64.0F;
/* 391:476 */       float var9 = 0.0F;
/* 392:477 */       GL11.glTranslatef(var7, var8, var9);
/* 393:478 */       GL11.glRotatef(MathHelper.sin((this.panoramaTimer + par3) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
/* 394:479 */       GL11.glRotatef(-(this.panoramaTimer + par3) * 0.1F, 0.0F, 1.0F, 0.0F);
/* 395:481 */       for (int var10 = 0; var10 < 6; var10++)
/* 396:    */       {
/* 397:483 */         GL11.glPushMatrix();
/* 398:485 */         if (var10 == 1) {
/* 399:487 */           GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 400:    */         }
/* 401:490 */         if (var10 == 2) {
/* 402:492 */           GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 403:    */         }
/* 404:495 */         if (var10 == 3) {
/* 405:497 */           GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/* 406:    */         }
/* 407:500 */         if (var10 == 4) {
/* 408:502 */           GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 409:    */         }
/* 410:505 */         if (var10 == 5) {
/* 411:507 */           GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 412:    */         }
/* 413:510 */         this.mc.getTextureManager().bindTexture(titlePanoramaPaths[var10]);
/* 414:511 */         var4.startDrawingQuads();
/* 415:512 */         var4.setColorRGBA_I(16777215, 255 / (var6 + 1));
/* 416:513 */         float var11 = 0.0F;
/* 417:514 */         var4.addVertexWithUV(-1.0D, -1.0D, 1.0D, 0.0F + var11, 0.0F + var11);
/* 418:515 */         var4.addVertexWithUV(1.0D, -1.0D, 1.0D, 1.0F - var11, 0.0F + var11);
/* 419:516 */         var4.addVertexWithUV(1.0D, 1.0D, 1.0D, 1.0F - var11, 1.0F - var11);
/* 420:517 */         var4.addVertexWithUV(-1.0D, 1.0D, 1.0D, 0.0F + var11, 1.0F - var11);
/* 421:518 */         var4.draw();
/* 422:519 */         GL11.glPopMatrix();
/* 423:    */       }
/* 424:522 */       GL11.glPopMatrix();
/* 425:523 */       GL11.glColorMask(true, true, true, false);
/* 426:    */     }
/* 427:526 */     var4.setTranslation(0.0D, 0.0D, 0.0D);
/* 428:527 */     GL11.glColorMask(true, true, true, true);
/* 429:528 */     GL11.glMatrixMode(5889);
/* 430:529 */     GL11.glPopMatrix();
/* 431:530 */     GL11.glMatrixMode(5888);
/* 432:531 */     GL11.glPopMatrix();
/* 433:532 */     GL11.glDepthMask(true);
/* 434:533 */     GL11.glEnable(2884);
/* 435:534 */     GL11.glEnable(2929);
/* 436:    */   }
/* 437:    */   
/* 438:    */   private void rotateAndBlurSkybox(float par1)
/* 439:    */   {
/* 440:542 */     this.mc.getTextureManager().bindTexture(this.field_110351_G);
/* 441:543 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 442:544 */     GL11.glTexParameteri(3553, 10240, 9729);
/* 443:545 */     GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
/* 444:546 */     GL11.glEnable(3042);
/* 445:547 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 446:548 */     GL11.glColorMask(true, true, true, false);
/* 447:549 */     Tessellator var2 = Tessellator.instance;
/* 448:550 */     var2.startDrawingQuads();
/* 449:551 */     GL11.glDisable(3008);
/* 450:552 */     byte var3 = 3;
/* 451:554 */     for (int var4 = 0; var4 < var3; var4++)
/* 452:    */     {
/* 453:556 */       var2.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F / (var4 + 1));
/* 454:557 */       int var5 = width;
/* 455:558 */       int var6 = height;
/* 456:559 */       float var7 = (var4 - var3 / 2) / 256.0F;
/* 457:560 */       var2.addVertexWithUV(var5, var6, zLevel, 0.0F + var7, 1.0D);
/* 458:561 */       var2.addVertexWithUV(var5, 0.0D, zLevel, 1.0F + var7, 1.0D);
/* 459:562 */       var2.addVertexWithUV(0.0D, 0.0D, zLevel, 1.0F + var7, 0.0D);
/* 460:563 */       var2.addVertexWithUV(0.0D, var6, zLevel, 0.0F + var7, 0.0D);
/* 461:    */     }
/* 462:566 */     var2.draw();
/* 463:567 */     GL11.glEnable(3008);
/* 464:568 */     GL11.glColorMask(true, true, true, true);
/* 465:    */   }
/* 466:    */   
/* 467:    */   private void renderSkybox(int par1, int par2, float par3)
/* 468:    */   {
/* 469:576 */     this.mc.getFramebuffer().unbindFramebuffer();
/* 470:577 */     GL11.glViewport(0, 0, 256, 256);
/* 471:578 */     drawPanorama(par1, par2, par3);
/* 472:579 */     rotateAndBlurSkybox(par3);
/* 473:580 */     rotateAndBlurSkybox(par3);
/* 474:581 */     rotateAndBlurSkybox(par3);
/* 475:582 */     rotateAndBlurSkybox(par3);
/* 476:583 */     rotateAndBlurSkybox(par3);
/* 477:584 */     rotateAndBlurSkybox(par3);
/* 478:585 */     rotateAndBlurSkybox(par3);
/* 479:586 */     this.mc.getFramebuffer().bindFramebuffer(true);
/* 480:587 */     GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 481:588 */     Tessellator var4 = Tessellator.instance;
/* 482:589 */     var4.startDrawingQuads();
/* 483:590 */     float var5 = width > height ? 120.0F / width : 120.0F / height;
/* 484:591 */     float var6 = height * var5 / 256.0F;
/* 485:592 */     float var7 = width * var5 / 256.0F;
/* 486:593 */     var4.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
/* 487:594 */     int var8 = width;
/* 488:595 */     int var9 = height;
/* 489:596 */     var4.addVertexWithUV(0.0D, var9, zLevel, 0.5F - var6, 0.5F + var7);
/* 490:597 */     var4.addVertexWithUV(var8, var9, zLevel, 0.5F - var6, 0.5F - var7);
/* 491:598 */     var4.addVertexWithUV(var8, 0.0D, zLevel, 0.5F + var6, 0.5F - var7);
/* 492:599 */     var4.addVertexWithUV(0.0D, 0.0D, zLevel, 0.5F + var6, 0.5F + var7);
/* 493:600 */     var4.draw();
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void drawScreen(int par1, int par2, float par3)
/* 497:    */   {
/* 498:    */     try
/* 499:    */     {
/* 500:610 */       Image backgroundImage = new Image(Nodus.theNodus.fileManager.nodusBackgroundImage.getAbsolutePath());
/* 501:611 */       backgroundImage.draw(0.0F, 0.0F, width, height);
/* 502:    */     }
/* 503:    */     catch (SlickException slickException)
/* 504:    */     {
/* 505:614 */       slickException.printStackTrace();
/* 506:    */     }
/* 507:616 */     drawGradientRect(0, 0, width, height, 285212671, 1090519039);
/* 508:    */     
/* 509:618 */     super.drawScreen(par1, par2, par3);
/* 510:    */     
/* 511:620 */     Tessellator var4 = Tessellator.instance;
/* 512:621 */     short var5 = 274;
/* 513:622 */     int var6 = width / 2 - var5 / 2;
/* 514:623 */     byte var7 = 30;
/* 515:    */     
/* 516:625 */     drawCenteredString(Fonts.fontRenderer, "                                                                       '", width / 2, height / 16, -1);
/* 517:    */     
/* 518:627 */     drawCenteredString(this.fontRendererObj, "MOTD: " + Nodus.theNodus.fileManager.MOTD, width / 2, height / 16 - 10, -1);
/* 519:    */     
/* 520:629 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 521:630 */     var4.setColorOpaque_I(-1);
/* 522:631 */     GL11.glPushMatrix();
/* 523:632 */     GL11.glTranslatef(width / 2 + 90, 70.0F, 0.0F);
/* 524:633 */     GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
/* 525:    */     
/* 526:635 */     GL11.glPopMatrix();
/* 527:636 */     String var9 = "Nodus 2.0" + EnumChatFormatting.GREEN + " RELEASE";
/* 528:638 */     if (this.mc.isDemo()) {
/* 529:640 */       var9 = var9 + " Demo";
/* 530:    */     }
/* 531:643 */     drawString(this.mc.fontRenderer, var9, 2, height - 10, -1);
/* 532:644 */     String var10 = "by ConnorM";
/* 533:645 */     drawString(this.mc.fontRenderer, var10, width - this.fontRendererObj.getStringWidth(var10) - 2, height - 10, -1);
/* 534:647 */     if ((this.field_92025_p != null) && (this.field_92025_p.length() > 0))
/* 535:    */     {
/* 536:649 */       drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
/* 537:650 */       drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
/* 538:651 */       drawString(this.fontRendererObj, this.field_146972_A, (width - this.field_92024_r) / 2, ((NodusGuiButton)this.buttonList.get(0)).yPosition - 12, -1);
/* 539:    */     }
/* 540:    */   }
/* 541:    */   
/* 542:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 543:    */   {
/* 544:660 */     super.mouseClicked(par1, par2, par3);
/* 545:661 */     Object var4 = this.field_104025_t;
/* 546:663 */     synchronized (this.field_104025_t)
/* 547:    */     {
/* 548:665 */       if ((this.field_92025_p.length() > 0) && (par1 >= this.field_92022_t) && (par1 <= this.field_92020_v) && (par2 >= this.field_92021_u) && (par2 <= this.field_92019_w))
/* 549:    */       {
/* 550:667 */         GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
/* 551:668 */         var5.func_146358_g();
/* 552:669 */         this.mc.displayGuiScreen(var5);
/* 553:    */       }
/* 554:    */     }
/* 555:    */   }
/* 556:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.NodusGuiMainMenu
 * JD-Core Version:    0.7.0.1
 */