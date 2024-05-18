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
/*     */ public class GuiPerformanceSettingsOF
/*     */   extends GuiScreen {
/*     */   private GuiScreen prevScreen;
/*  15 */   protected String title = "Performance Settings";
/*     */   private GameSettings settings;
/*  17 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.SMOOTH_FPS, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.FAST_RENDER, GameSettings.Options.FAST_MATH, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.LAZY_CHUNK_LOADING };
/*  18 */   private int lastMouseX = 0;
/*  19 */   private int lastMouseY = 0;
/*  20 */   private long mouseStillTime = 0L;
/*     */ 
/*     */   
/*     */   public GuiPerformanceSettingsOF(GuiScreen p_i37_1_, GameSettings p_i37_2_) {
/*  24 */     this.prevScreen = p_i37_1_;
/*  25 */     this.settings = p_i37_2_;
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
/* 142 */     (new String[5])[0] = "Stabilizes FPS by flushing the graphic driver buffers"; (new String[5])[1] = "  OFF - no stabilization, FPS may fluctuate"; (new String[5])[2] = "  ON - FPS stabilization"; (new String[5])[3] = "This option is graphics driver dependant and its effect"; (new String[5])[4] = "is not always visible"; (new String[5])[0] = "Removes lag spikes caused by the internal server."; (new String[5])[1] = "  OFF - no stabilization, FPS may fluctuate"; (new String[5])[2] = "  ON - FPS stabilization"; (new String[5])[3] = "Stabilizes FPS by distributing the internal server load."; (new String[5])[4] = "Effective only for local worlds (single player)."; (new String[6])[0] = "Loads the world chunks at distance Far."; (new String[6])[1] = "Switching the render distance does not cause all chunks "; (new String[6])[2] = "to be loaded again."; (new String[6])[3] = "  OFF - world chunks loaded up to render distance"; (new String[6])[4] = "  ON - world chunks loaded at distance Far, allows"; (new String[6])[5] = "       fast render distance switching"; (new String[6])[0] = "Defines an area in which no chunks will be loaded"; (new String[6])[1] = "  OFF - after 5m new chunks will be loaded"; (new String[6])[2] = "  2 - after 32m  new chunks will be loaded"; (new String[6])[3] = "  8 - after 128m new chunks will be loaded"; (new String[6])[4] = "Higher values need more time to load all the chunks"; (new String[6])[5] = "and may decrease the FPS."; (new String[6])[0] = "Chunk updates"; (new String[6])[1] = " 1 - slower world loading, higher FPS (default)"; (new String[6])[2] = " 3 - faster world loading, lower FPS"; (new String[6])[3] = " 5 - fastest world loading, lowest FPS"; (new String[6])[4] = "Number of chunk updates per rendered frame,"; (new String[6])[5] = "higher values may destabilize the framerate."; (new String[5])[0] = "Dynamic chunk updates"; (new String[5])[1] = " OFF - (default) standard chunk updates per frame"; (new String[5])[2] = " ON - more updates while the player is standing still"; (new String[5])[3] = "Dynamic updates force more chunk updates while"; (new String[5])[4] = "the player is standing still to load the world faster."; (new String[7])[0] = "Lazy Chunk Loading"; (new String[7])[1] = " OFF - default server chunk loading"; (new String[7])[2] = " ON - lazy server chunk loading (smoother)"; (new String[7])[3] = "Smooths the integrated server chunk loading by"; (new String[7])[4] = "distributing the chunks over several ticks."; (new String[7])[5] = "Turn it OFF if parts of the world do not load correctly."; (new String[7])[6] = "Effective only for local worlds and single-core CPU."; (new String[5])[0] = "Fast Math"; (new String[5])[1] = " OFF - standard math (default)"; (new String[5])[2] = " ON - faster math"; (new String[5])[3] = "Uses optimized sin() and cos() functions which can"; (new String[5])[4] = "better utilize the CPU cache and increase the FPS."; (new String[5])[0] = "Fast Render"; (new String[5])[1] = " OFF - standard rendering (default)"; (new String[5])[2] = " ON - optimized rendering (faster)"; (new String[5])[3] = "Uses optimized rendering algorithm which decreases"; (new String[5])[4] = "the GPU load and may substantionally increase the FPS."; return p_getTooltipLines_1_.equals("Smooth FPS") ? new String[5] : (p_getTooltipLines_1_.equals("Smooth World") ? new String[5] : (p_getTooltipLines_1_.equals("Load Far") ? new String[6] : (p_getTooltipLines_1_.equals("Preloaded Chunks") ? new String[6] : (p_getTooltipLines_1_.equals("Chunk Updates") ? new String[6] : (p_getTooltipLines_1_.equals("Dynamic Updates") ? new String[5] : (p_getTooltipLines_1_.equals("Lazy Chunk Loading") ? new String[7] : (p_getTooltipLines_1_.equals("Fast Math") ? new String[5] : (p_getTooltipLines_1_.equals("Fast Render") ? new String[5] : null))))))));
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


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\GuiPerformanceSettingsOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */