/*     */ package optfine;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiOptionButton;
/*     */ import net.minecraft.client.gui.GuiOptionSlider;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiVideoSettings;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ public class GuiQualitySettingsOF
/*     */   extends GuiScreen {
/*     */   private GuiScreen prevScreen;
/*  15 */   protected String title = "Quality Settings";
/*     */   private GameSettings settings;
/*  17 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY };
/*  18 */   private int lastMouseX = 0;
/*  19 */   private int lastMouseY = 0;
/*  20 */   private long mouseStillTime = 0L;
/*     */ 
/*     */   
/*     */   public GuiQualitySettingsOF(GuiScreen p_i38_1_, GameSettings p_i38_2_) {
/*  24 */     this.prevScreen = p_i38_1_;
/*  25 */     this.settings = p_i38_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  34 */     int i = 0; byte b; int j;
/*     */     GameSettings.Options[] arrayOfOptions;
/*  36 */     for (j = (arrayOfOptions = enumOptions).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*     */       
/*  38 */       int m = this.width / 2 - 155 + i % 2 * 160;
/*  39 */       int k = this.height / 6 + 21 * i / 2 - 10;
/*     */       
/*  41 */       if (!gamesettings$options.getEnumFloat()) {
/*     */         
/*  43 */         this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), m, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
/*     */       }
/*     */       else {
/*     */         
/*  47 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), m, k, gamesettings$options));
/*     */       } 
/*     */       
/*  50 */       i++;
/*     */       b++; }
/*     */     
/*  53 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  61 */     if (button.enabled) {
/*     */       
/*  63 */       if (button.id < 200 && button instanceof GuiOptionButton) {
/*     */         
/*  65 */         this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  66 */         button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/*  69 */       if (button.id == 200) {
/*     */         
/*  71 */         this.mc.gameSettings.saveOptions();
/*  72 */         this.mc.displayGuiScreen(this.prevScreen);
/*     */       } 
/*     */       
/*  75 */       if (button.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
/*     */         
/*  77 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  78 */         int i = scaledresolution.getScaledWidth();
/*  79 */         int j = scaledresolution.getScaledHeight();
/*  80 */         setWorldAndResolution(this.mc, i, j);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  90 */     drawDefaultBackground();
/*  91 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
/*  92 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/*  94 */     if (Math.abs(mouseX - this.lastMouseX) <= 5 && Math.abs(mouseY - this.lastMouseY) <= 5) {
/*     */       
/*  96 */       int i = 700;
/*     */       
/*  98 */       if (System.currentTimeMillis() >= this.mouseStillTime + i) {
/*     */         
/* 100 */         int j = this.width / 2 - 150;
/* 101 */         int k = this.height / 6 - 5;
/*     */         
/* 103 */         if (mouseY <= k + 98)
/*     */         {
/* 105 */           k += 105;
/*     */         }
/*     */         
/* 108 */         int l = j + 150 + 150;
/* 109 */         int i1 = k + 84 + 10;
/* 110 */         GuiButton guibutton = getSelectedButton(mouseX, mouseY);
/*     */         
/* 112 */         if (guibutton != null) {
/*     */           
/* 114 */           String s = getButtonName(guibutton.displayString);
/* 115 */           String[] astring = getTooltipLines(s);
/*     */           
/* 117 */           if (astring == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 122 */           drawGradientRect(j, k, l, i1, -536870912, -536870912);
/*     */           
/* 124 */           for (int j1 = 0; j1 < astring.length; j1++)
/*     */           {
/* 126 */             String s1 = astring[j1];
/* 127 */             int k1 = 14540253;
/*     */             
/* 129 */             if (s1.endsWith("!"))
/*     */             {
/* 131 */               k1 = 16719904;
/*     */             }
/*     */             
/* 134 */             this.fontRendererObj.drawStringWithShadow(s1, (j + 5), (k + 5 + j1 * 11), k1);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 141 */       this.lastMouseX = mouseX;
/* 142 */       this.lastMouseY = mouseY;
/* 143 */       this.mouseStillTime = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getTooltipLines(String p_getTooltipLines_1_) {
/* 149 */     (new String[6])[0] = "Visual effect which makes distant objects look better"; (new String[6])[1] = "by smoothing the texture details"; (new String[6])[2] = "  OFF - no smoothing"; (new String[6])[3] = "  1 - minimum smoothing"; (new String[6])[4] = "  4 - maximum smoothing"; (new String[6])[5] = "This option usually does not affect the performance."; (new String[6])[0] = "Visual effect which makes distant objects look better"; (new String[6])[1] = "by smoothing the texture details"; (new String[6])[2] = "  Nearest - rough smoothing (fastest)"; (new String[6])[3] = "  Linear - normal smoothing"; (new String[6])[4] = "  Bilinear - fine smoothing"; (new String[6])[5] = "  Trilinear - finest smoothing (slowest)"; (new String[6])[0] = "Anisotropic Filtering"; (new String[6])[1] = " OFF - (default) standard texture detail (faster)"; (new String[6])[2] = " 2-16 - finer details in mipmapped textures (slower)"; (new String[6])[3] = "The Anisotropic Filtering restores details in"; (new String[6])[4] = "mipmapped textures."; (new String[6])[5] = "When enabled it may substantially decrease the FPS."; (new String[8])[0] = "Antialiasing"; (new String[8])[1] = " OFF - (default) no antialiasing (faster)"; (new String[8])[2] = " 2-16 - antialiased lines and edges (slower)"; (new String[8])[3] = "The Antialiasing smooths jagged lines and "; (new String[8])[4] = "sharp color transitions."; (new String[8])[5] = "When enabled it may substantially decrease the FPS."; (new String[8])[6] = "Not all levels are supported by all graphics cards."; (new String[8])[7] = "Effective after a RESTART!"; (new String[3])[0] = "Clear Water"; (new String[3])[1] = "  ON - clear, transparent water"; (new String[3])[2] = "  OFF - default water"; (new String[4])[0] = "Better Grass"; (new String[4])[1] = "  OFF - default side grass texture, fastest"; (new String[4])[2] = "  Fast - full side grass texture, slower"; (new String[4])[3] = "  Fancy - dynamic side grass texture, slowest"; (new String[5])[0] = "Better Snow"; (new String[5])[1] = "  OFF - default snow, faster"; (new String[5])[2] = "  ON - better snow, slower"; (new String[5])[3] = "Shows snow under transparent blocks (fence, tall grass)"; (new String[5])[4] = "when bordering with snow blocks"; (new String[5])[0] = "Random Mobs"; (new String[5])[1] = "  OFF - no random mobs, faster"; (new String[5])[2] = "  ON - random mobs, slower"; (new String[5])[3] = "Random mobs uses random textures for the game creatures."; (new String[5])[4] = "It needs a texture pack which has multiple mob textures."; (new String[4])[0] = "Swamp Colors"; (new String[4])[1] = "  ON - use swamp colors (default), slower"; (new String[4])[2] = "  OFF - do not use swamp colors, faster"; (new String[4])[3] = "The swamp colors affect grass, leaves, vines and water."; (new String[6])[0] = "Smooth Biomes"; (new String[6])[1] = "  ON - smoothing of biome borders (default), slower"; (new String[6])[2] = "  OFF - no smoothing of biome borders, faster"; (new String[6])[3] = "The smoothing of biome borders is done by sampling and"; (new String[6])[4] = "averaging the color of all surrounding blocks."; (new String[6])[5] = "Affected are grass, leaves, vines and water."; (new String[5])[0] = "Custom Fonts"; (new String[5])[1] = "  ON - uses custom fonts (default), slower"; (new String[5])[2] = "  OFF - uses default font, faster"; (new String[5])[3] = "The custom fonts are supplied by the current"; (new String[5])[4] = "texture pack"; (new String[5])[0] = "Custom Colors"; (new String[5])[1] = "  ON - uses custom colors (default), slower"; (new String[5])[2] = "  OFF - uses default colors, faster"; (new String[5])[3] = "The custom colors are supplied by the current"; (new String[5])[4] = "texture pack"; (new String[3])[0] = "Show Capes"; (new String[3])[1] = "  ON - show player capes (default)"; (new String[3])[2] = "  OFF - do not show player capes"; (new String[8])[0] = "Connected Textures"; (new String[8])[1] = "  OFF - no connected textures (default)"; (new String[8])[2] = "  Fast - fast connected textures"; (new String[8])[3] = "  Fancy - fancy connected textures"; (new String[8])[4] = "Connected textures joins the textures of glass,"; (new String[8])[5] = "sandstone and bookshelves when placed next to"; (new String[8])[6] = "each other. The connected textures are supplied"; (new String[8])[7] = "by the current texture pack."; (new String[7])[0] = "Far View"; (new String[7])[1] = " OFF - (default) standard view distance"; (new String[7])[2] = " ON - 3x view distance"; (new String[7])[3] = "Far View is very resource demanding!"; (new String[7])[4] = "3x view distance => 9x chunks to be loaded => FPS / 9"; (new String[7])[5] = "Standard view distances: 32, 64, 128, 256"; (new String[7])[6] = "Far view distances: 96, 192, 384, 512"; (new String[8])[0] = "Natural Textures"; (new String[8])[1] = "  OFF - no natural textures (default)"; (new String[8])[2] = "  ON - use natural textures"; (new String[8])[3] = "Natural textures remove the gridlike pattern"; (new String[8])[4] = "created by repeating blocks of the same type."; (new String[8])[5] = "It uses rotated and flipped variants of the base"; (new String[8])[6] = "block texture. The configuration for the natural"; (new String[8])[7] = "textures is supplied by the current texture pack"; (new String[5])[0] = "Custom Sky"; (new String[5])[1] = "  ON - custom sky textures (default), slow"; (new String[5])[2] = "  OFF - default sky, faster"; (new String[5])[3] = "The custom sky textures are supplied by the current"; (new String[5])[4] = "texture pack"; return p_getTooltipLines_1_.equals("Mipmap Levels") ? new String[6] : (p_getTooltipLines_1_.equals("Mipmap Type") ? new String[6] : (p_getTooltipLines_1_.equals("Anisotropic Filtering") ? new String[6] : (p_getTooltipLines_1_.equals("Antialiasing") ? new String[8] : (p_getTooltipLines_1_.equals("Clear Water") ? new String[3] : (p_getTooltipLines_1_.equals("Better Grass") ? new String[4] : (p_getTooltipLines_1_.equals("Better Snow") ? new String[5] : (p_getTooltipLines_1_.equals("Random Mobs") ? new String[5] : (p_getTooltipLines_1_.equals("Swamp Colors") ? new String[4] : (p_getTooltipLines_1_.equals("Smooth Biomes") ? new String[6] : (p_getTooltipLines_1_.equals("Custom Fonts") ? new String[5] : (p_getTooltipLines_1_.equals("Custom Colors") ? new String[5] : (p_getTooltipLines_1_.equals("Show Capes") ? new String[3] : (p_getTooltipLines_1_.equals("Connected Textures") ? new String[8] : (p_getTooltipLines_1_.equals("Far View") ? new String[7] : (p_getTooltipLines_1_.equals("Natural Textures") ? new String[8] : (p_getTooltipLines_1_.equals("Custom Sky") ? new String[5] : null))))))))))))))));
/*     */   }
/*     */ 
/*     */   
/*     */   private String getButtonName(String p_getButtonName_1_) {
/* 154 */     int i = p_getButtonName_1_.indexOf(':');
/* 155 */     return (i < 0) ? p_getButtonName_1_ : p_getButtonName_1_.substring(0, i);
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiButton getSelectedButton(int p_getSelectedButton_1_, int p_getSelectedButton_2_) {
/* 160 */     for (int i = 0; i < this.buttonList.size(); i++) {
/*     */       
/* 162 */       GuiButton guibutton = this.buttonList.get(i);
/* 163 */       int j = GuiVideoSettings.getButtonWidth(guibutton);
/* 164 */       int k = GuiVideoSettings.getButtonHeight(guibutton);
/* 165 */       boolean flag = (p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + j && p_getSelectedButton_2_ < guibutton.yPosition + k);
/*     */       
/* 167 */       if (flag)
/*     */       {
/* 169 */         return guibutton;
/*     */       }
/*     */     } 
/*     */     
/* 173 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\GuiQualitySettingsOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */