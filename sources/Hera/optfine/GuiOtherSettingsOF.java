/*     */ package optfine;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiOptionButton;
/*     */ import net.minecraft.client.gui.GuiOptionSlider;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiVideoSettings;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ public class GuiOtherSettingsOF
/*     */   extends GuiScreen implements GuiYesNoCallback {
/*     */   private GuiScreen prevScreen;
/*  17 */   protected String title = "Other Settings";
/*     */   private GameSettings settings;
/*  19 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS, GameSettings.Options.AUTOSAVE_TICKS };
/*  20 */   private int lastMouseX = 0;
/*  21 */   private int lastMouseY = 0;
/*  22 */   private long mouseStillTime = 0L;
/*     */ 
/*     */   
/*     */   public GuiOtherSettingsOF(GuiScreen p_i36_1_, GameSettings p_i36_2_) {
/*  26 */     this.prevScreen = p_i36_1_;
/*  27 */     this.settings = p_i36_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  36 */     int i = 0; byte b; int j;
/*     */     GameSettings.Options[] arrayOfOptions;
/*  38 */     for (j = (arrayOfOptions = enumOptions).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*     */       
/*  40 */       int m = this.width / 2 - 155 + i % 2 * 160;
/*  41 */       int k = this.height / 6 + 21 * i / 2 - 10;
/*     */       
/*  43 */       if (!gamesettings$options.getEnumFloat()) {
/*     */         
/*  45 */         this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), m, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
/*     */       }
/*     */       else {
/*     */         
/*  49 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), m, k, gamesettings$options));
/*     */       } 
/*     */       
/*  52 */       i++;
/*     */       b++; }
/*     */     
/*  55 */     this.buttonList.add(new GuiButton(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, "Reset Video Settings..."));
/*  56 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  64 */     if (button.enabled) {
/*     */       
/*  66 */       if (button.id < 200 && button instanceof GuiOptionButton) {
/*     */         
/*  68 */         this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  69 */         button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/*  72 */       if (button.id == 200) {
/*     */         
/*  74 */         this.mc.gameSettings.saveOptions();
/*  75 */         this.mc.displayGuiScreen(this.prevScreen);
/*     */       } 
/*     */       
/*  78 */       if (button.id == 210) {
/*     */         
/*  80 */         this.mc.gameSettings.saveOptions();
/*  81 */         GuiYesNo guiyesno = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
/*  82 */         this.mc.displayGuiScreen((GuiScreen)guiyesno);
/*     */       } 
/*     */       
/*  85 */       if (button.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
/*     */         
/*  87 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  88 */         int i = scaledresolution.getScaledWidth();
/*  89 */         int j = scaledresolution.getScaledHeight();
/*  90 */         setWorldAndResolution(this.mc, i, j);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/*  97 */     if (result)
/*     */     {
/*  99 */       this.mc.gameSettings.resetSettings();
/*     */     }
/*     */     
/* 102 */     this.mc.displayGuiScreen(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 110 */     drawDefaultBackground();
/* 111 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
/* 112 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 114 */     if (Math.abs(mouseX - this.lastMouseX) <= 5 && Math.abs(mouseY - this.lastMouseY) <= 5) {
/*     */       
/* 116 */       int i = 700;
/*     */       
/* 118 */       if (System.currentTimeMillis() >= this.mouseStillTime + i) {
/*     */         
/* 120 */         int j = this.width / 2 - 150;
/* 121 */         int k = this.height / 6 - 5;
/*     */         
/* 123 */         if (mouseY <= k + 98)
/*     */         {
/* 125 */           k += 105;
/*     */         }
/*     */         
/* 128 */         int l = j + 150 + 150;
/* 129 */         int i1 = k + 84 + 10;
/* 130 */         GuiButton guibutton = getSelectedButton(mouseX, mouseY);
/*     */         
/* 132 */         if (guibutton != null) {
/*     */           
/* 134 */           String s = getButtonName(guibutton.displayString);
/* 135 */           String[] astring = getTooltipLines(s);
/*     */           
/* 137 */           if (astring == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 142 */           drawGradientRect(j, k, l, i1, -536870912, -536870912);
/*     */           
/* 144 */           for (int j1 = 0; j1 < astring.length; j1++)
/*     */           {
/* 146 */             String s1 = astring[j1];
/* 147 */             this.fontRendererObj.drawStringWithShadow(s1, (j + 5), (k + 5 + j1 * 11), 14540253);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 154 */       this.lastMouseX = mouseX;
/* 155 */       this.lastMouseY = mouseY;
/* 156 */       this.mouseStillTime = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getTooltipLines(String p_getTooltipLines_1_) {
/* 162 */     (new String[3])[0] = "Autosave interval"; (new String[3])[1] = "Default autosave interval (2s) is NOT RECOMMENDED."; (new String[3])[2] = "Autosave causes the famous Lag Spike of Death."; (new String[8])[0] = "Shows the lagometer on the debug screen (F3)."; (new String[8])[1] = "* Orange - Memory garbage collection"; (new String[8])[2] = "* Cyan - Tick"; (new String[8])[3] = "* Blue - Scheduled executables"; (new String[8])[4] = "* Purple - Chunk upload"; (new String[8])[5] = "* Red - Chunk updates"; (new String[8])[6] = "* Yellow - Visibility check"; (new String[8])[7] = "* Green - Render terrain"; (new String[5])[0] = "Debug Profiler"; (new String[5])[1] = "  ON - debug profiler is active, slower"; (new String[5])[2] = "  OFF - debug profiler is not active, faster"; (new String[5])[3] = "The debug profiler collects and shows debug information"; (new String[5])[4] = "when the debug screen is open (F3)"; (new String[6])[0] = "Time"; (new String[6])[1] = " Default - normal day/night cycles"; (new String[6])[2] = " Day Only - day only"; (new String[6])[3] = " Night Only - night only"; (new String[6])[4] = "The time setting is only effective in CREATIVE mode"; (new String[6])[5] = "and for local worlds."; (new String[5])[0] = "Weather"; (new String[5])[1] = "  ON - weather is active, slower"; (new String[5])[2] = "  OFF - weather is not active, faster"; (new String[5])[3] = "The weather controls rain, snow and thunderstorms."; (new String[5])[4] = "Weather control is only possible for local worlds."; (new String[5])[0] = "Fullscreen"; (new String[5])[1] = "  ON - use fullscreen mode"; (new String[5])[2] = "  OFF - use window mode"; (new String[5])[3] = "Fullscreen mode may be faster or slower than"; (new String[5])[4] = "window mode, depending on the graphics card."; (new String[5])[0] = "Fullscreen mode"; (new String[5])[1] = "  Default - use desktop screen resolution, slower"; (new String[5])[2] = "  WxH - use custom screen resolution, may be faster"; (new String[5])[3] = "The selected resolution is used in fullscreen mode (F11)."; (new String[5])[4] = "Lower resolutions should generally be faster."; (new String[1])[0] = "3D mode used with red-cyan 3D glasses."; (new String[6])[0] = "Shows compact FPS and render information"; (new String[6])[1] = "  C: - chunk renderers"; (new String[6])[2] = "  E: - rendered entities + block entities"; (new String[6])[3] = "  U: - chunk updates"; (new String[6])[4] = "The compact FPS information is only shown when the"; (new String[6])[5] = "debug screen is not visible."; return p_getTooltipLines_1_.equals("Autosave") ? new String[3] : (p_getTooltipLines_1_.equals("Lagometer") ? new String[8] : (p_getTooltipLines_1_.equals("Debug Profiler") ? new String[5] : (p_getTooltipLines_1_.equals("Time") ? new String[6] : (p_getTooltipLines_1_.equals("Weather") ? new String[5] : (p_getTooltipLines_1_.equals("Fullscreen") ? new String[5] : (p_getTooltipLines_1_.equals("Fullscreen Mode") ? new String[5] : (p_getTooltipLines_1_.equals("3D Anaglyph") ? new String[1] : (p_getTooltipLines_1_.equals("Show FPS") ? new String[6] : null))))))));
/*     */   }
/*     */ 
/*     */   
/*     */   private String getButtonName(String p_getButtonName_1_) {
/* 167 */     int i = p_getButtonName_1_.indexOf(':');
/* 168 */     return (i < 0) ? p_getButtonName_1_ : p_getButtonName_1_.substring(0, i);
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiButton getSelectedButton(int p_getSelectedButton_1_, int p_getSelectedButton_2_) {
/* 173 */     for (int i = 0; i < this.buttonList.size(); i++) {
/*     */       
/* 175 */       GuiButton guibutton = this.buttonList.get(i);
/* 176 */       int j = GuiVideoSettings.getButtonWidth(guibutton);
/* 177 */       int k = GuiVideoSettings.getButtonHeight(guibutton);
/* 178 */       boolean flag = (p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + j && p_getSelectedButton_2_ < guibutton.yPosition + k);
/*     */       
/* 180 */       if (flag)
/*     */       {
/* 182 */         return guibutton;
/*     */       }
/*     */     } 
/*     */     
/* 186 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\GuiOtherSettingsOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */