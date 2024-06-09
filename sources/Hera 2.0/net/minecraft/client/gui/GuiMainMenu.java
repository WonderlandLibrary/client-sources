/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URI;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.font.FontHelper;
/*     */ import me.eagler.gui.GuiChangeName;
/*     */ import me.eagler.gui.GuiFriends;
/*     */ import me.eagler.gui.GuiLogin;
/*     */ import me.eagler.gui.stuff.HeraShadowButton;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.realms.RealmsBridge;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.demo.DemoWorldServer;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import org.lwjgl.util.glu.Project;
/*     */ 
/*     */ 
/*     */ public class GuiMainMenu
/*     */   extends GuiScreen
/*     */   implements GuiYesNoCallback
/*     */ {
/*  47 */   private static final AtomicInteger field_175373_f = new AtomicInteger(0);
/*  48 */   private static final Logger logger = LogManager.getLogger();
/*  49 */   private static final Random RANDOM = new Random();
/*     */ 
/*     */   
/*     */   private float updateCounter;
/*     */ 
/*     */   
/*     */   private String splashText;
/*     */ 
/*     */   
/*     */   private GuiButton buttonResetDemo;
/*     */ 
/*     */   
/*     */   private int panoramaTimer;
/*     */ 
/*     */   
/*     */   private DynamicTexture viewportTexture;
/*     */ 
/*     */   
/*     */   private boolean field_175375_v = true;
/*     */ 
/*     */   
/*  70 */   private final Object threadLock = new Object();
/*     */ 
/*     */   
/*     */   private String openGLWarning1;
/*     */ 
/*     */   
/*     */   private String openGLWarning2;
/*     */   
/*     */   private String openGLWarningLink;
/*     */   
/*  80 */   private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
/*  81 */   private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
/*     */ 
/*     */   
/*  84 */   private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
/*  85 */   public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
/*     */   
/*     */   private int field_92024_r;
/*     */   
/*     */   private int field_92023_s;
/*     */   
/*     */   private int field_92022_t;
/*     */   private int field_92021_u;
/*     */   private int field_92020_v;
/*     */   private int field_92019_w;
/*     */   private ResourceLocation backgroundTexture;
/*     */   private GuiButton realmsButton;
/*     */   
/*     */   public GuiMainMenu() {
/*  99 */     this.openGLWarning2 = field_96138_a;
/* 100 */     this.splashText = "missingno";
/* 101 */     BufferedReader bufferedreader = null;
/*     */ 
/*     */     
/*     */     try {
/* 105 */       List<String> list = Lists.newArrayList();
/* 106 */       bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
/*     */       
/*     */       String s;
/* 109 */       while ((s = bufferedreader.readLine()) != null) {
/*     */         
/* 111 */         s = s.trim();
/*     */         
/* 113 */         if (!s.isEmpty())
/*     */         {
/* 115 */           list.add(s);
/*     */         }
/*     */       } 
/*     */       
/* 119 */       if (!list.isEmpty()) {
/*     */         do
/*     */         {
/*     */           
/* 123 */           this.splashText = list.get(RANDOM.nextInt(list.size()));
/*     */         }
/* 125 */         while (this.splashText.hashCode() == 125780783);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 132 */     catch (IOException iOException) {
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 138 */       if (bufferedreader != null) {
/*     */         
/*     */         try {
/*     */           
/* 142 */           bufferedreader.close();
/*     */         }
/* 144 */         catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     this.updateCounter = RANDOM.nextFloat();
/* 152 */     this.openGLWarning1 = "";
/*     */     
/* 154 */     if (!(GLContext.getCapabilities()).OpenGL20 && !OpenGlHelper.areShadersSupported()) {
/*     */       
/* 156 */       this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
/* 157 */       this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
/* 158 */       this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 167 */     this.panoramaTimer++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 192 */     this.viewportTexture = new DynamicTexture(256, 256);
/* 193 */     this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
/* 194 */     Calendar calendar = Calendar.getInstance();
/* 195 */     calendar.setTime(new Date());
/*     */     
/* 197 */     if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
/*     */       
/* 199 */       this.splashText = "Merry X-mas!";
/*     */     }
/* 201 */     else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
/*     */       
/* 203 */       this.splashText = "Happy new year!";
/*     */     }
/* 205 */     else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
/*     */       
/* 207 */       this.splashText = "OOoooOOOoooo! Spooky!";
/*     */     } 
/*     */     
/* 210 */     int i = 24;
/* 211 */     int j = this.height / 4 + 48;
/*     */     
/* 213 */     if (this.mc.isDemo()) {
/*     */       
/* 215 */       addDemoButtons(j, 24);
/*     */     }
/*     */     else {
/*     */       
/* 219 */       addSingleplayerMultiplayerButtons(j, 24);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     synchronized (this.threadLock) {
/*     */       
/* 228 */       this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
/* 229 */       this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
/* 230 */       int k = Math.max(this.field_92023_s, this.field_92024_r);
/* 231 */       this.field_92022_t = (this.width - k) / 2;
/* 232 */       this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
/* 233 */       this.field_92020_v = this.field_92022_t + k;
/* 234 */       this.field_92019_w = this.field_92021_u + 24;
/*     */     } 
/*     */     
/* 237 */     this.mc.func_181537_a(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
/* 248 */     ScaledResolution sr = new ScaledResolution(this.mc);
/*     */     
/* 250 */     this.buttonList.add(new HeraShadowButton(1, this.width / 2 - 60, p_73969_1_, 120, 20, "Singleplayer"));
/* 251 */     this.buttonList.add(new HeraShadowButton(2, this.width / 2 - 60, p_73969_1_ + p_73969_2_ * 1, 120, 20, "Multiplayer"));
/* 252 */     this.buttonList.add(new HeraShadowButton(14, this.width / 2 - 60, p_73969_1_ + p_73969_2_ * 2, 120, 20, "Alt-Login"));
/* 253 */     this.buttonList.add(new HeraShadowButton(0, this.width / 2 - 60, p_73969_1_ + p_73969_2_ * 3, 120, 20, "Options"));
/* 254 */     this.buttonList.add(new HeraShadowButton(22, 2, sr.getScaledHeight() - 24, 80, 20, "Name"));
/* 255 */     this.buttonList.add(new HeraShadowButton(33, sr.getScaledWidth() - 84, sr.getScaledHeight() - 24, 80, 20, "Friends"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
/* 263 */     this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
/* 264 */     this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
/* 265 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 266 */     WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
/*     */     
/* 268 */     if (worldinfo == null)
/*     */     {
/* 270 */       this.buttonResetDemo.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 279 */     if (button.id == 0)
/*     */     {
/* 281 */       this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/*     */     }
/*     */     
/* 284 */     if (button.id == 5)
/*     */     {
/* 286 */       this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
/*     */     }
/*     */     
/* 289 */     if (button.id == 1)
/*     */     {
/* 291 */       this.mc.displayGuiScreen(new GuiSelectWorld(this));
/*     */     }
/*     */     
/* 294 */     if (button.id == 2)
/*     */     {
/* 296 */       this.mc.displayGuiScreen(new GuiMultiplayer(this));
/*     */     }
/*     */     
/* 299 */     if (button.id == 14)
/*     */     {
/*     */ 
/*     */       
/* 303 */       this.mc.displayGuiScreen((GuiScreen)new GuiLogin(this));
/*     */     }
/*     */ 
/*     */     
/* 307 */     if (button.id == 4)
/*     */     {
/* 309 */       this.mc.shutdown();
/*     */     }
/*     */     
/* 312 */     if (button.id == 11)
/*     */     {
/* 314 */       this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
/*     */     }
/*     */     
/* 317 */     if (button.id == 22)
/*     */     {
/* 319 */       this.mc.displayGuiScreen((GuiScreen)new GuiChangeName(this, false));
/*     */     }
/*     */ 
/*     */     
/* 323 */     if (button.id == 33)
/*     */     {
/* 325 */       this.mc.displayGuiScreen((GuiScreen)new GuiFriends(this, false));
/*     */     }
/*     */ 
/*     */     
/* 329 */     if (button.id == 12) {
/*     */       
/* 331 */       ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 332 */       WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
/*     */       
/* 334 */       if (worldinfo != null) {
/*     */         
/* 336 */         GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
/* 337 */         this.mc.displayGuiScreen(guiyesno);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void switchToRealms() {
/* 344 */     RealmsBridge realmsbridge = new RealmsBridge();
/* 345 */     realmsbridge.switchToRealms(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 350 */     if (result && id == 12) {
/*     */       
/* 352 */       ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 353 */       isaveformat.flushCache();
/* 354 */       isaveformat.deleteWorldDirectory("Demo_World");
/* 355 */       this.mc.displayGuiScreen(this);
/*     */     }
/* 357 */     else if (id == 13) {
/*     */       
/* 359 */       if (result) {
/*     */         
/*     */         try {
/*     */           
/* 363 */           Class<?> oclass = Class.forName("java.awt.Desktop");
/* 364 */           Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 365 */           oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI(this.openGLWarningLink) });
/*     */         }
/* 367 */         catch (Throwable throwable) {
/*     */           
/* 369 */           logger.error("Couldn't open link", throwable);
/*     */         } 
/*     */       }
/*     */       
/* 373 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_) {
/* 382 */     Tessellator tessellator = Tessellator.getInstance();
/* 383 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 384 */     GlStateManager.matrixMode(5889);
/* 385 */     GlStateManager.pushMatrix();
/* 386 */     GlStateManager.loadIdentity();
/* 387 */     Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
/* 388 */     GlStateManager.matrixMode(5888);
/* 389 */     GlStateManager.pushMatrix();
/* 390 */     GlStateManager.loadIdentity();
/* 391 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 392 */     GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/* 393 */     GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 394 */     GlStateManager.enableBlend();
/* 395 */     GlStateManager.disableAlpha();
/* 396 */     GlStateManager.disableCull();
/* 397 */     GlStateManager.depthMask(false);
/* 398 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 399 */     int i = 8;
/*     */     
/* 401 */     for (int j = 0; j < i * i; j++) {
/*     */       
/* 403 */       GlStateManager.pushMatrix();
/* 404 */       float f = ((j % i) / i - 0.5F) / 64.0F;
/* 405 */       float f1 = ((j / i) / i - 0.5F) / 64.0F;
/* 406 */       float f2 = 0.0F;
/* 407 */       GlStateManager.translate(f, f1, f2);
/* 408 */       GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
/* 409 */       GlStateManager.rotate(-(this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);
/*     */       
/* 411 */       for (int k = 0; k < 6; k++) {
/*     */         
/* 413 */         GlStateManager.pushMatrix();
/*     */         
/* 415 */         if (k == 1)
/*     */         {
/* 417 */           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 420 */         if (k == 2)
/*     */         {
/* 422 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 425 */         if (k == 3)
/*     */         {
/* 427 */           GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 430 */         if (k == 4)
/*     */         {
/* 432 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 435 */         if (k == 5)
/*     */         {
/* 437 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 440 */         this.mc.getTextureManager().bindTexture(titlePanoramaPaths[k]);
/* 441 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 442 */         int l = 255 / (j + 1);
/* 443 */         float f3 = 0.0F;
/* 444 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
/* 445 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
/* 446 */         worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
/* 447 */         worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
/* 448 */         tessellator.draw();
/* 449 */         GlStateManager.popMatrix();
/*     */       } 
/*     */       
/* 452 */       GlStateManager.popMatrix();
/* 453 */       GlStateManager.colorMask(true, true, true, false);
/*     */     } 
/*     */     
/* 456 */     worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 457 */     GlStateManager.colorMask(true, true, true, true);
/* 458 */     GlStateManager.matrixMode(5889);
/* 459 */     GlStateManager.popMatrix();
/* 460 */     GlStateManager.matrixMode(5888);
/* 461 */     GlStateManager.popMatrix();
/* 462 */     GlStateManager.depthMask(true);
/* 463 */     GlStateManager.enableCull();
/* 464 */     GlStateManager.enableDepth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateAndBlurSkybox(float p_73968_1_) {
/* 472 */     this.mc.getTextureManager().bindTexture(this.backgroundTexture);
/* 473 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 474 */     GL11.glTexParameteri(3553, 10240, 9729);
/* 475 */     GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
/* 476 */     GlStateManager.enableBlend();
/* 477 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 478 */     GlStateManager.colorMask(true, true, true, false);
/* 479 */     Tessellator tessellator = Tessellator.getInstance();
/* 480 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 481 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 482 */     GlStateManager.disableAlpha();
/* 483 */     int i = 3;
/*     */     
/* 485 */     for (int j = 0; j < i; j++) {
/*     */       
/* 487 */       float f = 1.0F / (j + 1);
/* 488 */       int k = this.width;
/* 489 */       int l = this.height;
/* 490 */       float f1 = (j - i / 2) / 256.0F;
/* 491 */       worldrenderer.pos(k, l, this.zLevel).tex((0.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
/* 492 */       worldrenderer.pos(k, 0.0D, this.zLevel).tex((1.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
/* 493 */       worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex((1.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
/* 494 */       worldrenderer.pos(0.0D, l, this.zLevel).tex((0.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
/*     */     } 
/*     */     
/* 497 */     tessellator.draw();
/* 498 */     GlStateManager.enableAlpha();
/* 499 */     GlStateManager.colorMask(true, true, true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {
/* 507 */     this.mc.getFramebuffer().unbindFramebuffer();
/* 508 */     GlStateManager.viewport(0, 0, 256, 256);
/* 509 */     drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
/* 510 */     rotateAndBlurSkybox(p_73971_3_);
/* 511 */     rotateAndBlurSkybox(p_73971_3_);
/* 512 */     rotateAndBlurSkybox(p_73971_3_);
/* 513 */     rotateAndBlurSkybox(p_73971_3_);
/* 514 */     rotateAndBlurSkybox(p_73971_3_);
/* 515 */     rotateAndBlurSkybox(p_73971_3_);
/* 516 */     rotateAndBlurSkybox(p_73971_3_);
/* 517 */     this.mc.getFramebuffer().bindFramebuffer(true);
/* 518 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 519 */     float f = (this.width > this.height) ? (120.0F / this.width) : (120.0F / this.height);
/* 520 */     float f1 = this.height * f / 256.0F;
/* 521 */     float f2 = this.width * f / 256.0F;
/* 522 */     int i = this.width;
/* 523 */     int j = this.height;
/* 524 */     Tessellator tessellator = Tessellator.getInstance();
/* 525 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 526 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 527 */     worldrenderer.pos(0.0D, j, this.zLevel).tex((0.5F - f1), (0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 528 */     worldrenderer.pos(i, j, this.zLevel).tex((0.5F - f1), (0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 529 */     worldrenderer.pos(i, 0.0D, this.zLevel).tex((0.5F + f1), (0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 530 */     worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex((0.5F + f1), (0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 531 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 542 */     ScaledResolution sr = new ScaledResolution(this.mc);
/*     */     
/* 544 */     drawBackground();
/*     */     
/* 546 */     double y = (this.height / 4 + 48);
/*     */     
/* 548 */     FontHelper.cfBig.drawStringWithBGShadow("Hera v" + Client.instance.VERSION, (
/* 549 */         this.width / 2 - FontHelper.cfBig.getStringWidth("Hera v" + Client.instance.VERSION) / 2), y - 50.0D, Color.white);
/*     */     
/* 551 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 559 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 561 */     synchronized (this.threadLock) {
/*     */       
/* 563 */       if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
/*     */         
/* 565 */         GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
/* 566 */         guiconfirmopenlink.disableSecurityWarning();
/* 567 */         this.mc.displayGuiScreen(guiconfirmopenlink);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiMainMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */