/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiCreateWorld
/*     */   extends GuiScreen
/*     */ {
/*     */   private GuiScreen parentScreen;
/*     */   private GuiTextField field_146333_g;
/*     */   private GuiTextField field_146335_h;
/*     */   private String field_146336_i;
/*  22 */   private String gameMode = "survival";
/*     */   
/*     */   private String field_175300_s;
/*     */   
/*     */   private boolean field_146341_s = true;
/*     */   private boolean allowCheats;
/*     */   private boolean field_146339_u;
/*     */   private boolean field_146338_v;
/*     */   private boolean field_146337_w;
/*     */   private boolean field_146345_x;
/*     */   private boolean field_146344_y;
/*     */   private GuiButton btnGameMode;
/*     */   private GuiButton btnMoreOptions;
/*     */   private GuiButton btnMapFeatures;
/*     */   private GuiButton btnBonusItems;
/*     */   private GuiButton btnMapType;
/*     */   private GuiButton btnAllowCommands;
/*     */   private GuiButton btnCustomizeType;
/*     */   private String field_146323_G;
/*     */   private String field_146328_H;
/*     */   private String field_146329_I;
/*     */   private String field_146330_J;
/*     */   private int selectedIndex;
/*  45 */   public String chunkProviderSettingsJson = "";
/*     */ 
/*     */   
/*  48 */   private static final String[] disallowedFilenames = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
/*     */ 
/*     */   
/*     */   public GuiCreateWorld(GuiScreen p_i46320_1_) {
/*  52 */     this.parentScreen = p_i46320_1_;
/*  53 */     this.field_146329_I = "";
/*  54 */     this.field_146330_J = I18n.format("selectWorld.newWorld", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  62 */     this.field_146333_g.updateCursorCounter();
/*  63 */     this.field_146335_h.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  72 */     Keyboard.enableRepeatEvents(true);
/*  73 */     this.buttonList.clear();
/*  74 */     this.buttonList.add(new HeraButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/*  75 */     this.buttonList.add(new HeraButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  76 */     this.buttonList.add(this.btnGameMode = (GuiButton)new HeraButton(2, this.width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  77 */     this.buttonList.add(this.btnMoreOptions = (GuiButton)new HeraButton(3, this.width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions", new Object[0])));
/*  78 */     this.buttonList.add(this.btnMapFeatures = (GuiButton)new HeraButton(4, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures", new Object[0])));
/*  79 */     this.btnMapFeatures.visible = false;
/*  80 */     this.buttonList.add(this.btnBonusItems = (GuiButton)new HeraButton(7, this.width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems", new Object[0])));
/*  81 */     this.btnBonusItems.visible = false;
/*  82 */     this.buttonList.add(this.btnMapType = (GuiButton)new HeraButton(5, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType", new Object[0])));
/*  83 */     this.btnMapType.visible = false;
/*  84 */     this.buttonList.add(this.btnAllowCommands = (GuiButton)new HeraButton(6, this.width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  85 */     this.btnAllowCommands.visible = false;
/*  86 */     this.buttonList.add(this.btnCustomizeType = (GuiButton)new HeraButton(8, this.width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType", new Object[0])));
/*  87 */     this.btnCustomizeType.visible = false;
/*  88 */     this.field_146333_g = new GuiTextField(9, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  89 */     this.field_146333_g.setFocused(true);
/*  90 */     this.field_146333_g.setText(this.field_146330_J);
/*  91 */     this.field_146335_h = new GuiTextField(10, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  92 */     this.field_146335_h.setText(this.field_146329_I);
/*  93 */     func_146316_a(this.field_146344_y);
/*  94 */     func_146314_g();
/*  95 */     func_146319_h();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146314_g() {
/* 100 */     this.field_146336_i = this.field_146333_g.getText().trim(); byte b; int i;
/*     */     char[] arrayOfChar;
/* 102 */     for (i = (arrayOfChar = ChatAllowedCharacters.allowedCharactersArray).length, b = 0; b < i; ) { char c0 = arrayOfChar[b];
/*     */       
/* 104 */       this.field_146336_i = this.field_146336_i.replace(c0, '_');
/*     */       b++; }
/*     */     
/* 107 */     if (StringUtils.isEmpty(this.field_146336_i))
/*     */     {
/* 109 */       this.field_146336_i = "World";
/*     */     }
/*     */     
/* 112 */     this.field_146336_i = func_146317_a(this.mc.getSaveLoader(), this.field_146336_i);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146319_h() {
/* 117 */     this.btnGameMode.displayString = String.valueOf(I18n.format("selectWorld.gameMode", new Object[0])) + ": " + I18n.format("selectWorld.gameMode." + this.gameMode, new Object[0]);
/* 118 */     this.field_146323_G = I18n.format("selectWorld.gameMode." + this.gameMode + ".line1", new Object[0]);
/* 119 */     this.field_146328_H = I18n.format("selectWorld.gameMode." + this.gameMode + ".line2", new Object[0]);
/* 120 */     this.btnMapFeatures.displayString = String.valueOf(I18n.format("selectWorld.mapFeatures", new Object[0])) + " ";
/*     */     
/* 122 */     if (this.field_146341_s) {
/*     */       
/* 124 */       this.btnMapFeatures.displayString = String.valueOf(this.btnMapFeatures.displayString) + I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 128 */       this.btnMapFeatures.displayString = String.valueOf(this.btnMapFeatures.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 131 */     this.btnBonusItems.displayString = String.valueOf(I18n.format("selectWorld.bonusItems", new Object[0])) + " ";
/*     */     
/* 133 */     if (this.field_146338_v && !this.field_146337_w) {
/*     */       
/* 135 */       this.btnBonusItems.displayString = String.valueOf(this.btnBonusItems.displayString) + I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 139 */       this.btnBonusItems.displayString = String.valueOf(this.btnBonusItems.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 142 */     this.btnMapType.displayString = String.valueOf(I18n.format("selectWorld.mapType", new Object[0])) + " " + I18n.format(WorldType.worldTypes[this.selectedIndex].getTranslateName(), new Object[0]);
/* 143 */     this.btnAllowCommands.displayString = String.valueOf(I18n.format("selectWorld.allowCommands", new Object[0])) + " ";
/*     */     
/* 145 */     if (this.allowCheats && !this.field_146337_w) {
/*     */       
/* 147 */       this.btnAllowCommands.displayString = String.valueOf(this.btnAllowCommands.displayString) + I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 151 */       this.btnAllowCommands.displayString = String.valueOf(this.btnAllowCommands.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String func_146317_a(ISaveFormat p_146317_0_, String p_146317_1_) {
/* 157 */     p_146317_1_ = p_146317_1_.replaceAll("[\\./\"]", "_"); byte b; int i;
/*     */     String[] arrayOfString;
/* 159 */     for (i = (arrayOfString = disallowedFilenames).length, b = 0; b < i; ) { String s = arrayOfString[b];
/*     */       
/* 161 */       if (p_146317_1_.equalsIgnoreCase(s))
/*     */       {
/* 163 */         p_146317_1_ = "_" + p_146317_1_ + "_";
/*     */       }
/*     */       b++; }
/*     */     
/* 167 */     while (p_146317_0_.getWorldInfo(p_146317_1_) != null)
/*     */     {
/* 169 */       p_146317_1_ = String.valueOf(p_146317_1_) + "-";
/*     */     }
/*     */     
/* 172 */     return p_146317_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 180 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 188 */     if (button.enabled)
/*     */     {
/* 190 */       if (button.id == 1) {
/*     */         
/* 192 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 194 */       else if (button.id == 0) {
/*     */         
/* 196 */         this.mc.displayGuiScreen(null);
/*     */         
/* 198 */         if (this.field_146345_x) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 203 */         this.field_146345_x = true;
/* 204 */         long i = (new Random()).nextLong();
/* 205 */         String s = this.field_146335_h.getText();
/*     */         
/* 207 */         if (!StringUtils.isEmpty(s)) {
/*     */           
/*     */           try {
/*     */             
/* 211 */             long j = Long.parseLong(s);
/*     */             
/* 213 */             if (j != 0L)
/*     */             {
/* 215 */               i = j;
/*     */             }
/*     */           }
/* 218 */           catch (NumberFormatException var7) {
/*     */             
/* 220 */             i = s.hashCode();
/*     */           } 
/*     */         }
/*     */         
/* 224 */         WorldSettings.GameType worldsettings$gametype = WorldSettings.GameType.getByName(this.gameMode);
/* 225 */         WorldSettings worldsettings = new WorldSettings(i, worldsettings$gametype, this.field_146341_s, this.field_146337_w, WorldType.worldTypes[this.selectedIndex]);
/* 226 */         worldsettings.setWorldName(this.chunkProviderSettingsJson);
/*     */         
/* 228 */         if (this.field_146338_v && !this.field_146337_w)
/*     */         {
/* 230 */           worldsettings.enableBonusChest();
/*     */         }
/*     */         
/* 233 */         if (this.allowCheats && !this.field_146337_w)
/*     */         {
/* 235 */           worldsettings.enableCommands();
/*     */         }
/*     */         
/* 238 */         this.mc.launchIntegratedServer(this.field_146336_i, this.field_146333_g.getText().trim(), worldsettings);
/*     */       }
/* 240 */       else if (button.id == 3) {
/*     */         
/* 242 */         func_146315_i();
/*     */       }
/* 244 */       else if (button.id == 2) {
/*     */         
/* 246 */         if (this.gameMode.equals("survival")) {
/*     */           
/* 248 */           if (!this.field_146339_u)
/*     */           {
/* 250 */             this.allowCheats = false;
/*     */           }
/*     */           
/* 253 */           this.field_146337_w = false;
/* 254 */           this.gameMode = "hardcore";
/* 255 */           this.field_146337_w = true;
/* 256 */           this.btnAllowCommands.enabled = false;
/* 257 */           this.btnBonusItems.enabled = false;
/* 258 */           func_146319_h();
/*     */         }
/* 260 */         else if (this.gameMode.equals("hardcore")) {
/*     */           
/* 262 */           if (!this.field_146339_u)
/*     */           {
/* 264 */             this.allowCheats = true;
/*     */           }
/*     */           
/* 267 */           this.field_146337_w = false;
/* 268 */           this.gameMode = "creative";
/* 269 */           func_146319_h();
/* 270 */           this.field_146337_w = false;
/* 271 */           this.btnAllowCommands.enabled = true;
/* 272 */           this.btnBonusItems.enabled = true;
/*     */         }
/*     */         else {
/*     */           
/* 276 */           if (!this.field_146339_u)
/*     */           {
/* 278 */             this.allowCheats = false;
/*     */           }
/*     */           
/* 281 */           this.gameMode = "survival";
/* 282 */           func_146319_h();
/* 283 */           this.btnAllowCommands.enabled = true;
/* 284 */           this.btnBonusItems.enabled = true;
/* 285 */           this.field_146337_w = false;
/*     */         } 
/*     */         
/* 288 */         func_146319_h();
/*     */       }
/* 290 */       else if (button.id == 4) {
/*     */         
/* 292 */         this.field_146341_s = !this.field_146341_s;
/* 293 */         func_146319_h();
/*     */       }
/* 295 */       else if (button.id == 7) {
/*     */         
/* 297 */         this.field_146338_v = !this.field_146338_v;
/* 298 */         func_146319_h();
/*     */       }
/* 300 */       else if (button.id == 5) {
/*     */         
/* 302 */         this.selectedIndex++;
/*     */         
/* 304 */         if (this.selectedIndex >= WorldType.worldTypes.length)
/*     */         {
/* 306 */           this.selectedIndex = 0;
/*     */         }
/*     */         
/* 309 */         while (!func_175299_g()) {
/*     */           
/* 311 */           this.selectedIndex++;
/*     */           
/* 313 */           if (this.selectedIndex >= WorldType.worldTypes.length)
/*     */           {
/* 315 */             this.selectedIndex = 0;
/*     */           }
/*     */         } 
/*     */         
/* 319 */         this.chunkProviderSettingsJson = "";
/* 320 */         func_146319_h();
/* 321 */         func_146316_a(this.field_146344_y);
/*     */       }
/* 323 */       else if (button.id == 6) {
/*     */         
/* 325 */         this.field_146339_u = true;
/* 326 */         this.allowCheats = !this.allowCheats;
/* 327 */         func_146319_h();
/*     */       }
/* 329 */       else if (button.id == 8) {
/*     */         
/* 331 */         if (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT) {
/*     */           
/* 333 */           this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
/*     */         }
/*     */         else {
/*     */           
/* 337 */           this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_175299_g() {
/* 345 */     WorldType worldtype = WorldType.worldTypes[this.selectedIndex];
/* 346 */     return (worldtype != null && worldtype.getCanBeCreated()) ? ((worldtype == WorldType.DEBUG_WORLD) ? isShiftKeyDown() : true) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146315_i() {
/* 351 */     func_146316_a(!this.field_146344_y);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146316_a(boolean p_146316_1_) {
/* 356 */     this.field_146344_y = p_146316_1_;
/*     */     
/* 358 */     if (WorldType.worldTypes[this.selectedIndex] == WorldType.DEBUG_WORLD) {
/*     */       
/* 360 */       this.btnGameMode.visible = !this.field_146344_y;
/* 361 */       this.btnGameMode.enabled = false;
/*     */       
/* 363 */       if (this.field_175300_s == null)
/*     */       {
/* 365 */         this.field_175300_s = this.gameMode;
/*     */       }
/*     */       
/* 368 */       this.gameMode = "spectator";
/* 369 */       this.btnMapFeatures.visible = false;
/* 370 */       this.btnBonusItems.visible = false;
/* 371 */       this.btnMapType.visible = this.field_146344_y;
/* 372 */       this.btnAllowCommands.visible = false;
/* 373 */       this.btnCustomizeType.visible = false;
/*     */     }
/*     */     else {
/*     */       
/* 377 */       this.btnGameMode.visible = !this.field_146344_y;
/* 378 */       this.btnGameMode.enabled = true;
/*     */       
/* 380 */       if (this.field_175300_s != null) {
/*     */         
/* 382 */         this.gameMode = this.field_175300_s;
/* 383 */         this.field_175300_s = null;
/*     */       } 
/*     */       
/* 386 */       this.btnMapFeatures.visible = (this.field_146344_y && WorldType.worldTypes[this.selectedIndex] != WorldType.CUSTOMIZED);
/* 387 */       this.btnBonusItems.visible = this.field_146344_y;
/* 388 */       this.btnMapType.visible = this.field_146344_y;
/* 389 */       this.btnAllowCommands.visible = this.field_146344_y;
/* 390 */       this.btnCustomizeType.visible = (this.field_146344_y && (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT || WorldType.worldTypes[this.selectedIndex] == WorldType.CUSTOMIZED));
/*     */     } 
/*     */     
/* 393 */     func_146319_h();
/*     */     
/* 395 */     if (this.field_146344_y) {
/*     */       
/* 397 */       this.btnMoreOptions.displayString = I18n.format("gui.done", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 401 */       this.btnMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 411 */     if (this.field_146333_g.isFocused() && !this.field_146344_y) {
/*     */       
/* 413 */       this.field_146333_g.textboxKeyTyped(typedChar, keyCode);
/* 414 */       this.field_146330_J = this.field_146333_g.getText();
/*     */     }
/* 416 */     else if (this.field_146335_h.isFocused() && this.field_146344_y) {
/*     */       
/* 418 */       this.field_146335_h.textboxKeyTyped(typedChar, keyCode);
/* 419 */       this.field_146329_I = this.field_146335_h.getText();
/*     */     } 
/*     */     
/* 422 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/* 424 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */     
/* 427 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.field_146333_g.getText().length() > 0);
/* 428 */     func_146314_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 436 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 438 */     if (this.field_146344_y) {
/*     */       
/* 440 */       this.field_146335_h.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     else {
/*     */       
/* 444 */       this.field_146333_g.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 453 */     drawDefaultBackground();
/* 454 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create", new Object[0]), this.width / 2, 20, -1);
/*     */     
/* 456 */     if (this.field_146344_y) {
/*     */       
/* 458 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]), this.width / 2 - 100, 47, -6250336);
/* 459 */       drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]), this.width / 2 - 100, 85, -6250336);
/*     */       
/* 461 */       if (this.btnMapFeatures.visible)
/*     */       {
/* 463 */         drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]), this.width / 2 - 150, 122, -6250336);
/*     */       }
/*     */       
/* 466 */       if (this.btnAllowCommands.visible)
/*     */       {
/* 468 */         drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]), this.width / 2 - 150, 172, -6250336);
/*     */       }
/*     */       
/* 471 */       this.field_146335_h.drawTextBox();
/*     */       
/* 473 */       if (WorldType.worldTypes[this.selectedIndex].showWorldInfoNotice())
/*     */       {
/* 475 */         this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.selectedIndex].func_151359_c(), new Object[0]), this.btnMapType.xPosition + 2, this.btnMapType.yPosition + 22, this.btnMapType.getButtonWidth(), 10526880);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 480 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, -6250336);
/* 481 */       drawString(this.fontRendererObj, String.valueOf(I18n.format("selectWorld.resultFolder", new Object[0])) + " " + this.field_146336_i, this.width / 2 - 100, 85, -6250336);
/* 482 */       this.field_146333_g.drawTextBox();
/* 483 */       drawString(this.fontRendererObj, this.field_146323_G, this.width / 2 - 100, 137, -6250336);
/* 484 */       drawString(this.fontRendererObj, this.field_146328_H, this.width / 2 - 100, 149, -6250336);
/*     */     } 
/*     */     
/* 487 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146318_a(WorldInfo p_146318_1_) {
/* 492 */     this.field_146330_J = I18n.format("selectWorld.newWorld.copyOf", new Object[] { p_146318_1_.getWorldName() });
/* 493 */     this.field_146329_I = (new StringBuilder(String.valueOf(p_146318_1_.getSeed()))).toString();
/* 494 */     this.selectedIndex = p_146318_1_.getTerrainType().getWorldTypeID();
/* 495 */     this.chunkProviderSettingsJson = p_146318_1_.getGeneratorOptions();
/* 496 */     this.field_146341_s = p_146318_1_.isMapFeaturesEnabled();
/* 497 */     this.allowCheats = p_146318_1_.areCommandsAllowed();
/*     */     
/* 499 */     if (p_146318_1_.isHardcoreModeEnabled()) {
/*     */       
/* 501 */       this.gameMode = "hardcore";
/*     */     }
/* 503 */     else if (p_146318_1_.getGameType().isSurvivalOrAdventure()) {
/*     */       
/* 505 */       this.gameMode = "survival";
/*     */     }
/* 507 */     else if (p_146318_1_.getGameType().isCreative()) {
/*     */       
/* 509 */       this.gameMode = "creative";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiCreateWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */