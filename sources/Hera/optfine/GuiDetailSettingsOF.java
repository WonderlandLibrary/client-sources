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
/*     */ public class GuiDetailSettingsOF
/*     */   extends GuiScreen {
/*     */   private GuiScreen prevScreen;
/*  15 */   protected String title = "Detail Settings";
/*     */   private GameSettings settings;
/*  17 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.CLOUDS, GameSettings.Options.CLOUD_HEIGHT, GameSettings.Options.TREES, GameSettings.Options.RAIN, GameSettings.Options.SKY, GameSettings.Options.STARS, GameSettings.Options.SUN_MOON, GameSettings.Options.SHOW_CAPES, GameSettings.Options.TRANSLUCENT_BLOCKS, GameSettings.Options.HELD_ITEM_TOOLTIPS, GameSettings.Options.DROPPED_ITEMS, GameSettings.Options.ENTITY_SHADOWS, GameSettings.Options.VIGNETTE };
/*  18 */   private int lastMouseX = 0;
/*  19 */   private int lastMouseY = 0;
/*  20 */   private long mouseStillTime = 0L;
/*     */ 
/*     */   
/*     */   public GuiDetailSettingsOF(GuiScreen p_i35_1_, GameSettings p_i35_2_) {
/*  24 */     this.prevScreen = p_i35_1_;
/*  25 */     this.settings = p_i35_2_;
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
/* 127 */             this.fontRendererObj.drawStringWithShadow(s1, (j + 5), (k + 5 + j1 * 11), 14540253);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 134 */       this.lastMouseX = mouseX;
/* 135 */       this.lastMouseY = mouseY;
/* 136 */       this.mouseStillTime = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getTooltipLines(String p_getTooltipLines_1_) {
/* 142 */     (new String[7])[0] = "Clouds"; (new String[7])[1] = "  Default - as set by setting Graphics"; (new String[7])[2] = "  Fast - lower quality, faster"; (new String[7])[3] = "  Fancy - higher quality, slower"; (new String[7])[4] = "  OFF - no clouds, fastest"; (new String[7])[5] = "Fast clouds are rendered 2D."; (new String[7])[6] = "Fancy clouds are rendered 3D."; (new String[3])[0] = "Cloud Height"; (new String[3])[1] = "  OFF - default height"; (new String[3])[2] = "  100% - above world height limit"; (new String[6])[0] = "Trees"; (new String[6])[1] = "  Default - as set by setting Graphics"; (new String[6])[2] = "  Fast - lower quality, faster"; (new String[6])[3] = "  Fancy - higher quality, slower"; (new String[6])[4] = "Fast trees have opaque leaves."; (new String[6])[5] = "Fancy trees have transparent leaves."; (new String[6])[0] = "Grass"; (new String[6])[1] = "  Default - as set by setting Graphics"; (new String[6])[2] = "  Fast - lower quality, faster"; (new String[6])[3] = "  Fancy - higher quality, slower"; (new String[6])[4] = "Fast grass uses default side texture."; (new String[6])[5] = "Fancy grass uses biome side texture."; (new String[4])[0] = "Dropped Items"; (new String[4])[1] = "  Default - as set by setting Graphics"; (new String[4])[2] = "  Fast - 2D dropped items, faster"; (new String[4])[3] = "  Fancy - 3D dropped items, slower"; (new String[6])[0] = "Water"; (new String[6])[1] = "  Default - as set by setting Graphics"; (new String[6])[2] = "  Fast  - lower quality, faster"; (new String[6])[3] = "  Fancy - higher quality, slower"; (new String[6])[4] = "Fast water (1 pass) has some visual artifacts"; (new String[6])[5] = "Fancy water (2 pass) has no visual artifacts"; (new String[7])[0] = "Rain & Snow"; (new String[7])[1] = "  Default - as set by setting Graphics"; (new String[7])[2] = "  Fast  - light rain/snow, faster"; (new String[7])[3] = "  Fancy - heavy rain/snow, slower"; (new String[7])[4] = "  OFF - no rain/snow, fastest"; (new String[7])[5] = "When rain is OFF the splashes and rain sounds"; (new String[7])[6] = "are still active."; (new String[4])[0] = "Sky"; (new String[4])[1] = "  ON - sky is visible, slower"; (new String[4])[2] = "  OFF  - sky is not visible, faster"; (new String[4])[3] = "When sky is OFF the moon and sun are still visible."; (new String[3])[0] = "Sun & Moon"; (new String[3])[1] = "  ON - sun and moon are visible (default)"; (new String[3])[2] = "  OFF  - sun and moon are not visible (faster)"; (new String[3])[0] = "Stars"; (new String[3])[1] = "  ON - stars are visible, slower"; (new String[3])[2] = "  OFF  - stars are not visible, faster"; (new String[3])[0] = "Depth Fog"; (new String[3])[1] = "  ON - fog moves closer at bedrock levels (default)"; (new String[3])[2] = "  OFF - same fog at all levels"; (new String[3])[0] = "Show Capes"; (new String[3])[1] = "  ON - show player capes (default)"; (new String[3])[2] = "  OFF - do not show player capes"; (new String[3])[0] = "Held item tooltips"; (new String[3])[1] = "  ON - show tooltips for held items (default)"; (new String[3])[2] = "  OFF - do not show tooltips for held items"; (new String[6])[0] = "Translucent Blocks"; (new String[6])[1] = "  Fancy - correct color blending (default)"; (new String[6])[2] = "  Fast - fast color blending (faster)"; (new String[6])[3] = "Controls the color blending of translucent blocks"; (new String[6])[4] = "with different color (stained glass, water, ice)"; (new String[6])[5] = "when placed behind each other with air between them."; (new String[8])[0] = "Visual effect which slightly darkens the screen corners"; (new String[8])[1] = "  Default - as set by the setting Graphics (default)"; (new String[8])[2] = "  Fast - vignette disabled (faster)"; (new String[8])[3] = "  Fancy - vignette enabled (slower)"; (new String[8])[4] = "The vignette may have a significant effect on the FPS,"; (new String[8])[5] = "especially when playing fullscreen."; (new String[8])[6] = "The vignette effect is very subtle and can safely"; (new String[8])[7] = "be disabled"; return p_getTooltipLines_1_.equals("Clouds") ? new String[7] : (p_getTooltipLines_1_.equals("Cloud Height") ? new String[3] : (p_getTooltipLines_1_.equals("Trees") ? new String[6] : (p_getTooltipLines_1_.equals("Grass") ? new String[6] : (p_getTooltipLines_1_.equals("Dropped Items") ? new String[4] : (p_getTooltipLines_1_.equals("Water") ? new String[6] : (p_getTooltipLines_1_.equals("Rain & Snow") ? new String[7] : (p_getTooltipLines_1_.equals("Sky") ? new String[4] : (p_getTooltipLines_1_.equals("Sun & Moon") ? new String[3] : (p_getTooltipLines_1_.equals("Stars") ? new String[3] : (p_getTooltipLines_1_.equals("Depth Fog") ? new String[3] : (p_getTooltipLines_1_.equals("Show Capes") ? new String[3] : (p_getTooltipLines_1_.equals("Held Item Tooltips") ? new String[3] : (p_getTooltipLines_1_.equals("Translucent Blocks") ? new String[6] : (p_getTooltipLines_1_.equals("Vignette") ? new String[8] : null))))))))))))));
/*     */   }
/*     */ 
/*     */   
/*     */   private String getButtonName(String p_getButtonName_1_) {
/* 147 */     int i = p_getButtonName_1_.indexOf(':');
/* 148 */     return (i < 0) ? p_getButtonName_1_ : p_getButtonName_1_.substring(0, i);
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiButton getSelectedButton(int p_getSelectedButton_1_, int p_getSelectedButton_2_) {
/* 153 */     for (int i = 0; i < this.buttonList.size(); i++) {
/*     */       
/* 155 */       GuiButton guibutton = this.buttonList.get(i);
/* 156 */       int j = GuiVideoSettings.getButtonWidth(guibutton);
/* 157 */       int k = GuiVideoSettings.getButtonHeight(guibutton);
/* 158 */       boolean flag = (p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + j && p_getSelectedButton_2_ < guibutton.yPosition + k);
/*     */       
/* 160 */       if (flag)
/*     */       {
/* 162 */         return guibutton;
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\GuiDetailSettingsOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */