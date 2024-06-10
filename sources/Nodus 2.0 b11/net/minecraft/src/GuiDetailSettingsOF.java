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
/*  11:    */ import net.minecraft.client.gui.ScaledResolution;
/*  12:    */ import net.minecraft.client.resources.I18n;
/*  13:    */ import net.minecraft.client.settings.GameSettings;
/*  14:    */ import net.minecraft.client.settings.GameSettings.Options;
/*  15:    */ 
/*  16:    */ public class GuiDetailSettingsOF
/*  17:    */   extends GuiScreen
/*  18:    */ {
/*  19:    */   private GuiScreen prevScreen;
/*  20: 15 */   protected String title = "Detail Settings";
/*  21:    */   private GameSettings settings;
/*  22: 17 */   private static GameSettings.Options[] enumOptions = { GameSettings.Options.CLOUDS, GameSettings.Options.CLOUD_HEIGHT, GameSettings.Options.TREES, GameSettings.Options.GRASS, GameSettings.Options.WATER, GameSettings.Options.RAIN, GameSettings.Options.SKY, GameSettings.Options.STARS, GameSettings.Options.SUN_MOON, GameSettings.Options.SHOW_CAPES, GameSettings.Options.DEPTH_FOG, GameSettings.Options.HELD_ITEM_TOOLTIPS, GameSettings.Options.TRANSLUCENT_BLOCKS, GameSettings.Options.DROPPED_ITEMS };
/*  23: 18 */   private int lastMouseX = 0;
/*  24: 19 */   private int lastMouseY = 0;
/*  25: 20 */   private long mouseStillTime = 0L;
/*  26:    */   
/*  27:    */   public GuiDetailSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
/*  28:    */   {
/*  29: 24 */     this.prevScreen = guiscreen;
/*  30: 25 */     this.settings = gamesettings;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void initGui()
/*  34:    */   {
/*  35: 33 */     int i = 0;
/*  36: 34 */     GameSettings.Options[] aenumoptions = enumOptions;
/*  37: 35 */     int j = aenumoptions.length;
/*  38: 37 */     for (int k = 0; k < j; k++)
/*  39:    */     {
/*  40: 39 */       GameSettings.Options enumoptions = aenumoptions[k];
/*  41: 40 */       int x = width / 2 - 155 + i % 2 * 160;
/*  42: 41 */       int y = height / 6 + 21 * (i / 2) - 10;
/*  43: 43 */       if (!enumoptions.getEnumFloat()) {
/*  44: 45 */         this.buttonList.add(new GuiOptionButton(enumoptions.returnEnumOrdinal(), x, y, enumoptions, this.settings.getKeyBinding(enumoptions)));
/*  45:    */       } else {
/*  46: 49 */         this.buttonList.add(new GuiOptionSlider(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
/*  47:    */       }
/*  48: 52 */       i++;
/*  49:    */     }
/*  50: 55 */     this.buttonList.add(new NodusGuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected void actionPerformed(NodusGuiButton guibutton)
/*  54:    */   {
/*  55: 60 */     if (guibutton.enabled)
/*  56:    */     {
/*  57: 62 */       if ((guibutton.id < 100) && ((guibutton instanceof GuiOptionButton)))
/*  58:    */       {
/*  59: 64 */         this.settings.setOptionValue(((GuiOptionButton)guibutton).func_146136_c(), 1);
/*  60: 65 */         guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
/*  61:    */       }
/*  62: 68 */       if (guibutton.id == 200)
/*  63:    */       {
/*  64: 70 */         this.mc.gameSettings.saveOptions();
/*  65: 71 */         this.mc.displayGuiScreen(this.prevScreen);
/*  66:    */       }
/*  67: 74 */       if (guibutton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal())
/*  68:    */       {
/*  69: 76 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/*  70: 77 */         int i = scaledresolution.getScaledWidth();
/*  71: 78 */         int j = scaledresolution.getScaledHeight();
/*  72: 79 */         setWorldAndResolution(this.mc, i, j);
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void drawScreen(int x, int y, float f)
/*  78:    */   {
/*  79: 89 */     drawDefaultBackground();
/*  80: 90 */     drawCenteredString(this.fontRendererObj, this.title, width / 2, 20, 16777215);
/*  81: 91 */     super.drawScreen(x, y, f);
/*  82: 93 */     if ((Math.abs(x - this.lastMouseX) <= 5) && (Math.abs(y - this.lastMouseY) <= 5))
/*  83:    */     {
/*  84: 95 */       short activateDelay = 700;
/*  85: 97 */       if (System.currentTimeMillis() >= this.mouseStillTime + activateDelay)
/*  86:    */       {
/*  87: 99 */         int x1 = width / 2 - 150;
/*  88:100 */         int y1 = height / 6 - 5;
/*  89:102 */         if (y <= y1 + 98) {
/*  90:104 */           y1 += 105;
/*  91:    */         }
/*  92:107 */         int x2 = x1 + 150 + 150;
/*  93:108 */         int y2 = y1 + 84 + 10;
/*  94:109 */         NodusGuiButton btn = getSelectedButton(x, y);
/*  95:111 */         if (btn != null)
/*  96:    */         {
/*  97:113 */           String s = getButtonName(btn.displayString);
/*  98:114 */           String[] lines = getTooltipLines(s);
/*  99:116 */           if (lines == null) {
/* 100:118 */             return;
/* 101:    */           }
/* 102:121 */           drawGradientRect(x1, y1, x2, y2, -536870912, -536870912);
/* 103:123 */           for (int i = 0; i < lines.length; i++)
/* 104:    */           {
/* 105:125 */             String line = lines[i];
/* 106:126 */             this.fontRendererObj.drawStringWithShadow(line, x1 + 5, y1 + 5 + i * 11, 14540253);
/* 107:    */           }
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:133 */       this.lastMouseX = x;
/* 114:134 */       this.lastMouseY = y;
/* 115:135 */       this.mouseStillTime = System.currentTimeMillis();
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private String[] getTooltipLines(String btnName)
/* 120:    */   {
/* 121:141 */     return btnName.equals("Translucent Blocks") ? new String[] { "Translucent Blocks", "  Fancy - correct color blending (default)", "  Fast - fast color blending (faster)", "Controls the color blending of translucent blocks", "with different color (stained glass, water, ice)", "when placed behind each other with air between them." } : btnName.equals("Held Item Tooltips") ? new String[] { "Held item tooltips", "  ON - show tooltips for held items (default)", "  OFF - do not show tooltips for held items" } : btnName.equals("Show Capes") ? new String[] { "Show Capes", "  ON - show player capes (default)", "  OFF - do not show player capes" } : btnName.equals("Depth Fog") ? new String[] { "Depth Fog", "  ON - fog moves closer at bedrock levels (default)", "  OFF - same fog at all levels" } : btnName.equals("Stars") ? new String[] { "Stars", "  ON - stars are visible, slower", "  OFF  - stars are not visible, faster" } : btnName.equals("Sun & Moon") ? new String[] { "Sun & Moon", "  ON - sun and moon are visible (default)", "  OFF  - sun and moon are not visible (faster)" } : btnName.equals("Sky") ? new String[] { "Sky", "  ON - sky is visible, slower", "  OFF  - sky is not visible, faster", "When sky is OFF the moon and sun are still visible." } : btnName.equals("Rain & Snow") ? new String[] { "Rain & Snow", "  Default - as set by setting Graphics", "  Fast  - light rain/snow, faster", "  Fancy - heavy rain/snow, slower", "  OFF - no rain/snow, fastest", "When rain is OFF the splashes and rain sounds", "are still active." } : btnName.equals("Water") ? new String[] { "Water", "  Default - as set by setting Graphics", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Fast water (1 pass) has some visual artifacts", "Fancy water (2 pass) has no visual artifacts" } : btnName.equals("Dropped Items") ? new String[] { "Dropped Items", "  Default - as set by setting Graphics", "  Fast - 2D dropped items, faster", "  Fancy - 3D dropped items, slower" } : btnName.equals("Grass") ? new String[] { "Grass", "  Default - as set by setting Graphics", "  Fast - lower quality, faster", "  Fancy - higher quality, slower", "Fast grass uses default side texture.", "Fancy grass uses biome side texture." } : btnName.equals("Trees") ? new String[] { "Trees", "  Default - as set by setting Graphics", "  Fast - lower quality, faster", "  Fancy - higher quality, slower", "Fast trees have opaque leaves.", "Fancy trees have transparent leaves." } : btnName.equals("Cloud Height") ? new String[] { "Cloud Height", "  OFF - default height", "  100% - above world height limit" } : btnName.equals("Clouds") ? new String[] { "Clouds", "  Default - as set by setting Graphics", "  Fast - lower quality, faster", "  Fancy - higher quality, slower", "  OFF - no clouds, fastest", "Fast clouds are rendered 2D.", "Fancy clouds are rendered 3D." } : null;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private String getButtonName(String displayString)
/* 125:    */   {
/* 126:146 */     int pos = displayString.indexOf(':');
/* 127:147 */     return pos < 0 ? displayString : displayString.substring(0, pos);
/* 128:    */   }
/* 129:    */   
/* 130:    */   private NodusGuiButton getSelectedButton(int i, int j)
/* 131:    */   {
/* 132:152 */     for (int k = 0; k < this.buttonList.size(); k++)
/* 133:    */     {
/* 134:154 */       NodusGuiButton btn = (NodusGuiButton)this.buttonList.get(k);
/* 135:155 */       int btnWidth = GuiVideoSettings.getButtonWidth(btn);
/* 136:156 */       int btnHeight = GuiVideoSettings.getButtonHeight(btn);
/* 137:157 */       boolean flag = (i >= btn.xPosition) && (j >= btn.yPosition) && (i < btn.xPosition + btnWidth) && (j < btn.yPosition + btnHeight);
/* 138:159 */       if (flag) {
/* 139:161 */         return btn;
/* 140:    */       }
/* 141:    */     }
/* 142:165 */     return null;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.GuiDetailSettingsOF
 * JD-Core Version:    0.7.0.1
 */