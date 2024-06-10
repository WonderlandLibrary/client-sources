/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.gui.FontRenderer;
/*   7:    */ import net.minecraft.client.gui.GuiOptionButton;
/*   8:    */ import net.minecraft.client.gui.GuiOptionSlider;
/*   9:    */ import net.minecraft.client.gui.GuiScreen;
/*  10:    */ import net.minecraft.client.gui.GuiVideoSettings;
/*  11:    */ import net.minecraft.client.gui.GuiYesNo;
/*  12:    */ import net.minecraft.client.gui.ScaledResolution;
/*  13:    */ import net.minecraft.client.resources.I18n;
/*  14:    */ import net.minecraft.client.settings.GameSettings;
/*  15:    */ import net.minecraft.client.settings.GameSettings.Options;
/*  16:    */ 
/*  17:    */ public class GuiOtherSettingsOF
/*  18:    */   extends GuiScreen
/*  19:    */ {
/*  20:    */   private GuiScreen prevScreen;
/*  21: 16 */   protected String title = "Other Settings";
/*  22:    */   private GameSettings settings;
/*  23: 18 */   private static GameSettings.Options[] enumOptions = { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.ANAGLYPH, GameSettings.Options.AUTOSAVE_TICKS };
/*  24: 19 */   private int lastMouseX = 0;
/*  25: 20 */   private int lastMouseY = 0;
/*  26: 21 */   private long mouseStillTime = 0L;
/*  27:    */   
/*  28:    */   public GuiOtherSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
/*  29:    */   {
/*  30: 25 */     this.prevScreen = guiscreen;
/*  31: 26 */     this.settings = gamesettings;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void initGui()
/*  35:    */   {
/*  36: 34 */     int i = 0;
/*  37: 35 */     GameSettings.Options[] aenumoptions = enumOptions;
/*  38: 36 */     int j = aenumoptions.length;
/*  39: 38 */     for (int k = 0; k < j; k++)
/*  40:    */     {
/*  41: 40 */       GameSettings.Options enumoptions = aenumoptions[k];
/*  42: 41 */       int x = width / 2 - 155 + i % 2 * 160;
/*  43: 42 */       int y = height / 6 + 21 * (i / 2) - 10;
/*  44: 44 */       if (!enumoptions.getEnumFloat()) {
/*  45: 46 */         this.buttonList.add(new GuiOptionButton(enumoptions.returnEnumOrdinal(), x, y, enumoptions, this.settings.getKeyBinding(enumoptions)));
/*  46:    */       } else {
/*  47: 50 */         this.buttonList.add(new GuiOptionSlider(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
/*  48:    */       }
/*  49: 53 */       i++;
/*  50:    */     }
/*  51: 56 */     this.buttonList.add(new NodusGuiButton(210, width / 2 - 100, height / 6 + 168 + 11 - 44, "Reset Video Settings..."));
/*  52: 57 */     this.buttonList.add(new NodusGuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void actionPerformed(NodusGuiButton guibutton)
/*  56:    */   {
/*  57: 62 */     if (guibutton.enabled)
/*  58:    */     {
/*  59: 64 */       if ((guibutton.id < 100) && ((guibutton instanceof GuiOptionButton)))
/*  60:    */       {
/*  61: 66 */         this.settings.setOptionValue(((GuiOptionButton)guibutton).func_146136_c(), 1);
/*  62: 67 */         guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
/*  63:    */       }
/*  64: 70 */       if (guibutton.id == 200)
/*  65:    */       {
/*  66: 72 */         this.mc.gameSettings.saveOptions();
/*  67: 73 */         this.mc.displayGuiScreen(this.prevScreen);
/*  68:    */       }
/*  69: 76 */       if (guibutton.id == 210)
/*  70:    */       {
/*  71: 78 */         this.mc.gameSettings.saveOptions();
/*  72: 79 */         GuiYesNo scaledresolution = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
/*  73: 80 */         this.mc.displayGuiScreen(scaledresolution);
/*  74:    */       }
/*  75: 83 */       if (guibutton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal())
/*  76:    */       {
/*  77: 85 */         ScaledResolution scaledresolution1 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/*  78: 86 */         int i = scaledresolution1.getScaledWidth();
/*  79: 87 */         int j = scaledresolution1.getScaledHeight();
/*  80: 88 */         setWorldAndResolution(this.mc, i, j);
/*  81:    */       }
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void confirmClicked(boolean flag, int i)
/*  86:    */   {
/*  87: 95 */     if (flag) {
/*  88: 97 */       this.mc.gameSettings.resetSettings();
/*  89:    */     }
/*  90:100 */     this.mc.displayGuiScreen(this);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void drawScreen(int x, int y, float f)
/*  94:    */   {
/*  95:108 */     drawDefaultBackground();
/*  96:109 */     drawCenteredString(this.fontRendererObj, this.title, width / 2, 20, 16777215);
/*  97:110 */     super.drawScreen(x, y, f);
/*  98:112 */     if ((Math.abs(x - this.lastMouseX) <= 5) && (Math.abs(y - this.lastMouseY) <= 5))
/*  99:    */     {
/* 100:114 */       short activateDelay = 700;
/* 101:116 */       if (System.currentTimeMillis() >= this.mouseStillTime + activateDelay)
/* 102:    */       {
/* 103:118 */         int x1 = width / 2 - 150;
/* 104:119 */         int y1 = height / 6 - 5;
/* 105:121 */         if (y <= y1 + 98) {
/* 106:123 */           y1 += 105;
/* 107:    */         }
/* 108:126 */         int x2 = x1 + 150 + 150;
/* 109:127 */         int y2 = y1 + 84 + 10;
/* 110:128 */         NodusGuiButton btn = getSelectedButton(x, y);
/* 111:130 */         if (btn != null)
/* 112:    */         {
/* 113:132 */           String s = getButtonName(btn.displayString);
/* 114:133 */           String[] lines = getTooltipLines(s);
/* 115:135 */           if (lines == null) {
/* 116:137 */             return;
/* 117:    */           }
/* 118:140 */           drawGradientRect(x1, y1, x2, y2, -536870912, -536870912);
/* 119:142 */           for (int i = 0; i < lines.length; i++)
/* 120:    */           {
/* 121:144 */             String line = lines[i];
/* 122:145 */             this.fontRendererObj.drawStringWithShadow(line, x1 + 5, y1 + 5 + i * 11, 14540253);
/* 123:    */           }
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:    */     else
/* 128:    */     {
/* 129:152 */       this.lastMouseX = x;
/* 130:153 */       this.lastMouseY = y;
/* 131:154 */       this.mouseStillTime = System.currentTimeMillis();
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private String[] getTooltipLines(String btnName)
/* 136:    */   {
/* 137:160 */     return btnName.equals("3D Anaglyph") ? new String[] { "3D mode used with red-cyan 3D glasses." } : btnName.equals("Fullscreen Mode") ? new String[] { "Fullscreen mode", "  Default - use desktop screen resolution, slower", "  WxH - use custom screen resolution, may be faster", "The selected resolution is used in fullscreen mode (F11).", "Lower resolutions should generally be faster." } : btnName.equals("Fullscreen") ? new String[] { "Fullscreen", "  ON - use fullscreen mode", "  OFF - use window mode", "Fullscreen mode may be faster or slower than", "window mode, depending on the graphics card." } : btnName.equals("Weather") ? new String[] { "Weather", "  ON - weather is active, slower", "  OFF - weather is not active, faster", "The weather controls rain, snow and thunderstorms.", "Weather control is only possible for local worlds." } : btnName.equals("Time") ? new String[] { "Time", " Default - normal day/night cycles", " Day Only - day only", " Night Only - night only", "The time setting is only effective in CREATIVE mode", "and for local worlds." } : btnName.equals("Debug Profiler") ? new String[] { "Debug Profiler", "  ON - debug profiler is active, slower", "  OFF - debug profiler is not active, faster", "The debug profiler collects and shows debug information", "when the debug screen is open (F3)" } : btnName.equals("Lagometer") ? new String[] { "Lagometer", " OFF - no lagometer, faster", " ON - debug screen with lagometer, slower", "Shows the lagometer on the debug screen (F3).", "* White - tick", "* Red - chunk loading", "* Green - frame rendering + internal server", "* Blue - internal server (when Smooth World is ON)" } : btnName.equals("Autosave") ? new String[] { "Autosave interval", "Default autosave interval (2s) is NOT RECOMMENDED.", "Autosave causes the famous Lag Spike of Death." } : null;
/* 138:    */   }
/* 139:    */   
/* 140:    */   private String getButtonName(String displayString)
/* 141:    */   {
/* 142:165 */     int pos = displayString.indexOf(':');
/* 143:166 */     return pos < 0 ? displayString : displayString.substring(0, pos);
/* 144:    */   }
/* 145:    */   
/* 146:    */   private NodusGuiButton getSelectedButton(int i, int j)
/* 147:    */   {
/* 148:171 */     for (int k = 0; k < this.buttonList.size(); k++)
/* 149:    */     {
/* 150:173 */       NodusGuiButton btn = (NodusGuiButton)this.buttonList.get(k);
/* 151:174 */       int btnWidth = GuiVideoSettings.getButtonWidth(btn);
/* 152:175 */       int btnHeight = GuiVideoSettings.getButtonHeight(btn);
/* 153:176 */       boolean flag = (i >= btn.xPosition) && (j >= btn.yPosition) && (i < btn.xPosition + btnWidth) && (j < btn.yPosition + btnHeight);
/* 154:178 */       if (flag) {
/* 155:180 */         return btn;
/* 156:    */       }
/* 157:    */     }
/* 158:184 */     return null;
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.GuiOtherSettingsOF
 * JD-Core Version:    0.7.0.1
 */